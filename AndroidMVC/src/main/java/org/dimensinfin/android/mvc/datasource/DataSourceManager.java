package org.dimensinfin.android.mvc.datasource;

import androidx.annotation.NonNull;

import org.dimensinfin.android.mvc.core.AppCompatibilityUtils;
import org.dimensinfin.android.mvc.exception.ExceptionReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * Controls and caches all DataSources in use that are allowed to be cacheable. Will use a single multifield Locator to
 * store and remember used DataSources and their state.
 *
 * @author Adam Antinoo
 */
public class DataSourceManager {
	private static final HashMap<String, IDataSource> dataSources = new HashMap<String, IDataSource>();
	// - S T A T I C - S E C T I O N
	private static Logger logger = LoggerFactory.getLogger(DataSourceManager.class);

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
		if (null == newSource) throw new NullPointerException("The data source cannot be a null reference. Please review the " +
				                                                      "fragment code and implement the 'createDS' method.");
		// Check if the data source can be cached.
		if (newSource.isCacheable()) {
			DataSourceLocator locator = newSource.getDataSourceLocator();
			// Search for locator on cache.
			final IDataSource found = DataSourceManager.dataSources.get(locator.getIdentity());
			if (null == found) { // Register the new data source.
				DataSourceManager.dataSources.put(locator.getIdentity(), newSource);
				DataSourceManager.logger
						.info("-- [DataSourceManager.registerDataSource]> Registering new DataSource: {}", locator.getIdentity());
			} else return found;
		}
		AppCompatibilityUtils.backgroundExecutor.submit(() -> {
			try {
				newSource.prepareModel();
			} catch (Exception ex) {
				logger.info("-- [DataSourceManager.registerDataSource]> Exception while preparing data source data: {}",
				            ex.getMessage());
				ex.printStackTrace();
				// Put this exception on the header contents so the developer can see the message.
				newSource.addHeaderContents(new ExceptionReport(ex));
			}
		});
		return newSource;
	}

	public static void clear() {
		dataSources.clear();
	}

	public IDataSource searchDataSource( final DataSourceLocator locator ) {
		return DataSourceManager.dataSources.get(locator.getIdentity());
	}
}
