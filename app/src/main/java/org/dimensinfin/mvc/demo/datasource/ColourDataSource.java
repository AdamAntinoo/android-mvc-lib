package org.dimensinfin.mvc.demo.datasource;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.dimensinfin.android.mvc.domain.Container;
import org.dimensinfin.android.mvc.factory.IControllerFactory;
import org.dimensinfin.mvc.demo.domain.ColorfulFigure;
import org.dimensinfin.mvc.demo.domain.Colours;

public class ColourDataSource extends FigureColorDataSource {
	private Map<Colours, Container<ColorfulFigure>> groups = new HashMap<>();

	// - I D A T A S O U R C E
	@Override
	public void collaborate2Model() {
		for (ColorfulFigure figure : this.elements) {
			final Colours color = figure.getColor();
			Container<ColorfulFigure> hit = this.groups.get( color );
			if (null == hit) {
				hit = new Container<>();
				this.groups.put(color,hit);
			}
			hit.addContent( figure );
		}
		for (Container<ColorfulFigure> group : this.groups.values())
			this.addModelContents( group );
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
