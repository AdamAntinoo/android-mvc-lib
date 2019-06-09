package org.dimensinfin.android.mvc.activity;

import java.util.List;
import java.util.concurrent.TimeUnit;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.dimensinfin.android.mvc.R;
import org.dimensinfin.android.mvc.controller.AAndroidController;
import org.dimensinfin.android.mvc.controller.IAndroidController;
import org.dimensinfin.android.mvc.core.AppCompatibilityUtils;
import org.dimensinfin.android.mvc.datasource.DataSourceAdapter;
import org.dimensinfin.android.mvc.datasource.DataSourceManager;
import org.dimensinfin.android.mvc.datasource.IDataSource;
import org.dimensinfin.android.mvc.exception.MVCExceptionHandler;
import org.dimensinfin.android.mvc.exception.ToastExceptionHandler;
import org.dimensinfin.android.mvc.interfaces.IMenuActionTarget;
import org.dimensinfin.android.mvc.interfaces.IRender;

import org.joda.time.Instant;
import org.joda.time.format.DateTimeFormatterBuilder;

/**
 * @author Adam Antinoo
 * @since 1.0.0
 */
public abstract class AMVCPagerFragment extends AMVCFragment {
	/**
	 * Evolved adapter to connect the source of data in the form of a <b>AndroidController</b> list to the
	 * <code>ListView</code> that contains the displayed render.
	 */
	private DataSourceAdapter _adapter = null;

	// - U I    F I E L D S
	/**
	 * This is the reference to the root view generated by the inflation of the fragment's layout.
	 */
	protected ViewGroup _container = null;
	/**
	 * The view that handles the non scrolling header. It accepts a list of Views but has no scroll capabilities.
	 */
	protected ViewGroup _headerContainer = null;
	/**
	 * The view that represent the list view and the space managed though the adapter.
	 */
	private ListView _dataSectionContainer = null;
	/**
	 * The UI graphical element that defines the loading progress spinner layout.
	 */
	private ViewGroup _progressLayout = null;
	/**
	 * This is a text field defined inside the loading progress spinner that will show the time elapsed waiting for the
	 * completion of the loading process.
	 */
	private TextView _progressElapsedCounter = null;

	// - F R A G M E N T   L I F E C Y C L E

	/**
	 * During the creation process we connect the local fields to the UI graphical objects defined by the layout. We use a
	 * generic layout that defines the two items that compose all the displays for the MVC fragments. The <b>Header</b>
	 * ViewGroup container, the <b>Data Section</b> implemented by a ListView and the loading progress indicator that will
	 * be present on the DataSection display are while the model is generated and transformed into the View list. At that
	 * point the progress will be removed to show the view list for this time instant model.
	 *
	 * The method has two sections. The first section will find and reference the ui graphical elements where to render
	 * the data while the second section will instantiate and initialize the application specific code to generate the
	 * models for this fragment instance.
	 *
	 * @param inflater           <code>LayoutInflater</code> received from the context to create the layout from the XML
	 *                           definition file.
	 * @param container          container where this layout is displayed.
	 * @param savedInstanceState if we are recovering the layout from a task switch this is the previous saved state of
	 *                           the fragment when the application was switched out of focus.
	 * @return the view that represents the fragment ui layout structure.
	 */
	@Override
	public View onCreateView( final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState ) {
		logger.info(">> [AMVCPagerFragment.onCreateView]");
		// Install the default library exception interceptor to show lib exceptions.
		Thread.setDefaultUncaughtExceptionHandler(new MVCExceptionHandler(this.getActivity()));
		super.onCreateView(inflater, container, savedInstanceState);
		// TODO analyze what is returned by the savedInstanceState when recovering the application. That will help to recover the
		// functional state of the application.
		// - S E C T I O N   1. Where we get access to the UI elements.
		_container = (ViewGroup) inflater.inflate(R.layout.fragment_base, container, false);
		_headerContainer = AppCompatibilityUtils.assertNotNull(_container.findViewById(R.id.headerContainer));
		_dataSectionContainer = AppCompatibilityUtils.assertNotNull(_container.findViewById(R.id.listContainer));
		_progressLayout = AppCompatibilityUtils.assertNotNull(_container.findViewById(R.id.progressLayout));
		_progressElapsedCounter = AppCompatibilityUtils.assertNotNull(_container.findViewById(R.id.progressCounter));

		// Set the visual state of all items.
		_progressLayout.setVisibility(View.VISIBLE);
		_dataSectionContainer.setVisibility(View.VISIBLE);
		_progressElapsedCounter.setVisibility(View.VISIBLE);
		// Prepare the structures for the context menu.
		// TODO Check if the menus can be tied to the Parts independently and not to the whole Header.
		//			this.registerForContextMenu(_headerContainer);
		this.registerForContextMenu(_dataSectionContainer);

		// - S E C T I O N   2. Where we setup the data sources for the adapters. Only include no timing operations.
		// Install the adapter before any data request or model generation.
		final IDataSource ds = DataSourceManager.registerDataSource(this.createDS());
		_adapter = new DataSourceAdapter(this, ds);
		_dataSectionContainer.setAdapter(_adapter);

		// - S E C T I O N   3. Post the tak to generate the header contents to be rendered.
		AppCompatibilityUtils.backgroundExecutor.submit(() -> {
			logger.info("-- [AMVCPagerFragment.DS Initialisation]");
			_adapter.collaborateData(); // Call the ds to generate the root contents.
			this.generateHeaderContents(ds.getHeaderSectionContents());
		});

		logger.info("<< [AMVCPagerFragment.onCreateView]");
		return _container;
	}

	/**
	 * At this point on the Fragment life cycle we are sure that the fragment is already constructed and that the flow is
	 * ready to get and process the model data. The model data is going to be feed directly to the rendering layout while
	 * it is being generated so the experience is more close to real time data presentation. INstead waiting for all the
	 * model generation and model transformation processed to complete we will be streaming the data since the first
	 * moment we have something to show.
	 */
	@Override
	public void onStart() {
		logger.info(">> [AMVCPagerFragment.onStart]");
		super.onStart();
		Thread.setDefaultUncaughtExceptionHandler(new ToastExceptionHandler(this.getActivityContext()));
		// Start counting the elapsed time while we generate and load the  model.
		this.initializeProgressIndicator();
		// We use another thread to perform the data source generation that is a long time action.
		AppCompatibilityUtils.backgroundExecutor.submit(() -> {
			logger.info("-- [AMVCPagerFragment.Render data section]");
			//            _adapter.collaborateData(); // Call the ds to generate the root contents.
			handler.post(() -> { // After the model is created used the UI thread to render the collaboration to view.
				//                _adapter.collaborateData(); // Call the ds to generate the root contents.
				_adapter.notifyDataSetChanged();
				this.hideProgressIndicator(); // Hide the waiting indicator after the model is generated and the view populated.
			});
		});
		logger.info("<< [AMVCPagerFragment.onStart]");
	}

	private void hideProgressIndicator() {
		_progressLayout.setVisibility(View.GONE);
		_dataSectionContainer.setVisibility(View.VISIBLE);
		_progressElapsedCounter.setVisibility(View.GONE);
	}

	@Override
	public void onViewStateRestored( Bundle savedInstanceState ) {
		// restore the variant name.
		if (null != savedInstanceState)
			setVariant(savedInstanceState.getString(AMVCMultiPageActivity.EMVCExtras.EXTRA_VARIANT.name()));
		super.onViewStateRestored(savedInstanceState);
	}

	@Override
	public void onSaveInstanceState( Bundle outState ) {
		super.onSaveInstanceState(outState);
		// Save the variant assigned to this fragment instance.
		outState.putString(AMVCMultiPageActivity.EMVCExtras.EXTRA_VARIANT.name(), getVariant());
	}

	// - H E A D E R   M A N A G E M E N T   S E C T I O N

	/**
	 * This method is the way to transform the list of model data prepared for the Header to end on a list of Views inside
	 * the Header container. We follow a similar mechanics that for the DataSection ListView but instead keeping the
	 * intermediate AndroidController instances we go directly to the View output by the <b>Render</b> instance.
	 * <p>
	 * The use of a fake <code>@link{MVCModelRootNode}</code> allows to also support model elements that have contents
	 * that should be rendered when expanded. Even the header contents are limited in interaction we can have the
	 * expand/collapse functionality to calculate the final list of Views to render.
	 */
	protected void generateHeaderContents( final List<IAndroidController> headerControllers ) {
		logger.info(">> [AMVCPagerFragment.generateHeaderContents]");
		handler.post(() -> {
			_headerContainer.removeAllViews();
			for (IAndroidController part : headerControllers) {
				if (part instanceof IAndroidController) addView2Header(part);
			}
		});
		logger.info("<< [AMVCPagerFragment.generateHeaderContents]");
	}

	/**
	 * This method extracts the view from the parameter controller and generates the final View element that it is able to
	 * be inserted on the ui ViewGroup container.
	 *
	 * @param target the AndroidController to render to a View.
	 */
	private void addView2Header( final IAndroidController target ) {
		logger.info(">> [AMVCPagerFragment.addView2Header]");
		try {
			final IRender holder = target.buildRender(this.getActivityContext());
			final View hv = holder.getView();
			holder.updateContent();
			_headerContainer.addView(hv);
			// Add the connection to the click listener
			if (target instanceof OnClickListener) {
				hv.setClickable(true);
				hv.setOnClickListener((OnClickListener) target);
			}
			_headerContainer.setVisibility(View.VISIBLE);
		} catch (final RuntimeException rtex) {
			logger.info("RTEX [AMVCPagerFragment.addView2Header]> Problem generating view for: {}", target.getClass().getCanonicalName());
			logger.info("RTEX [AMVCPagerFragment.addView2Header]> RuntimeException. {}", rtex.getMessage());
			rtex.printStackTrace();
			Toast.makeText(this.getActivityContext()
					, "RTEX [AMVCPagerFragment.addView2Header]> RuntimeException. " + rtex.getMessage()
					, Toast.LENGTH_LONG).show();
		}
		logger.info("<< [AMVCPagerFragment.addView2Header]");
	}

	// - CONTEXTUAL MENU FOR THE HEADER
	@Override
	public boolean onContextItemSelected( final MenuItem item ) {
		logger.info(">> ManufactureContextFragment.onContextItemSelected");
		final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
		final int menuItemIndex = item.getItemId();
		final AAndroidController part = (AAndroidController) info.targetView.getTag();
		if (part instanceof IMenuActionTarget)
			return ((IMenuActionTarget) part).onContextItemSelected(item);
		else
			return true;
	}

	@Override
	public void onCreateContextMenu( final ContextMenu menu, final View view, final ContextMenu.ContextMenuInfo menuInfo ) {
		logger.info(">> [AMVCPagerFragment.onCreateContextMenu]");
		// REFACTOR If we call the super then the fragment's parent context gets called. So the listcallback and the Activity
		// have not to be the same
		//			super.onCreateContextMenu(menu, view, menuInfo);
		// Check parameters to detect the item selected for menu target.
		//		if (view == _headerContainer) {
		//			// Check if this fragment has the callback configured
		//			final IAndroidAndroidController part = _headerContents.firstElement();
		//			if (part instanceof IMenuActionTarget) {
		//				((IMenuActionTarget) part).onCreateContextMenu(menu, view, menuInfo);
		//			}
		//		}
		//		if (view == _dataSectionContainer) {
		// Now header and data section ave the same functionality.
		// Get the tag assigned to the selected view and if implements the callback interface send it the message.
		final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
		// Check if the selected item is suitable for menu and select it depending on item part class.
		AAndroidController part = (AAndroidController) info.targetView.getTag();
		if (part instanceof IMenuActionTarget) {
			((IMenuActionTarget) part).onCreateContextMenu(menu, view, menuInfo);
		}
		//		}
		logger.info("<< [AMVCPagerFragment.onCreateContextMenu]"); //$NON-NLS-1$
	}

	// - U T I L I T I E S
	//    private <T> T assertNotNull(final T target) {
	//        assert (target != null);
	//        return target;
	//    }

	private void initializeProgressIndicator() {
		_progressElapsedCounter = AppCompatibilityUtils.assertNotNull(_container.findViewById(R.id.progressCounter));
		final Instant _elapsedTimer = Instant.now();
		new CountDownTimer(TimeUnit.DAYS.toMillis(1), TimeUnit.MILLISECONDS.toMillis(10)) {
			@Override
			public void onFinish() {
				_progressElapsedCounter.setText(generateTimeString(_elapsedTimer.getMillis()));
				_progressElapsedCounter.invalidate();
			}

			@Override
			public void onTick( final long millisUntilFinished ) {
				_progressElapsedCounter.setText(generateTimeString(_elapsedTimer.getMillis()));
				_progressElapsedCounter.invalidate();
			}
		}.start();
	}

	private String generateTimeString( final long millis ) {
		try {
			final long elapsed = Instant.now().getMillis() - millis;
			final DateTimeFormatterBuilder timeFormatter = new DateTimeFormatterBuilder();
			if (elapsed > TimeUnit.HOURS.toMillis(1)) {
				timeFormatter.appendHourOfDay(2).appendLiteral("h ");
			}
			if (elapsed > TimeUnit.MINUTES.toMillis(1)) {
				timeFormatter.appendMinuteOfHour(2).appendLiteral("m ").appendSecondOfMinute(2).appendLiteral("s");
			} else timeFormatter.appendSecondOfMinute(2).appendLiteral("s");
			return timeFormatter.toFormatter().print(new Instant(elapsed));
		} catch (final RuntimeException rtex) {
			return "0m 00s";
		}
	}
}
