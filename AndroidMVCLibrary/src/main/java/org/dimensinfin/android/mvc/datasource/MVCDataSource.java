//  PROJECT:     Android.MVC (A.MVC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Android API16.
//  DESCRIPTION: Library that defines a generic Model View Controller core classes to be used
//               on Android projects. Defines the Part factory and the Part core methods to manage
//               a generic converter from a Graph Model to a hierarchycal Part model that finally will
//               be converted to a Part list to be used on a BaseAdapter tied to a ListView.
package org.dimensinfin.android.mvc.datasource;

//import android.app.Activity;
//import android.app.Activity;
//import android.app.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import org.dimensinfin.android.mvc.activity.AbstractPagerFragment;
import org.dimensinfin.android.mvc.constants.SystemWideConstants;
import org.dimensinfin.android.mvc.core.AbstractAndroidPart;
import org.dimensinfin.android.mvc.core.AbstractPart;
import org.dimensinfin.android.mvc.core.AbstractRender;
import org.dimensinfin.android.mvc.core.RootPart;
import org.dimensinfin.android.mvc.interfaces.IAndroidPart;
import org.dimensinfin.android.mvc.interfaces.IDataSource;
import org.dimensinfin.android.mvc.interfaces.IPart;
import org.dimensinfin.android.mvc.interfaces.IPartFactory;
import org.dimensinfin.android.mvc.interfaces.IRootPart;
import org.dimensinfin.core.datasource.DataSourceLocator;
import org.dimensinfin.core.interfaces.ICollaboration;
import org.dimensinfin.core.model.AbstractPropertyChanger;
import org.dimensinfin.core.model.RootNode;
import org.dimensinfin.core.model.Separator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

//import android.view.View;

/**
 * New complete core implementation for the DataSource that should be connected to the extended BaseAdapter to
 * provide the Adapter with the list of Parts to be used for the rendering on the LitView.
 * @author Adam Antinoo
 */

// - CLASS IMPLEMENTATION ...................................................................................
public abstract class MVCDataSource extends AbstractPropertyChanger implements IDataSource {
	// - S T A T I C - S E C T I O N ..........................................................................
	public static Logger logger = LoggerFactory.getLogger("MVCDataSource");

	// - F I E L D - S E C T I O N ............................................................................
	/**
	 * Unique DataSource string identifier to locate this instance on the <code>DataSourceManager</code> in case the instances
	 * should be cached.
	 */
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
	/**
	 * Flag to indicate if the model contents generated can be cached and we can avoid running the <code>collaborate2Model
	 * ()</code> method on every fragment instantiation. If the model is suitable for caching we can speed up the turn of the
	 * device because we have not to generate again the DataSource and its model sta structure.
	 */
	private boolean _shouldBeCached = false;
	/**
	 * The initial node where to store the model. Model elements are children of this root. This version exports this
	 * node to dynamically detect the changes and generate the missing hierarchy elements that are being added during
	 * the Model generation.
	 * There are only a variant for the RootNode so we can lock it to a predefined instance.
	 * If we need to customize the Root nodes (for example to add special filtering) we should remove the
	 * <code>final</code> from this field.
	 */
	protected final RootNode _dataModelRoot = new RootNode();
	/**
	 * The root node for the Part hierarchy that matches the data model hierarchy. This field can be changed from the predefined
	 * instance to any developer needs that matches the IRootPart requirements.
	 */
	private IRootPart _partModelRoot = null;
	/**
	 * The list of Parts to show on the viewer. This is the body section that is scrollable. This instance is shared
	 * during the <code>collaboration2View()</code> phase to use less memory and avoid copying references from list to
	 * list during the generation process.
	 */
	private final List<IAndroidPart> _dataSectionParts = new Vector<IAndroidPart>(100);

	// - C O N S T R U C T O R - S E C T I O N ................................................................
//	public MVCDataSource(final IPartFactory factory) {
//		super();
//	}
	public MVCDataSource(final DataSourceLocator locator, final String variant, final IPartFactory factory, final Bundle extras) {
		super();
		_locator = locator;
		_variant = variant;
		_partFactory = factory;
		this._extras = extras;
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	// --- I D A T A S O U R C E
	@Override
	public DataSourceLocator getDataSourceLocator() {
		return _locator;
	}

	public String getVariant() {
		return _variant;
	}

	public IDataSource setVariant(final String variant) {
		_variant = variant;
		return this;
	}

	public Bundle getExtras() {
		return _extras;
	}

	public void cleanup() {
		_dataModelRoot.clean();
	}

	/**
	 * This is the single way to add more content to the DataSource internal model representation. Encapsulating this
	 * functionality on this method we make sure that the right events are generated and the model is properly updated and the
	 * renderization process will work as expected.
	 * @param newnode a new node to be added to the contents of the root point of the model.
	 * @return this IDataSource instance to allow functional coding.
	 */
	public IDataSource addModelContents(final ICollaboration newnode) {
		_dataModelRoot.addChild(newnode);
		// Fire the model structure change event. This processing is done on the background and on the UI thread.
		// TODO Check if this execution on this thread works or I should leave it on the UI thread.
		AbstractPagerFragment._uiExecutor.submit(() -> {
			// Notify the Adapter that the Root node has been modified to regenerate the collaboration2View.
			propertyChange(new PropertyChangeEvent(this
					, SystemWideConstants.events.EVENTSTRUCTURE_NEWDATA.name(), newnode, _dataModelRoot));
		});
		return this;
	}

	/**
	 * This method checks if the DataSource is compatible with caching and if this is the case checks if there are contents
	 * already cached so we can avoid to regenerate the model again.
	 * @return
	 */
	public boolean isCached() {
		if (_shouldBeCached)
			if (!_dataModelRoot.isEmpty())
				return true;
		return false;
	}

	/**
	 * Get the current cache selected state. This is used internally to do some checks.
	 * @return
	 */
	private boolean isCacheable() {
		return _shouldBeCached;
	}

	/**
	 * Sets the cacheable state for this DataSource. By default the cache state is <code>false</code> so no sources are caches.
	 * But in some cases the developer can speed up the model generation process and made it suitable for single
	 * initialization and caching. Use this setter to set the right state.
	 * @param cachestate new cache state for this data source. Affects at new source registrations with this same inique
	 *                   identifier.
	 * @return this same instance to allow functional programming.
	 */
	public IDataSource setCacheable(final boolean cachestate) {
		this._shouldBeCached = cachestate;
		return this;
	}

	public abstract RootNode collaborate2Model();

	/**
	 * After the model is created we have to transform it into the Part list expected by the DataSourceAdapter.
	 * The Part creation is performed by the corresponding PartFactory we got at the DataSource creation.
	 * <p>
	 * We transform the model recursively and keeping the already available Part elements. We create a
	 * duplicated of the resulting Part model and we move already available parts from the current model to the new model
	 * or create new part and finally remove what is left and unused.
	 * This new implementation will use partial generation to split and speed up this phase.
	 */
	private void transformModel2Parts() {
		logger.info(">> [MVCDataSource.transformModel2Parts]");
		// Check if we have already a Part model.
		// But do not forget to associate the new Data model even if the old exists.
		if (null == _partModelRoot) {
			_partModelRoot = createRootPart();
		}
		_partModelRoot.setRootModel(_dataModelRoot);

		logger.info("-- [MVCDataSource.transformModel2Parts]> Initiating the refreshChildren() for the Model Root");
		// Intercept any exception on the creation of the model but do not cut the progress of the already added items.
		try {
//			_dataSectionParts.clear();
			_partModelRoot.refreshChildren();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("<< [MVCDataSource.transformModel2Parts]> _dataSectionParts.size: {}", _dataSectionParts.size());
	}

	/**
	 * This methods can be customized by developers to change the features imeplemented by the <code>IRootPart</code>. The
	 * library provides an implementation but the code is open to make replacements at the key points to enhance flexibility on
	 * the use of the library. This method is called whenever the root part container is still undefined and calls any inherithed
	 * implementation that defines a new instance for this nose. The internal creation method will generate a
	 * <code>@link{RootAndroidPart}</code> inatance that is suitable for most of developments.
	 * @return a new instance of a <code>IRootPart</code> interface to be used as the root for the part hierarchy.
	 */
	public IRootPart createRootPart() {
		return new RootAndroidPart(_dataModelRoot, _partFactory);
	}
//	@Override
//	public List<IAndroidPart> getBodyParts () {
//		return _dataSectionParts;
//	}
//	public MVCDataSource() {
//		super();
//	}
//	public ArrayList<AbstractAndroidPart> getHeaderParts () {
//		return new ArrayList<>();
//	}


	// --- P R O P E R T Y C H A N G E R

	/**
	 * This method is called whenever there is an event from any model change or any Part interaction. There are two groups of
	 * events, <b>structural</b> that change the model structure and contents and that require a full regeneration of all the
	 * transformations and <b>content</b> that can change the list of elements to be visible at this point in time but that do
	 * not change the initial structure. The contents can happen from changes on the model data or by interactions on the Parts
	 * that have some graphical impact.
	 * <p>
	 * If the model structure changes we should recreate the Model -> Part transformation and generate another Part tree with
	 * Parts matching the current model graph. At this transformation we can transform any data connected structure real or
	 * virtual to a hierarchy graph with the standard parent-child structure. We use the <code>collaborate2Model()</code> as a
	 * way to convert internal data structures to a hierarchy representation on a point in time. We isolate internal model ways to
	 * deal with data and we can optimize for the Part hierarchy without compromising thet model flexibility.
	 * <p>
	 * If the contents change we only should run over the Part tree to make the transformation to generate a new Part list for
	 * all the new visible and renderable items. This is performed with the <code>collaborate2View()</code> method for any Part
	 * that will then decide which of its internal children are going to be referenced for the collaborating list of Parts. This
	 * is the right place where to set up programmatic filtering or sorting because at this point we can influence the output
	 * representation for the model instance. We can also decorate the resulting Part list breaking the one to one relationship
	 * between a model instance and a Part instance.
	 * @param event the event to be processed. Event have a property name that is used as a selector.
	 */
	@Override
	public void propertyChange(final PropertyChangeEvent event) {
		logger.info(">> [MVCDataSource.propertyChange]> Processing Event: {}" + event.getPropertyName());
		//--- C O N T E N T   E V E N T S
		// The expand/collapse state has changed.
		if (SystemWideConstants.events.valueOf(event.getPropertyName()) ==
				SystemWideConstants.events.EVENTCONTENTS_ACTIONEXPANDCOLLAPSE) {
			_dataSectionParts.clear();
			_partModelRoot.collaborate2View(_dataSectionParts);
		}

		//--- S T R U C T U R E   E V E N T S
		if (SystemWideConstants.events.valueOf(event.getPropertyName()) ==
				SystemWideConstants.events.EVENTSTRUCTURE_NEWDATA) {
			this.transformModel2Parts();
			_dataSectionParts.clear();
			_partModelRoot.collaborate2View(_dataSectionParts);
		}
		if (SystemWideConstants.events.valueOf(event.getPropertyName()) ==
				SystemWideConstants.events.EVENTSTRUCTURE_DOWNLOADDATA) {
			this.transformModel2Parts();
			_dataSectionParts.clear();
			_partModelRoot.collaborate2View(_dataSectionParts);
		}

		//--- A D A P T E R   E V E N T S
		if (SystemWideConstants.events
				.valueOf(event.getPropertyName()) == SystemWideConstants.events.EVENTADAPTER_REQUESTNOTIFYCHANGES) {
			this.fireStructureChange(SystemWideConstants.events.EVENTADAPTER_REQUESTNOTIFYCHANGES.name(), event.getOldValue(),
					event.getNewValue());
			logger.info("<< [MVCDataSource.propertyChange]");
			return;
		}
		this.fireStructureChange(SystemWideConstants.events.EVENTADAPTER_REQUESTNOTIFYCHANGES.name(), event.getOldValue(),
				event.getNewValue());
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer("MVCDataSource [");
		buffer.append("name: ").append(0);
		buffer.append("]");
		//		buffer.append("->").append(super.toString());
		return buffer.toString();
	}

}



// - CLASS IMPLEMENTATION ...................................................................................
final class RootAndroidPart extends RootPart implements IRootPart, IAndroidPart {
	// - S T A T I C - S E C T I O N ..........................................................................

	// - F I E L D - S E C T I O N ............................................................................
	private RootNode _rootModelNode = null;

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public RootAndroidPart(final RootNode node, final IPartFactory factory) {
		super(node, factory);
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	public void setRootModel(final RootNode rootNode) {
		_rootModelNode = rootNode;
	}
	/**
	 * Create the Part for the model object received. We have then to have access to the Factory from the root
	 * element and all the other parts should have a reference to the root to be able to do the same.
	 */
	protected IPart createNewPart(final ICollaboration model) {
		IPart part = null;
		IPartFactory factory = this.getRoot().getPartFactory();
		if (null != factory) {
			// If the factory is unable to create the Part then skip this element or wait to be replaced by a dummy
			part = factory.createPart(model);
			// Connect the new part to its parent.
			if (null != part) {
				part.setParent(this);
			}
		}
		return part;
	}


	//****************************************************************

	/**
	 * The refresh process should optimize the reuse of the already available Parts. We should check for model identity on the
	 * already available parts to be able to reuse one of them. So once we have a model item we search on the list of available
	 * parts for one of them containing as model this same instance. If found we reuse the Part. Otherwise we create a new Part
	 * for this model node and continue the transformation process.
	 * <p>
	 * OBSOLETE: On Android is highly recommended that all
	 * the model nodes that are used on the ListView adapters have a unique numeric identifier. In this library we use the
	 * Parts as the source for that kind of adapters so they should adhere to that restriction. Using that unique identifier
	 * obtained with the method <code>getModelId()</code> we can check if the Part is already available or we should create a
	 * new one for the model we have at work.
	 * <p>
	 * It is a recursive process that it is repeated for each one of the nodes added to the already processing structure while
	 * the nodes have any number of children.
	 * <p>
	 * The process gets the Model attached to the Part we are working with. Then finds the Model children to reconstruct their
	 * parts if they do not have them already and match them to the current list of Parts already connected to the Part in the
	 * work bench. Any discrepancies create or delete the required Parts. The we start again this process with the first of the
	 * children until all the model nodes have been processed.
	 * <p>
	 * During the transformation process we use the <code>collaborate2Model(final String variant)</code> to generate a fresh
	 * list of nodes from this model instance. We use the <b>Variant</b> to allow the model to generate different sets of new
	 * model instances depending on that variable. The flexibility of this approach allows a single model object to generate
	 * different outputs for each variant received and this value is set for each different Activity Page.
	 */
	public void refreshChildren() {
		AbstractPart.logger.info(">> [AbstractPart.refreshChildren]");
		// Create the new list of Parts for this node model contents if it have any collaboration.
		ICollaboration partModel = this.getModel();
		if (null == partModel) {
			AbstractPart.logger.warn("WR [AbstractPart.refreshChildren]> Exception case: no Model defined for this Part. {}"
					, this.toString());
			return;
		}
		// Get the new list of children for this model node. Use the Variant for generation discrimination.
		final List<ICollaboration> modelInstances = partModel.collaborate2Model(this.getPartFactory().getVariant());
		if (modelInstances.size() > 0) {
			AbstractPart.logger.info("-- [AbstractPart.refreshChildren]> modelInstances count: " + modelInstances.size());
			// Check all the model instances have a matching Part instance.
			final List<IPart> newPartChildren = new ArrayList<IPart>(modelInstances.size());
			final List<IPart> currentPartChildren = this.getChildren();
			for (ICollaboration modelNode : modelInstances) {
				// Search for the model instance on the current part list.
				IPart foundPart = null;
				for (IPart currentPart : currentPartChildren)
					if (currentPart.getModel().equals(modelNode)) {
						foundPart = currentPart;
						break;
					}
				if (null == foundPart) {
					AbstractPart.logger.info("-- [AbstractPart.refreshChildren]> Part for Model not found. Generating a new one for: {}",
							modelNode.getClass().getCanonicalName());
					// Model not found on the current list of Parts. Needs a new one.
					foundPart = createNewPart(modelNode);
				}
				// Add to the new list of parts.
				newPartChildren.add(foundPart);
				// Recursively process their children.
				foundPart.refreshChildren();
			}
			// The new list part is complete. Discard the old list and set the new one as the current list of children.
			clean();
			for (IPart child : newPartChildren) addChild(child);
		} else {
			AbstractPart.logger.info("-- [AbstractPart.refreshChildren]> Processing model: {}"
					, partModel.getClass().getCanonicalName());
			// The part is already created for leave nodes. Terminate this leave.
			return;
		}
		AbstractPart.logger.info("<< [AbstractPart.refreshChildren]> Content size: " + this.getChildren().size());
	}


	@Override
	public void collaborate2View(final List<IAndroidPart> contentCollector) {
		AbstractPart.logger.info(">< [RootAndroidPart.collaborate2View]> Collaborator: " + this.getClass().getSimpleName());
		//			ArrayList<IPart> result = new ArrayList<IPart>();
		// If the node is expanded then give the children the opportunity to also be added.
		if (this.isExpanded()) {
			// ---This is the section that is different for any Part. This should be done calling the list of policies.
			List<IPart> ch = this.runPolicies(this.getChildren());
			AbstractPart.logger.info("-- [AbstractPart.collaborate2View]> Collaborator children: " + ch.size());
			// --- End of policies
			for (IPart part : ch) {
				if (part instanceof IAndroidPart)
					((IAndroidPart) part).collaborate2View(contentCollector);
				//						AbstractPart.logger.info("-- [AbstractPart.collaborate2View]> Collaboration parts: " + collaboration.size());
				//						contentCollector.addAll(collaboration);
			}
		} else {
			// Check for items that will not shown when empty and not expanded.
			if (this.isRenderWhenEmpty()) {
				contentCollector.add(this);
			}
		}
	}

	@Override
	public Activity getActivity() {
		return null;
	}

	@Override
	public Fragment getFragment() {
		return null;
	}

	@Override
	public AbstractRender getRenderer(final Activity activity) {
		return null;
	}

	@Override
	public AbstractRender getRenderer(final Activity activity) {
		return null;
	}

	@Override
	public View getView() {
		return null;
	}

	@Override
	public void invalidate() {

	}

	@Override
	public void needsRedraw() {

	}

	@Override
	public void setView(final View convertView) {

	}

	@Override
	public void setView(final View convertView) {

	}
}

// - UNUSED CODE ............................................................................................
//[01]
