package org.dimensinfin.android.mvc.support;

import org.dimensinfin.android.mvc.controller.AndroidController;
import org.dimensinfin.android.mvc.controller.ControllerFactory;
import org.dimensinfin.android.mvc.datasource.DataSourceLocator;
import org.dimensinfin.android.mvc.datasource.MVCDataSource;
import org.mockito.Mockito;

/**
 * @author Adam Antinoo
 */

public class PojoContractsTest {
	private static final ControllerFactory factory = Mockito.mock(ControllerFactory.class);

//	@Test
	public void accessorContract() {
		PojoTestUtils.validateAccessors(MVCDataSource.class);
		PojoTestUtils.validateAccessors(AndroidController.class);
//		PojoTestUtils.validateAccessors(RootController.class);
//		PojoTestUtils.validateAccessors(SeparatorController.class);
	}

//	@Test
	public void toStringContract() {
		final DataSourceLocator locator = new DataSourceLocator().addIdentifier("TEST");
		final ControllerFactory factory = new TestControllerFactory("TEST");
//		final MVCModelRootNode root = new MVCModelRootNode();
//		final Separator separator = new Separator("TEST");

//		Assert.assertNotNull(new TestDataSource(locator, factory).toString());
//		Assert.assertNotNull(new RootController(root, factory).toString());
//		Assert.assertNotNull(new SeparatorController(separator, factory).toString());
	}
}
