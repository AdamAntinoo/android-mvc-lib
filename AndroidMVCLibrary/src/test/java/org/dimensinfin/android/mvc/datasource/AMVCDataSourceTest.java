package org.dimensinfin.android.mvc.datasource;

import org.dimensinfin.android.mvc.core.UIGlobalExecutor;
import org.dimensinfin.android.mvc.factory.ControllerFactory;
import org.dimensinfin.android.mvc.interfaces.IControllerFactory;
import org.dimensinfin.android.mvc.utils.PojoTestUtils;
import org.dimensinfin.core.datasource.DataSourceLocator;
import org.dimensinfin.core.interfaces.ICollaboration;
import org.dimensinfin.core.model.Separator;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

/**
 * @author Adam Antinoo
 */
public class AMVCDataSourceTest {
	private static final ICollaboration node = new Separator("TEST");

	public static class TestDataSource extends AMVCDataSource {

		public TestDataSource(final DataSourceLocator locator, final IControllerFactory controllerFactory) {
			super(locator, controllerFactory);
		}

//		@Override
//		public boolean isCached() {
//			return false;
//		}

		@Override
		public boolean isCacheable() {
			return false;
		}

		@Override
		public void collaborate2Model() {

		}
	}

//	@Before
//	public void setUp() throws Exception {
//	}

	@Test
	public void testAccesors() {
		PojoTestUtils.validateAccessors(TestDataSource.class);
	}
@Test
public void transformModel2Parts(){

}
	@Test
	public void cleanup() {
	}

	@Test
	public void isCached() {
	}

//	@Test
	public void addModelContents() {
		// Given
		final DataSourceLocator locator = new DataSourceLocator().addIdentifier("TEST");
		final ControllerFactory factory = Mockito.mock(ControllerFactory.class);
		final TestDataSource ds = new TestDataSource(locator, factory);
//		final AbstractPagerFragment apf = Mockito.mock(AbstractPagerFragment.class);
		final AMVCDataSource amvcds = Mockito.mock(AMVCDataSource.class);
		final UIGlobalExecutor executor = Mockito.mock(UIGlobalExecutor.class);
//		final Waiter waiter = new Waiter();
		// When
//		Mockito.when(AMVCDataSource.propertyChange(ArgumentMatchers.any(PropertyChangeEvent)).then

		// Test
//		ds.addModelContents(node);
//		UIGlobalExecutor.submit(() -> {
//			// Use alternate thread to load the event on the thread.
//			ds.addModelContents(node);
//			// End this thread and resume with other executions.
//			waiter.resume();
//		});
//		final List<IAndroidController> controllers = ds.getDataSectionContents();

		// Asserts
//		Assert.assertNotNull(controllers);
//		Assert.assertEquals(1, controllers.size());
//		Mockito.verify(executor, Mockito.times(1)).submit(ArgumentMatchers.any(Runnable.class));
	}

	@Test
	public void getDataSectionContents() {
	}

	@Test
	public void addPropertyChangeListener() {
	}

	@Test
	public void sendChangeEvent() {
	}

	@Test
	public void propertyChange() {
	}
}