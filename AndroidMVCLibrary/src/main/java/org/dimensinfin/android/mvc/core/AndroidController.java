//  PROJECT:     Android.MVC (A.MVC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2019 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Android API16.
//  DESCRIPTION: Library that defines a generic Model View Controller core classes to be used
//               on Android projects. Defines the AndroidController factory and the AndroidController core methods to manage
//               a generic converter from a Graph Model to a hierarchical AndroidController model that finally will
//               be converted to a AndroidController list to be used on a BaseAdapter tied to a ListView.
package org.dimensinfin.android.mvc.core;

import lombok.Builder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.dimensinfin.android.mvc.interfaces.IAndroidController;
import org.dimensinfin.android.mvc.interfaces.IControllerFactory;
import org.dimensinfin.core.interfaces.ICollaboration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * This is the new initial part that contains the core code for all parts. This part is able to locate the part factory
 * for this hierarchy and also create new list of parts for the view rendering system. This version used generic model
 * instances to isolate from the different models that can ge manager on the hierarchies.
 * @author Adam Antinoo
 */
@Builder
public abstract class AndroidController<T extends ICollaboration> implements IAndroidController<T> {
	public static Logger logger = LoggerFactory.getLogger(AndroidController.class);

	// - F I E L D - S E C T I O N
	/** List of children of the hierarchy. */
	private List<IAndroidController> children = new Vector<>();
	/** The parent element on the hierarchy chain. Null only if tis is the root of the hierarchy. */
	private IAndroidController parent;
//	/** The root part of the hierarchy. Calculated when the parent is set. If null the parentship has not created. */
//	private IRootPart root;
	/** Reference to the Model node. */
	/** This field caches the factory that is set during the construction. */
	private IControllerFactory factory = null;
	private T model; // Holds the model node
//	private IControllerFactory factory = null; // This field caches the factory once the hierarchy is run and the factory searched.

	// - C O N S T R U C T O R - S E C T I O N
//	public AndroidController( final T model) {
////		super();
//		if (null == model) throw new NullPointerException("The constructor model field should not be null.");
//		this.model = model;
//	}

	// - G E T T E R S   &   S E T T E R S

//	/**
//	 * This can lead to invalid code because the root part can be any part just without a parent. So more tests should be
//	 * run to check that we are not returning an invalid conversion. See the instanceOf.
//	 * @return the root part of the hierarchy.
//	 */
//	public RootAndroidController getRootPart() {
//		if (this.isRoot()) return this;
//		else return this.getParentPart().getRootPart();
////		if (null == this.getParentPart()) return (IAndroidController) this;
////		return this.getParentPart().getRootPart();
//	}

	/**
	 * The factory is set on all the Parts during the creation time by the factory itself. This allows to construct any
	 * Model supported by the factory from any AndroidController created by that Factory.
	 */
	public IControllerFactory getPartFactory() {
		return this.factory;
	}

//	/**
//	 * Used to detect if the node is the root of a hierarchy because there is no more parent upwards.
//	 * @return true if the parent is null and then I am the root.
//	 */
//	public boolean isRoot() {
//		return (null == this.parent) ? true : false;
//	}

	public List<IAndroidController> getChildren() {
		if (children == null) return new Vector<IAndroidController>(2);
		return children;
	}

	public IAndroidController getParentPart() {
		return parent;
	}

	public T getModel() {
		return model;
	}

	/**
	 * Sets the parent AndroidController. There is no reason to override this method.
	 */
	public void setParent(final IAndroidController parent) {
		this.parent = parent;
	}

	// - M E T H O D - S E C T I O N
	public void addChild(final IAndroidController child) {
		children.add(child);
	}

	/**
	 * The refresh process should optimize the reuse of the already available Parts. We should check for model identity on
	 * the already available parts to be able to reuse one of them. So once we have a model item we search on the list of
	 * available parts for one of them containing a model being that same instance. If found we reuse the AndroidController. Otherwise
	 * we create a new AndroidController for this model node and continue the transformation process.
	 *
	 * It is a recursive process that it is repeated for each one of the nodes added to the already processing structure
	 * while the nodes have any number of children.
	 *
	 * The process gets the Model attached to the AndroidController we are working with. Then finds the Model children to reconstruct
	 * their parts if they do not have them already and match them to the current list of Parts already connected to the
	 * AndroidController in the work bench. Any discrepancies create or delete the required Parts. Then we start again this process
	 * with the first of the children until all the model nodes have been processed.
	 *
	 * During the transformation process we use the <code>collaborate2Model(final String variant)</code> to generate a
	 * fresh list of nodes from the model instance. We use the <b>Variant</b> to allow the model to generate different
	 * sets of new model instances depending on that parameter. The flexibility of this approach allows a single model
	 * object to generate different outputs for each variant received and this value is set for each different Activity
	 * Page.
	 */
	public void refreshChildren() {
		AbstractAndroidController.logger.info(">> [AbstractAndroidController.refreshChildren]");
		// Create the new list of Parts for this node model contents if it have any collaboration.
		T partModel = this.getModel();
		if (null == partModel) {
			AbstractAndroidController.logger.warn("WR [AbstractAndroidController.refreshChildren]> Exception case: no Model defined for this AndroidController. {}"
					, this.toString());
			return;
		}
		// Get the new list of children for this model node. Use the Variant for generation discrimination.
		final List<ICollaboration> modelInstances = partModel.collaborate2Model(this.getPartFactory().getVariant());
		if (modelInstances.size() > 0) {
			logger.info("-- [AbstractAndroidController.refreshChildren]> modelInstances count: " + modelInstances.size());
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
					foundPart = this.factory.createPart(modelNode);
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
//			cleanLinks();
			for (IAndroidController child : newPartChildren) addChild(child);
		} else {
			AbstractAndroidController.logger.info("-- [AbstractAndroidController.refreshChildren]> Processing model: {}"
					, partModel.getClass().getSimpleName());
			// The part is already created for leave nodes. Terminate this leave.
			return;
		}
		AbstractAndroidController.logger.info("<< [AbstractAndroidController.refreshChildren]> Content size: {}", this.getChildren().size());
	}

//	/**
//	 * Create the AndroidController for the model object received. We have then to have access to the Factory from the root element.
//	 * All the parts should have a reference to the root to be able to do that.
//	 */
//	public IAndroidController createNewPart(final ICollaboration model) {
//		IAndroidController part = null;
//		IControllerFactory factory = this.getRootPart().getPartFactory();
//		if (null != factory) {
//			// If the factory is unable to create the AndroidController then skip this element or wait to be replaced by a dummy
//			part = factory.createPart(model);
//			// Connect the new part to its parent.
//			if (null != part) {
//				part.setParent(this);
//				// Connect parts as listeners for fast objects. Watch this connections for AndroidController destruction.
//				if (model instanceof IEventEmitter)
//					((IEventEmitter) model).addPropertyChangeListener(this);
//			}
//		}
//		return part;
//	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof AndroidController)) return false;
		AndroidController that = (AndroidController) o;
		return new EqualsBuilder()
//				.append(this.root, that.root)
				.append(this.parent, that.parent)
				.append(this.model, that.model)
				.append(this.children, that.children)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(19, 41)
//				.append(this.root)
				.append(this.parent)
				.append(this.model)
				.append(this.children)
				.toHashCode();
	}

	@Override
	public String toString() {
		return new StringBuilder("AndroidController [")
				.append("content count: ").append(this.children.size())
//				.append("hasParent: ").append(this.isRoot())
				.append("[model-> ").append(this.getModel().toString())
				.append("] ]")
				.toString();
	}

//	public static class Builder<T extends ICollaboration> {
//		// Required parameters
//		private T model;
//		private IControllerFactory factory;
////		private IRootPart root;
////		private IAndroidController parent;
//
//		public Builder(final T model, final IControllerFactory factory) {
//			this.model = model;
//			this.factory = factory;
//		}
//
//		public AndroidController.Builder withModel(final T model) {
//			this.model = model;
//			return this;
//		}
//
//		public AndroidController.Builder withFactory(final IControllerFactory factory) {
//			this.factory = factory;
//			return this;
//		}
//
//		public AndroidController build() {
//			return new AndroidController(this);
//		}
//	}

//	private AndroidController(final Builder<T> builder) {
//		this.model = builder.model;
//		this.factory = builder.factory;
//	}
}

//	/**
//	 * Set the primary model object that this EditPart represents. This method is used by an
//	 * <code>EditPartFactory</code> when creating an EditPart.
//	 */
//	public void setModel(final T model) {
//		this.model = model;
//	}
//	/**
//	 * Parts are special elements. The root element that is a AbstractPropertyChanger is not responsible to store the
//	 * model but needs it as reference to set a parent for notifications. So do not forget to pass the reference up and
//	 * store the model at the same time.
//	 * @param model the Data model linked to this part.
//	 */
//	public AbstractAndroidController(final RootNode model, final IControllerFactory factory) {
//		this(model);
//		//		this.model = model;
//		factory = factory;
//		//		setParentChanger(this);
//	}

