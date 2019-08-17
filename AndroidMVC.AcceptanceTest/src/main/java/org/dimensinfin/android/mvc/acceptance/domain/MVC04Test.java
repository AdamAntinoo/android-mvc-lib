package org.dimensinfin.android.mvc.acceptance.domain;

public class MVC04Test {
	// - B U I L D E R
	public static class Builder {
		private MVC04Test onConstruction;

		public Builder() {
			this.onConstruction = new MVC04Test();
		}

		public MVC04Test build() {
			return this.onConstruction;
		}
	}
}
