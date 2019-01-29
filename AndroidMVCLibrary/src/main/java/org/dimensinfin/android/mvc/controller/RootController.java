//  PROJECT:     Android.MVC (A.MVC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2019 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Android API16.
//  DESCRIPTION: Library that defines a generic Model View Controller core classes to be used
//               on Android projects. Defines the AndroidController factory and the AndroidController core methods to manage
//               a generic converter from a Graph Model to a hierarchical AndroidController model that finally will
//               be converted to a AndroidController list to be used on a BaseAdapter tied to a ListView.
package org.dimensinfin.android.mvc.controller;

import org.dimensinfin.android.mvc.controller.AAndroidController;
import org.dimensinfin.android.mvc.interfaces.*;
import org.dimensinfin.android.mvc.interfaces.IAndroidAndroidController;
import org.dimensinfin.android.mvc.interfaces.IAndroidController;
import org.dimensinfin.core.model.RootNode;

import java.util.List;

public class RootController extends AAndroidController {

	// - F I E L D - S E C T I O N

	// - C O N S T R U C T O R - S E C T I O N
	public RootController(final RootNode node, final IControllerFactory factory ) {
		super(node, factory);
	}

	// - M E T H O D - S E C T I O N
	@Override
	public boolean isExpanded() {
		return true;
	}

	// - I P A R T   I N T E R F A C E

	/**
	 * Most of root parts should order the result by alphabetical name but this should be configurable. If the
	 * sort flag is active and the nodes are INamed then sort them.
	 */
	@Override
	public List<IAndroidController> runPolicies(final List<IAndroidController> targets ) {
		return targets;
	}
	//	public List<IAndroidController> runPolicies ( final List<IAndroidController> targets ) {
	////		if ( this.doSort() ) {
	//			Collections.sort(targets, partComparator);
	////		}
	//		return targets;
	//	}

	/**
	 * Optimized process to generate the list of Parts that should end on the render graphical process. While we are
	 * collecting the data we are feeding it on the final collection list and making it available to the rendering if we
	 * decide to do so by firing any graphical need for update method.
	 *
	 * Models should always return the same number of nodes not depending on presentation states. It is the AndroidController that
	 * should interpret the current visual state to decide which nodes collaborate to the vien and in which order and
	 * presentation.
	 * @param contentCollector the list where we are collecting the Parts for rendering.
	 */
	public void collaborate2View( final List<IAndroidAndroidController> contentCollector ) {
		AAndroidController.logger.info(">< [RootAndroidPart.collaborate2View]> Collaborator: {}", this.getClass().getSimpleName());
		// If the node is expanded then give the children the opportunity to also be added.
		// --- This is the section that is different for any AndroidController. This should be done calling the list of policies.
		List<IAndroidController> ch = this.runPolicies(this.getChildren());
		AAndroidController.logger.info("-- [AbstractAndroidController.collaborate2View]> Collaborator children: {}", ch.size());
		// --- End of policies
		for (IAndroidController part : ch) {
			if (part instanceof IAndroidController)
				((IAndroidController) part).collaborate2View(contentCollector);
		}
	}

	public boolean isEmpty() {
		if (getChildren().size() > 0) return false;
		else return true;
	}

	// - I R O O T P A R T   I N T E R F A C E
	public void setRootModel( final RootNode rootNode ) {
		setModel(rootNode);
	}
}
// - UNUSED CODE ............................................................................................
