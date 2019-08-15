package org.dimensinfin.android.mvc.acceptance.controller;

import android.content.Context;

import androidx.annotation.NonNull;

import org.dimensinfin.android.mvc.TitlePanel;
import org.dimensinfin.android.mvc.acceptance.TestIdentifiers;
import org.dimensinfin.android.mvc.acceptance.activity.AcceptanceActivity;
import org.dimensinfin.android.mvc.acceptance.renders.ExceptionTitlePanelRender;
import org.dimensinfin.android.mvc.acceptance.renders.TitlePanelRender;
import org.dimensinfin.android.mvc.controller.AndroidController;
import org.dimensinfin.android.mvc.interfaces.IControllerFactory;
import org.dimensinfin.android.mvc.interfaces.IRender;

public class TitlePanelController extends AndroidController<TitlePanel> {
	public TitlePanelController( @NonNull final TitlePanel model, @NonNull final IControllerFactory factory ) {
		super(model, factory);
	}

	@Override
	public IRender buildRender( final Context context ) {
		if (this.getRenderMode().equalsIgnoreCase(TestIdentifiers.MVC01_01.name()))
			return new ExceptionTitlePanelRender(this, context);
		else return new TitlePanelRender(this, context);
	}
}
