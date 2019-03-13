package org.dimensinfin.android.mvc;

import org.dimensinfin.android.mvc.datasource.AMVCDataSource;
import org.dimensinfin.android.mvc.datasource.DataSourceLocator;
import org.dimensinfin.android.mvc.interfaces.IControllerFactory;

public class TestDataSource extends AMVCDataSource {
	public TestDataSource(final DataSourceLocator locator, final IControllerFactory controllerFactory) {
		super(locator, controllerFactory);
	}

	@Override
	public void collaborate2Model() {
	}
}
