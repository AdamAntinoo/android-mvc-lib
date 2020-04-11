package org.dimensinfin.android.mvc.controller;

import android.content.Context;

import androidx.annotation.NonNull;

import org.dimensinfin.android.mvc.domain.IRender;
import org.dimensinfin.android.mvc.domain.Spinner;
import org.dimensinfin.android.mvc.factory.IControllerFactory;
import org.dimensinfin.core.domain.Node;
import org.dimensinfin.android.mvc.render.ProgressSpinnerRender;

public class ProgressSpinnerController extends AndroidController<Spinner> {

	public ProgressSpinnerController( @NonNull final Spinner model, @NonNull final IControllerFactory factory ) {
		super( model, factory );
	}

	@Override
	public IRender buildRender( final Context context ) {
		return new ProgressSpinnerRender(this,context);
	}
}
