package org.dimensinfin.mvc.demo.domain;

import org.apache.commons.lang3.NotImplementedException;

public enum Figures {
	TRIANGLE( 1, "Triangle" ),
	SQUARE( 2, "Square" ),
	PENTAGON( 3, "Pentagon" ),
	EXAGON( 4, "Exagon" );

	public static Figures decode( final int targetCode ) {
		for (Figures figure : Figures.values())
			if (figure.getSides() == targetCode) return figure;
		throw new NotImplementedException( "The figure is not on the list of defined figures." );
	}
	int sides;
	String label;

	Figures( final int sides, final String label ) {
		this.sides = sides;
		this.label = label;
	}

	public String getLabel() {
		return this.label;
	}

	public int getSides() {
		return this.sides;
	}
}
