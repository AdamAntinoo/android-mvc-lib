package org.dimensinfin.android.mvc.controller;

import android.content.Context;

import androidx.annotation.NonNull;

import org.dimensinfin.android.mvc.TitlePanel;
import org.dimensinfin.android.mvc.activity.AcceptanceActivity;
import org.dimensinfin.android.mvc.interfaces.IControllerFactory;
import org.dimensinfin.android.mvc.interfaces.IRender;
import org.dimensinfin.android.mvc.renders.ExceptionTitlePanelRender;
import org.dimensinfin.android.mvc.renders.TitlePanelRender;

public class TitlePanelController extends AndroidController<TitlePanel> {
	public TitlePanelController( @NonNull final TitlePanel model, @NonNull final IControllerFactory factory ) {
		super(model, factory);
	}

	@Override
	public IRender buildRender( final Context context ) {
		if ( this.getRenderMode().equalsIgnoreCase(AcceptanceActivity.TestIdentifiers.MVC01_01.name()))
		return new ExceptionTitlePanelRender(this, context);
		else return new TitlePanelRender(this, context);
	}
}
