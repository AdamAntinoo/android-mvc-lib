package org.dimensinfin.android.mvc.demo.activity;

import androidx.annotation.NonNull;

import java.security.acl.Group;

import org.dimensinfin.android.mvc.controller.ControllerFactory;
import org.dimensinfin.android.mvc.controller.IAndroidController;
import org.dimensinfin.android.mvc.demo.R;
import org.dimensinfin.android.mvc.domain.IControllerFactory;
import org.dimensinfin.core.interfaces.ICollaboration;

/**
 * @author Adam Antinoo
 */
public class DemoControllerFactory extends ControllerFactory implements IControllerFactory {
	// - C O N S T R U C T O R S
	public DemoControllerFactory(@NonNull final String selectedVariant) {
		super(selectedVariant);
	}

	/**
	 * The method should create the matching controller for the model received. We can use the variant to change at
	 * creation time the controller or to replace controllers when required.
	 */
	@Override
	public IAndroidController createController( final ICollaboration node) {
		logger.info("-- [DemoControllerFactory.createController]> Node class: " + node.getClass().getSimpleName());
		if (node instanceof ListElement) {
			// These shows the selected Separator but with a different render.
			return new DemoHeaderTitleController((DemoHeaderTitle) node, this)
					.setIconReference( R.drawable.arrowleft)
					.setRenderMode(this.getVariant());
		}
		if (node instanceof DemoContainer) {
			// These shows the selected Separator but with another rendering.
			return new DemoContainerController((DemoContainer) node, this)
					.setRenderMode(this.getVariant());
		}

		// Demo classes and models
		if (node instanceof DemoLabelCounter) {
			// These shows the selected Separator but with another rendering.
			return new DemoLabelCounterController((DemoLabelCounter) node, this).setRenderMode("-LABEL-");
		}
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
		if (node instanceof Group) {
			// These shows the selected Separator but with another rendering.
			return new DemoItemAndroidController((DemoLabel) node, this).setRenderMode("-LABEL-");
		}
		// If no part is trapped then call the parent chain until one is found.
		return super.createController(node);
	}
}