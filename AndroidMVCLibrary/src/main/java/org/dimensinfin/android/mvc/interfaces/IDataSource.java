//  PROJECT:     Android.MVC (A.MVC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Android API16.
//  DESCRIPTION: Library that defines a generic Model View Controller core classes to be used
//               on Android projects. Defines the AndroidController factory and the AndroidController core methods to manage
//               a generic converter from a Graph Model to a hierarchycal AndroidController model that finally will
//               be converted to a AndroidController list to be used on a BaseAdapter tied to a ListView.
package org.dimensinfin.android.mvc.interfaces;

import android.os.Bundle;
import org.dimensinfin.android.mvc.controller.IAndroidController;
import org.dimensinfin.android.mvc.datasource.AMVCDataSource;
import org.dimensinfin.android.mvc.datasource.DataSourceLocator;

import java.beans.PropertyChangeListener;
import java.util.List;

// - CLASS IMPLEMENTATION
public interface IDataSource extends PropertyChangeListener {
	DataSourceLocator getDataSourceLocator();
	Bundle getExtras();
	AMVCDataSource setExtras(final Bundle extras);
	String getVariant();
	IDataSource setVariant(final String variant);

	void collaborate2Model();
	List<IAndroidController> getDataSectionContents();

	void addPropertyChangeListener(final PropertyChangeListener newListener);
}
