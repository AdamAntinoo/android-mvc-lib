package org.dimensinfin.android.mvc.demo.services;

public class LabelGeneratorModule {
	private LabelGeneratorModule() {}

	// - B U I L D E R
	public static class Builder {
		private LabelGeneratorModule onConstruction;

		public Builder() {
			this.onConstruction = new LabelGeneratorModule();
		}

		public LabelGeneratorModule build() {
			return this.onConstruction;
		}
	}
}
