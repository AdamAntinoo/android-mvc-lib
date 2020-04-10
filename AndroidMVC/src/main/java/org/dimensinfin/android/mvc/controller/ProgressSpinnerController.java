package org.dimensinfin.android.mvc.controller;

import android.content.Context;

import androidx.annotation.NonNull;

import org.dimensinfin.android.mvc.core.IRender;
import org.dimensinfin.core.domain.Node;
import org.dimensinfin.android.mvc.domain.IControllerFactory;
import org.dimensinfin.android.mvc.render.ProgressSpinnerRender;

public class ProgressSpinnerController extends AndroidController<Node> {

	public ProgressSpinnerController( @NonNull final Node model, @NonNull final IControllerFactory factory ) {
		super( model, factory );
	}

	@Override
	public IRender buildRender( final Context context ) {
		return new ProgressSpinnerRender(this,context);
	}
}
