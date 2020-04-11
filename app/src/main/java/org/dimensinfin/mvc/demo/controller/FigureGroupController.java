package org.dimensinfin.mvc.demo.controller;

import android.content.Context;
import androidx.annotation.NonNull;

import org.dimensinfin.android.mvc.controller.ExpandAndroidController;
import org.dimensinfin.android.mvc.domain.Container;
import org.dimensinfin.android.mvc.domain.IRender;
import org.dimensinfin.android.mvc.factory.IControllerFactory;
import org.dimensinfin.mvc.demo.render.FigureGroupRender;

public class FigureGroupController extends ExpandAndroidController<Container> {

	public FigureGroupController( @NonNull final Container model, @NonNull final IControllerFactory factory ) {
		super( model, factory );
	}

	@Override
	public IRender buildRender( final Context context ) {
		return new FigureGroupRender( this, context );
	}
}
