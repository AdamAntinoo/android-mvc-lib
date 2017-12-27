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
import org.dimensinfin.android.mvc.core.AbstractAndroidPart;
import org.dimensinfin.android.mvc.core.AbstractRender;
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
	protected Activity _context = null;
	protected IDataSource _datasource = null;
	private final ArrayList<AbstractAndroidPart> _hierarchy = new ArrayList<AbstractAndroidPart>();
	protected AbstractPagerFragment _fragment = null;

	// - C O N S T R U C T O R - S E C T I O N ................................................................
/** Neutral creator for the initialization of the parent. */
	public DataSourceAdapter () {
		super();
	}

	/**
	 * The real separation of data sources requires that it is not tied to an Activity. So the base adapter has
	 * to receive both parameters on construction to be able to get Pilot based information and connect to the
	 * data source. At the same time there are two versions, one for Fragments and another for Activities.
	 * On some cases that we use the MVC on non Fragment developments the fragment may be null. Adapt the code
	 * to get the context from another place.
	 *
	 * @param fragment   reference to the fragment to where this Adapter is tied.
	 * @param datasource the source for the data to be represented on the view structures.
	 */
	public DataSourceAdapter (final AbstractPagerFragment fragment, final IDataSource datasource) {
		this();
		_fragment = fragment;
		_context = _fragment.getActivity();
		if(null==_context)_context= MVCAppConnector.getSingleton().getFirstActivity();
		_datasource = datasource;
		_datasource.addPropertyChangeListener(this);
		this.setModel(_datasource.getBodyParts());
	}


	// - M E T H O D - S E C T I O N ..........................................................................
	public AbstractAndroidPart getCastedItem (final int position) {
		return _hierarchy.get(position);
	}

	public int getCount () {
		return _hierarchy.size();
	}

	public Object getItem (final int position) {
		return _hierarchy.get(position);
	}

	@Override
	public long getItemId (final int position) {
		return _hierarchy.get(position).getModelID();
	}

	/**
	 * This method is called so many times that represent the most consuming tasks on the Activity. The
	 * optimization to not create more views than the needed ones and the reduction of code line is s must that
	 * will improve user response times.
	 */
	@SuppressLint("ViewHolder")
	public View getView (final int position, View convertView, final ViewGroup parent) {
		Chrono chrono = new Chrono();
		try {
			// If the request is new we are sure this has to be created.
			AbstractAndroidPart item = this.getCastedItem(position);
			if ( null == convertView ) {
				DataSourceAdapter.logger.info(
						"-- [DataSourceAdapter.getView]> Getting view [" + position + "] - " + item.getClass().getSimpleName());
				AbstractRender holder = item.getRenderer(this.getFragment());
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
					DataSourceAdapter.logger.info("-- [DataSourceAdapter.getView]> Getting view [" + position + "] - "
							+ item.getClass().getSimpleName() + " RECREATE");
					// Recreate the view.
					AbstractRender holder = this.getCastedItem(position).getRenderer(this.getFragment());
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
					DataSourceAdapter.logger.info("-- [DataSourceAdapter.getView]> Getting view [" + position + "] - "
							+ item.getClass().getSimpleName() + " CACHED");
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
			logger.info("-- [DataSourceAdapter.getView]> Rendering time: " + chrono.printElapsed(ChonoOptions.SHOWMILLIS));
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

	private AbstractPagerFragment getFragment () {
		return _fragment;
	}

	@Override
	public boolean hasStableIds () {
		return true;
	}

	/**
	 * Update the Part list from the model. It should have been already updated by the detection of the
	 * structure change.
	 */
	@Override
	public void notifyDataSetChanged () {
		this.setModel(_datasource.getBodyParts());
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

	public void setModel (final ArrayList<AbstractAndroidPart> partData) {
		_hierarchy.clear();
		//		_hierarchyViews.clear();
		_hierarchy.addAll(partData);
	}
}
// - UNUSED CODE ............................................................................................
