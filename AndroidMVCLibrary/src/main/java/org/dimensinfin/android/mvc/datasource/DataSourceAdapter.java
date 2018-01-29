//	PROJECT:        NeoCom.MVC (NEOC.MVC)
//	AUTHORS:        Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2013-2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:		Android API16.
//	DESCRIPTION:		Library that defines a generic Model View Controller core classes to be used
//									on Android projects. Defines the Part factory and the Part core methods to manage
//									the extended GEF model into the Android View to be used on ListViews.
package org.dimensinfin.android.mvc.datasource;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.dimensinfin.android.mvc.R;
import org.dimensinfin.android.mvc.activity.AbstractPagerFragment;
import org.dimensinfin.android.mvc.connector.MVCAppConnector;
import org.dimensinfin.android.mvc.constants.SystemWideConstants;
import org.dimensinfin.android.mvc.core.AbstractRender;
import org.dimensinfin.android.mvc.interfaces.IAndroidPart;
import org.dimensinfin.android.mvc.interfaces.IDataSource;
import org.dimensinfin.core.util.Chrono;
import org.dimensinfin.core.util.Chrono.ChonoOptions;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.logging.Logger;

// - CLASS IMPLEMENTATION ...................................................................................

/**
 * This is the class that connects the ListView to a model list. If is an extension of the generic BaseAdapter
 * and implements the methods to convert lists of Parts to a list of Renders to the linked to the View
 * elements to be used by the ViewList.<br>
 * The model connection is performed through the DataSource instance that also gets connected to the Event
 * Listener chain.
 *
 * @author Adam Antinoo
 */
public class DataSourceAdapter extends BaseAdapter implements PropertyChangeListener {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger logger = Logger.getLogger("DataSourceAdapter");

	// - F I E L D - S E C T I O N ............................................................................
	private Activity _context = null;
	private IDataSource _datasource = null;
	private final ArrayList<IAndroidPart> _contentPartList = new ArrayList<IAndroidPart>();
//	protected AbstractPagerFragment _fragment = null;

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
	 *
	 * @param activity   reference to the Activity and the Context where we should connect the Views.
	 * @param datasource the source for the data to be represented on the view structures.
	 */
	public DataSourceAdapter (final Activity activity, final IDataSource datasource) {
		this();
//		_fragment = fragment;
		_context = activity;
		if ( null == _context ) _context = MVCAppConnector.getSingleton().getFirstActivity();
		_datasource = datasource;
		_datasource.addPropertyChangeListener(this);
		//		this.setModel();
		_contentPartList.clear();
		_contentPartList.addAll(_datasource.getBodyParts());
	}
	public DataSourceAdapter (final AbstractPagerFragment fragment, final IDataSource datasource) {
		this(fragment.getActivity(), datasource);
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	public IAndroidPart getCastedItem (final int position) {
		return _contentPartList.get(position);
	}

	public int getCount () {
		return _contentPartList.size();
	}

	public Object getItem (final int position) {
		return _contentPartList.get(position);
	}

	@Override
	public long getItemId (final int position) {
		return _contentPartList.get(position).getModelID();
	}

	/**
	 * This method is called so many times that represent the most consuming tasks on the Activity. The
	 * optimization to not create more views than the needed ones and the reduction of code line is s must that
	 * will improve user response times.
	 */
	@SuppressLint("ViewHolder")
	public View getView (final int position, View convertView, final ViewGroup parent) {
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
			logger.info(exitMessage + " - Rendering time: " + chrono.printElapsed(ChonoOptions.SHOWMILLIS));
			return convertView;
		} catch (RuntimeException rtex) {
			String message = rtex.getMessage();
			if ( null == message ) {
				message = "NullPointerException detected.";
			}
			DataSourceAdapter.logger.severe("RTEX [DataSourceAdapter.getView]> Runtime Exception: " + message);
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

//	private AbstractPagerFragment getFragment () {
//		return _fragment;
//	}

	@Override
	public boolean hasStableIds () {
		return true;
	}

	/**
	 * Update the Part list to be used by the Adapter from the DataSource. This action should trigger the generation
	 * of the list of parts to be used on the Adapter from the Part hierarchy generated from the Model.
	 */
	@Override
	public void notifyDataSetChanged () {
		//		this.setModel(_datasource.getBodyParts());
		_contentPartList.clear();
		_contentPartList.addAll(_datasource.getBodyParts());
		super.notifyDataSetChanged();
	}

	/**
	 * Send messages to the parent activity that is the one that has code implemented for every different case.
	 * This class is a generic class that must not be upgraded because we start then to replicate most of the
	 * code.
	 */
	public void propertyChange (final PropertyChangeEvent event) {
		if ( SystemWideConstants.events
				.valueOf(event.getPropertyName()) == SystemWideConstants.events.EVENTADAPTER_REQUESTNOTIFYCHANGES ) {
			this.notifyDataSetChanged();
		}
	}

	protected Activity getContext () {
		return _context;
	}

	//	/**
	//	 * This method is the entry point where the external environment will feed in the data to be used by this adapter
	//	 * to render the model into the ListView.
	//	 * @param partData
	//	 */
	//	private void setModel (final ArrayList<AbstractAndroidPart> partData) {
	//		_contentPartList.clear();
	//		_contentPartList.addAll(partData);
	//	}
}
// - UNUSED CODE ............................................................................................
