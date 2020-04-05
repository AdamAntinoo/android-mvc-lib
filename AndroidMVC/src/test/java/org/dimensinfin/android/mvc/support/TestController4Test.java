package org.dimensinfin.android.mvc.support;

import android.content.Context;
import androidx.annotation.NonNull;

import org.mockito.Mockito;

import org.dimensinfin.android.mvc.controller.AndroidController;
import org.dimensinfin.android.mvc.domain.IControllerFactory;
import org.dimensinfin.android.mvc.domain.IRender;
import org.dimensinfin.android.mvc.domain.MVCNode;

public class TestController4Test extends AndroidController<MVCNode> {

	public TestController4Test( @NonNull final MVCNode model, @NonNull final IControllerFactory factory ) {
		super( model, factory );
	}

	@Override
	public IRender buildRender( final Context context ) {
		final IRender render = Mockito.mock( IRender.class );
		return render;
	}
}
