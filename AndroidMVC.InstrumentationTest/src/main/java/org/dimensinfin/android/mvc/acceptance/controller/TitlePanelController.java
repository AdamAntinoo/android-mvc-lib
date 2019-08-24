package org.dimensinfin.android.mvc.acceptance.controller;

import android.content.Context;

import androidx.annotation.NonNull;

import org.dimensinfin.android.mvc.acceptance.TestIdentifiers;
import org.dimensinfin.android.mvc.acceptance.TitlePanel;
import org.dimensinfin.android.mvc.acceptance.renders.TitlePanelCompleteRender;
import org.dimensinfin.android.mvc.acceptance.renders.TitlePanelFailureRender;
import org.dimensinfin.android.mvc.controller.AndroidController;
import org.dimensinfin.android.mvc.controller.ExceptionController;
import org.dimensinfin.android.mvc.domain.IControllerFactory;
import org.dimensinfin.android.mvc.domain.IRender;

public class TitlePanelController extends AndroidController<TitlePanel> {
	public TitlePanelController( @NonNull final TitlePanel model, @NonNull final IControllerFactory factory ) {
		super(model, factory);
	}

	@Override
	public IRender buildRender( final Context context ) {
		if (this.getRenderMode().equalsIgnoreCase(TestIdentifiers.MVC00.name()))
			return new TitlePanelCompleteRender(this, context);
		if (this.getRenderMode().equalsIgnoreCase(TestIdentifiers.MVC01_01.name()))
			return new TitlePanelFailureRender(this, context);
		if (this.getRenderMode().equalsIgnoreCase(TestIdentifiers.MVC01_02.name()))
			return new TitlePanelCompleteRender(this, context);
		return new ExceptionController.ExceptionRender(this, context);
	}
}
