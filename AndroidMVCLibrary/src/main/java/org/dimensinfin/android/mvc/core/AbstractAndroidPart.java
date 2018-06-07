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
package org.dimensinfin.android.mvc.core;

import java.util.List;

import android.app.Activity;
import android.view.View;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.dimensinfin.android.mvc.activity.AbstractPagerFragment;
import org.dimensinfin.android.mvc.interfaces.IAndroidPart;
import org.dimensinfin.android.mvc.interfaces.IPart;
import org.dimensinfin.android.mvc.part.SeparatorPart;
import org.dimensinfin.core.interfaces.ICollaboration;
import org.dimensinfin.core.model.Separator;

// - CLASS IMPLEMENTATION ...................................................................................

/**
 * Core code for any Android Part. Will have enough code to deal with the adaptation of a Part to the
 * DataSourceAdapter needs to connect the part with the view. Has the knowledge of the Render and how to
 * report tehm to the Adapter.
 * @author Adam Antinoo
 */
public abstract class AbstractAndroidPart extends AbstractPart implements IAndroidPart {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static final long serialVersionUID = 7467855028114565679L;
	protected static Logger logger = LoggerFactory.getLogger("AbstractAndroidPart");

	// - F I E L D - S E C T I O N ............................................................................
	protected Activity _activity = null;
	protected AbstractPagerFragment _fragment = null;
	private View _view = null;

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public AbstractAndroidPart( final ICollaboration model ) {
		super(model);
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	@Override
	public Activity getActivity() {
		if ( null == _fragment )
			return _activity;
		else
			return this.getFragment().getActivity();
	}

	//	@Override
	public AbstractPagerFragment getFragment() {
		if ( null != _fragment )
			return _fragment;
		else
			throw new RuntimeException("Fragment object not available on access on a Part.");
	}

	public void collaborate2View( List<IAndroidPart> contentCollector ) {
		AbstractPart.logger.info(">< [AbstractPart.collaborate2View]> Collaborator: " + this.getClass().getSimpleName());
		//		ArrayList<IPart> result = new ArrayList<IPart>();
		// If the node is expanded then give the children the opportunity to also be added.
		if ( this.isExpanded() ) {
			contentCollector.add(this);
			// ---This is the section that is different for any Part. This should be done calling the list of policies.
			List<IPart> ch = this.runPolicies(this.getChildren());
			AbstractPart.logger.info("-- [AbstractPart.collaborate2View]> Collaborator children: " + ch.size());
			// --- End of policies
			for (IPart part : ch) {
				if ( part instanceof IAndroidPart )
					((IAndroidPart) part).collaborate2View(contentCollector);
				//				AbstractPart.logger.info("-- [AbstractPart.collaborate2View]> Collaboration parts: " + collaboration.size());
				//				contentCollector.addAll(collaboration);
			}
		} else {
			// Check for items that will not shown when empty and not expanded.
			if ( this.isRenderWhenEmpty() ) {
				runDependencies();
				contentCollector.add(this);
			}
		}
		//		return contentCollector;
	}

	public List<IPart> runPolicies( final List<IPart> targets ) {
		return targets;
	}

	/**
	 * Dependencies are when one model object contains or has references to other model instances. During rendering we cannot wait to do
	 * each access and resolve it to their data so we can fire a dependency event when we are collaborating to the view so we can perform
	 * any downloads and resolutions while we have already informed the used of a lengthy data processing under way.
	 * @return
	 */
	public  boolean runDependencies(){
		return false;
	}

	/**
	 * Activities are the preferred Context ot be used on the parts. That make them usable on any context and not
	 * depending on the Pager implementation. This is the opposite from the initial implementation where we required
	 * Fragment to be used on all the implementations.
	 */
	@Override
	public AbstractRender getRenderer( final Activity activity ) {
		_activity = activity;
		return this.selectRenderer();
	}

	//	@Override
	//	public AbstractRender getRenderer (final Fragment fragment) {
	//		if ( fragment instanceof AbstractPagerFragment ) _fragment = (AbstractPagerFragment) fragment;
	//		else throw new RuntimeException("Using on MVC fragments that are not compatible.");
	//		_activity = ((AbstractPagerFragment)fragment).getMetaActivity();
	//		return this.selectRenderer();
	//	}

	//	@Override
	public View getView() {
		return _view;
	}

	@Override
	public void invalidate() {
		if ( null != _view ) {
			this.needsRedraw();
		}
	}

	public boolean isEmpty() {
		if ( getChildren().size() > 0 ) return false;
		else return true;
	}

	@Override
	public void needsRedraw() {
		_view.invalidate();
		_view = null;
	}

	//	@Override
	public void setView( final View convertView ) {
		_view = convertView;
	}

	//	public abstract AbstractRender selectRenderer ();
}

// - UNUSED CODE ............................................................................................
