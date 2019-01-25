//  PROJECT:     Android.MVC (A.MVC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Android API16.
//  DESCRIPTION: Library that defines a generic Model View Controller core classes to be used
//               on Android projects. Defines the AndroidController factory and the AndroidController core methods to manage
//               a generic converter from a Graph Model to a hierarchical AndroidController model that finally will
//               be converted to a AndroidController list to be used on a BaseAdapter tied to a ListView.
//               The new implementation performs the model to list transformation on the fly each time
//               a model change is detected so the population of the displayed view should be done in
//               real time while processing the model sources. This should allow for search and filtering.
package org.dimensinfin.android.mvc.core;

import org.dimensinfin.android.mvc.interfaces.IAndroidController;
import org.dimensinfin.android.mvc.interfaces.IDataSource;
import org.dimensinfin.android.mvc.interfaces.IEventProjector;
import org.dimensinfin.android.mvc.interfaces.IControllerFactory;
import org.dimensinfin.core.interfaces.ICollaboration;
import org.dimensinfin.core.interfaces.IExpandable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractAndroidController /*extends AbstractPropertyChanger */ extends AndroidController implements IAndroidController {
	// - S T A T I C - S E C T I O N
	private static final long serialVersionUID = 7601587036153405892L;
	protected static Logger logger = LoggerFactory.getLogger(AbstractAndroidController.class);

	// - F I E L D - S E C T I O N


	// - F I E L D - S E C T I O N
	//	/** Stores the user activation state. Usually becomes true when the users is interacting with the part. */
	//	private boolean active = true;
//	private IControllerFactory _factory = null; // This field caches the factory once the hierarchy is run and the factory searched.
	private IDataSource _dataSource = null;
	protected String renderMode = "-DEFAULT-";
	//	protected boolean newImplementation = false;

	// - C O N S T R U C T O R - S E C T I O N
//	public AbstractAndroidController(final T model) {
////		super();
//		this.model = model;
//	}


	/**
	 * Parts are special elements. The root element that is a AbstractPropertyChanger is not responsible to store the
	 * model but needs it as reference to set a parent for notifications. So do not forget to pass the reference up and
	 * store the model at the same time.
	 * @param model the Data model linked to this part.
	 */
//	public AbstractAndroidController(final ICollaboration model) {
//		super(model);
//		// TODO This is a dependency to the removed inheritance for AbstractPropertyChanger
////		super.setParentChanger(this);
//	}

//	/**
//	 * Adds a child <code>EditPart</code> to this EditPart. This method is called from {@link #refreshChildren()}. The
//	 * following events occur in the order listed:
//	 * <OL>
//	 * <LI>The child is added to the {@link #children} List, and its parent is set to <code>this</code>
//	 * <LI><code>EditPartListeners</code> are notified that the child has been added.
//	 * </OL>
//	 * <p>
//	 * @param child The <code>EditPart</code> to add
//	 * @param index The index
//	 */
//	public void addChild(final IAndroidController child, int index) {
//		if (index == -1) {
//			index = this.getChildren().size();
//		}
//		children = getChildren();
//
//		children.add(index, child);
//		child.setParent(this);
//	}

	//	public void clean () {
	//		children.clear();
	//	}
//	public void cleanLinks() {
//		for (IAndroidController part : children) {
//			if (part.getModel() instanceof AbstractPropertyChanger)
//				((AbstractPropertyChanger) part.getModel()).removePropertyChangeListener(part);
//		}
//		children.clear();
//	}


	//	/**
	//	 * Returns the list of parts that are available for this node. If the node it is expanded then the list will
	//	 * include the children and any other grandchildren of this one. If the node is collapsed then the only
	//	 * result will be the node itself. <br>
	//	 * This method is being deprecated and replaced with the <code>collaborate2View</code>. The first change is
	//	 * to add myself only if not empty and the
	//	 *
	//	 * @return list of parts that are accessible for this node.
	//	 */
	//	@Deprecated
	//	public ArrayList<IAndroidController> getPartChildren () {
	//		return this.collaborate2View();
	//	}

//	/**
//	 * The factory is set on the Root parts. Most of the other parts do not declare it or is not set. This method will
//	 * allow any part at any hierarchy level to run on the hierarchy to reach a root node and get the factory from that
//	 * node.
//	 */
//	public IControllerFactory getPartFactory() {
//		if (null == _factory) {
//			// Search for the factory at the root.
//			this._factory = this.getRootPart().getPartFactory();
//		}
//		return _factory;
//	}

	/**
	 * Search for the RootAndroidController up in the hierarchy until the search runts into a node without parent. Then check for the
	 * type and if it is the right type then get the Factory.
	 * @return the hierarchy RootAndroidController.
	 */
	public RootAndroidController getRootPart() {
		if (this instanceof RootAndroidController) return (RootAndroidController) this;
		else return (RootAndroidController) this.getParentPart().getRootPart();
	}

	public String getRenderMode() {
		return renderMode;
	}

	//	public boolean isActive () {
	//		return active;
	//	}

	//	/**
	//	 * This method does not apply to all models so first we check if the model implements the right interface before doing
	//	 * it. The default behavior is that nodes are always downloaded and available.
	//	 *
	//	 * @return the download state of lazy evaluated nodes.
	//	 */
	//	public boolean isDownloaded () {
	//		if ( model instanceof IDownloadable ) {
	//			return ((IDownloadable) model).isDownloaded();
	//		} else return true;
	//	}

	/**
	 * This method applies to a concrete set of nodes. Nodes can be of two classes. By itself final leaves or expandable
	 * that can expand hierarchies. Expandable nodes also can collaborate to the MVC.
	 * @return the model expand state when it applies. False if not expandable.
	 */
	public boolean isExpanded() {
		if (getModel() instanceof IExpandable) {
			return ((IExpandable) getModel()).isExpanded();
		}
		return false;
	}

	//	/**
	//	 * This method should be removed when the new implementation changes all the model node classes.
	//	 *
	//	 * @return true if the node is already compatible with the new MVC implementation.
	//	 */
	//	@Deprecated
	//	public boolean isNewImplemented () {
	//		// TODO This methods should be removed after node rewrite.
	//		return true;
	//	}

	/**
	 * Expandable nodes can also have the proprrty to hide themselves if they are empty or that their collaboration to the
	 * model is empty.
	 * @return the model expand state when it applies. False if not expandable.
	 */
	public boolean isRenderWhenEmpty() {
		if (getModel() instanceof IExpandable) {
			return ((IExpandable) getModel()).isRenderWhenEmpty();
		}
		return true;
	}

	//	@Deprecated
	//	public boolean isVisible () {
	//		return true;
	//	}

	public void propertyChange(final PropertyChangeEvent evt) {
	}


//	public boolean clickRunning() {
//		return _clickRunning;
//	}


	//	public abstract List<IAndroidController> runPolicies (List<IAndroidController> targets);

	//	public boolean runDependencies () {
	//		return true;
	//	}
	//	//	public void setActive (final boolean active) {
	//	//		this.active = active;
	//	//	}

//	public IAndroidController setDataStore( final IDataSource ds ) {
//		_dataSource = ds;
//		return this;
//	}

//	public IAndroidController setFactory(final IControllerFactory partFactory) {
//		_factory = partFactory;
//		return this;
//	}

	public IAndroidController setRenderMode(final String renderMode) {
		this.renderMode = renderMode;
		return this;
	}

	public boolean toggleExpanded() {
		if (getModel() instanceof IExpandable) {
			if (((IExpandable) getModel()).isExpanded()) ((IExpandable) getModel()).collapse();
			else ((IExpandable) getModel()).expand();
			return ((IExpandable) getModel()).isExpanded();
		} else return false;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer("AbstractAndroidController [");
		buffer.append(getModel().toString()).append(" ");
		buffer.append("]");
		return buffer.toString();
	}

//	/**
//	 * Create the AndroidController for the model object received. We have then to have access to the Factory from the root
//	 * element and all the other parts should have a reference to the root to be able to do the same.
//	 */
//	protected IAndroidController createChild( final ICollaboration model ) {
//		IControllerFactory factory = this.getRoot().getPartFactory();
//		IAndroidController part = factory.createPart(model);
//		// If the factory is unable to create the AndroidController then skip this element or wait to be replaced by a dummy
//		if (null != part) {
//			part.setParent(this);
//		}
//		return part;
//	}

//	/**
//	 * Removes a child <code>EditPart</code>. This method is called from {@link #refreshChildren()}. The
//	 * following events occur in the order listed:
//	 * <OL>
//	 * <LI><code>EditPartListeners</code> are notified that the child is being removed
//	 * <LI><code>deactivate()</code> is called if the child is active
//	 * <LI>IEditPart#removeNotify is called on the child.
//	 * <LI>removeChildVisual(IEditPart) is called to remove the child's visual object.
//	 * <LI>The child's parent is set to <code>null</code>
//	 * </OL>
//	 * <p>
//	 * Subclasses should implement removeChildVisual(IEditPart).
//	 * @param child EditPart being removed
//	 */
//	protected void removeChild( final IAndroidController child ) {
//		//		Assert.isNotNull(child);
//		int index = this.getChildren().indexOf(child);
//		if (index < 0) return;
//		//		this.fireRemovingChild(child, index);
//		child.setParent(null);
//		this.getChildren().remove(child);
//	}
//
//	/**
//	 * Moves a child <code>EditPart</code> into a lower index than it currently occupies. This method is called
//	 * from {@link #refreshChildren()}.
//	 * @param editpart the child being reordered
//	 * @param index    new index for the child
//	 */
//	protected void reorderChild( final IAndroidController editpart, final int index ) {
//		children.remove(editpart);
//		children.add(index, editpart);
//	}

	// - I P A R T   I N T E R F A C E
	public abstract List<IAndroidController> runPolicies(final List<IAndroidController> targets);

	/**
	 * Create the AndroidController for the model object received. We have then to have access to the Factory from the root element.
	 * All the parts should have a reference to the root to be able to do that.
	 */
	public IAndroidController createNewPart(final ICollaboration model) {
		IAndroidController part = null;
		IControllerFactory factory = this.getRoot().getPartFactory();
		if (null != factory) {
			// If the factory is unable to create the AndroidController then skip this element or wait to be replaced by a dummy
			part = factory.createPart(model);
			// Connect the new part to its parent.
			if (null != part) {
				part.setParent(this);
				// Connect parts as listeners for fast objects. Watch this connections for AndroidController destruction.
				if (model instanceof IEventProjector)
					((IEventProjector) model).addPropertyChangeListener(this);
			}
		}
		return part;
	}

	/**
	 * The refresh process should optimize the reuse of the already available Parts. We should check for model identity on
	 * the already available parts to be able to reuse one of them. So once we have a model item we search on the list of
	 * available parts for one of them containing a model being that same instance. If found we reuse the AndroidController. Otherwise
	 * we create a new AndroidController for this model node and continue the transformation process.
	 * <p>
	 * It is a recursive process that it is repeated for each one of the nodes added to the already processing structure
	 * while the nodes have any number of children.
	 * <p>
	 * The process gets the Model attached to the AndroidController we are working with. Then finds the Model children to reconstruct
	 * their parts if they do not have them already and match them to the current list of Parts already connected to the
	 * AndroidController in the work bench. Any discrepancies create or delete the required Parts. Then we start again this process
	 * with the first of the children until all the model nodes have been processed.
	 * <p>
	 * During the transformation process we use the <code>collaborate2Model(final String variant)</code> to generate a
	 * fresh list of nodes from the model instance. We use the <b>Variant</b> to allow the model to generate different
	 * sets of new model instances depending on that parameter. The flexibility of this approach allows a single model
	 * object to generate different outputs for each variant received and this value is set for each different Activity
	 * Page.
	 */
	public void refreshChildren() {
		AbstractAndroidController.logger.info(">> [AbstractAndroidController.refreshChildren]");
		// Create the new list of Parts for this node model contents if it have any collaboration.
		ICollaboration partModel = this.getModel();
		if (null == partModel) {
			AbstractAndroidController.logger.warn("WR [AbstractAndroidController.refreshChildren]> Exception case: no Model defined for this AndroidController. {}"
					, this.toString());
			return;
		}
		// Get the new list of children for this model node. Use the Variant for generation discrimination.
		final List<ICollaboration> modelInstances = partModel.collaborate2Model(this.getPartFactory().getVariant());
		if (modelInstances.size() > 0) {
			AbstractAndroidController.logger.info("-- [AbstractAndroidController.refreshChildren]> modelInstances count: " + modelInstances.size());
			// Check all the model instances have a matching AndroidController instance.
			final List<IAndroidController> newPartChildren = new ArrayList<IAndroidController>(modelInstances.size());
			final List<IAndroidController> currentPartChildren = this.getChildren();
			for (ICollaboration modelNode : modelInstances) {
				// Search for the model instance on the current part list.
				IAndroidController foundPart = null;
				for (IAndroidController currentPart : currentPartChildren)
					if (currentPart.getModel().equals(modelNode)) {
						foundPart = currentPart;
						break;
					}
				if (null == foundPart) {
					AbstractAndroidController.logger.info("-- [AbstractAndroidController.refreshChildren]> AndroidController for Model not found. Generating a new one for: {}",
							modelNode.getClass().getSimpleName());
					// Model not found on the current list of Parts. Needs a new one.
					foundPart = createNewPart(modelNode);
					// Check if the creation has failed. In that exceptional case skip this model and leave a warning.
					if (null == foundPart) {
						AbstractAndroidController.logger.warn("WR [AbstractAndroidController.refreshChildren]> Exception case: Factory failed to generate AndroidController for " +
										"model. {}"
								, modelNode.toString());
					}
				}
				// Add to the new list of parts.
				newPartChildren.add(foundPart);
				// Recursively process their children.
				foundPart.refreshChildren();
			}
			// The new list part is complete. Discard the old list and set the new one as the current list of children.
			cleanLinks();
			for (IAndroidController child : newPartChildren) addChild(child);
		} else {
			AbstractAndroidController.logger.info("-- [AbstractAndroidController.refreshChildren]> Processing model: {}"
					, partModel.getClass().getSimpleName());
			// The part is already created for leave nodes. Terminate this leave.
			return;
		}
		AbstractAndroidController.logger.info("<< [AbstractAndroidController.refreshChildren]> Content size: {}", this.getChildren().size());
	}
//	public abstract void collaborate2View( List<IAndroidController> contentCollector );
//	/*
//	 * Updates the set of children EditParts so that it is in sync with the model children. This method is
//	 * called from {@link #refresh()}, and may also be called in response to notification from the model. This
//	 * method requires linear time to complete. Clients should call this method as few times as possible.
//	 * Consider also calling {@link #removeChild(IEditPart)} and {@link #addChild(IEditPart, int)} which run in
//	 * constant time.
//	 * <p>
//	 * The update is performed by comparing the existing EditParts with the set of model children returned from
//	 * {@link #getModelChildren()}. EditParts whose models no longer exist are {@link #removeChild(IEditPart)
//	 * removed}. New models have their EditParts {@link #createChild(Object) created}.
//	 * <p>
//	 * This method should <em>not</em> be overridden.
//	 *
//	 * @see #getModelChildren()
//	 */
//	@Deprecated
//	public void refreshChildren() {
//		AbstractAndroidController.logger.info(">> [AbstractAndroidController.refreshChildren]");
//		// Get the list of children for this AndroidController.
//		List<IAndroidController> selfChildren = this.getChildren();
//		int size = selfChildren.size();
//		AbstractAndroidController.logger.info("-- [AbstractAndroidController.refreshChildren]> Current children size: " + size);
//		// This variable has the list of Parts pointed by their corresponding model.
//		HashMap<ICollaboration, IAndroidController> modelToEditPart = new HashMap<ICollaboration, IAndroidController>(size + 1);
//		if (size > 0) {
//			for (int i = 0; i < size; i++) {
//				IAndroidController editPart = selfChildren.get(i);
//				modelToEditPart.put(editPart.getModel(), editPart);
//			}
//		}
//
//		// Get the list of model elements that collaborate to the AndroidController model. This is the complex-simple model transformation.
//		ICollaboration partModel = this.getModel();
//		AbstractAndroidController.logger.info("-- [AbstractAndroidController.refreshChildren]> partModel: " + partModel);
//		// TODO There are cases where the partModel is null. Try to detect and stop that cases.
//		if (null == partModel) {
//			AbstractAndroidController.logger
//					.info("-- [AbstractAndroidController.refreshChildren]> Exception case: partModel is NULL: " + this.toString());
//			return;
//		}
//		final List<ICollaboration> modelObjects = partModel.collaborate2Model(this.getPartFactory().getVariant());
//		AbstractAndroidController.logger.info("-- [AbstractAndroidController.refreshChildren]> modelObjects: " + modelObjects);
//
//		// Process the list of model children for this AndroidController.
//		int i = 0;
//		for (i = 0; i < modelObjects.size(); i++) {
//			ICollaboration nodemodel = (ICollaboration) modelObjects.get(i);
//
//			// Do a quick check to see if editPart[i] == model[i]
//			IAndroidController editPart = modelToEditPart.get(nodemodel);
//			if ((i < selfChildren.size()) && (selfChildren.get(i).getModel() == nodemodel)) {
//				// But in any case try to update all the children
//				AbstractAndroidController.logger.info("-- [AbstractAndroidController.refreshChildren]> model matches. Refreshing children.");
//				if (editPart != null) {
//					editPart.refreshChildren();
//				}
//				continue;
//			}
//
//			// Look to see if the EditPart is already around but in the wrong location
//			//			editPart = (AbstractAndroidController) modelToEditPart.get(model);
//
//			if (editPart != null) {
//				AbstractAndroidController.logger.info("-- [AbstractAndroidController.refreshChildren]> model found but out of order.");
//				this.reorderChild(editPart, i);
//				editPart.refreshChildren();
//			} else {
//				// An EditPart for this model doesn't exist yet. Create and insert one.
//				editPart = this.createChild(nodemodel);
//				AbstractAndroidController.logger.info("-- [AbstractAndroidController.refreshChildren]> New AndroidController: " + editPart);
//				// If the factory is unable to create the AndroidController then skip this element or wait to be replaced by a dummy
//				if (null != editPart) {
//					this.addChild(editPart, i);
//					editPart.refreshChildren();
//				}
//			}
//		}
//
//		// Remove the remaining EditParts
//		size = selfChildren.size();
//		if (i < size) {
//			Vector<IAndroidController> trash = new Vector<IAndroidController>(size - i);
//			for (; i < size; i++) {
//				trash.add(selfChildren.get(i));
//			}
//			for (i = 0; i < trash.size(); i++) {
//				IAndroidController ep = trash.get(i);
//				this.removeChild(ep);
//			}
//		}
//		AbstractAndroidController.logger.info("<< [AbstractAndroidController.refreshChildren]> Content size: " + this.getChildren().size());
//	}
//	/**
//	 * The goal of this method is to return the list of Parts on the children list that should be visible and
//	 * that collaborates to the ListView population. The default behavior for this method is to check if the
//	 * Model behind the AndroidController is expanded, in that case the children have the opportunity to be added to the
//	 * visible list.
//	 */
//	@Deprecated
//	public List<IAndroidController> collaborate2View() {
//		AbstractAndroidController.logger.info(">< [AbstractAndroidController.collaborate2View]> Collaborator: " + this.getClass().getSimpleName());
//		ArrayList<IAndroidController> result = new ArrayList<IAndroidController>();
//		// If the node is expanded then give the children the opportunity to also be added.
//		if (this.isExpanded()) {
//			// ---This is the section that is different for any AndroidController. This should be done calling the list of policies.
//			List<IAndroidController> ch = this.runPolicies(this.getChildren());
//			AbstractAndroidController.logger.info("-- [AbstractAndroidController.collaborate2View]> Collaborator children: " + ch.size());
//			// --- End of policies
//			for (IAndroidController part : ch) {
//				final List<IAndroidController> collaboration = part.collaborate2View();
//				AbstractAndroidController.logger.info("-- [AbstractAndroidController.collaborate2View]> Collaboration parts: " + collaboration.size());
//				result.addAll(collaboration);
//			}
//		} else {
//			// Check for items that will not shown when empty and not expanded.
//			if (this.isRenderWhenEmpty()) {
//				result.add(this);
//			}
//		}
//		return result;
//	}
}
// - UNUSED CODE ............................................................................................
