package org.dimensinfin.android.mvc.acceptance.activity;

import org.dimensinfin.android.mvc.acceptance.TestIdentifiers;
import org.dimensinfin.android.mvc.acceptance.TitlePanel;
import org.dimensinfin.android.mvc.activity.MVCPagerFragment;
import org.dimensinfin.android.mvc.datasource.IDataSource;
import org.dimensinfin.android.mvc.datasource.MVCDataSource;
import org.dimensinfin.android.mvc.domain.IControllerFactory;

public class MVC02Fragment extends MVCPagerFragment {
	@Override
	public IControllerFactory createFactory() {
		return new AcceptanceControllerFactory(this.getVariant());
	}

	@Override
	public IDataSource createDS() {
		if (this.getVariant().equalsIgnoreCase(TestIdentifiers.MVC02_01.name()))
			return null;
		if (this.getVariant().equalsIgnoreCase(TestIdentifiers.MVC02_02.name()))
			return new MVC02DataSource.Builder()
					       .addIdentifier(this.getVariant())
					       .withVariant(this.getVariant())
					       .withExtras(this.getExtras())
					       .withFactory(this.getFactory())
					       .build();
		return null;
	}

	public static class MVC02DataSource extends MVCDataSource {

		@Override
		public void prepareModel() {
			throw new NullPointerException("Exception generated while on 'prepareModel' phase.");
		}

		@Override
		public void collaborate2Model() {
			this.addHeaderContents(new TitlePanel.Builder().withTitle("MVC-Acceptance").build());
			this.addModelContents(new TitlePanel.Builder().withTitle("MVC-Acceptance").build());
		}

		public static class Builder extends MVCDataSource.Builder<MVC02DataSource, MVC02DataSource.Builder> {
			private MVC02DataSource onConstruction;

			@Override
			protected MVC02DataSource getActual() {
				if (null == this.onConstruction) this.onConstruction = new MVC02DataSource();
				return this.onConstruction;
			}

			@Override
			protected MVC02DataSource.Builder getActualBuilder() {
				return this;
			}
		}
	}
}
