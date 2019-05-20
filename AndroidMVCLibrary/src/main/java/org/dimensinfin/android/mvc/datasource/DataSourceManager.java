package org.dimensinfin.android.mvc.datasource;

import java.util.HashMap;
import java.util.Objects;

import androidx.annotation.NonNull;

import org.dimensinfin.android.mvc.core.AppCompatibilityUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controls and caches all DataSources in use that are allowed to be cacheable. Will use a single multifield Locator to
 * store and remember used DataSources and their state.
 *
 * @author Adam Antinoo
 */
public class DataSourceManager {
	// - S T A T I C - S E C T I O N
	private static Logger logger = LoggerFactory.getLogger(DataSourceManager.class);
	private static final HashMap<String, IDataSource> dataSources = new HashMap<String, IDataSource>();

	private DataSourceManager() {
	}

	/**
	 * Registers this new DataSource on the Manager or returns the source located already on the cache if they unique
	 * identifiers match. This way I will get a cached and already prepared DataSource if I try to create another with the
	 * same identifier.
	 *
	 * @param newSource new DataSource to add to the Manager
	 * @return the oldest DataSource with the same identifier.
	 */
	public static IDataSource registerDataSource( @NonNull final IDataSource newSource ) {
		//		if (null == newSource) return newSource;
		Objects.requireNonNull(newSource);
		// Check if the data source can be cached.
		if (newSource.needsCaching()) {
			DataSourceLocator locator = newSource.getDataSourceLocator();
			// Search for locator on cache.
			final IDataSource found = DataSourceManager.dataSources.get(locator.getIdentity());
			if (null == found) {
				DataSourceManager.dataSources.put(locator.getIdentity(), newSource);
				DataSourceManager.logger
						.info("-- [DataSourceManager.registerDataSource]> Registering new DataSource: {}", locator.getIdentity());
				//				found = newSource;
				AppCompatibilityUtils.backgroundExecutor.submit(() -> {
					try {
						newSource.prepareModel();
					} catch (RuntimeException runtime) {
						runtime.printStackTrace();
					}
				});
			}
		}
		return newSource;
	}

	public static void clear() {
		dataSources.clear();
	}
}
