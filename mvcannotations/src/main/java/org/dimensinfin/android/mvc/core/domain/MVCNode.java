package org.dimensinfin.android.mvc.core.domain;

public class MVCNode {
	private MVCNode() {}

	// - B U I L D E R
	public static class Builder {
		private MVCNode onConstruction;

		public Builder() {
			this.onConstruction = new MVCNode();
		}

		public MVCNode build() {
			return this.onConstruction;
		}
	}
}
