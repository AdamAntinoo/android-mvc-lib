package org.dimensinfin.android.mvc.support;


public enum MassUnit {
	TONS {
		public long toTons( long m ) { return m; }

		public long toKilograms( long m ) { return m * 1000; }
	};

	public double toKilogramsD( final long m ) {
		return m * 1000;
	}
}
