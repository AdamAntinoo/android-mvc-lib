package org.dimensinfin.mvc.demo.domain;

import org.apache.commons.lang3.NotImplementedException;

public enum Colours {
	WHITE( 1, "White" ),
	RED( 2, "Red" ),
	GREEN( 3, "Green" ),
	BLUE( 4, "Blue" );

	int code;
	String label;

	Colours( final int numCode, final String label ) {
		this.code = numCode;
		this.label = label;
	}

	public int getCode() {
		return this.code;
	}

	public String getLabel() {
		return this.label;
	}

	public static Colours decode( final int targetCode ) {
		for (Colours color : Colours.values())
			if (color.getCode() == targetCode) return color;
		throw new NotImplementedException( "The colour code is not on the list of defined colours." );
	}
}