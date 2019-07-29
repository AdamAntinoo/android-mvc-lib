//  PROJECT:     Android.MVC (A.MVC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Android API16.
//  DESCRIPTION: Library that defines a generic Model View Controller core classes to be used
//               on Android projects. Defines the Part factory and the Part core methods to manage
//               a generic converter from a Graph Model to a hierarchycal Part model that finally will
//               be converted to a Part list to be used on a BaseAdapter tied to a ListView.
package org.dimensinfin.android.mvc.interfaces;

import java.beans.PropertyChangeListener;
import java.util.List;

import android.os.Bundle;

import org.dimensinfin.android.mvc.datasource.DataSourceLocator;
import org.dimensinfin.core.interfaces.ICollaboration;

// - CLASS IMPLEMENTATION ...................................................................................
public interface IDataSource extends PropertyChangeListener {
	public String getVariant();

	public IDataSource setVariant( final String variant );

	public DataSourceLocator getDataSourceLocator();

	public Bundle getExtras();

	public void addPropertyChangeListener( final PropertyChangeListener newListener );

	public void cleanup();

	public boolean isCached();

	public boolean isCacheable();

	public IDataSource setCacheable( final boolean cachestate );

	public IDataSource addModelContents( final ICollaboration newnode );

	public void collaborate2Model();

	public List<IAndroidPart> getDataSectionContents();

	public IRootPart createRootPart();

	public void startOnLoadProcess();
}

// - UNUSED CODE ............................................................................................
