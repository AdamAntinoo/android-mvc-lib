package org.dimensinfin.android.mvc.domain;

import org.dimensinfin.android.mvc.R;
import org.dimensinfin.android.mvc.annotations.BindField;
import org.dimensinfin.android.mvc.annotations.GenerateMVC;
import org.dimensinfin.core.domain.Node;

import static org.dimensinfin.android.mvc.R.layout.label4list;

//@GenerateMVC(layout = R.layout.label4list, onClickFeature = false)
public class Label extends Node {
//	@BindField(R.id.label)
	private String label;

	public Label() {}

	public String getLabel() {
		return this.label;
	}

	public Label setLabel( final String label ) {
		this.label = label;
		return this;
	}

	// - B U I L D E R
	public static class Builder {
		private Label onConstruction;

		public Builder() {
			this.onConstruction = new Label();
		}

		public Label.Builder withLabel( final String label ) {
			this.onConstruction.label = label;
			return this;
		}

		public Label build() {
			return this.onConstruction;
		}
	}
}