package org.dimensinfin.android.mvc.domain;

import java.util.Objects;

import org.dimensinfin.core.domain.Node;

public class Label extends Node {
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
			this.onConstruction.label = Objects.requireNonNull( label );
			return this;
		}

		public Label build() {
			Objects.requireNonNull( this.onConstruction.label );
			return this.onConstruction;
		}
	}
}