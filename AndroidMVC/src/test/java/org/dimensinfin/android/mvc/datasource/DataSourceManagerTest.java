package org.dimensinfin.android.mvc.datasource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.times;

public class DataSourceManagerTest {
	@BeforeEach
	void beforeEach() {
		// Clear DataSourceManager data before start.
		DataSourceManager.clear();
	}
	public void registerDataSource_null() {
		Assertions.assertThrows( NullPointerException.class, () -> {
			DataSourceManager.registerDataSource(null);
		} );
	}

	@Test
	public void registerDataSource_notcached() throws Exception {
		final IDataSource datasource = Mockito.mock(IDataSource.class);
		Mockito.when(datasource.isCacheable()).thenReturn(false);
		DataSourceManager.registerDataSource(datasource);
		Thread.sleep(TimeUnit.SECONDS.toMillis(1));
		Mockito.verify(datasource, times(1)).prepareModel();
	}

	@Test
	public void registerDataSource_cached() throws Exception {
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
	public void registerDataSource_cached_multiple() throws Exception {
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
