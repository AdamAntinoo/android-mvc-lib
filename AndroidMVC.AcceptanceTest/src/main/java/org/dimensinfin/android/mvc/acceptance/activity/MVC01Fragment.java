package org.dimensinfin.android.mvc.acceptance.activity;

import org.dimensinfin.android.mvc.TitlePanel;
import org.dimensinfin.android.mvc.activity.MVCPagerFragment;
import org.dimensinfin.android.mvc.datasource.IDataSource;
import org.dimensinfin.android.mvc.datasource.MVCDataSource;
import org.dimensinfin.android.mvc.interfaces.IControllerFactory;

public class MVC01Fragment extends MVCPagerFragment {
	@Override
	public IControllerFactory createFactory() {
		return new AcceptanceControllerFactory(this.getVariant());
	}

	@Override
	public IDataSource createDS() {
		return new MVC01DataSource.Builder()
				       .addIdentifier(this.getVariant())
				       .withVariant(this.getVariant())
				       .withExtras(this.getExtras())
				       .withFactory(this.getFactory())
				       .build();
	}

	public static class MVC01DataSource extends MVCDataSource {

		@Override
		public void prepareModel() {
		}

		@Override
		public void collaborate2Model() {
//			this.addHeaderContents(new TitlePanel.Builder().withTitle("MVC-Acceptance").build());
			this.addModelContents(new TitlePanel.Builder().withTitle("MVC-Acceptance").build());
		}

		public static class Builder extends MVCDataSource.BaseBuilder<MVC01DataSource, MVC01DataSource.Builder> {
			private MVC01DataSource onConstruction;

			@Override
			protected MVC01DataSource getActual() {
				if (null == this.onConstruction) this.onConstruction = new MVC01DataSource();
				return this.onConstruction;
			}

			@Override
			protected Builder getActualBuilder() {
				return this;
			}
		}
	}
}
