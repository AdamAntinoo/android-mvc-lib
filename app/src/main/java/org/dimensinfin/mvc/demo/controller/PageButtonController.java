package org.dimensinfin.mvc.demo.controller;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import androidx.annotation.NonNull;

import org.dimensinfin.android.mvc.controller.AndroidController;
import org.dimensinfin.android.mvc.domain.IRender;
import org.dimensinfin.android.mvc.exception.MVCException;
import org.dimensinfin.android.mvc.factory.IControllerFactory;
import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.mvc.demo.domain.PageButton;
import org.dimensinfin.mvc.demo.render.PageButtonRender;

public class PageButtonController extends AndroidController<PageButton> implements View.OnClickListener {

	public PageButtonController( @NonNull final PageButton model, @NonNull final IControllerFactory factory ) {
		super( model, factory );
	}

	@Override
	public IRender buildRender( final Context context ) {
		return new PageButtonRender( this, context );
	}

	@Override
	public void onClick( final View v ) {
		LogWrapper.enter();
		final Intent destination;
		try {
			destination = this.getControllerFactory().prepareActivity( this.getModel().getPageName(), v.getContext() );
			v.getContext().startActivity( destination );
		} catch (final MVCException mvce) {
			LogWrapper.error( mvce );
		}
		LogWrapper.exit();
	}
}
