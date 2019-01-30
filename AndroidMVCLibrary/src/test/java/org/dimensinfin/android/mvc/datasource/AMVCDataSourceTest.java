package org.dimensinfin.android.mvc.datasource;

import org.dimensinfin.android.mvc.interfaces.IControllerFactory;
import org.dimensinfin.android.mvc.utils.PojoTestUtils;
import org.dimensinfin.core.datasource.DataSourceLocator;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Adam Antinoo
 */
public class AMVCDataSourceTest {
	public static class TestDataSource extends AMVCDataSource {

		public TestDataSource(final DataSourceLocator locator, final IControllerFactory controllerFactory) {
			super(locator, controllerFactory);
		}

		@Override
		public boolean isCached() {
			return false;
		}

		@Override
		public boolean isCacheable() {
			return false;
		}

		@Override
		public void collaborate2Model() {

		}
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testAccesors() {
		PojoTestUtils.validateAccessors(TestDataSource.class);
	}


	@Test
	public void isShouldBeCached() {
	}


	@Test
	public void cleanup() {
	}

	@Test
	public void isCached() {
	}

	@Test
	public void isCacheable() {
	}

	@Test
	public void setCacheable() {
	}

	@Test
	public void addModelContents() {
	}

	@Test
	public void addModelContents1() {
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