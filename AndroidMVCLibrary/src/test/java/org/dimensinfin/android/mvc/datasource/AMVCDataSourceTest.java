package org.dimensinfin.android.mvc.datasource;

import junit.framework.Assert;

import org.dimensinfin.android.mvc.controller.ControllerFactory;
import org.dimensinfin.android.mvc.controller.IAndroidController;
import org.dimensinfin.android.mvc.support.EmptyNode;
import org.dimensinfin.android.mvc.support.PojoTestUtils;
import org.dimensinfin.android.mvc.support.TestController;
import org.dimensinfin.android.mvc.support.TestControllerFactory;
import org.dimensinfin.android.mvc.support.TestDataSource;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;

/**
 * @author Adam Antinoo
 */
public class AMVCDataSourceTest {
	private static final DataSourceLocator locator = new DataSourceLocator().addIdentifier("TEST");
	private static final ControllerFactory factory = new TestControllerFactory("TEST");
//	private static final ICollaboration node = new Separator("TEST");

//	public static class TestNode extends Separator {
//		private List<ICollaboration> children = new ArrayList<>();
//
//		public void addChild(final ICollaboration child) {
//			this.children.add(child);
//		}
//
//		@Override
//		public List<ICollaboration> collaborate2Model(final String variant) {
//			return new ArrayList<ICollaboration>(this.children);
//		}
//	}

	@Test
	public void testAccesors() {
		PojoTestUtils.validateAccessors(TestDataSource.class);
		PojoTestUtils.validateAccessors(AMVCDataSource.class);
	}

//	@Test
	public void addModelContents() {
		// Mocks
		final AMVCDataSource amvcds = Mockito.mock(AMVCDataSource.class);
//		final UIGlobalExecutor executor = Mockito.mock(UIGlobalExecutor.class);

		// Given
		final TestDataSource ds = new TestDataSource(locator, factory);

		// Test
		ds.addModelContents(new EmptyNode("TEST"));

		// Asserts
		// TODO - Test removed after deleting the UIGlobalExecutor
//		Mockito.verify(executor, Mockito.times(1)).submit(() -> {
//			int i = 0;
//		});
	}

	@Test
	public void getDataSectionContents_simpleLinear() {
		// Given
		final TestDataSource ds = new TestDataSource(locator, factory);
		ds.addModelContents(new EmptyNode("Test 1"));
		ds.addModelContents(new EmptyNode("Test 2"));

		// Test
		final List<IAndroidController> obtained = ds.getDataSectionContents();

		// Asserts
		Assert.assertTrue("Check the output element by element.",
				new TestController(new EmptyNode("Test 1"),factory).getModel().equals(obtained.get(0).getModel()));
		Assert.assertTrue("Check the output element by element.",
				new TestController(new EmptyNode("Test 2"),factory).getModel().equals(obtained.get(1).getModel()));
	}
}