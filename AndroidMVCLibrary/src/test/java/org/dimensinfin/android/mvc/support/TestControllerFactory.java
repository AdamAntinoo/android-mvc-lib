package org.dimensinfin.android.mvc.support;

import org.dimensinfin.android.mvc.controller.ControllerFactory;
import org.dimensinfin.android.mvc.controller.IAndroidController;
import org.dimensinfin.core.interfaces.ICollaboration;
import org.dimensinfin.core.model.Separator;

public class TestControllerFactory extends ControllerFactory {

	public TestControllerFactory(final String selectedVariant) {
		super(selectedVariant);
	}

	@Override
	public IAndroidController createController(final ICollaboration node) {
//		if (node instanceof RootControllerTest.TestNode) {
//			return new SeparatorController((RootControllerTest.TestNode) node, this);
//		}
		if (node instanceof Separator) {
			return new SeparatorController((Separator) node, this);
		}
		if (node instanceof EmptyNode) {
			return new MockController((EmptyNode) node, this);
		}
		return super.createController(node);
	}
}
