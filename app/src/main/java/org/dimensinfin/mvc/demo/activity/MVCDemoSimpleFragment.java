package org.dimensinfin.mvc.demo.activity;

import org.dimensinfin.android.mvc.activity.MVCPagerFragment;
import org.dimensinfin.android.mvc.datasource.IDataSource;
import org.dimensinfin.android.mvc.factory.IControllerFactory;
import org.dimensinfin.mvc.demo.R;
import org.dimensinfin.mvc.demo.datasource.MVCDemoDataSource;
import org.dimensinfin.mvc.demo.factory.DemoControllerFactory;

public class MVCDemoSimpleFragment extends MVCPagerFragment {
	// - M V C F R A G M E N T
	@Override
	public IControllerFactory createFactory() {
		return new DemoControllerFactory( this.getVariant() );
	}

	@Override
	public IDataSource createDS() {
		return new MVCDemoDataSource.Builder( this.getFactory() )
				       .addIdentifier( this.getVariant() )
				       .addIdentifier( "DEMO" )
				       .withExtras( this.getExtras() )
				       .withVariant( this.getVariant() )
				       .withApplicationName( this.getContext().getResources().getString( R.string.appname ) )
				       .withApplicationVersion( this.getContext().getResources().getString( R.string.appversion ) )
				       .build();
	}
}
