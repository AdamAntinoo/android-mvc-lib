package org.dimensinfin.mvc.demo.activity;

import org.apache.commons.lang3.NotImplementedException;

import org.dimensinfin.android.mvc.activity.MVCPagerFragment;
import org.dimensinfin.android.mvc.datasource.IDataSource;
import org.dimensinfin.android.mvc.factory.IControllerFactory;
import org.dimensinfin.mvc.demo.datasource.ColourDataSource;
import org.dimensinfin.mvc.demo.datasource.SidesDataSource;
import org.dimensinfin.mvc.demo.factory.ColourControllerFactory;
import org.dimensinfin.mvc.demo.factory.DemoControllerFactory;

public class MultiPageFragment extends MVCPagerFragment {
	// - M V C F R A G M E N T
	@Override
	public IControllerFactory createFactory() {
		return new ColourControllerFactory( this.getVariant() );
	}

	@Override
	public IDataSource createDS() {
		if (this.getVariant().equalsIgnoreCase( PageDefinitions.BYCOLOUR.name() ))
			return new ColourDataSource.Builder( this.getFactory() )
					       .addIdentifier( this.getVariant() )
					       .withExtras( this.getExtras() )
					       .withVariant( this.getVariant() )
					       .build();
		if (this.getVariant().equalsIgnoreCase( PageDefinitions.BYSIDES.name() ))
			return new SidesDataSource.Builder( this.getFactory() )
					       .addIdentifier( this.getVariant() )
					       .withExtras( this.getExtras() )
					       .withVariant( this.getVariant() )
					       .build();
		throw new NotImplementedException( "There is no data source ready for the type of output required." );
	}
}
