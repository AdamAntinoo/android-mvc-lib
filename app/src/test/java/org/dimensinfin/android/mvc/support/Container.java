package org.dimensinfin.android.mvc.support;

public class Container<T> {
	public Container() {}

	// - B U I L D E R
	public static class Builder {
		private Container onConstruction;

		public Builder() {
			this.onConstruction = new Container();
		}

		public Container build() {
			return this.onConstruction;
		}
	}
}
