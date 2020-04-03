package org.dimensinfin.android.mvc.support;

import org.dimensinfin.android.mvc.datasource.MVCDataSource;

public class Test4MVCDataSource extends MVCDataSource {
	private Test4MVCDataSource() {}

	@Override
	public void prepareModel() throws Exception {

	}

	@Override
	public void collaborate2Model() {

	}

	// - B U I L D E R
	public static class Builder extends MVCDataSource.Builder<Test4MVCDataSource,Test4MVCDataSource.Builder>{
		private Test4MVCDataSource onConstruction;

		public Builder() {
			this.onConstruction = new Test4MVCDataSource();
		}

		@Override
		protected Test4MVCDataSource getActual() {
			if ( null == this.onConstruction)this.onConstruction=new Test4MVCDataSource();
			return this.onConstruction;
		}

		@Override
		protected Builder getActualBuilder() {
			return this;
		}

		public Test4MVCDataSource build() {
			super.build();
			return this.onConstruction;
		}
	}
}
