package org.dimensinfin.mvc.demo.controller;

import android.content.Context;
import androidx.annotation.NonNull;

import org.dimensinfin.android.mvc.controller.AndroidController;
import org.dimensinfin.android.mvc.domain.IControllerFactory;
import org.dimensinfin.android.mvc.domain.IRender;
import org.dimensinfin.mvc.demo.render.TitledActionBarRender;
import org.dimensinfin.mvc.demo.ui.TitledActionBar;

public class TitledActionBarController extends AndroidController<TitledActionBar> {

	public TitledActionBarController( @NonNull final TitledActionBar model, @NonNull final IControllerFactory factory ) {
		super( model, factory );
	}

	@Override
	public IRender buildRender( final Context context ) {
		return new TitledActionBarRender( this, context );
	}
}
