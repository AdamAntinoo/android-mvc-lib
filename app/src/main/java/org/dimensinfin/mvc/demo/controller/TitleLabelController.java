package org.dimensinfin.mvc.demo.controller;

import android.content.Context;
import androidx.annotation.NonNull;

import org.dimensinfin.android.mvc.controller.AndroidController;
import org.dimensinfin.android.mvc.factory.IControllerFactory;
import org.dimensinfin.mvc.demo.domain.TitleLabel;
import org.dimensinfin.mvc.demo.render.TitleLabelRender;
import org.dimensinfin.android.mvc.domain.IRender;

public class TitleLabelController extends AndroidController<TitleLabel> {

	public TitleLabelController( @NonNull final TitleLabel model, @NonNull final IControllerFactory factory ) {
		super( model, factory );
	}

	@Override
	public IRender buildRender( final Context context ) {
		return new TitleLabelRender( this, context );
	}
}
