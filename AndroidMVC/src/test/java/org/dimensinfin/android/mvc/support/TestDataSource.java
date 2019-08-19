package org.dimensinfin.android.mvc.support;

import org.dimensinfin.android.mvc.datasource.MVCDataSource;
import org.dimensinfin.android.mvc.datasource.DataSourceLocator;
import org.dimensinfin.android.mvc.interfaces.IControllerFactory;

public class TestDataSource extends MVCDataSource {
//	public TestDataSource(final DataSourceLocator locator, final IControllerFactory controllerFactory) {
//		super(locator, controllerFactory);
//	}

	@Override
	public void prepareModel() {

	}

	//	@Override
//	public IControllerFactory getControllerFactory() {
//		return super.con();
//	}
//
	@Override
	public void collaborate2Model() {
//		final List<ICollaboration> results = new ArrayList<>();
		this.addModelContents(new EmptyNode("Test"));
//		return results;
	}
}
