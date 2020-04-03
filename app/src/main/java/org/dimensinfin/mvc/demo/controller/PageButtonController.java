package org.dimensinfin.mvc.demo.controller;

import android.content.Context;
import androidx.annotation.NonNull;

import org.dimensinfin.android.mvc.controller.AndroidController;
import org.dimensinfin.android.mvc.domain.IControllerFactory;
import org.dimensinfin.android.mvc.domain.IRender;
import org.dimensinfin.mvc.demo.domain.PageButton;
import org.dimensinfin.mvc.demo.render.PageButtonRender;

public class PageButtonController extends AndroidController<PageButton> {

	public PageButtonController( @NonNull final PageButton model, @NonNull final IControllerFactory factory ) {
		super( model, factory );
	}

	@Override
	public IRender buildRender( final Context context ) {
		return new PageButtonRender( this, context );
	}
}
