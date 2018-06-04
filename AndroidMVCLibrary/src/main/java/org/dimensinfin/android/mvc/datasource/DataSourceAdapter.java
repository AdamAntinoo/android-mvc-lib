//  PROJECT:     Android.MVC (A.MVC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Android API16.
//  DESCRIPTION: Library that defines a generic Model View Controller core classes to be used
//               on Android projects. Defines the Part factory and the Part core methods to manage
//               a generic converter from a Graph Model to a hierarchical Part model that finally will
//               be converted to a Part list to be used on a BaseAdapter tied to a ListView.
//               The new implementation performs the model to list transformation on the fly each time
//               a model change is detected so the population of the displayed view should be done in
//               real time while processing the model sources. This should allow for search and filtering.
package org.dimensinfin.android.mvc.datasource;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.dimensinfin.android.mvc.R;
import org.dimensinfin.android.mvc.activity.AbstractPagerFragment;
import org.dimensinfin.android.mvc.constants.SystemWideConstants;
import org.dimensinfin.android.mvc.core.AbstractRender;
import org.dimensinfin.android.mvc.interfaces.IAndroidPart;
import org.dimensinfin.android.mvc.interfaces.IDataSource;
import org.dimensinfin.core.util.Chrono;

// - CLASS IMPLEMENTATION ...................................................................................

/**
 * This is the class that connects the ListView to a model list. If is an extension of the generic BaseAdapter
 * and implements the methods to convert lists of Parts to a list of Renders to the linked to the View
 * elements to be used by the ViewList.<br>
 * The model connection is performed through the DataSource instance that also gets connected to the Event
 * Listener chain.
 * @author Adam Antinoo
 */
public class DataSourceAdapter extends BaseAdapter implements PropertyChangeListener {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger logger = LoggerFactory.getLogger("DataSourceAdapter");
	private static boolean logAllowed = false;

	// - F I E L D - S E C T I O N ............................................................................
	/** The Activity where all this structures beong an that is used as the core display context. */
	private Activity _context = null;
	/** An instance for a source of data that will provide the list of <b>Parts</b> to be used to construct the Views. */
	private IDataSource _datasource = null;
	/** The current list of Parts that is being displayed. */
	private final List<IAndroidPart> _contentPartList = new ArrayList<IAndroidPart>();

	// - C O N S T R U C T O R - S E C T I O N ................................................................

	/** Neutral creator for the initialization of the parent. */
	public DataSourceAdapter () {
		super();
	}

	/**
	 * During the creation of the Adapter we setup the Fragment or the Activity that contains the layout with the
	 * ListView to render the Model and the DataSource with the list of Parts to be rendered there. To make this
	 * Adapter compatible with Fragment and Activities we should have two constructors and consolidate the reference to
	 * the Context that is the only element really required for the constructions and connection of the views.
	 * @param activity   reference to the Activity and the Context where we should connect the Views.
	 * @param datasource the source for the data to be represented on the view structures.
	 */
	public DataSourceAdapter ( final Activity activity, final IDataSource datasource ) {
		this();
		_context = activity;
		_datasource = datasource;
		_datasource.addPropertyChangeListener(this);
		//		this.setModel();
		//		_contentPartList.clear();
		//		_contentPartList.addAll(_datasource.getBodyParts());
	}

	public DataSourceAdapter ( final AbstractPagerFragment fragment, final IDataSource datasource ) {
		this(fragment.getAppContext(), datasource);
	}

	// - M E T H O D - S E C T I O N ..........................................................................

	public Activity getContext () {
		return _context;
	}

	public IAndroidPart getCastedItem ( final int position ) {
		return _contentPartList.get(position);
	}


	public Object getItem ( final int position ) {
		return _contentPartList.get(position);
	}

	//--- B A S E   A D A P T E R   I M P L E M E N T A T I O N
	public int getCount () {
		return _contentPartList.size();
	}

	public long getItemId ( final int position ) {
		return _contentPartList.get(position).getModelId();
	}


	public boolean areAllItemsEnabled () {
		return true;
	}

	public boolean isEnabled ( int position ) {
		return true;
	}

	public int getItemViewType ( int position ) {
		return 0;
	}

	public int getViewTypeCount () {
		return 1;
	}

	/**
	 * This method is called so many times that represent the most consuming tasks on the Activity. The
	 * optimization to not create more views than the needed ones and the reduction of code line is s must that
	 * will improve user response times.
	 */
	//	@SuppressLint("ViewHolder")
	public View getView ( final int position, View convertView, final ViewGroup parent ) {
		Chrono chrono = new Chrono();
		String exitMessage = "";
		try {
			// If the request is new we are sure this has to be created.
			IAndroidPart item = this.getCastedItem(position);
			if ( null == convertView ) {
				exitMessage = "-- [DataSourceAdapter.getView]> Getting view [" + position + "] - " + item.getClass().getSimpleName();
				AbstractRender holder = item.getRenderer(_context);
				holder.initializeViews();
				convertView = holder.getView();
				convertView.setTag(item);
				holder.updateContent();
				// Store view on the Part.
				if ( SystemWideConstants.ENABLECACHE ) {
					item.setView(convertView);
				}
			} else {
				View cachedView = item.getView();
				if ( null == cachedView ) {
					exitMessage = "-- [DataSourceAdapter.getView]> Getting view [" + position + "] - "
							+ item.getClass().getSimpleName() + " RECREATE";
					// Recreate the view.
					AbstractRender holder = this.getCastedItem(position).getRenderer(_context);
					holder.initializeViews();
					convertView = holder.getView();
					convertView.setTag(item);
					holder.updateContent();
					// Store view on the Part.
					if ( SystemWideConstants.ENABLECACHE ) {
						item.setView(convertView);
					}
				} else {
					// Cached view found. Return new view.
					convertView = cachedView;
					exitMessage = "-- [DataSourceAdapter.getView]> Getting view [" + position + "] - "
							+ item.getClass().getSimpleName() + " CACHED";
				}
			}
			// Activate listeners if the Part supports that feature.
			convertView.setClickable(false);
			convertView.setLongClickable(true);
			if ( item instanceof OnClickListener ) {
				convertView.setClickable(true);
				convertView.setOnClickListener((OnClickListener) item);
			}
			if ( item instanceof OnLongClickListener ) {
				convertView.setClickable(true);
				convertView.setOnLongClickListener((OnLongClickListener) item);
			}
			// REFACTOR Add the DataSource as an event listener because that feature does not depend on the interfaces.
			item.addPropertyChangeListener(_datasource);
			if ( logAllowed )		logger.info(exitMessage + " - Rendering time: " + chrono.printElapsed(Chrono.ChronoOptions.SHOWMILLIS));
			return convertView;
		} catch (RuntimeException rtex) {
			String message = rtex.getMessage();
			if ( null == message ) {
				message = "NullPointerException detected.";
			}
			DataSourceAdapter.logger.error("RTEX [DataSourceAdapter.getView]> Runtime Exception: " + message);
			rtex.printStackTrace();
			//DEBUG Add exception registration to the exception page.
			final LayoutInflater mInflater = (LayoutInflater) this.getContext()
			                                                      .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			// Under an exception we can replace the View item by this special layout with the Exception message.
			convertView = mInflater.inflate(R.layout.exception4list, null);
			TextView exceptionMessage = (TextView) convertView.findViewById(R.id.exceptionMessage);
			exceptionMessage.setText("[DataSourceAdapter.getView]> RTEX > " + message);
			return convertView;
		}
	}

	@Override
	public boolean hasStableIds () {
		return true;
	}

	/**
	 * This method should be called when the model transformation has changed. The model may be the same but the
	 * instantiation of the elements that should be rendered may have changed so the model should be run again and a new
	 * list part should be generated to be adapted to the viewer contents.
	 * <p>
	 * This action should trigger the display regeneration and the requests for new View instances to be used for the new
	 * ui render contents.
	 */
	@Override
	public void notifyDataSetChanged () {
		_contentPartList.clear();
		_contentPartList.addAll(_datasource.getDataSectionContents());
		super.notifyDataSetChanged();
	}

	//--- P R O P E R T Y C H A N G E L I S T E N E R   I N T E R F A C E

	/**
	 * Send messages to the parent activity that is the one that has code implemented for every different case.
	 * This class is a generic class that must not be upgraded because we start then to replicate most of the
	 * code.
	 */
	public void propertyChange ( final PropertyChangeEvent event ) {
		// Be sure to run graphical changes on the UI thread. If we alerady are on it this has no effect.
		getContext().runOnUiThread(() -> {
			if ( SystemWideConstants.events.valueOf(event.getPropertyName()) ==
					SystemWideConstants.events.EVENTADAPTER_REQUESTNOTIFYCHANGES ) {
				this.notifyDataSetChanged();
			}
		});
	}
}
// - UNUSED CODE ............................................................................................
//	private AbstractPagerFragment getFragment () {
//		return _fragment;
//	}
//	/**
//	 * This method is the entry point where the external environment will feed in the data to be used by this adapter
//	 * to render the model into the ListView.
//	 * @param partData
//	 */
//	private void setModel (final ArrayList<AbstractAndroidPart> partData) {
//		_contentPartList.clear();
//		_contentPartList.addAll(partData);
//	}

