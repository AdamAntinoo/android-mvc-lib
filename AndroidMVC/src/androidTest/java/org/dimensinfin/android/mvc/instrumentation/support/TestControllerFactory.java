package org.dimensinfin.android.mvc.instrumentation.support;

import org.dimensinfin.android.mvc.controller.IAndroidController;
import org.dimensinfin.android.mvc.factory.ControllerFactory;
import org.dimensinfin.core.interfaces.ICollaboration;

public class TestControllerFactory extends ControllerFactory {
	public TestControllerFactory( final String selectedVariant ) {
		super( selectedVariant );
	}

	@Override
	public IAndroidController createController( final ICollaboration node ) {
		return super.createController( node );
	}
}
