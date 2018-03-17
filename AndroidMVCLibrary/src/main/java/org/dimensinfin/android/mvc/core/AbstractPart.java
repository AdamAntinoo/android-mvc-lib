//	PROJECT:        Android.MVC (A.MVC)
//	AUTHORS:        Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2013-2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:		Android API22.
//	DESCRIPTION:		Library that defines a generic Model View Controller core classes to be used
//									on Android projects. Defines the Part factory and the Part core methods to manage
//									a generic data graph into a Part hierarchy and finally on the Android View to be
//                  used on ListViews.
package org.dimensinfin.android.mvc.core;

import org.dimensinfin.android.mvc.interfaces.IPart;
import org.dimensinfin.android.mvc.interfaces.IPartFactory;
import org.dimensinfin.core.interfaces.ICollaboration;
import org.dimensinfin.core.interfaces.IExpandable;
import org.dimensinfin.core.model.AbstractPropertyChanger;
import org.dimensinfin.core.model.RootNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

// - CLASS IMPLEMENTATION ...................................................................................
public abstract class AbstractPart extends AbstractPropertyChanger implements IPart {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static final long serialVersionUID = 7601587036153405892L;
	protected static Logger logger = LoggerFactory.getLogger("AbstractPart");

	// - F I E L D - S E C T I O N ............................................................................
	protected boolean _clickRunning = false;

	// - F I E L D - S E C T I O N ............................................................................
	/** List of children of the hierarchy. */
	private Vector<IPart> children = new Vector<IPart>();
	/** Tha parent element on the hierarchy chain. Null if this is the Parent and we are a root node. */
	private IPart parent;
	/** Reference to the Model node. */
	private ICollaboration model;
	//	/** Stores the user activation state. Usually becomes true when the users is interacting with the part. */
	//	private boolean active = true;
	private IPartFactory _factory = null;
	private AbstractDataSource _dataSource = null;
	protected String renderMode = "-DEFAULT-";
	//	protected boolean newImplementation = false;

	// - C O N S T R U C T O R - S E C T I O N ................................................................

	/**
	 * Parts are special elements. The root element that is a AbstractPropertyChanger is not responsible to
	 * store the model but needs it as reference to set a parent for notifications. So do not forget to pass the
	 * reference up and store the model at the same time.
	 *
	 * @param model the Data model linked to this part.
	 */
	public AbstractPart (final ICollaboration model) {
		super();
		this.model = model;
		super.setParentChanger(this);
	}

	/**
	 * Parts are special elements. The root element that is a AbstractPropertyChanger is not responsible to
	 * store the model but needs it as reference to set a parent for notifications. So do not forget to pass the
	 * reference up and store the model at the same time.
	 *
	 * @param model the Data model linked to this part.
	 */
	public AbstractPart (final RootNode model, final IPartFactory factory) {
		this(model);
		//		this.model = model;
		_factory = factory;
		//		setParentChanger(this);
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	public void addChild (final IPart child) {
		children.add(child);
	}

	/**
	 * Adds a child <code>EditPart</code> to this EditPart. This method is called from
	 * {@link #refreshChildren()}. The following events occur in the order listed:
	 * <OL>
	 * <LI>The child is added to the {@link #children} List, and its parent is set to <code>this</code>
	 * <LI><code>EditPartListeners</code> are notified that the child has been added.
	 * </OL>
	 * <p>
	 *
	 * @param child The <code>EditPart</code> to add
	 * @param index The index
	 */
	public void addChild (final IPart child, int index) {
		//		Assert.isNotNull(child);
		if ( index == -1 ) {
			index = this.getChildren().size();
		}
		if ( children == null ) {
			children = new Vector<IPart>(2);
		}

		children.add(index, child);
		child.setParent(this);
		//		this.fireChildAdded(child, index);
	}

	public void clean () {
		children.clear();
	}

	/**
	 * The goal of this method is to return the list of Parts on the children list that should be visible and
	 * that collaborates to the ListView population. The default behavior for this method is to check if the
	 * Model behind the Part is expanded, in that case the children have the opportunity to be added to the
	 * visible list.
	 */
	@Deprecated
	public List<IPart> collaborate2View () {
		AbstractPart.logger.info(">< [AbstractPart.collaborate2View]> Collaborator: " + this.getClass().getSimpleName());
		ArrayList<IPart> result = new ArrayList<IPart>();
		// If the node is expanded then give the children the opportunity to also be added.
		if ( this.isExpanded() ) {
			// ---This is the section that is different for any Part. This should be done calling the list of policies.
			List<IPart> ch = this.runPolicies(this.getChildren());
			AbstractPart.logger.info("-- [AbstractPart.collaborate2View]> Collaborator children: " + ch.size());
			// --- End of policies
			for (IPart part : ch) {
				final List<IPart> collaboration = part.collaborate2View();
				AbstractPart.logger.info("-- [AbstractPart.collaborate2View]> Collaboration parts: " + collaboration.size());
				result.addAll(collaboration);
			}
		} else {
			// Check for items that will not shown when empty and not expanded.
			if ( this.isRenderWhenEmpty() ) {
				result.add(this);
			}
		}
		return result;
	}

	public List<IPart> getChildren () {
		if ( children == null ) return new Vector<IPart>(2);
		return children;
	}

	public ICollaboration getModel () {
		return model;
	}

	public IPart getParentPart () {
		return parent;
	}

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
	//	public ArrayList<IPart> getPartChildren () {
	//		return this.collaborate2View();
	//	}

	/**
	 * The factory is set on the Root parts. Most of the other parts do not declare it or is not setup. To
	 * detect this problem and correct if if we detect the null we search for the parent until a factory is
	 * found.
	 */
	public IPartFactory getPartFactory () {
		if ( null == _factory )
			// Search for the factory at the parent. 
			return this.getParentPart().getPartFactory();
		else
			return _factory;
	}

	public String getRenderMode () {
		return renderMode;
	}

	public RootPart getRoot () {
		if ( this.getParentPart() == null ) return (RootPart) this;
		return this.getParentPart().getRoot();
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
	 * This method applies to a concrete set of nodes. Nodes can be of two classes. By itself final leaves or
	 * expandable that can expand hierarchies. Expandable nodes also can collaborate to the MVC.
	 *
	 * @return the model expand state when it applies. False if not expandable.
	 */
	public boolean isExpanded () {
		if ( model instanceof IExpandable ) {
			return ((IExpandable) model).isExpanded();
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
	 *
	 * @return the model expand state when it applies. False if not expandable.
	 */
	public boolean isRenderWhenEmpty () {
		if ( model instanceof IExpandable ) {
			return ((IExpandable) model).isRenderWhenEmpty();
		}
		return true;
	}

	//	@Deprecated
	//	public boolean isVisible () {
	//		return true;
	//	}

	public void propertyChange (final PropertyChangeEvent evt) {
	}

	/*
	 * Updates the set of children EditParts so that it is in sync with the model children. This method is
	 * called from {@link #refresh()}, and may also be called in response to notification from the model. This
	 * method requires linear time to complete. Clients should call this method as few times as possible.
	 * Consider also calling {@link #removeChild(IEditPart)} and {@link #addChild(IEditPart, int)} which run in
	 * constant time.
	 * <p>
	 * The update is performed by comparing the existing EditParts with the set of model children returned from
	 * {@link #getModelChildren()}. EditParts whose models no longer exist are {@link #removeChild(IEditPart)
	 * removed}. New models have their EditParts {@link #createChild(Object) created}.
	 * <p>
	 * This method should <em>not</em> be overridden.
	 *
	 * @see #getModelChildren()
	 */
	public void refreshChildren () {
		AbstractPart.logger.info(">> [AbstractPart.refreshChildren]");
		// Get the list of children for this Part.
		List<IPart> selfChildren = this.getChildren();
		int size = selfChildren.size();
		AbstractPart.logger.info("-- [AbstractPart.refreshChildren]> Current children size: " + size);
		// This variable has the list of Parts pointed by their corresponding model.
		HashMap<ICollaboration, IPart> modelToEditPart = new HashMap<ICollaboration, IPart>(size + 1);
		if ( size > 0 ) {
			for (int i = 0; i < size; i++) {
				IPart editPart = selfChildren.get(i);
				modelToEditPart.put(editPart.getModel(), editPart);
			}
		}

		// Get the list of model elements that collaborate to the Part model. This is the complex-simple model transformation.
		ICollaboration partModel = this.getModel();
		AbstractPart.logger.info("-- [AbstractPart.refreshChildren]> partModel: " + partModel);
		// TODO There are cases where the partModel is null. Try to detect and stop that cases.
		if ( null == partModel ) {
			AbstractPart.logger
					.info("-- [AbstractPart.refreshChildren]> Exception case: partModel is NULL: " + this.toString());
			return;
		}
		final List<ICollaboration> modelObjects = partModel.collaborate2Model(this.getPartFactory().getVariant());
		AbstractPart.logger.info("-- [AbstractPart.refreshChildren]> modelObjects: " + modelObjects);

		// Process the list of model children for this Part.
		int i = 0;
		for (i = 0; i < modelObjects.size(); i++) {
			ICollaboration nodemodel = (ICollaboration) modelObjects.get(i);

			// Do a quick check to see if editPart[i] == model[i]
			IPart editPart = modelToEditPart.get(nodemodel);
			if ( (i < selfChildren.size()) && (selfChildren.get(i).getModel() == nodemodel) ) {
				// But in any case try to update all the children
				AbstractPart.logger.info("-- [AbstractPart.refreshChildren]> model matches. Refreshing children.");
				if ( editPart != null ) {
					editPart.refreshChildren();
				}
				continue;
			}

			// Look to see if the EditPart is already around but in the wrong location
			//			editPart = (AbstractPart) modelToEditPart.get(model);

			if ( editPart != null ) {
				AbstractPart.logger.info("-- [AbstractPart.refreshChildren]> model found but out of order.");
				this.reorderChild(editPart, i);
				editPart.refreshChildren();
			} else {
				// An EditPart for this model doesn't exist yet. Create and insert one.
				editPart = this.createChild(nodemodel);
				AbstractPart.logger.info("-- [AbstractPart.refreshChildren]> New Part: " + editPart);
				// If the factory is unable to create the Part then skip this element or wait to be replaced by a dummy
				if ( null != editPart ) {
					this.addChild(editPart, i);
					editPart.refreshChildren();
				}
			}
		}

		// Remove the remaining EditParts
		size = selfChildren.size();
		if ( i < size ) {
			Vector<IPart> trash = new Vector<IPart>(size - i);
			for (; i < size; i++) {
				trash.add(selfChildren.get(i));
			}
			for (i = 0; i < trash.size(); i++) {
				IPart ep = trash.get(i);
				this.removeChild(ep);
			}
		}
		AbstractPart.logger.info("<< [AbstractPart.refreshChildren]> Content size: " + this.getChildren().size());
	}

	public boolean clickRunning () {
		return _clickRunning;
	}

	public boolean activateClick () {
		_clickRunning = true;
		return _clickRunning;
	}

	public boolean completeClick () {
		_clickRunning = false;
		return _clickRunning;
	}

//	public abstract List<IPart> runPolicies (List<IPart> targets);

//	public boolean runDependencies () {
//		return true;
//	}
//	//	public void setActive (final boolean active) {
//	//		this.active = active;
//	//	}

	public IPart setDataStore (final AbstractDataSource ds) {
		_dataSource = ds;
		return this;
	}

	public IPart setFactory (final IPartFactory partFactory) {
		_factory = partFactory;
		return this;
	}

	/**
	 * Set the primary model object that this EditPart represents. This method is used by an
	 * <code>EditPartFactory</code> when creating an EditPart.
	 */
	public void setModel (final ICollaboration model) {
		this.model = model;
	}

	/**
	 * Sets the parent EditPart. There is no reason to override this method.
	 */
	public void setParent (final IPart parent) {
		this.parent = parent;
	}

	public IPart setRenderMode (final String renderMode) {
		this.renderMode = renderMode;
		return this;
	}
	public boolean toggleExpanded () {
		if ( model instanceof IExpandable ) {
			if ( ((IExpandable) model).isExpanded() ) ((IExpandable) model).collapse();
			else ((IExpandable) model).expand();
			return ((IExpandable) model).isExpanded();
		} else return false;
	}

	public String toString () {
		StringBuffer buffer = new StringBuffer("AbstractPart [");
		buffer.append(model.toString()).append(" ");
		buffer.append("]");
		return buffer.toString();
	}

	/**
	 * Create the Part for the model object received. We have then to have access to the Factory from the root
	 * element and all the other parts should have a reference to the root to be able to do the same.
	 */
	protected IPart createChild (final ICollaboration model) {
		IPartFactory factory = this.getRoot().getPartFactory();
		IPart part = factory.createPart(model);
		// If the factory is unable to create the Part then skip this element or wait to be replaced by a dummy
		if ( null != part ) {
			part.setParent(this);
		}
		return part;
	}

	/**
	 * Removes a child <code>EditPart</code>. This method is called from {@link #refreshChildren()}. The
	 * following events occur in the order listed:
	 * <OL>
	 * <LI><code>EditPartListeners</code> are notified that the child is being removed
	 * <LI><code>deactivate()</code> is called if the child is active
	 * <LI>IEditPart#removeNotify is called on the child.
	 * <LI>removeChildVisual(IEditPart) is called to remove the child's visual object.
	 * <LI>The child's parent is set to <code>null</code>
	 * </OL>
	 * <p>
	 * Subclasses should implement removeChildVisual(IEditPart).
	 *
	 * @param child EditPart being removed
	 */
	protected void removeChild (final IPart child) {
		//		Assert.isNotNull(child);
		int index = this.getChildren().indexOf(child);
		if ( index < 0 ) return;
		//		this.fireRemovingChild(child, index);
		child.setParent(null);
		this.getChildren().remove(child);
	}

	/**
	 * Moves a child <code>EditPart</code> into a lower index than it currently occupies. This method is called
	 * from {@link #refreshChildren()}.
	 *
	 * @param editpart the child being reordered
	 * @param index    new index for the child
	 */
	protected void reorderChild (final IPart editpart, final int index) {
		children.remove(editpart);
		children.add(index, editpart);
	}

	//	private void fireChildAdded (final IPart child, final int index) {
	//		this.fireStructureChange(SystemWideConstants.events.ADD_CHILD.name(), child, index);
	//	}
	//
	//	private void fireRemovingChild (final IPart child, final int index) {
	//		this.fireStructureChange(SystemWideConstants.events.REMOVE_CHILD.name(), child, index);
	//	}
}

// - UNUSED CODE ............................................................................................
