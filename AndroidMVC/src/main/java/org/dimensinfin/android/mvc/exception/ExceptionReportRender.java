package org.dimensinfin.android.mvc.exception;

import android.content.Context;
import android.widget.TextView;
import androidx.annotation.NonNull;

import java.util.Objects;

import org.dimensinfin.android.mvc.R;
import org.dimensinfin.android.mvc.controller.ExceptionReportController;
import org.dimensinfin.android.mvc.render.TypedRender;

public class ExceptionReportRender   extends TypedRender<ExceptionReportController> {
	private TextView exceptionCode;
//	private TextView exceptionType;
	private TextView exceptionMessage;
	private TextView exceptionClassName;
	private TextView exceptionMethodName;

	public ExceptionReportRender( @NonNull final ExceptionReportController controller, @NonNull final Context context ) {
		super( controller, context );
	}

	// - I R E N D E R
	@Override
	public int accessLayoutReference() {
		return R.layout.exception4list;
	}

	@Override
	public void initializeViews() {
		this.exceptionCode = Objects.requireNonNull(this.getView().findViewById(R.id.exceptionCode));
//		this.exceptionType = this.getView().findViewById(R.id.exceptionType);
		this.exceptionMessage = Objects.requireNonNull(this.getView().findViewById(R.id.exceptionMessage));
		this.exceptionClassName = Objects.requireNonNull(this.getView().findViewById(R.id.exceptionClassName));
		this.exceptionMethodName = Objects.requireNonNull(this.getView().findViewById(R.id.exceptionMethodName));
	}

	@Override
	public void updateContent() {
		this.exceptionCode.setText(this.getController().getModel().getExceptionCode());
		this.exceptionMessage.setText(this.getController().getModel().getMessage());
//			this.exceptionClassName.setText(this.accessExceptionClass());
		this.exceptionMethodName.setText(this.accessExceptionMethodName());
	}
	//		private String accessExceptionClass(){
//			final String longName = this.getController().getModel().getExceptionClass();
//			return longName.substring (longName.lastIndexOf ('.'));
//		}
	private String accessExceptionMethodName ( ){
		final String longName = this.getController().getModel().getExceptionClass();
		return longName+"."+ this.getController().getModel().getExceptionMethodName();
	}
}
