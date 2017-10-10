//	PROJECT:      NeoCom.model (NEOC.M)
//	AUTHORS:      Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:    (c) 2013-2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:	Java 1.8 Library.
//	DESCRIPTION:	Isolated model structures to access and manage Eve Online character data and their
//								available databases.
//								This version includes the access to the latest 6.x version of eveapi libraries to
//								download ad parse the CCP XML API data.
//								Code integration that is not dependent on any specific platform.
package org.dimensinfin.android.mvc.datasource;

//- IMPORT SECTION .........................................................................................
import java.util.HashMap;
import java.util.logging.Logger;

import org.dimensinfin.android.datasource.DataSourceLocator;
import org.dimensinfin.android.mvc.interfaces.IExtendedDataSource;

// - CLASS IMPLEMENTATION ...................................................................................
/**
 * Controls and caches all DataSources in use. Will use a single multifield Locator to store and remember used
 * DataSources and their state.
 * 
 * @author Adam Antinoo
 */
public class DataSourceManager /* implements IDataSourceConnector */ {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger																			logger			= Logger.getLogger("DataSourceManager");
	private static final HashMap<String, IExtendedDataSource>	dataSources	= new HashMap<String, IExtendedDataSource>();

	/**
	 * Registers this new DataSource on the Manager or returns the source located already on the cache if they
	 * match. This way I will get a cached and already prepared DataSource if I try to create it again.
	 * 
	 * @param newSource
	 *          - new DataSource to add to the Manager
	 * @return the oldest DataSource with the same identifier.
	 */
	public static IExtendedDataSource registerDataSource(final IExtendedDataSource newSource) {
		DataSourceLocator locator = newSource.getDataSourceLocator();
		// Search for locator on cache.
		IExtendedDataSource found = DataSourceManager.dataSources.get(locator.getIdentity());
		// REFACTOR Do not return cached datasources.
		found = null;
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
