package org.dimensinfin.android.mvc.support;

import org.dimensinfin.android.mvc.domain.Container;

public class TestContainerNode extends Container {
	public TestContainerNode() {}

	// - B U I L D E R
	public static class Builder {
		private TestContainerNode onConstruction;

		public Builder() {
			this.onConstruction = new TestContainerNode();
		}

		public TestContainerNode build() {
			return this.onConstruction;
		}
	}
}
