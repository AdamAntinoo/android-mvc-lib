package org.dimensinfin.android.mvc.support;

import org.dimensinfin.android.mvc.controller.ControllerFactory;
import org.dimensinfin.android.mvc.controller.IAndroidController;
import org.dimensinfin.android.mvc.domain.Spacer;
import org.dimensinfin.core.interfaces.ICollaboration;

public class TestControllerFactory extends ControllerFactory {

	public TestControllerFactory(final String selectedVariant) {
		super(selectedVariant);
	}

	@Override
	public IAndroidController createController(final ICollaboration node) {
//		if (node instanceof RootControllerTest.TestNode) {
//			return new SeparatorController((RootControllerTest.TestNode) node, this);
//		}
		if (node instanceof Spacer) {
			return new SpacerController((Spacer) node, this);
		}
		if (node instanceof EmptyNode) {
			return new MockController((EmptyNode) node, this);
		}
		return super.createController(node);
	}
}
