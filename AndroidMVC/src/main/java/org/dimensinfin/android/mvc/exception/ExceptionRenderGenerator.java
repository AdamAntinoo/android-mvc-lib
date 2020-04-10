package org.dimensinfin.android.mvc.exception;

import android.content.Context;
import android.view.View;

import java.util.Objects;

import org.dimensinfin.android.mvc.controller.ExceptionReportController;
import org.dimensinfin.android.mvc.factory.IControllerFactory;

public class ExceptionRenderGenerator {
	private Context context;
	private IControllerFactory factory;
	private ExceptionReport exceptionReport;

	private ExceptionRenderGenerator() {
	}

	public View getView() {
		final ExceptionReportController controller = (ExceptionReportController) this.factory.createController( this.exceptionReport );
		final ExceptionReportRender render = (ExceptionReportRender) controller.buildRender( this.context );
		final View view = render.getView();
		render.updateContent(); // Set the initial value for the view fields.
		view.setTag( controller ); // Piggyback the controller to the view to allow access.
		return view;
	}

	// - B U I L D E R
	public static class Builder {
		private ExceptionRenderGenerator onConstruction;

		public Builder() {
			this.onConstruction = new ExceptionRenderGenerator();
		}

		public ExceptionRenderGenerator.Builder withContext( final Context context ) {
			this.onConstruction.context = Objects.requireNonNull( context );
			return this;
		}

		public ExceptionRenderGenerator.Builder withExceptionReport( final ExceptionReport exceptionReport ) {
			this.onConstruction.exceptionReport = Objects.requireNonNull( exceptionReport );
			return this;
		}

		public ExceptionRenderGenerator.Builder withFactory( final IControllerFactory factory ) {
			this.onConstruction.factory = Objects.requireNonNull( factory );
			return this;
		}

		public ExceptionRenderGenerator build() {
			Objects.requireNonNull( this.onConstruction.context );
			Objects.requireNonNull( this.onConstruction.exceptionReport );
			Objects.requireNonNull( this.onConstruction.factory );
			return this.onConstruction;
		}
	}
}
