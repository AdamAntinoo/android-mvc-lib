package org.dimensinfin.android.mvc.acceptance.datasource;

import org.dimensinfin.android.mvc.datasource.MVCDataSource;
import org.dimensinfin.android.mvc.domain.Spacer;
import org.dimensinfin.core.domain.IntercommunicationEvent;

public class MVC04DataSource extends MVCDataSource {
	private boolean prepareModel = false;
	private boolean collaborate2Model = false;

	private MVC04DataSource() {
		super();
	}

	@Override
	public void prepareModel() {
		this.prepareModel = true;
	}

	@Override
	public void collaborate2Model() {
		this.collaborate2Model = true;
		this.addHeaderContents(new Spacer.Builder().withLabel("-MVC04DataSource-").build());
	}

	@Override
	public void receiveEvent( final IntercommunicationEvent event ) {

	}

	// - B U I L D E R
	public static class Builder extends MVCDataSource.Builder<MVC04DataSource, MVC04DataSource.Builder> {
		private MVC04DataSource onConstruction;

		public Builder() {
			this.onConstruction = new MVC04DataSource();
		}

		@Override
		protected MVC04DataSource getActual() {
			if (null == this.onConstruction) this.onConstruction = new MVC04DataSource();
			return this.onConstruction;
		}

		@Override
		protected MVC04DataSource.Builder getActualBuilder() {
			return this;
		}

		public MVC04DataSource build() {
			return this.onConstruction;
		}
	}
}
