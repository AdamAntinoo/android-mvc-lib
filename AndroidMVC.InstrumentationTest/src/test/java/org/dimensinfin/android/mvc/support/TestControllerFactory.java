package org.dimensinfin.android.mvc.support;

import org.dimensinfin.android.mvc.controller.ControllerFactory;
import org.dimensinfin.android.mvc.controller.IAndroidController;
import org.dimensinfin.core.interfaces.ICollaboration;

public class TestControllerFactory extends ControllerFactory {

	public TestControllerFactory( final String selectedVariant ) {
		super(selectedVariant);
	}

	@Override
	public IAndroidController createController( final ICollaboration node ) {
		if (node instanceof TestNode)
			return new TestNodeController((TestNode) node, this);
		return super.createController(node);
	}
}
