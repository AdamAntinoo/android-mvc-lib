package org.dimensinfin.android.mvc.demo.services;

public class LabelGenerator {
	private LabelGenerator() {}

	// - B U I L D E R
	public static class Builder {
		private LabelGenerator onConstruction;

		public Builder() {
			this.onConstruction = new LabelGenerator();
		}

		public LabelGenerator build() {
			return this.onConstruction;
		}
	}
}
