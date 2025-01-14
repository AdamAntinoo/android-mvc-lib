package org.dimensinfin.android.mvc.support;

import android.content.Context;

import androidx.annotation.NonNull;

import org.dimensinfin.android.mvc.controller.AndroidController;
import org.dimensinfin.android.mvc.domain.IRender;
import org.dimensinfin.android.mvc.factory.IControllerFactory;

public class TestContainerNodeController extends AndroidController<TestContainerNode> {

	public TestContainerNodeController( @NonNull final TestContainerNode model, @NonNull final IControllerFactory factory ) {
		super(model, factory);
	}

	@Override
	public IRender buildRender( final Context context ) {
		return null;
	}
}
