package org.dimensinfin.android.mvc.support;

import org.dimensinfin.android.mvc.controller.RootControllerTest;
import org.dimensinfin.android.mvc.controller.SeparatorController;
import org.dimensinfin.android.mvc.factory.ControllerFactory;
import org.dimensinfin.android.mvc.interfaces.IAndroidController;
import org.dimensinfin.android.mvc.interfaces.ICollaboration;
import org.dimensinfin.android.mvc.model.Separator;

public class TestControllerFactory extends ControllerFactory {

	public TestControllerFactory(final String selectedVariant) {
		super(selectedVariant);
	}

	@Override
	public IAndroidController createController(final ICollaboration node) {
		if (node instanceof RootControllerTest.TestNode) {
			return new SeparatorController((RootControllerTest.TestNode) node, this);
		}
		if (node instanceof Separator) {
			return new SeparatorController((Separator) node, this);
		}
		return super.createController(node);
	}
}