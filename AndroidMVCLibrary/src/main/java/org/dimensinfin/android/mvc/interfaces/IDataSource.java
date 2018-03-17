//  PROJECT:     Android.MVC (A.MVC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Android API16.
//  DESCRIPTION: Library that defines a generic Model View Controller core classes to be used
//               on Android projects. Defines the Part factory and the Part core methods to manage
//               a generic converter from a Graph Model to a hierarchycal Part model that finally will
//               be converted to a Part list to be used on a BaseAdapter tied to a ListView.
package org.dimensinfin.android.mvc.interfaces;

import android.os.Bundle;

import org.dimensinfin.core.datasource.DataSourceLocator;
import org.dimensinfin.core.interfaces.ICollaboration;

import java.beans.PropertyChangeListener;

// - CLASS IMPLEMENTATION ...................................................................................
public interface IDataSource extends PropertyChangeListener {
	public DataSourceLocator getDataSourceLocator();

	public void addPropertyChangeListener(final PropertyChangeListener newListener);

	public String getVariant();

	public IDataSource setVariant(final String variant);

	public Bundle getExtras();

	public void cleanup();

	public IDataSource addModelContents(final ICollaboration newnode);

	public boolean isCached();

	public IDataSource setCacheable(final boolean cachestate);

	public void collaborate2Model();

	//	public void transformModel2Parts ();
//
//	public List<IAndroidPart> getBodyParts ();

//	public ArrayList<AbstractAndroidPart> getHeaderParts ();
	//
	//	public int getItemsCount();
	//
	//	public void updateContentHierarchy();
}

// - UNUSED CODE ............................................................................................
