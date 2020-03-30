package org.dimensinfin.android.mvc.demo.domain;

public class ApplicationHeaderTitle {
	private String name;
	private String version;

	private ApplicationHeaderTitle() {}

	// - B U I L D E R
	public static class Builder {
		private ApplicationHeaderTitle onConstruction;

		public Builder() {
			this.onConstruction = new ApplicationHeaderTitle();
		}

		public ApplicationHeaderTitle build() {
			return this.onConstruction;
		}
	}
}
