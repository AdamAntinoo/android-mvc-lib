package org.dimensinfin.mvc.demo.domain;

import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.dimensinfin.android.mvc.annotations.BindField;
import org.dimensinfin.android.mvc.annotations.GenerateMVC;
import org.dimensinfin.core.domain.Node;
import org.dimensinfin.mvc.demo.R;

@GenerateMVC(layout = R.layout.colorfulfigure4list, onClickFeature = false)
public class ColorfulFigure extends Node {
	private Colours color = Colours.WHITE;
//	@BindField(R.id.figure)
	private Figures figure = Figures.TRIANGLE;
	@BindField(R.id.label)
	private String label;

	private ColorfulFigure() {}

	public Colours getColor() {
		return this.color;
	}

	public Figures getFigure() {
		return this.figure;
	}

	public String getLabel() {
		return this.label;
	}

	@Override
	public String toString() {
		return new ToStringBuilder( this , ToStringStyle.JSON_STYLE)
				       .append( "color", this.color )
				       .append( "figure", this.figure )
				       .append( "label", this.label )
				       .toString();
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

		public ColorfulFigure.Builder withLabel( final String label ) {
			this.onConstruction.label = Objects.requireNonNull( label );
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
