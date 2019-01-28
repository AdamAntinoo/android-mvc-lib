//  PROJECT:     Android.MVC (A.MVC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2019 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Android API16.
//  DESCRIPTION: Library that defines a generic Model View Controller core classes to be used
//               on Android projects. Defines the AndroidController factory and the AndroidController core methods to manage
//               a generic converter from a Graph Model to a hierarchical AndroidController model that finally will
//               be converted to a AndroidController list to be used on a BaseAdapter tied to a ListView.
package org.dimensinfin.android.mvc.interfaces;

import android.content.Context;
import android.view.View;

import java.util.List;

public interface IAndroidController<M> extends IEventEmitter {
	M getModel();
	void refreshChildren();
	IRender buildRender(final Context context);
	View getViewCache();
	void setViewCache(final View targetView);
	long getModelId();
	void collaborate2View(final List<IAndroidController> contentCollector);
}
