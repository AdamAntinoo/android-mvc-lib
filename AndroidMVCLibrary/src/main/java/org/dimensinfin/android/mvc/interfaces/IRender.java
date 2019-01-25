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
package org.dimensinfin.android.mvc.interfaces;

import android.view.View;

// - CLASS IMPLEMENTATION ...................................................................................
public interface IRender {
	public void initializeViews();

	public void updateContent();

	public View getView();

	public int accessLayoutReference();
}

// - UNUSED CODE ............................................................................................
