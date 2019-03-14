package org.dimensinfin.android.mvc.demo.support;

import org.dimensinfin.android.mvc.controller.DemoHeaderTitleController;
import org.dimensinfin.android.mvc.factory.ControllerFactory;
import org.dimensinfin.android.mvc.model.DemoHeaderTitle;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * @author Adam Antinoo
 */

public class PojoContractsTest {
	private static final ControllerFactory factory = Mockito.mock(ControllerFactory.class);
	private static DemoHeaderTitle headerTitle1;
	private static DemoHeaderTitle headerTitle2;
	private static DemoHeaderTitle headerTitle3;
	private static DemoHeaderTitleController headerTitleController1;
	private static DemoHeaderTitleController headerTitleController2;
	private static DemoHeaderTitleController headerTitleController3;

	@Before
	public void setUp() throws Exception {
		// - D E M O H E A D E R T I T L E
		headerTitle1 = new DemoHeaderTitle("Name 1", "Version");
		headerTitle2 = new DemoHeaderTitle("Name 2", "Version");
		headerTitle3 = headerTitle1;
		// - D E M O H E A D E R T I T L E C O N T R O L L E R
		headerTitleController1 = new DemoHeaderTitleController(headerTitle1, factory);
		headerTitleController2 = new DemoHeaderTitleController(headerTitle2, factory);
		headerTitleController3 = new DemoHeaderTitleController(headerTitle3, factory);
	}

	@Test
	public void accessorContract() {
		PojoTestUtilsDemo.validateAccessors(DemoHeaderTitle.class);
	}

	@Test
	public void equalsContract() {
		// - D E M O H E A D E R T I T L E C O N T R O L L E R
		Assert.assertFalse("Controllers are different.", headerTitleController2.equals(headerTitleController1));
		Assert.assertFalse("Controllers are different.", headerTitleController2.equals(headerTitleController3));
		Assert.assertTrue("Controllers are equal.", headerTitleController1.equals(headerTitleController3));
	}

	@Test
	public void hashCodeContract() {
		// - D E M O H E A D E R T I T L E C O N T R O L L E R
		Assert.assertNotEquals("Controllers are different.", headerTitleController2.hashCode(), headerTitleController1.hashCode());
		Assert.assertNotEquals("Controllers are different.", headerTitleController2.hashCode(), headerTitleController3.hashCode());
		Assert.assertEquals("Controllers are equal.", headerTitleController1.hashCode(), headerTitleController3.hashCode());
	}

	@Test
	public void toStringContract() {
		// - D E M O H E A D E R T I T L E C O N T R O L L E R
		Assert.assertNotNull("To String should not raise an exception.", headerTitleController1.toString());
	}
}
