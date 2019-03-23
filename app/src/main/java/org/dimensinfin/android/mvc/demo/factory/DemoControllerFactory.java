package org.dimensinfin.android.mvc.demo.factory;

import android.support.annotation.NonNull;
import org.dimensinfin.android.mvc.controller.DemoContainerController;
import org.dimensinfin.android.mvc.controller.DemoHeaderTitleController;
import org.dimensinfin.android.mvc.demo.R;
import org.dimensinfin.android.mvc.factory.ControllerFactory;
import org.dimensinfin.android.mvc.interfaces.IAndroidController;
import org.dimensinfin.android.mvc.interfaces.ICollaboration;
import org.dimensinfin.android.mvc.interfaces.IControllerFactory;
import org.dimensinfin.android.mvc.model.DemoContainer;
import org.dimensinfin.android.mvc.model.DemoHeaderTitle;
import org.dimensinfin.android.mvc.model.DemoItem;
import org.dimensinfin.android.mvc.model.DemoLabel;
import org.dimensinfin.android.mvc.controller.DemoItemAndroidController;

/**
 * @author Adam Antinoo
 */
public class DemoControllerFactory extends ControllerFactory implements IControllerFactory {
	// - F I E L D - S E C T I O N

	// - C O N S T R U C T O R - S E C T I O N
	public DemoControllerFactory(@NonNull final String selectedVariant) {
		super(selectedVariant);
	}

	// - M E T H O D - S E C T I O N

	/**
	 * The method should create the matching controller for the model received. We can use the variant to change at
	 * creation time the controller or to replace controllers when required.
	 */
	@Override
	public IAndroidController createController(final ICollaboration node) {
		logger.info("-- [DemoControllerFactory.createController]> Node class: " + node.getClass().getSimpleName());
		if (node instanceof DemoHeaderTitle) {
			// These shows the selected Separator but with a different render.
			return new DemoHeaderTitleController((DemoHeaderTitle) node, this)
					.setIconReference(R.drawable.arrowleft)
					.setRenderMode(this.getVariant());
		}
		if (node instanceof DemoContainer) {
			// These shows the selected Separator but with another rendering.
			return new DemoContainerController((DemoContainer) node, this)
					.setRenderMode(this.getVariant());
		}

		// Demo classes and models
		if (node instanceof DemoItem) {
			// These shows the selected Separator but with another rendering.
			IAndroidController part = new DemoItemAndroidController((DemoItem) node, this).setRenderMode("-ITEM-");
			return part;
		}
		// WARNING - When node classes have direct inheritance put the parent below their children.
		if (node instanceof DemoLabel) {
			// These shows the selected Separator but with another rendering.
			return new DemoItemAndroidController((DemoLabel) node, this).setRenderMode("-LABEL-");
		}
		// If no part is trapped then call the parent chain until one is found.
		return super.createController(node);
	}
}
