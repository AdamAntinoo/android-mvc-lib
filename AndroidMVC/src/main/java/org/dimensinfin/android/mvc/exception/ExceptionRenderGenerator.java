package org.dimensinfin.android.mvc.exception;

import android.content.Context;
import android.view.View;

import org.dimensinfin.android.mvc.controller.ExceptionController;
import org.dimensinfin.android.mvc.interfaces.IControllerFactory;

import java.util.Objects;

public class ExceptionRenderGenerator {
	private Exception exception;
	private ExceptionReport report;
	private ExceptionController controller;
	private ExceptionController.ExceptionRender render;
	// - C O M P O N E N T S
	private Context context;
	private IControllerFactory factory;

	private ExceptionRenderGenerator( final Exception exception ) {
		this.exception = exception;
		this.report = new ExceptionReport.Builder(this.exception).build();
	}

	public View getView() {
		final View view = this.render.getView();
		this.render.updateContent(); // Set the initial value for the view fields.
		view.setTag(this.controller); // Piggyback the controller to the view to allow access.
		return view;
	}

	// - B U I L D E R
	public static class Builder {
		private ExceptionRenderGenerator onConstruction;

		public Builder( final Exception exception ) {
			this.onConstruction = new ExceptionRenderGenerator(exception);
		}

		public ExceptionRenderGenerator.Builder withContext( final Context context ) {
			this.onConstruction.context = context;
			return this;
		}

		public ExceptionRenderGenerator.Builder withFactory( final IControllerFactory factory ) {
			this.onConstruction.factory = factory;
			return this;
		}

		public ExceptionRenderGenerator build() {
			Objects.requireNonNull(this.onConstruction.context);
			Objects.requireNonNull(this.onConstruction.factory);
			this.onConstruction.controller =
					(ExceptionController) this.onConstruction.factory.createController(this.onConstruction.report);
			this.onConstruction.render = (ExceptionController.ExceptionRender) this.onConstruction.controller.buildRender(
					this.onConstruction.context);
			return this.onConstruction;
		}
	}
}
