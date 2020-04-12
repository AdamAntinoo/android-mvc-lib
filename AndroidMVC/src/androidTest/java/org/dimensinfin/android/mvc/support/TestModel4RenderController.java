package org.dimensinfin.android.mvc.support;

import android.content.Context;
import androidx.annotation.NonNull;

import org.dimensinfin.android.mvc.controller.AndroidController;
import org.dimensinfin.android.mvc.domain.IRender;
import org.dimensinfin.android.mvc.factory.IControllerFactory;

public class TestModel4RenderController extends AndroidController<TestModel4Render> {

	public TestModel4RenderController( @NonNull final TestModel4Render model, @NonNull final IControllerFactory factory ) {
		super( model, factory );
	}

	@Override
	public IRender buildRender( final Context context ) {
		return new TestModel4RenderRender( this, context );
	}
}
