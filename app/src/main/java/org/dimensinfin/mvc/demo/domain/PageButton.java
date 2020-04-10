package org.dimensinfin.mvc.demo.domain;

import java.util.Objects;

import org.dimensinfin.core.domain.Node;

public class PageButton extends Node {
	private String label;
	private String pageName;

	private PageButton() {}

	public String getLabel() {
		return this.label;
	}

	public String getPageName() {
		return this.pageName;
	}

	// - B U I L D E R
	public static class Builder {
		private PageButton onConstruction;

		public Builder() {
			this.onConstruction = new PageButton();
		}

		public PageButton.Builder withLabel( final String label ) {
			this.onConstruction.label = Objects.requireNonNull( label );
			return this;
		}

		public PageButton.Builder withPageName( final String pageName ) {
			this.onConstruction.pageName = Objects.requireNonNull( pageName );
			return this;
		}

		public PageButton build() {
			Objects.requireNonNull( this.onConstruction.label );
			Objects.requireNonNull( this.onConstruction.pageName );
			return this.onConstruction;
		}
	}
}
