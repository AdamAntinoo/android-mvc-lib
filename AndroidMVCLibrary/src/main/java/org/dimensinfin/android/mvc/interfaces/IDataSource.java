//  PROJECT:     Android.MVC (A.MVC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Android API16.
//  DESCRIPTION: Library that defines a generic Model View Controller core classes to be used
//               on Android projects. Defines the Part factory and the Part core methods to manage
//               a generic converter from a Graph Model to a hierarchycal Part model that finally will
//               be converted to a Part list to be used on a BaseAdapter tied to a ListView.
package org.dimensinfin.android.mvc.interfaces;

import org.dimensinfin.android.mvc.core.AbstractAndroidPart;
import org.dimensinfin.core.datasource.DataSourceLocator;
import org.dimensinfin.core.model.RootNode;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

// - CLASS IMPLEMENTATION ...................................................................................
public interface IDataSource extends PropertyChangeListener {
		public void addPropertyChangeListener (final PropertyChangeListener newListener);
	public String getVariant ();
	public IDataSource setVariant (final String variant);
	public DataSourceLocator getDataSourceLocator ();
	public RootNode collaborate2Model ();



//
//
//	public void createContentHierarchy ();
//
//	public List<IAndroidPart> getBodyParts ();
//
//
//
//	public ArrayList<AbstractAndroidPart> getHeaderParts ();
	//
	//	public int getItemsCount();
	//
	//	public void updateContentHierarchy();
}

// - UNUSED CODE ............................................................................................
