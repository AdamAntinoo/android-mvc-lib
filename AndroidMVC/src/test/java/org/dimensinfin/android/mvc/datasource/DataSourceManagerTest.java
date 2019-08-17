package org.dimensinfin.android.mvc.datasource;

import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.times;

public class DataSourceManagerTest {
	@Before
	public void setUp() throws Exception {
		// Clear DataSourceManager data before start.
		DataSourceManager.clear();
	}

	@Test(expected = NullPointerException.class)
	public void registerDataSource_null() {
		DataSourceManager.registerDataSource(null);
	}

	@Test
	public void registerDataSource_notcached() throws InterruptedException {
		final IDataSource datasource = Mockito.mock(IDataSource.class);
		Mockito.when(datasource.isCacheable()).thenReturn(false);
		DataSourceManager.registerDataSource(datasource);
		Thread.sleep(TimeUnit.SECONDS.toMillis(1));
		Mockito.verify(datasource, times(1)).prepareModel();
	}

	@Test
	public void registerDataSource_cached() throws InterruptedException {
		final IDataSource datasource = Mockito.mock(IDataSource.class);

		// When
		Mockito.when(datasource.isCacheable()).thenReturn(true);
		Mockito.when(datasource.getDataSourceLocator()).thenReturn(
				new DataSourceLocator().addIdentifier("TEST/LOCATOR")
		);

		DataSourceManager.registerDataSource(datasource);
		Thread.sleep(TimeUnit.SECONDS.toMillis(1));

		// Asserts
		Mockito.verify(datasource, times(1)).prepareModel();
	}

	@Test
	public void registerDataSource_cached_multiple() throws InterruptedException {
		final IDataSource datasource = Mockito.mock(IDataSource.class);

		// When
		Mockito.when(datasource.isCacheable()).thenReturn(true);
		Mockito.when(datasource.getDataSourceLocator()).thenReturn(
				new DataSourceLocator().addIdentifier("TEST/LOCATOR")
		);

		DataSourceManager.registerDataSource(datasource);
		DataSourceManager.registerDataSource(datasource);
		Thread.sleep(TimeUnit.SECONDS.toMillis(1));

		// Asserts
		Mockito.verify(datasource, times(1)).prepareModel();
	}
}
