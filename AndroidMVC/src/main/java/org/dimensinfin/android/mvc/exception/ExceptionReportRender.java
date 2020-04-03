package org.dimensinfin.android.mvc.exception;

public class ExceptionReportRender {
	private ExceptionReportRender() {}

	// - B U I L D E R
	public static class Builder {
		private ExceptionReportRender onConstruction;

		public Builder() {
			this.onConstruction = new ExceptionReportRender();
		}

		public ExceptionReportRender build() {
			return this.onConstruction;
		}
	}
}
