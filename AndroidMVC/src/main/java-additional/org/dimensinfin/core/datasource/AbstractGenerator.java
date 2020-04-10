//	PROJECT:      NeoCom.model (NEOC.M)
//	AUTHORS:      Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:    (c) 2013-2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:	Java 1.8 Library.
//	DESCRIPTION:	Isolated model structures to access and manage Eve Online character data and their
//								available databases.
//								This version includes the access to the latest 6.x version of eveapi libraries to
//								download ad parse the CCP XML API data.
//								Code integration that is not dependent on any specific platform.
package org.dimensinfin.core.datasource;

import org.dimensinfin.core.interfaces.IModelGenerator;
import org.dimensinfin.core.model.RootNode;

import java.util.HashMap;
import java.util.logging.Logger;

// - CLASS IMPLEMENTATION ...................................................................................
public abstract class AbstractGenerator implements IModelGenerator {
	// - S T A T I C - S E C T I O N ..........................................................................
	protected static Logger logger = Logger.getLogger("AbstractGenerator");

	// - F I E L D - S E C T I O N ............................................................................
	protected DataSourceLocator _locator = null;
	protected String _variant = null;
	private boolean cacheable = true;
	private final HashMap<String, Object> _parameters = new HashMap<String, Object>();
	/** The initial node where to store the model. Model elements are children of this root. */
	protected RootNode _dataModelRoot = null;

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public AbstractGenerator (final DataSourceLocator locator, final String variant) {
		_locator = locator;
		_variant = variant;
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	public AbstractGenerator addParameter (final String name, final int value) {
		_parameters.put(name, Integer.valueOf(value));
		return this;
	}

	public AbstractGenerator addParameter (final String name, final long value) {
		_parameters.put(name, Long.valueOf(value));
		return this;
	}

	public AbstractGenerator addParameter (final String name, final String value) {
		_parameters.put(name, value);
		return this;
	}

	public boolean isCacheable () {
		return this.cacheable;
	}

	public DataSourceLocator getDataSourceLocator () {
		return _locator;
	}

	protected int getParameterInteger (final String name) {
		Object param = _parameters.get(name);
		if ( null != param ) if ( param instanceof Integer ) return ((Integer) param).intValue();
		return 0;
	}

	protected long getParameterLong (final String name) {
		Object param = _parameters.get(name);
		if ( null != param ) if ( param instanceof Long ) return ((Long) param).longValue();
		return 0;
	}

	protected String getParameterString (final String name) {
		Object param = _parameters.get(name);
		if ( null != param ) if ( param instanceof String ) return (String) param;
		return "";
	}

	public void setDataSourceLocator (DataSourceLocator newLocator) {
		_locator = newLocator;
	}

	public String getVariant () {
		return _variant;
	}

	public void setCacheable (final boolean cacheState) {
		cacheable = cacheState;
	}

	public void setDataModel (final RootNode root) {
		_dataModelRoot = root;
	}

	public void setVariant (final String variant) {
		_variant = variant;
	}


	protected boolean cacheIsInvalid () {
		if ( isCacheable() )
			if ( null == _dataModelRoot ) {
				// Initialize the Adapter data structures.
				this.setDataModel(new RootNode());
				return true;
			} else return false;
		else {
			// Initialize the Adapter data structures.
			this.setDataModel(new RootNode());
			return true;
		}
	}
}

// - UNUSED CODE ............................................................................................
