//  PROJECT:     Android.MVC (A.MVC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2019 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Android API16.
//  DESCRIPTION: Library that defines a generic Model View Controller core classes to be used
//               on Android projects. Defines the AndroidController factory and the AndroidController core methods to manage
//               a generic converter from a Graph Model to a hierarchical AndroidController model that finally will
//               be converted to a AndroidController list to be used on a BaseAdapter tied to a ListView.
package org.dimensinfin.android.mvc.controller;

import android.support.annotation.NonNull;
import org.dimensinfin.android.mvc.interfaces.IAndroidController;
import org.dimensinfin.android.mvc.interfaces.IControllerFactory;
import org.dimensinfin.android.mvc.model.MVCRootNode;
import org.dimensinfin.core.interfaces.ICollaboration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class RootController {
	private static final Logger logger = LoggerFactory.getLogger(RootController.class);

	// - F I E L D - S E C T I O N
	/** List of children of the hierarchy. */
	private List<IAndroidController> children = new Vector<>();
	/** This field caches the factory that is set during the construction. */
	private IControllerFactory factory = null;
	private MVCRootNode model; // Holds the model node.

	// - C O N S T R U C T O R - S E C T I O N
	public RootController(@NonNull final MVCRootNode node, @NonNull final IControllerFactory factory) {
		this.model = node;
		this.factory = factory;
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
		if (children == null) this.children = new Vector<IAndroidController>(2);
		return children;
	}

	public void addChild(final IAndroidController child) {
		this.getChildren().add(child);
	}

	public void addChildren(final List<IAndroidController> modelList) {
		for (IAndroidController node : modelList)
			this.addChild(node);
	}

	public void clean() {
		this.getChildren().clear();
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
		// Get the new list of children for this model node. Use the Variant for generation discrimination.
		final List<ICollaboration> firstLevelNodes = this.getModel().collaborate2Model(this.getControllerFactory().getVariant());
		if (firstLevelNodes.isEmpty()) return;
		logger.info("-- [AbstractAndroidController.refreshChildren]> firstLevelNodes count: {}", firstLevelNodes.size());
		// Create the model-controller current map to check the elements missing.
		final HashMap<ICollaboration, IAndroidController> currentMap = new HashMap<>(firstLevelNodes.size());
		for (IAndroidController control : this.getChildren()) {
			currentMap.put(control.getModel(), control);
		}
		// Create the new children list ot be able to delete nodes.
		final List<IAndroidController> newChildrenList = new ArrayList<>(firstLevelNodes.size());
		// Check all the model instances have a matching AndroidController instance.
		for (ICollaboration modelNode : firstLevelNodes) {
			// Search for the model instance on the current controller map.
			if (currentMap.containsKey(modelNode)) {
				currentMap.get(modelNode).refreshChildren();
				newChildrenList.add(currentMap.get(modelNode));
			} else {
				// The controller is non existent for this model node. Create a new one from the factory.
				logger.info("-- [RootController.refreshChildren]> New AndroidController for Model: {}",
						modelNode.getClass().getSimpleName());
				final IAndroidController newController = this.getControllerFactory().createController(modelNode);
				newController.refreshChildren();
				newChildrenList.add(newController);
			}
		}
		// Replace the new children list.
		this.clean();
		this.addChildren(newChildrenList);
		logger.info("<< [AbstractAndroidController.refreshChildren]> Content size: {}", this.getChildren().size());
	}

	/**
	 * Optimized process to generate the list of Parts that should end on the render graphical process. While we are
	 * collecting the data we are feeding it on the final collection list and making it available to the rendering if we
	 * decide to do so by firing any graphical need for update method.
	 *
	 * Models should always return the same number of nodes not depending on presentation states. It is the
	 * AndroidController that should interpret the current visual state to decide which nodes collaborate to the view and
	 * in which order and presentation.
	 * @param contentCollector the list where we are collecting the Parts for rendering.
	 */
	public void collaborate2View(final List<IAndroidController> contentCollector) {
		logger.info(">< [AAndroidController.collaborate2View]> Collaborator: {}", this.getClass().getSimpleName());
		// If the node is expanded then give the children the opportunity to also be added.
		// --- This is the section that is different for any AndroidController. This should be done calling the list of policies.
		List<IAndroidController> ch = this.runPolicies(this.getChildren());
		logger.info("-- [AAndroidController.collaborate2View]> Collaborator children: {}", ch.size());
		// --- End of policies
		for (IAndroidController controller : ch) {
			if (controller instanceof IAndroidController)
				controller.collaborate2View(contentCollector);
		}
	}

	public List<IAndroidController> runPolicies(final List<IAndroidController> targets) {
		return targets;
	}
}