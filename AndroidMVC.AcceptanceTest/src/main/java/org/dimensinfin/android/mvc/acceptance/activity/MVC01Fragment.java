package org.dimensinfin.android.mvc.acceptance.activity;

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
//		return new MVCDataSource() {
//			@Override
//			public void prepareModel() {
//
//			}
//
//			@Override
//			public void collaborate2Model() {
//
//			}
//		};
		return null;
	}
}
