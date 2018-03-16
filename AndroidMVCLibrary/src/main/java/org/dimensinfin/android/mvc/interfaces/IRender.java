//  PROJECT:     Android.MVC (A.MVC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Android API16.
//  DESCRIPTION: Library that defines a generic Model View Controller core classes to be used
//               on Android projects. Defines the Part factory and the Part core methods to manage
//               a generic converter from a Graph Model to a hierarchycal Part model that finally will
//               be converted to a Part list to be used on a BaseAdapter tied to a ListView.
package org.dimensinfin.android.mvc.interfaces;

import android.app.Activity;
import android.view.View;

import org.dimensinfin.android.mvc.core.AbstractAndroidPart;
import org.dimensinfin.core.datasource.DataSourceLocator;
import org.dimensinfin.core.model.RootNode;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

// - CLASS IMPLEMENTATION ...................................................................................
public interface IRender  {
	public void initializeViews ();
	public void updateContent ();
	public View getView ();

//	public void setContext (final Activity context) {
//		_context = context;
//	}
//
//	public void setExtraInteger (final String key, final Integer integer) {
//		_extras.put(key, integer);
//	}

}

// - UNUSED CODE ............................................................................................
