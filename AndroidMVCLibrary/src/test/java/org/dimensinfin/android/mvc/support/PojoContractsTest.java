//  PROJECT:     Android.MVC (A.MVC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2019 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Android API16.
//  DESCRIPTION: Library that defines a generic Model View Controller core classes to be used
//               on Android projects. Defines the Part factory and the Part core methods to manage
//               a generic converter from a Graph Model to a hierarchical Part model that finally will
//               be converted to a Part list to be used on a BaseAdapter tied to a ListView.
package org.dimensinfin.android.mvc.support;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.dimensinfin.android.mvc.controller.AAndroidController;
import org.dimensinfin.android.mvc.controller.RootController;
import org.dimensinfin.android.mvc.controller.SeparatorController;
import org.dimensinfin.android.mvc.datasource.AMVCDataSource;
import org.dimensinfin.android.mvc.factory.ControllerFactory;
import org.dimensinfin.android.mvc.model.MVCRootNode;
import org.dimensinfin.core.datasource.DataSourceLocator;
import org.dimensinfin.core.model.Separator;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Adam Antinoo
 */

public class PojoContractsTest {

	@Test
	public void accessorContract() {
		PojoTestUtils.validateAccessors(AMVCDataSource.class);
		PojoTestUtils.validateAccessors(AAndroidController.class);
		PojoTestUtils.validateAccessors(RootController.class);
		PojoTestUtils.validateAccessors(SeparatorController.class);
	}

	//	@Test
	public void equalsContract() {
//		EqualsVerifier.forClass(AMVCDataSource.class).verify();
		EqualsVerifier.forClass(AAndroidController.class).verify();
//		EqualsVerifier.forClass(RootController.class).verify();
//		EqualsVerifier.forClass(SeparatorController.class).verify();
	}

	@Test
	public void toStringContract() {
		final DataSourceLocator locator = new DataSourceLocator().addIdentifier("TEST");
		final ControllerFactory factory = new TestControllerFactory("TEST");
		final MVCRootNode root = new MVCRootNode();
		final Separator separator = new Separator("TEST");

		Assert.assertNotNull(new TestDataSource(locator, factory).toString());
		Assert.assertNotNull(new RootController(root, factory).toString());
		Assert.assertNotNull(new SeparatorController(separator, factory).toString());
	}
}
