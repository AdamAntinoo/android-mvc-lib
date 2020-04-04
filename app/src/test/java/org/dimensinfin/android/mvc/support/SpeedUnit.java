package org.dimensinfin.android.mvc.support;


public enum SpeedUnit {
	KMH {
		public Double toKMH( Double m ) { return m; }

		public Double toKMS( Double m ) { return m / 3600.0; }

		public Double toCMS( Double m ) { return m * 1000.0 * 100.0 / 3600.0; }
	},
	KMS {
		public Double toKMH( Double m ) { return m * 3600.0; }

		public Double toKMS( Double m ) { return m; }

		public Double toCMS( Double m ) { return m * 1000.0 * 100.0; }
	},
	CMS {
		public Double toKMH( Double m ) { return m / 1000.0 / 100.0 / 3600.0; }

		public Double toKMS( Double m ) { return m / 1000.0 / 100.0; }

		public Double toCMS( Double m ) { return m; }
	};

	public Double toKMS( final Double m ) {
		throw new AbstractMethodError();
	}

	public Double toCMS( final Double m ) {
		throw new AbstractMethodError();
	}

	public Double toKMH( final Double m ) {
		throw new AbstractMethodError();
	}
}
