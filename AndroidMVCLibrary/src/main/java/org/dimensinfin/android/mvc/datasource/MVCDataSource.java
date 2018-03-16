//  PROJECT:     Android.MVC (A.MVC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Android API16.
//  DESCRIPTION: Library that defines a generic Model View Controller core classes to be used
//               on Android projects. Defines the Part factory and the Part core methods to manage
//               a generic converter from a Graph Model to a hierarchycal Part model that finally will
//               be converted to a Part list to be used on a BaseAdapter tied to a ListView.
package org.dimensinfin.android.mvc.datasource;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.View;

import org.dimensinfin.android.mvc.constants.SystemWideConstants;
import org.dimensinfin.android.mvc.core.AbstractAndroidPart;
import org.dimensinfin.android.mvc.core.AbstractPart;
import org.dimensinfin.android.mvc.core.AbstractRender;
import org.dimensinfin.android.mvc.core.RootPart;
import org.dimensinfin.android.mvc.interfaces.IAndroidPart;
import org.dimensinfin.android.mvc.interfaces.IDataSource;
import org.dimensinfin.android.mvc.interfaces.IPart;
import org.dimensinfin.android.mvc.interfaces.IPartFactory;
import org.dimensinfin.core.datasource.DataSourceLocator;
import org.dimensinfin.core.model.AbstractPropertyChanger;
import org.dimensinfin.core.model.RootNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * New complete core implementation for the DataSource that should be connected to the extended BaseAdapter to
 * provide the Adapter with the list of Parts to be used for the rendering on the LitView.
 *
 * @author Adam Antinoo
 */

// - CLASS IMPLEMENTATION ...................................................................................
public abstract class MVCDataSource extends AbstractPropertyChanger implements IDataSource {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger logger = LoggerFactory.getLogger("MVCDataSource");

	// - F I E L D - S E C T I O N ............................................................................
	/** Unique DataSource string identifier to locate this instance on the <code>DataSourceManager</code> in case the instances
	 * should be cached. */
	private final DataSourceLocator _locator;
	/** Copy of the extras bundle received by the Activity. */
	private Bundle _extras = new Bundle();
	/** This is the Fragment or Activity code used to differentiate between different model generations. */
	private String _variant = "-DEFAULT-VARIANT-";
	/**
	 * Factory to be used on the hierarchy generation. Each part has a connection to this factory to create its
	 * children from the model nodes.
	 */
	private IPartFactory _partFactory = null;
	/** Flag to indicate if the model contents generated can be cached and we can avoid running the <code>collaborate2Model
	 * ()</code> method on every fragment instantiation. If the model is suitable for caching we can speed up the turn of the
	 * device because we have not to generate again the DataSource and its model sta structure. */
	private boolean shouldBeCached=false;
	/**
	 * The initial node where to store the model. Model elements are children of this root. This version exports this
	 * node to dynamically detect the changes and generate the missing hierarchy elements that are being added during
	 * the Model generation.
	 * There are only a variant for the RootNode so we can lock it to a predefined instance.
	 * If we need to customize the Root nodes (for example to add special filtering) we should remove the
	 * <code>final</code> from this field.
	 */
	protected final RootNode _dataModelRoot = new RootNode();
	/** The root node for the Part hierarchy that matches the data model hierarchy. */
	private RootAndroidPart _partModelRoot = null;
	/**
	 * The list of Parts to show on the viewer. This is the body section that is scrollable. This instance is shared
	 * during the <code>collaboration2View</code> phase tu use less memory and avoid copuing references from list to
	 * list during the generation process.
	 */
	private final List<IAndroidPart> _bodyParts = new Vector<IAndroidPart>(100);

	// - C O N S T R U C T O R - S E C T I O N ................................................................
//	public MVCDataSource(final IPartFactory factory) {
//		super();
//	}
	public MVCDataSource (final DataSourceLocator locator, final String variant,final IPartFactory factory, final Bundle extras) {
		super();
		_locator = locator;
		_variant = variant;
		_partFactory = factory;
		this._extras=extras;
	}
	// - M E T H O D - S E C T I O N ..........................................................................
	// --- I D A T A S O U R C E
	public String getVariant() {
		return _variant;
	}

	public IDataSource setVariant (final String variant) {
		_variant = variant;
		return this;
	}

	@Override
	public DataSourceLocator getDataSourceLocator() {
		return null;
	}

	public abstract RootNode collaborate2Model();

	/**
	 * After the model is created we have to transform it into the Part list expected by the DataSourceAdapter.
	 * The Part creation is performed by the corresponding PartFactory we got at the DataSource creation.<br>
	 * We transform the model recursively and keeping the already available Part elements. We create a
	 * duplicated of the resulting Part model and we move already parts from the current model to the new model
	 * or create new part and finally remove what is left and unused.
	 * This new implementation will use partial generation to split and speed up this phase.
	 */
	public void createContentHierarchy () {
		//		try {
		logger.info(">> [MVCDataSource.createContentHierarchy]");
		// Check if we have already a Part model.
		// But do not forget to associate the new Data model even if the old exists.
		if ( null == _partModelRoot ) {
			_partModelRoot = new RootAndroidPart(_dataModelRoot, _partFactory);
		} else {
			_partModelRoot.setModel(_dataModelRoot);
		}

		logger.info("-- [MVCDataSource.createContentHierarchy]> Initiating the refreshChildren() for the " +
				"_partModelRoot");
		// Intercept any exception on the creation of the model but do not cut the progress of the already added items
		try {
			_partModelRoot.refreshChildren();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		//			// Get the list of Parts that will be used for the ListView
		//			_bodyParts = new ArrayList<IPart>();
		//			// Select for the body contents only the viewable Parts from the Part model. Make it a list.
		//			_bodyParts.addAll(_partModelRoot.collaborate2View());
		//		} catch (Exception ex) {
		//			ex.printStackTrace();
		//		}
		logger.info("<< [MVCDataSource.createContentHierarchy]> _bodyParts.size: {}", _bodyParts.size());
	}
//	@Override
//	public List<IAndroidPart> getBodyParts () {
//		return _bodyParts;
//	}
//	public MVCDataSource() {
//		super();
//	}
//	public ArrayList<AbstractAndroidPart> getHeaderParts () {
//		return new ArrayList<>();
//	}


	// --- P R O P E R T Y C H A N G E R

	/**
	 * This method is called whenever there is an event on any Part related to this DataSource. We just process
	 * structure changes that need the DataSource to reconstruct the Part model from the new Model state.
	 * There are two states for the reconstruction. If the Model changes we should regenerate the art hierarchy and
	 * from it the Part list that should be send to the Adapter. This is the <code>collaborate2Model</code> phase.
	 * The other state only changes the list of Parts that should collaborate to the view because the model has not
	 * changed on structure (it can be have changed on content) so the Part hierarchy should not be recreated, but the
	 * final list to be rendered on the View has to be reconstructed completely.
	 */
	@Override
	public void propertyChange (final PropertyChangeEvent event) {
		logger.info(">> [MVCDataSource.propertyChange]> Processing Event: {}" + event.getPropertyName());
		// The expand/collapse state has changed.
		if ( SystemWideConstants.events
				.valueOf(event.getPropertyName()) == SystemWideConstants.events.EVENTSTRUCTURE_ACTIONEXPANDCOLLAPSE ) {
			//			_bodyParts = new ArrayList<IPart>();
			_bodyParts.clear();
			_partModelRoot.collaborate2View(_bodyParts);
		}
		if ( SystemWideConstants.events
				.valueOf(event.getPropertyName()) == SystemWideConstants.events.EVENTSTRUCTURE_DOWNLOADDATA ) {
			this.createContentHierarchy();
			_bodyParts.clear();
			_partModelRoot.collaborate2View(_bodyParts);
		}
		if ( SystemWideConstants.events
				.valueOf(event.getPropertyName()) == SystemWideConstants.events.EVENTSTRUCTURE_NEWDATA ) {
			this.createContentHierarchy();
			_bodyParts.clear();
			_partModelRoot.collaborate2View(_bodyParts);
		}
		if ( SystemWideConstants.events
				.valueOf(event.getPropertyName()) == SystemWideConstants.events.EVENTADAPTER_REQUESTNOTIFYCHANGES ) {
			this.fireStructureChange(SystemWideConstants.events.EVENTADAPTER_REQUESTNOTIFYCHANGES.name(), event.getOldValue(),
					event.getNewValue());
			logger.info("<< [MVCDataSource.propertyChange]");
			return;
		}
		//		super.propertyChange(event);
		this.fireStructureChange(SystemWideConstants.events.EVENTADAPTER_REQUESTNOTIFYCHANGES.name(), event.getOldValue(),
				event.getNewValue());
	}
	@Override
	public String toString () {
		StringBuffer buffer = new StringBuffer("MVCDataSource [");
		buffer.append("name: ").append(0);
		buffer.append("]");
		//		buffer.append("->").append(super.toString());
		return buffer.toString();
	}

	public static class RootAndroidPart extends RootPart implements IAndroidPart {

		public RootAndroidPart (final RootNode node, final IPartFactory factory) {
			super(node, factory);
		}

		@Override
		public void collaborate2View (final List<IAndroidPart> contentCollector) {
			AbstractPart.logger.info(">< [RootAndroidPart.collaborate2View]> Collaborator: " + this.getClass().getSimpleName());
			//			ArrayList<IPart> result = new ArrayList<IPart>();
			// If the node is expanded then give the children the opportunity to also be added.
			if ( this.isExpanded() ) {
				// ---This is the section that is different for any Part. This should be done calling the list of policies.
				List<IPart> ch = this.runPolicies(this.getChildren());
				AbstractPart.logger.info("-- [AbstractPart.collaborate2View]> Collaborator children: " + ch.size());
				// --- End of policies
				for (IPart part : ch) {
					if ( part instanceof IAndroidPart )
						((IAndroidPart)part).collaborate2View(contentCollector);
					//						AbstractPart.logger.info("-- [AbstractPart.collaborate2View]> Collaboration parts: " + collaboration.size());
					//						contentCollector.addAll(collaboration);
				}
			} else {
				// Check for items that will not shown when empty and not expanded.
				if ( this.isRenderWhenEmpty() ) {
					contentCollector.add(this);
				}
			}
		}

		@Override
		public Activity getActivity () {
			return null;
		}

		@Override
		public Fragment getFragment () {
			return null;
		}

		@Override
		public AbstractRender getRenderer (final Activity activity) {
			return null;
		}

		@Override
		public View getView () {
			return null;
		}

		@Override
		public void invalidate () {

		}

		@Override
		public void needsRedraw () {

		}

		@Override
		public void setView (final View convertView) {

		}
	}
}
// - UNUSED CODE ............................................................................................
//[01]
