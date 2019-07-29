package org.dimensinfin.android.mvc.exception;

import java.util.ArrayList;
import java.util.List;

import org.dimensinfin.core.interfaces.ICollaboration;

public class ExceptionReport implements ICollaboration {
	private Exception exceptionDelegate;
	private StackTraceElement element;
	private String exceptionClass;
	private String exceptionMethodName;

	public ExceptionReport( final Exception exceptionDelegate ) {
		this.exceptionDelegate = exceptionDelegate;
	}

	public String getExceptionCode() {
		return this.exceptionDelegate.getClass().getSimpleName();
	}

	public String getMessage() {
		return this.exceptionDelegate.getMessage();
	}

	public String getExceptionClass() {
		return this.exceptionClass;
	}

	public String getExceptionMethodName() {
		return this.exceptionMethodName;
	}

	@Override
	public List<ICollaboration> collaborate2Model( final String variation ) {
		return new ArrayList<>();
	}

	@Override
	public int compareTo( final Object o ) {
		return 0;
	}

	// - B U I L D E R
	public static class Builder {
		private ExceptionReport onConstruction;

		public Builder( final Exception exception ) {
			this.onConstruction = new ExceptionReport(exception);
		}

		public ExceptionReport build() {
			// Expand the report.
			this.onConstruction.element = this.onConstruction.exceptionDelegate.getStackTrace()[0];
			this.onConstruction.exceptionClass = this.onConstruction.element.getClass().getSimpleName();
			this.onConstruction.exceptionMethodName = this.onConstruction.element.getMethodName();
			return this.onConstruction;
		}
	}
}
