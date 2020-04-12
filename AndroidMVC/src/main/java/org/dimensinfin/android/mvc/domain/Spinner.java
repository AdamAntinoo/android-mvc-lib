package org.dimensinfin.android.mvc.domain;

import org.dimensinfin.core.domain.Node;

public class Spinner extends Node {
	private String label;

	private Spinner() {}

	public String getLabel() {
		return this.label;
	}

	public Spinner setLabel( final String label ) {
		this.label = label;
		return this;
	}

	// - B U I L D E R
	public static class Builder {
		private Spinner onConstruction;

		public Builder() {
			this.onConstruction = new Spinner();
		}

		public Spinner build() {
			return this.onConstruction;
		}
	}
}
