package org.dimensinfin.android.mvc.support;

import org.dimensinfin.android.mvc.annotations.BindField;
import org.dimensinfin.android.mvc.annotations.GenerateMVC;
import org.dimensinfin.core.domain.Node;

@GenerateMVC(layout = TestModel4Render.LAYOUT_NUMBER, onClickFeature = false)
public class TestModel4Render extends Node {
	public final static int LAYOUT_NUMBER = 321654;
	public final static int FIELD_NUMBER = 321654;
	@BindField(FIELD_NUMBER)
	private String name;

	private TestModel4Render() {}

	public String getName() {
		return name;
	}

	public TestModel4Render setName( final String name ) {
		this.name = name;
		return this;
	}

	// - B U I L D E R
	public static class Builder {
		private TestModel4Render onConstruction;

		public Builder() {
			this.onConstruction = new TestModel4Render();
		}

		public TestModel4Render build() {
			return this.onConstruction;
		}
	}
}
