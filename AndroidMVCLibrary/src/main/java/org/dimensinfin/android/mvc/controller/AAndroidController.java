//  PROJECT:     Android.MVC (A.MVC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2019 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Android API16.
//  DESCRIPTION: Library that defines a generic Model View Controller core classes to be used
//               on Android projects. Defines the AndroidController factory and the AndroidController core methods to manage
//               a generic converter from a Graph Model to a hierarchical AndroidController model that finally will
//               be converted to a AndroidController list to be used on a BaseAdapter tied to a ListView.
package org.dimensinfin.android.mvc.controller;

import android.content.Context;
import android.view.View;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.dimensinfin.android.mvc.interfaces.IAndroidController;
import org.dimensinfin.android.mvc.interfaces.IControllerFactory;
import org.dimensinfin.android.mvc.interfaces.IRender;
import org.dimensinfin.core.interfaces.ICollaboration;
import org.dimensinfin.core.model.AbstractPropertyChanger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * This class will implement the core Android interaction controller on the classic pattern Model-View-Controller
 * special and generic implementation into a library for Android native projects. This controller pattern will be the
 * intermediate connection nodes between the free graph composition of the Model and the final Visual Controller list
 * that will connect the Model and the Render inside the View containers used in the Android interface.
 *
 * On version 4 I have replaced the old GEF AndroidController concept by the traditional Controller and started to add
 * unit test code and replaced old style java code coding by the new advanced code techniques and refactorings.
 * @author Adam Antinoo
 * @since 4.0.0
 */

public abstract class AAndroidController<M extends ICollaboration> implements IAndroidController<M> {
	/** This is the public logger that should be used by all the Controllers. */
	public static final Logger logger = LoggerFactory.getLogger(AAndroidController.class);

	// - F I E L D - S E C T I O N
	/** List of children of the hierarchy. */
	private List<IAndroidController> children = new Vector<>();
	/** This field caches the factory that is set during the construction. */
	private IControllerFactory factory = null;
	/** Reference to the Model node. */
	@Getter
	private M model; // Holds the model node.
	@Getter
	@Setter
	private String renderMode; // Holds the type of the render to be used on this instance.
	@Getter
	@Setter
	private View viewCache; // Caches the render generated view used to the Adapter so it can be reused multiple times.
	private AbstractPropertyChanger eventController=new AbstractPropertyChanger();

	// - C O N S T R U C T O R - S E C T I O N
	protected AAndroidController(final AAndroidController.Builder<M> builder) {
		this.model = builder.model;
		this.factory = builder.factory;
		this.renderMode = builder.renderMode;
	}

	// - G E T T E R S   &   S E T T E R S

	/**
	 * The factory is set on all the Parts during the creation time by the factory itself. This allows to construct any
	 * Model supported by the factory from any AndroidController created by that Factory.
	 */
	public IControllerFactory getControllerFactory() {
		return this.factory;
	}

	public List<IAndroidController> getChildren() {
		if (children == null) return new Vector<IAndroidController>(2);
		return children;
	}

	public void addChild(final IAndroidController child) {
		children.add(child);
	}

	// - I A N D R I D C O N T R O L L E R   I N T E R F A C E

	/**
	 * This is the call that should create and inflate the render UI view. During all other processes the Context is not needed not used but
	 * this is the right moment to get to the Android system and instantiate a new UI element. The Context can be discarded after this moment
	 * since the view is going to be cached and if needed to be constructed again this call will be issued another time.
	 * @param context
	 * @return
	 */
	public abstract IRender getRenderer(final Context context);

	// - I E V E N T E M I T T E R   I N T E R F A C E

	/**
	 * Add a new listener to the list of listeners on the delegated listen and event processing node.
	 * @param listener the new listener to connect to this instance messages.
	 */
	public void addPropertyChangeListener(final PropertyChangeListener listener){
		this.eventController.addPropertyChangeListener(listener);
	}

	// - M E T H O D - S E C T I O N

	/**
	 * The refresh process should optimize the reuse of the already available Parts. We should check for model identity on
	 * the already available parts to be able to reuse one of them. So once we have a model item we search on the list of
	 * available parts for one of them containing a model being that same instance. If found we reuse the
	 * AndroidController. Otherwise we create a new AndroidController for this model node and continue the transformation
	 * process.
	 *
	 * It is a recursive process that it is repeated for each one of the nodes added to the already processing structure
	 * while the nodes have any number of children.
	 *
	 * The process gets the Model attached to the AndroidController we are working with. Then finds the Model children to
	 * reconstruct their parts if they do not have them already and match them to the current list of Parts already
	 * connected to the AndroidController in the work bench. Any discrepancies create or delete the required Parts. Then
	 * we start again this process with the first of the children until all the model nodes have been processed.
	 *
	 * During the transformation process we use the <code>collaborate2Model(final String variant)</code> to generate a
	 * fresh list of nodes from the model instance. We use the <b>Variant</b> to allow the model to generate different
	 * sets of new model instances depending on that parameter. The flexibility of this approach allows a single model
	 * object to generate different outputs for each variant received and this value is set for each different Activity
	 * Page.
	 */
	public void refreshChildren() {
		logger.info(">> [AbstractAndroidController.refreshChildren]");
		// Create the new list of Parts for this node model contents if it have any collaboration.
		M partModel = this.getModel();
		if (null == partModel) {
			logger.warn("WR [AbstractAndroidController.refreshChildren]> Exception case: no Model defined for this AndroidController. {}"
					, this.toString());
			return;
		}
		// Get the new list of children for this model node. Use the Variant for generation discrimination.
		final List<ICollaboration> modelInstances = partModel.collaborate2Model(this.getControllerFactory().getVariant());
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
					logger.info("-- [AbstractAndroidController.refreshChildren]> AndroidController for Model not found. Generating a new one for: {}",
							modelNode.getClass().getSimpleName());
					// Model not found on the current list of Parts. Needs a new one.
					foundPart = this.factory.createController(modelNode);
					// Check if the creation has failed. In that exceptional case skip this model and leave a warning.
					if (null == foundPart) {
						logger.warn("WR [AbstractAndroidController.refreshChildren]> Exception case: Factory failed to generate AndroidController for " +
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
			logger.info("-- [AbstractAndroidController.refreshChildren]> Processing model: {}"
					, partModel.getClass().getSimpleName());
			// The part is already created for leave nodes. Terminate this leave.
			return;
		}
		logger.info("<< [AbstractAndroidController.refreshChildren]> Content size: {}", this.getChildren().size());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof AAndroidController)) return false;
		AAndroidController that = (AAndroidController) o;
		return new EqualsBuilder()
				.append(this.model, that.model)
				.append(this.children, that.children)
				.append(this.renderMode, that.renderMode)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(19, 41)
				.append(this.model)
				.append(this.children)
				.append(this.renderMode)
				.toHashCode();
	}

	@Override
	public String toString() {
		return new StringBuilder("AAndroidController [")
				.append("content count: ").append(this.getChildren().size())
				.append("[ model-> ").append(this.getModel().toString()).append(" ]")
				.append("render.type -> ").append(this.getRenderMode())
				.append(" ]")
				.toString();
	}

	// - B U I L D E R
	public abstract static class Builder<M extends ICollaboration> {
		private M model;
		private IControllerFactory factory;
		private String renderMode;

		public Builder() {
		}

		public Builder(final M model, final IControllerFactory factory) {
			this.model = model;
			this.factory = factory;
		}

		public Builder model(final M model) {
			this.model = model;
			return this;
		}

		public Builder factory(final IControllerFactory factory) {
			this.factory = factory;
			return this;
		}

		public Builder renderMode(final String renderMode) {
			this.renderMode = renderMode;
			return this;
		}

		public abstract AAndroidController build();
	}
}
