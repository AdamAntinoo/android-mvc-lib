package org.dimensinfin.mvc.demo.domain;

import java.util.ArrayList;
import java.util.List;

public class ColorfulFigureGroup {
	private List<ColorfulFigure> contents = new ArrayList<>();

	private ColorfulFigureGroup() {}

	public List<ColorfulFigure> getContents() {
		return this.contents;
	}

	public int addFigure( final ColorfulFigure figure ) {
		this.contents.add( figure );
		return this.contents.size();
	}

	// - B U I L D E R
	public static class Builder {
		private ColorfulFigureGroup onConstruction;

		public Builder() {
			this.onConstruction = new ColorfulFigureGroup();
		}

		public ColorfulFigureGroup build() {
			return this.onConstruction;
		}
	}
}
