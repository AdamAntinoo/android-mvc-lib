package org.dimensinfin.android.mvc.controller;

import android.content.Context;
import androidx.annotation.NonNull;

import org.dimensinfin.android.mvc.domain.IControllerFactory;
import org.dimensinfin.android.mvc.domain.IRender;
import org.dimensinfin.android.mvc.exception.ExceptionReport;
import org.dimensinfin.android.mvc.exception.ExceptionReportRender;

public class ExceptionReportController extends AndroidController<ExceptionReport> {
	public ExceptionReportController( @NonNull final ExceptionReport model, @NonNull final IControllerFactory factory ) {
		super( model, factory );
	}

	@Override
	public IRender buildRender( final Context context ) {
		return new ExceptionReportRender( this, context );
	}
}
