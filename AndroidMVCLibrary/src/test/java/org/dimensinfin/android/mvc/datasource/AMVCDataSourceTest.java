package org.dimensinfin.android.mvc.datasource;

import org.dimensinfin.android.mvc.core.UIGlobalExecutor;
import org.dimensinfin.android.mvc.factory.ControllerFactory;
import org.dimensinfin.android.mvc.support.PojoTestUtils;
import org.dimensinfin.android.mvc.support.TestControllerFactory;
import org.dimensinfin.android.mvc.support.TestDataSource;
import org.dimensinfin.core.datasource.DataSourceLocator;
import org.dimensinfin.core.interfaces.ICollaboration;
import org.dimensinfin.core.model.Separator;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Adam Antinoo
 */
public class AMVCDataSourceTest {
	private static final DataSourceLocator locator = new DataSourceLocator().addIdentifier("TEST");
	private static final ControllerFactory factory = new TestControllerFactory("TEST");
	private static final ICollaboration node = new Separator("TEST");

	public static class TestNode extends Separator {
		private List<ICollaboration> children = new ArrayList<>();

		public void addChild(final ICollaboration child) {
			this.children.add(child);
		}

		@Override
		public List<ICollaboration> collaborate2Model(final String variant) {
			return new ArrayList<ICollaboration>(this.children);
		}
	}

	@Test
	public void testAccesors() {
		PojoTestUtils.validateAccessors(TestDataSource.class);
		PojoTestUtils.validateAccessors(AMVCDataSource.class);
	}

	@Test
	public void addModelContents() {
		// Mocks
		final AMVCDataSource amvcds = Mockito.mock(AMVCDataSource.class);
		final UIGlobalExecutor executor = Mockito.mock(UIGlobalExecutor.class);

		// Given
		final TestDataSource ds = new TestDataSource(locator, factory);

		// Test
		ds.addModelContents(node);

		// Asserts
		Mockito.verify(executor, Mockito.times(1)).submit(() -> {
			int i = 0;
		});
	}

//	@Test
//	public void transformModel2Parts() {
//		// Given
//		final TestDataSource ds = new TestDataSource(locator, factory);
// // Test
//		ds.transformModel2Parts();
//
//	}
}