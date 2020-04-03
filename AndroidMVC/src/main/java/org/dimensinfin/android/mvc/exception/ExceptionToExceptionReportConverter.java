package org.dimensinfin.android.mvc.exception;

public class ExceptionToExceptionReportConverter {
	private ExceptionToExceptionReportConverter() {}

	// - B U I L D E R
	public static class Builder {
		private ExceptionToExceptionReportConverter onConstruction;

		public Builder() {
			this.onConstruction = new ExceptionToExceptionReportConverter();
		}

		public ExceptionToExceptionReportConverter build() {
			return this.onConstruction;
		}
	}
}
