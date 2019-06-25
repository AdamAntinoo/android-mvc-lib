package org.dimensinfin.android.mvc.controller;

import android.content.Context;
import android.widget.TextView;
import androidx.annotation.NonNull;

import org.dimensinfin.android.mvc.R;
import org.dimensinfin.android.mvc.exception.ExceptionReport;
import org.dimensinfin.android.mvc.exception.MVCExceptionHandler;
import org.dimensinfin.android.mvc.interfaces.IControllerFactory;
import org.dimensinfin.android.mvc.interfaces.IRender;
import org.dimensinfin.android.mvc.render.AMVCRender;

public class ExceptionController extends AAndroidController<ExceptionReport> {
	public ExceptionController( @NonNull final ExceptionReport model, @NonNull final IControllerFactory factory ) {
		super(model, factory);
	}

	@Override
	public IRender buildRender( final Context context ) {
		return new ExceptionRender(this, context);
	}

	// - E X C E P T I O N R E N D E R
	public static class ExceptionRender extends AMVCRender {
		private TextView exceptionCode;
		private TextView exceptionType;
		private TextView exceptionMessage;
		private TextView exceptionClassName;
		private TextView exceptionMethodName;

		public ExceptionRender( @NonNull final IAndroidController controller, @NonNull final Context context ) {
			super(controller, context);
		}

		@Override
		public ExceptionController getController() {
			return (ExceptionController) super.getController();
		}

		@Override
		public int accessLayoutReference() {
			return R.layout.exception4list;
		}

		@Override
		public void initializeViews() {
			this.exceptionCode = this.getView().findViewById(R.id.exceptionCode);
			this.exceptionType = this.getView().findViewById(R.id.exceptionType);
			this.exceptionMessage = this.getView().findViewById(R.id.exceptionMessage);
			this.exceptionClassName = this.getView().findViewById(R.id.exceptionClassName);
			this.exceptionMethodName = this.getView().findViewById(R.id.exceptionMethodName);
		}

		@Override
		public void updateContent() {
			this.exceptionCode.setText(this.getController().getModel().getExceptionCode());
			this.exceptionMessage.setText(this.getController().getModel().getMessage());
			this.exceptionClassName.setText(this.getController().getModel().getExceptionClass());
			this.exceptionMethodName.setText(this.getController().getModel().getExceptionMethodName());
		}
	}
}