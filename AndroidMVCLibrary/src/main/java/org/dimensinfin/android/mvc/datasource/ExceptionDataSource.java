//  PROJECT:     Android.MVC (A.MVC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Android API16.
//  DESCRIPTION: Library that defines a generic Model View Controller core classes to be used
//               on Android projects. Defines the AndroidController factory and the AndroidController core methods to manage
//               a generic converter from a Graph Model to a hierarchical AndroidController model that finally will
//               be converted to a AndroidController list to be used on a BaseAdapter tied to a ListView.
//               The new implementation performs the model to list transformation on the fly each time
//               a model change is detected so the population of the displayed view should be done in
//               real time while processing the model sources. This should allow for search and filtering.
package org.dimensinfin.android.mvc.datasource;

import android.os.Bundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.dimensinfin.android.mvc.interfaces.IPartFactory;
import org.dimensinfin.android.mvc.model.PanelException;
import org.dimensinfin.core.datasource.DataSourceLocator;

/**
 * This exceptional Data Source will accept an exception on construction to render a Exception Panel with the exception message and other
 * information.
 * @author Adam Antinoo
 */

// - CLASS IMPLEMENTATION ...................................................................................
public class ExceptionDataSource extends MVCDataSource {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger logger = LoggerFactory.getLogger("ExceptionDataSource");

	// - F I E L D - S E C T I O N ............................................................................
	private Exception interceptedException = null;

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public ExceptionDataSource( final DataSourceLocator locator, final String variant, final IPartFactory factory, final Bundle extras ) {
		super(locator, variant, factory, extras);
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	public ExceptionDataSource registerException( final Exception exception ) {
		this.interceptedException = exception;
		return this;
	}

	@Override
	public void collaborate2Model() {
		this.addModelContents(new PanelException(this.interceptedException));
	}
}
// - UNUSED CODE ............................................................................................
//[01]
