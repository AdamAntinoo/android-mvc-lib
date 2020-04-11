package org.dimensinfin.mvc.demo.controller;

import android.content.Context;

import org.dimensinfin.android.mvc.domain.IRender;
import org.dimensinfin.android.mvc.factory.IControllerFactory;
import org.dimensinfin.mvc.demo.domain.ColorfulFigure;
import org.dimensinfin.mvc.demo.domain.ColorfulFigureController$Base;

public class ColorfulFigureController extends ColorfulFigureController$Base {

	public ColorfulFigureController( final ColorfulFigure model, final IControllerFactory factory ) {
		super( model, factory );
	}

	@Override
	public IRender buildRender( final Context context ) {
		return null;
	}
}
