package org.dimensinfin.android.mvc.controller;

import android.content.Context;

import androidx.annotation.NonNull;

import org.dimensinfin.android.mvc.core.IRender;
import org.dimensinfin.android.mvc.core.MVCNode;
import org.dimensinfin.android.mvc.domain.IControllerFactory;
import org.dimensinfin.android.mvc.render.ProgressSpinnerRender;

public class ProgressSpinnerController extends AndroidController<MVCNode> {

	public ProgressSpinnerController( @NonNull final MVCNode model, @NonNull final IControllerFactory factory ) {
		super( model, factory );
	}

	@Override
	public IRender buildRender( final Context context ) {
		return new ProgressSpinnerRender(this,context);
	}
}
