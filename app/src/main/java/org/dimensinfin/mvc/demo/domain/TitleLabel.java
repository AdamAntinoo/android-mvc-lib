package org.dimensinfin.mvc.demo.domain;

import java.util.Objects;

import org.dimensinfin.core.domain.Node;

public class TitleLabel extends Node {
	private String title;

	private TitleLabel() {super();}

	public String getTitle() {
		return this.title;
	}

	// - B U I L D E R
	public static class Builder {
		private TitleLabel onConstruction;

		public Builder() {
			this.onConstruction = new TitleLabel();
		}

		public TitleLabel.Builder withTitle( final String title ) {
			Objects.requireNonNull( title );
			this.onConstruction.title = title;
			return this;
		}

		public TitleLabel build() {
			return this.onConstruction;
		}
	}
}
