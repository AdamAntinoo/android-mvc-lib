//  PROJECT:     Android.MVC (A.MVC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Android API16.
//  DESCRIPTION: Library that defines a generic Model View Controller core classes to be used
//               on Android projects. Defines the Part factory and the Part core methods to manage
//               a generic converter from a Graph Model to a hierarchical Part model that finally will
//               be converted to a Part list to be used on a BaseAdapter tied to a ListView.
//               The new implementation performs the model to list transformation on the fly each time
//               a model change is detected so the population of the displayed view should be done in
//               real time while processing the model sources. This should allow for search and filtering.
package org.dimensinfin.android.mvc.datasource;

import android.os.Bundle;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.dimensinfin.android.mvc.core.PartFactory;
import org.dimensinfin.android.mvc.interfaces.IDataSource;
import org.dimensinfin.android.mvc.interfaces.IPartFactory;
import org.dimensinfin.core.datasource.DataSourceLocator;

// - CLASS IMPLEMENTATION ...................................................................................
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MVCDataSourceTestUnit {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger logger = LoggerFactory.getLogger("MVCDataSourceTestUnit");

	//	@BeforeClass
	//	public static void testOpenAndConnectGlobal() {
	//	}

	// - F I E L D - S E C T I O N ............................................................................

	// - C O N S T R U C T O R - S E C T I O N ................................................................

	// - M E T H O D - S E C T I O N ..........................................................................
	@Test
	public void test01CheckConstructor() {
		logger.info(">> [MVCDataSourceTestUnit.test01CheckConstructor]");
		final String variant = "-VARIANT-";
		final DataSourceLocator locator = new DataSourceLocator()
				.addIdentifier(variant)
				.addIdentifier("TEST");
		IDataSource dstest = new DemoDataSource(locator, variant, new PartFactory(variant), null)
				.setCacheable(true);
		Assert.assertNotNull("-> Validating the DataSource is not empty...", dstest);
		Assert.assertEquals("-> Checking the loaded fields [variant]..."
				, variant
				, dstest.getVariant());
		Assert.assertEquals("-> Checking the loaded fields [locator}..."
				, locator.getIdentity()
				, dstest.getDataSourceLocator().getIdentity());
		logger.info("<< [MVCDataSourceTestUnit.test01CheckConstructor]");
	}

	public static class DemoDataSource extends MVCDataSource {

		public DemoDataSource( final DataSourceLocator locator, final String variant, final IPartFactory factory, final Bundle
				extras ) {
			super(locator, variant, factory, extras);
		}

		@Override
		public void collaborate2Model() {

		}
	}
}