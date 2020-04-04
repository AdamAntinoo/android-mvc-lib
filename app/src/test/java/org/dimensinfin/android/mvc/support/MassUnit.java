package org.dimensinfin.android.mvc.support;


public enum MassUnit {
	TONS {
		public Double toTons( Double m ) { return m; }

		public Double toKilograms( Double m ) { return m * 1000.0; }

		public Double toGrams( Double m ) { return m * 1000.0 * 1000.0; }
	},
	KG {
		public Double toTons( Double m ) { return m / 1000.0; }

		public Double toKilograms( Double m ) { return m; }

		public Double toGrams( Double m ) { return m * 1000.0; }
	};

	public Double toTons( final Double m ) {
		throw new AbstractMethodError();
	}

	public Double toKilograms( final Double m ) {
		throw new AbstractMethodError();
	}

	public Double toGrams( final Double m ) {
		throw new AbstractMethodError();
	}
}
