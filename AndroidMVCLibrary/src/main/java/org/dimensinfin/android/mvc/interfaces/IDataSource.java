//  PROJECT:     Android.MVC (A.MVC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Android API16.
//  DESCRIPTION: Library that defines a generic Model View Controller core classes to be used
//               on Android projects. Defines the AndroidController factory and the AndroidController core methods to manage
//               a generic converter from a Graph Model to a hierarchycal AndroidController model that finally will
//               be converted to a AndroidController list to be used on a BaseAdapter tied to a ListView.
package org.dimensinfin.android.mvc.interfaces;

import java.beans.PropertyChangeListener;
import java.util.List;

import android.os.Bundle;

import org.dimensinfin.core.datasource.DataSourceLocator;
import org.dimensinfin.core.interfaces.ICollaboration;

// - CLASS IMPLEMENTATION ...................................................................................
public interface IDataSource extends PropertyChangeListener {
	 String getVariant();

//	 IDataSource setVariant( final String variant );

	 DataSourceLocator getDataSourceLocator();

	 Bundle getExtras();

	 void addPropertyChangeListener( final PropertyChangeListener newListener );

	 void cleanup();

	 boolean isCached();

	 boolean isCacheable();

	 IDataSource setCacheable( final boolean cachestate );

	 IDataSource addModelContents( final ICollaboration newnode );

	 void collaborate2Model();

	 List<IAndroidAndroidController> getDataSectionContents();

//	 IRootPart createRootPart();

	 void startOnLoadProcess();

	IControllerFactory getControllerFactory();
}

// - UNUSED CODE ............................................................................................
