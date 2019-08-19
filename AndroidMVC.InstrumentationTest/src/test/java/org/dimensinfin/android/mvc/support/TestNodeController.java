package org.dimensinfin.android.mvc.support;

import android.content.Context;
import androidx.annotation.NonNull;

import org.dimensinfin.android.mvc.controller.AndroidController;
import org.dimensinfin.android.mvc.interfaces.IControllerFactory;
import org.dimensinfin.android.mvc.interfaces.IRender;

public class TestNodeController extends AndroidController<TestNode> {
	//	private boolean visible = true;

	public TestNodeController( @NonNull final TestNode model, @NonNull final IControllerFactory factory ) {
		super(model, factory);
	}
	@Override
	public IRender buildRender( final Context context ) {
		return new TestNodeRender(this, context);
	}
}
