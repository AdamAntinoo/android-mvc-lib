package org.dimensinfin.android.mvc.support;

import org.dimensinfin.android.mvc.datasource.AMVCDataSource;
import org.dimensinfin.android.mvc.interfaces.IControllerFactory;
import org.dimensinfin.core.datasource.DataSourceLocator;

public class TestDataSource extends AMVCDataSource {
	public TestDataSource(final DataSourceLocator locator, final IControllerFactory controllerFactory) {
		super(locator, controllerFactory);
	}

	@Override
	public void collaborate2Model() {
	}
}
