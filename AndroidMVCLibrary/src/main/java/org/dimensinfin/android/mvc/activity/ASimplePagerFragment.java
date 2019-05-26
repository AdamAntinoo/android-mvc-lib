package org.dimensinfin.android.mvc.activity;

import java.util.List;
import java.util.concurrent.TimeUnit;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.dimensinfin.android.mvc.R;
import org.dimensinfin.android.mvc.controller.AAndroidController;
import org.dimensinfin.android.mvc.controller.IAndroidController;
import org.dimensinfin.android.mvc.core.AppCompatibilityUtils;
import org.dimensinfin.android.mvc.exception.MVCExceptionHandler;
import org.dimensinfin.android.mvc.exception.ToastExceptionHandler;
import org.dimensinfin.android.mvc.datasource.DataSourceManager;
import org.dimensinfin.android.mvc.datasource.IDataSource;
import org.dimensinfin.android.mvc.interfaces.IMenuActionTarget;
import org.dimensinfin.android.mvc.interfaces.IRender;

import org.joda.time.Instant;
import org.joda.time.format.DateTimeFormatterBuilder;

public abstract class ASimplePagerFragment extends AMVCFragment {
//	/**
//	 * Task handler to manage execution of code that should be done on the main loop thread.
//	 */
//	protected static final Handler _handler = new Handler(Looper.getMainLooper());

	// - F I E L D - S E C T I O N
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

	protected ViewGroup _mapContainer = null;
	private MapContentAdapter _adapter = null;


	// - G E T T E R S   &   S E T T E R S

	// - L I F E C Y C L E
	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
		logger.info(">> [ASimplePagerFragment.onCreateView]");
		// Install the default library exception interceptor to show lib exceptions.
		Thread.setDefaultUncaughtExceptionHandler(new MVCExceptionHandler(this.getActivityContext()));
		super.onCreateView(inflater, container, savedInstanceState);
		// - S E C T I O N   1. Where we get access to the UI elements.
		_container = (ViewGroup) inflater.inflate(R.layout.fragment_map, container, false);
		_headerContainer = AppCompatibilityUtils.assertNotNull(_container.findViewById(R.id.headerContainer));
		_mapContainer = AppCompatibilityUtils.assertNotNull(_container.findViewById(R.id.mapCanvas));
		_headerContainer.setVisibility(View.VISIBLE);
		_mapContainer.setVisibility(View.VISIBLE);

		// - S E C T I O N   2. Where we setup the data sources for the adapters. Only include no timing operations.
		final IDataSource ds = DataSourceManager.registerDataSource(this.createDS());
		_adapter = new MapContentAdapter(this, ds, _mapContainer);

		// - S E C T I O N   3. Post the tak to generate the header contents to be rendered.
		AppCompatibilityUtils.backgroundExecutor.submit(() -> {
			AMVCFragment.logger.info("-- [ASimplePagerFragment.DS Initialisation]");
			_adapter.collaborateData(); // Call the ds to generate the root contents.
		});
		// Generate the views and set them into the containers, header and map for rendering.
		AppCompatibilityUtils.backgroundExecutor.submit(() -> {
			AMVCFragment.logger.info("-- [ASimplePagerFragment.Render Header section]");
			this.generateHeaderContents(ds.getHeaderSectionContents());
			AMVCFragment.logger.info("-- [ASimplePagerFragment.Render Map section]");
			_adapter.notifyDataSetChanged();
		});

		AMVCFragment.logger.info("<< [ASimplePagerFragment.onCreateView]");
		return _container;
	}

	@Override
	public void onStart() {
		AMVCFragment.logger.info(">> [ASimplePagerFragment.onStart]");
		super.onStart();
		Thread.setDefaultUncaughtExceptionHandler(new ToastExceptionHandler(this.getActivityContext()));
		AMVCFragment.logger.info("<< [ASimplePagerFragment.onStart]");
	}

	@Override
	public void onViewStateRestored(Bundle savedInstanceState) {
		// restore the variant name.
		if (null != savedInstanceState)
			setVariant(savedInstanceState.getString(AMVCMultiPageActivity.EMVCExtras.EXTRA_VARIANT.name()));
		super.onViewStateRestored(savedInstanceState);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
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
	protected void generateHeaderContents(final List<IAndroidController> headerControllers) {
		AMVCFragment.logger.info(">> [AMVCPagerFragment.generateHeaderContents]");
		// Create the list of controllers from the model list received.
		//        final List<IAndroidController> rootControllers = new ArrayList<>(headerData.size());
		//        for (ICollaboration modelNode : headerData) {
		//            final IAndroidController newController = this._factory.createController(modelNode);
		//            newController.refreshChildren();
		//            rootControllers.add(newController);
		//        }

		// Compose the final list from the controllers collaborating to the view.
		//        final List<IAndroidController> controllers = new ArrayList<>();
		//        for (IAndroidController controller : headerControllers) {
		//            controller.collaborate2View(controllers);
		//        }
		// Now create the view and add it to the header list.
		handler.post(() -> {
			_headerContainer.removeAllViews();
			for (IAndroidController part : headerControllers) {
				if (part instanceof IAndroidController) addView2Header(part);
			}
		});
		AMVCFragment.logger.info("<< [AMVCPagerFragment.generateHeaderContents]");
	}

	/**
	 * This method extracts the view from the parameter controller and generates the final View element that it is able to
	 * be inserted on the ui ViewGroup container.
	 *
	 * @param target the AndroidController to render to a View.
	 */
	private void addView2Header(final IAndroidController target) {
		logger.info(">> [AMVCPagerFragment.addView2Header]");
//		try {
			final IRender holder = target.buildRender(this.getActivityContext());
			// TODO. This holder does not call the initializeViews of the view before the update.
			holder.updateContent();
			final View hv = holder.getView();
			_headerContainer.addView(hv);
			// Add the connection to the click listener
			if (target instanceof View.OnClickListener) {
				hv.setClickable(true);
				hv.setOnClickListener((View.OnClickListener) target);
			}
			_headerContainer.setVisibility(View.VISIBLE);
//		} catch (final RuntimeException rtex) {
//			logger.info("RTEX [AMVCPagerFragment.addView2Header]> Problem generating view for: {}", target.getClass().getCanonicalName());
//			logger.info("RTEX [AMVCPagerFragment.addView2Header]> RuntimeException. {}", rtex.getMessage());
//			rtex.printStackTrace();
//			Toast.makeText(this.getActivityContext()
//					, "RTEX [AMVCPagerFragment.addView2Header]> RuntimeException. " + rtex.getMessage()
//					, Toast.LENGTH_LONG).show();
//		}
		logger.info("<< [AMVCPagerFragment.addView2Header]");
	}

	// - CONTEXTUAL MENU FOR THE HEADER
	@Override
	public boolean onContextItemSelected(final MenuItem item) {
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
	public void onCreateContextMenu(final ContextMenu menu, final View view, final ContextMenu.ContextMenuInfo menuInfo) {
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
	private void initializeProgressIndicator() {
		_progressElapsedCounter = AppCompatibilityUtils.assertNotNull(_container.findViewById(org.dimensinfin.android.mvc.R.id.progressCounter));
		final Instant _elapsedTimer = Instant.now();
		new CountDownTimer(TimeUnit.DAYS.toMillis(1), TimeUnit.MILLISECONDS.toMillis(10)) {
			@Override
			public void onFinish() {
				_progressElapsedCounter.setText(generateTimeString(_elapsedTimer.getMillis()));
				_progressElapsedCounter.invalidate();
			}

			@Override
			public void onTick(final long millisUntilFinished) {
				_progressElapsedCounter.setText(generateTimeString(_elapsedTimer.getMillis()));
				_progressElapsedCounter.invalidate();
			}
		}.start();
	}

	private String generateTimeString(final long millis) {
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
