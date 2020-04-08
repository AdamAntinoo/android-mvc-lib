package org.dimensinfin.mvc.demo.domain;

import java.util.Objects;

public class ColorfulFigure {
	private Colours color = Colours.WHITE;
	private Figures figure = Figures.TRIANGLE;

	private ColorfulFigure() {}

	public Colours getColor() {
		return this.color;
	}

	public Figures getFigure() {
		return this.figure;
	}

	// - B U I L D E R
	public static class Builder {
		private ColorfulFigure onConstruction;

		public Builder() {
			this.onConstruction = new ColorfulFigure();
		}

		public ColorfulFigure.Builder withColour( final Colours color ) {
			this.onConstruction.color = Objects.requireNonNull( color );
			return this;
		}
		public ColorfulFigure.Builder withFigure( final Figures figure ) {
			this.onConstruction.figure = Objects.requireNonNull( figure );
			return this;
		}

		public ColorfulFigure build() {
			return this.onConstruction;
		}
	}
}
