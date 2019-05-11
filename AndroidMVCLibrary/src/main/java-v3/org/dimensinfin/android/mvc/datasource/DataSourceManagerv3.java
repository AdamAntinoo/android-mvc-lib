//  PROJECT:     Android.MVC (A.MVC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Android API16.
//  DESCRIPTION: Library that defines a generic Model View Controller core classes to be used
//               on Android projects. Defines the Part factory and the Part core methods to manage
//               a generic converter from a Graph Model to a hierarchical Part model that finally will
//               be converted to a Part list to be used on a BaseAdapter tied to a ListView.
//               The new implementation performs the model to list transformation on the fly each time
//               a model change is detected so the population of the displayed view should be done in
//               real time while processing the model sources. This should allow for search and filtering.
package org.dimensinfin.android.mvc.datasource;

import org.dimensinfin.android.mvc.interfaces.IPartsDataSource;
import org.dimensinfin.core.datasource.DataSourceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

// - CLASS IMPLEMENTATION ...................................................................................

/**
 * Controls and caches all DataSources in use that are allowed to be cacheable. Will use a single multifield Locator to
 * store and remember used DataSources and their state.
 * @author Adam Antinoo
 */
public class DataSourceManagerv3 {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger logger = LoggerFactory.getLogger("DataSourceManagerv3");
	private static final HashMap<String, IPartsDataSource> dataSources = new HashMap<String, IPartsDataSource>();

	/**
	 * Registers this new DataSource on the Manager or returns the source located already on the cache if they unique
	 * identifiers match. This way I will get a cached and already prepared DataSource if I try to create another with the
	 * same identifier.
	 * @param newSource new DataSource to add to the Manager
	 * @return the oldest DataSource with the same identifier.
	 */
	public static IPartsDataSource registerDataSource( final IPartsDataSource newSource ) {
		if (null == newSource) return newSource;
		// Check if the datasource is cacheable.
		if (newSource.isCacheable()) {
			DataSourceLocator locator = newSource.getDataSourceLocator();
			// Search for locator on cache.
			IPartsDataSource found = DataSourceManagerv3.dataSources.get(locator.getIdentity());
			if (null == found) {
				DataSourceManagerv3.dataSources.put(locator.getIdentity(), newSource);
				DataSourceManagerv3.logger
						.info("-- [DataSourceManagerv3.registerDataSource]> Registering new DataSource: " + locator.getIdentity());
				return newSource;
			} else
				return found;
		}
		return newSource;
	}
	// - F I E L D - S E C T I O N ............................................................................

	// - C O N S T R U C T O R - S E C T I O N ................................................................

	// - M E T H O D - S E C T I O N ..........................................................................
}

// - UNUSED CODE ............................................................................................
