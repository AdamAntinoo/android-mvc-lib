package org.dimensinfin.mvc.demo.datasource;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.dimensinfin.android.mvc.domain.IControllerFactory;
import org.dimensinfin.mvc.demo.domain.ColorfulFigure;
import org.dimensinfin.mvc.demo.domain.ColorfulFigureGroup;
import org.dimensinfin.mvc.demo.domain.Colours;

public class ColourDataSource extends FigureColorDataSource {
	private Map<Colours, ColorfulFigureGroup> groups = new HashMap<>();

	// - I D A T A S O U R C E
	@Override
	public void collaborate2Model() {
		for (ColorfulFigure figure : this.elements) {
			final Colours color = figure.getColor();
			ColorfulFigureGroup hit = this.groups.get( color );
			if (null == hit)
				hit = new ColorfulFigureGroup.Builder().build();
			hit.addFigure( figure );
		}
	}

	// - B U I L D E R
	public static class Builder extends FigureColorDataSource.Builder<ColourDataSource, ColourDataSource.Builder> {
		private ColourDataSource onConstruction;

		public Builder( final IControllerFactory factory ) {
			Objects.requireNonNull( factory );
			this.onConstruction = new ColourDataSource();
			this.withFactory( factory );
		}

		@Override
		protected ColourDataSource getActual() {
			if (null == this.onConstruction) this.onConstruction = new ColourDataSource();
			return this.onConstruction;
		}

		@Override
		protected ColourDataSource.Builder getActualBuilder() {
			return this;
		}

		public ColourDataSource build() {
			super.build();
			return this.onConstruction;
		}
	}
}
