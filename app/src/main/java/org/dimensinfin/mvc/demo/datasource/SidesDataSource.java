package org.dimensinfin.mvc.demo.datasource;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.dimensinfin.android.mvc.domain.Container;
import org.dimensinfin.android.mvc.factory.IControllerFactory;
import org.dimensinfin.mvc.demo.domain.ColorfulFigure;
import org.dimensinfin.mvc.demo.domain.Figures;

public class SidesDataSource extends FigureColorDataSource {
	private Map<Figures, Container<ColorfulFigure>> groups = new HashMap<>();

	// - I D A T A S O U R C E
	@Override
	public void collaborate2Model() {
		for (ColorfulFigure figure : this.elements) {
			final Figures figureType = figure.getFigure();
			Container<ColorfulFigure> hit = this.groups.get( figureType );
			if (null == hit) {
				hit = new Container<>();
				this.groups.put( figureType, hit );
			}
			hit.addContent( figure );
		}
		for (Container<ColorfulFigure> group : this.groups.values())
			this.addModelContents( group );
	}

	// - B U I L D E R
	public static class Builder extends FigureColorDataSource.Builder<SidesDataSource, SidesDataSource.Builder> {
		private SidesDataSource onConstruction;

		public Builder( final IControllerFactory factory ) {
			Objects.requireNonNull( factory );
			this.onConstruction = new SidesDataSource();
			this.withFactory( factory );
		}

		@Override
		protected SidesDataSource getActual() {
			if (null == this.onConstruction) this.onConstruction = new SidesDataSource();
			return this.onConstruction;
		}

		@Override
		protected SidesDataSource.Builder getActualBuilder() {
			return this;
		}

		public SidesDataSource build() {
			super.build();
			return this.onConstruction;
		}
	}
}
