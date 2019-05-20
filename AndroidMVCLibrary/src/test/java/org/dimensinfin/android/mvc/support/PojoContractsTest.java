//  PROJECT:     Android.MVC (A.MVC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2019 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Android API16.
//  DESCRIPTION: Library that defines a generic Model View Controller core classes to be used
//               on Android projects. Defines the Part factory and the Part core methods to manage
//               a generic converter from a Graph Model to a hierarchical Part model that finally will
//               be converted to a Part list to be used on a BaseAdapter tied to a ListView.
package org.dimensinfin.android.mvc.support;

import org.dimensinfin.android.mvc.controller.AAndroidController;
import org.dimensinfin.android.mvc.datasource.AMVCDataSource;
import org.dimensinfin.android.mvc.datasource.DataSourceLocator;
import org.dimensinfin.android.mvc.controller.ControllerFactory;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * @author Adam Antinoo
 */

public class PojoContractsTest {
	private static final ControllerFactory factory = Mockito.mock(ControllerFactory.class);

//	@Test
	public void accessorContract() {
		PojoTestUtils.validateAccessors(AMVCDataSource.class);
		PojoTestUtils.validateAccessors(AAndroidController.class);
//		PojoTestUtils.validateAccessors(RootController.class);
		PojoTestUtils.validateAccessors(SeparatorController.class);
	}

	@Test
	public void equalsContract() {
		// - A A N D R O I D C O N T R O L L E R
		final EmptyNode node1 = new EmptyNode("Test1");
		final EmptyNode node2 = new EmptyNode("Test2");
		final EmptyNode node3 = node1;
		final TestController controller1 = new TestController(node1, factory);
		final TestController controller2 = new TestController(node2, factory);
		final TestController controller3 = new TestController(node3, factory);

		// Asserts
		Assert.assertFalse("Controllers are different.", controller2.equals(controller1));
		Assert.assertFalse("Controllers are different.", controller2.equals(controller3));
//		Assert.assertTrue("Controllers are equal.", controller1.equals(controller3));
	}

	@Test
	public void hashCodeContract() {
		// - A A N D R O I D C O N T R O L L E R
		final EmptyNode node1 = new EmptyNode("Test1");
		final EmptyNode node2 = new EmptyNode("Test2");
		final EmptyNode node3 = node1;
		final TestController controller1 = new TestController(node1, factory);
		final TestController controller2 = new TestController(node2, factory);
		final TestController controller3 = new TestController(node3, factory);

		// Asserts
		Assert.assertNotEquals("Controllers are different.", controller2.hashCode(), controller1.hashCode());
		Assert.assertNotEquals("Controllers are different.", controller2.hashCode(), controller3.hashCode());
//		Assert.assertEquals("Controllers are equal.", controller1.hashCode(), controller3.hashCode());
	}

	@Test
	public void toStringContract() {
		final DataSourceLocator locator = new DataSourceLocator().addIdentifier("TEST");
		final ControllerFactory factory = new TestControllerFactory("TEST");
//		final MVCModelRootNode root = new MVCModelRootNode();
		final Separator separator = new Separator("TEST");

		Assert.assertNotNull(new TestDataSource(locator, factory).toString());
//		Assert.assertNotNull(new RootController(root, factory).toString());
		Assert.assertNotNull(new SeparatorController(separator, factory).toString());
	}
}
