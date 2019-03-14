package org.dimensinfin.android.mvc.datasource;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import org.dimensinfin.android.mvc.R;
import org.dimensinfin.android.mvc.activity.AbstractPagerFragment;
import org.dimensinfin.android.mvc.core.EEvents;
import org.dimensinfin.android.mvc.interfaces.IAndroidController;
import org.dimensinfin.android.mvc.interfaces.IDataSource;
import org.dimensinfin.android.mvc.interfaces.IRender;
import org.joda.time.Instant;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the class that connects the ListView to a model list. If is an extension of the generic BaseAdapter and
 * implements the methods to convert lists of Parts to a list of Renders to the linked to the View elements to be used
 * by the ViewList.<br> The model connection is performed through the DataSource instance that also gets connected to
 * the Event Listener chain.
 *
 * We can use the Activity as the Context to be used on view creation since the lifetime for this Adapter should be the
 * same as for the Activity and when the context terminates the system should be able to recover the adapters and all
 * the views. But references for this Context should not live outside this instance to remove the problem to lock
 * references on uncontrolled places.
 * @author Adam Antinoo
 */
public class DataSourceAdapter extends BaseAdapter implements PropertyChangeListener {
	// - S T A T I C - S E C T I O N
	private static Logger logger = LoggerFactory.getLogger(DataSourceAdapter.class);
	private static final boolean LOG_ALLOWED = true;
	private static final String GETTING_VIEW = "-- [DataSourceAdapter.getView]> Getting view [";

	// - F I E L D - S E C T I O N
	/** The Activity where all this structures belong and that is used as the core display context. */
	private Context context = null;
	/** An instance for a source of data that will provide the list of <b>Parts</b> to be used to construct the Views. */
	private IDataSource datasource = null;
	/** The current list of Parts that is being displayed. */
	private final List<IAndroidController> contentControllerList = new ArrayList<>();

	// - C O N S T R U C T O R - S E C T I O N

	/** Neutral creator for the initialization of the parent BaseAdapter. */
	public DataSourceAdapter() {
		super();
	}

	/**
	 * During the creation of the Adapter we setup the Fragment or the Activity that contains the layout with the ListView
	 * to render the Model and the DataSource with the list of Controllers to be rendered there. To make this Adapter
	 * compatible with Fragment and Activities we should have two constructors and consolidate the reference to the
	 * Context that is the only element really required for the constructions and connection of the views.
	 * @param fragment   The fragment source for this adapter contents.
	 * @param datasource the source for the data to be represented on the view structures.
	 */
	public DataSourceAdapter(@NonNull final AbstractPagerFragment fragment, @NonNull final IDataSource datasource) {
		this.context = fragment.getAppContext();
		this.datasource = datasource;
		this.datasource.addPropertyChangeListener(this); // Connect the listener to the data source events.
	}

	// - M E T H O D - S E C T I O N
	public void load (){
		if ( null != this.datasource)this.datasource.collaborate2Model();
	}
	// - B A S E   A D A P T E R   I M P L E M E N T A T I O N
	@Override
	public Object getItem(final int position) {
		return contentControllerList.get(position);
	}
	@Override
	public int getCount() {
		return contentControllerList.size();
	}

	@Override
	public long getItemId(final int position) {
		return contentControllerList.get(position).getModelId();
	}

	@Override
	public boolean areAllItemsEnabled() {
		return true;
	}

	@Override
	public boolean isEnabled(int position) {
		return true;
	}

	@Override
	public int getItemViewType(int position) {
		return 0;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	/**
	 * This requires the number of different views we have on the list. This really can be calculated by running over the
	 * list of controllers and getting the unique list of their render types. This optimization is not already implemented
	 * but added to the list of features to be added.
	 * @return the number of different views. Forced to be 1 because it is not being calculated.
	 */
	public int getViewTypeCount() {
		return 1;
	}

	/**
	 * This method is called so many times that represent the most consuming tasks on the Activity. The optimization to
	 * not create more views than the needed ones and the reduction of code line is s must that will improve user response
	 * times.
	 */
	//	@SuppressLint("ViewHolder")
	public View getView(final int position, View convertView, final ViewGroup parent) {
		final Instant chrono = Instant.now();
		String exitMessage;
		try {
			// If the request is new we are sure this has to be created.
			IAndroidController item = this.getCastedItem(position);
			if (null == convertView) {
				exitMessage = GETTING_VIEW + position + "] - " + item.getClass().getSimpleName();
				// At this point we have access to the Context. Create the view, initialize the fields and connect it to the controller.
				convertView = this.constructRender(context, item);
			} else {
				View cachedView = item.getViewCache();
				if (null == cachedView) {
					exitMessage = GETTING_VIEW + position + "] - "
							+ item.getClass().getSimpleName() + " RECREATE";
					// Recreate the view.
					convertView = this.constructRender(context, item);
				} else {
					// Cached view found. Return new view.
					convertView = cachedView;
					exitMessage = GETTING_VIEW + position + "] - "
							+ item.getClass().getSimpleName() + " CACHED";
				}
			}
			// Activate listeners if the AndroidController supports that feature.
			convertView.setClickable(false);
			convertView.setLongClickable(true);
			if (item instanceof OnClickListener) {
				convertView.setClickable(true);
				convertView.setOnClickListener((OnClickListener) item);
			}
			if (item instanceof OnLongClickListener) {
				convertView.setClickable(true);
				convertView.setOnLongClickListener((OnLongClickListener) item);
			}
			// REFACTOR Add the DataSource as an event listener because that feature does not depend on the interfaces.
			item.addPropertyChangeListener(datasource);
			if (LOG_ALLOWED) {
				// Filter out the spinner.
				if (!exitMessage.contains("OnLoadSpinnerController")) {
					final Period elapsedPeriod = new Period(chrono, Instant.now(), PeriodType.millis());
					final String elapsedMs = MessageFormat.format("{0,number}ms", elapsedPeriod.getMillis());
					logger.info("{} - Rendering time: {}", exitMessage, elapsedMs);
				}
			}
		} catch (RuntimeException rtex) {
			String message = rtex.getMessage();
			if (null == message) {
				message = "NullPointerException detected.";
			}
			DataSourceAdapter.logger.error("RTEX [DataSourceAdapter.getView]> Runtime Exception: {}", message);
			rtex.printStackTrace();
			//DEBUG Add exception registration to the exception page.
			final LayoutInflater mInflater = (LayoutInflater) this.getContext()
					.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			// Under an exception we can replace the View item by this special layout with the Exception message.
			convertView = mInflater.inflate(R.layout.exception4list, parent);
			TextView exceptionMessage = convertView.findViewById(R.id.exceptionMessage);
			exceptionMessage.setText(new StringBuilder("[DataSourceAdapter.getView]> RTEX > {}").append(message).toString());
		}
		return convertView;
	}

	private View constructRender(final Context context, final IAndroidController controller) {
		IRender render = controller.buildRender(context);
		final View view = render.getView();
		view.setTag(controller); // Piggyback the controller to the view to allow access.
		render.initializeViews(); // Associate the fields to variables.
		render.updateContent(); // Set the initial value for the view fields.
		// Store view on the AndroidController for cache.
//		if (this.getContext().getResources().getBoolean(R.id.exceptionMessage)) {
		if (true) {
			controller.setViewCache(view);
		}
		return view;
	}

	private Context getContext() {
		return context;
	}
	private IAndroidController getCastedItem(final int position) {
		return contentControllerList.get(position);
	}

	/**
	 * This method should be called when the model transformation has changed. The model may be the same but the
	 * instantiation of the elements that should be rendered may have changed so the model should be run again and a new
	 * list part should be generated to be adapted to the viewer contents.
	 *
	 * This action should trigger the reconstruction of the view list starting from the data source stored data. Most of
	 * the already generated controllers should already exist and have their generated views so most of the data on
	 * repetitive calls is already present and cached. Only when the trigger activated new controllers or it is created by
	 * a new data event then the controllers may change and the views should be regenerated for new items.
	 *
	 * If an element has changed it is supposed that its view was destroyed so on the next iteration to render it should
	 * be recreated again..
	 */
	@Override
	public void notifyDataSetChanged() {
		contentControllerList.clear();
		contentControllerList.addAll(datasource.getDataSectionContents());
		super.notifyDataSetChanged();
	}

	// - P R O P E R T Y C H A N G E L I S T E N E R   I N T E R F A C E

	/**
	 * Send messages to the parent context that is the one that has code implemented for every different case. This class
	 * is a generic class that must not be upgraded because we start then to replicate most of the code.
	 */
	public void propertyChange(final PropertyChangeEvent event) {
		// Be sure to run graphical changes on the UI thread. If we already are on it this has no effect.
		((Activity) this.getContext()).runOnUiThread(() -> {
			if (EEvents.valueOf(event.getPropertyName()) ==
					EEvents.EVENTADAPTER_REQUESTNOTIFYCHANGES) {
				this.notifyDataSetChanged();
			}
		});
	}
}
