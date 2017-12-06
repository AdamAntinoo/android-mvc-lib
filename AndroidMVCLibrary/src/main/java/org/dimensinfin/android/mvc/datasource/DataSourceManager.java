//	PROJECT:        NeoCom.MVC (NEOC.MVC)
//	AUTHORS:        Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2013-2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:		Android API16.
//	DESCRIPTION:		Library that defines a generic Model View Controller core classes to be used
//									on Android projects. Defines the Part factory and the Part core methods to manage
//									the extended GEF model into the Android View to be used on ListViews.
package org.dimensinfin.android.mvc.datasource;

import org.dimensinfin.android.mvc.interfaces.IDataSource;
import org.dimensinfin.core.datasource.DataSourceLocator;

import java.util.HashMap;
import java.util.logging.Logger;

// - CLASS IMPLEMENTATION ...................................................................................
/**
 * Controls and caches all DataSources in use. Will use a single multifield Locator to store and remember used
 * DataSources and their state.
 * 
 * @author Adam Antinoo
 */
public class DataSourceManager /* implements IDataSourceConnector */ {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger logger			= Logger.getLogger("DataSourceManager");
	private static final HashMap<String, IDataSource> dataSources	= new HashMap<String, IDataSource>();

	/**
	 * Registers this new DataSource on the Manager or returns the source located already on the cache if they
	 * match. This way I will get a cached and already prepared DataSource if I try to create it again.
	 * 
	 * @param newSource
	 *          - new DataSource to add to the Manager
	 * @return the oldest DataSource with the same identifier.
	 */
	public static IDataSource registerDataSource(final IDataSource newSource) {
		DataSourceLocator locator = newSource.getDataSourceLocator();
		// Search for locator on cache.
		IDataSource found = DataSourceManager.dataSources.get(locator.getIdentity());
		// REFACTOR Do not return cached datasources.
		//		found = null;
		if (null == found) {
			DataSourceManager.dataSources.put(locator.getIdentity(), newSource);
			DataSourceManager.logger
					.info("-- [DataSourceManager.registerDataSource]> Registering new DataSource: " + locator.getIdentity());
			//		newSource.connect(this);
			return newSource;
		} else
			return found;
	}
	// - F I E L D - S E C T I O N ............................................................................

	// - C O N S T R U C T O R - S E C T I O N ................................................................

	// - M E T H O D - S E C T I O N ..........................................................................
}

// - UNUSED CODE ............................................................................................
