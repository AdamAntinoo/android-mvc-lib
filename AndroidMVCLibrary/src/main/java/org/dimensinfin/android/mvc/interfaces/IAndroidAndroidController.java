//	PROJECT:        Android.MVC (A.MVC)
//	AUTHORS:        Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2013-2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:		Android API22.
//	DESCRIPTION:		Library that defines a generic Model View Controller core classes to be used
//									on Android projects. Defines the AndroidController factory and the AndroidController core methods to manage
//									a generic data graph into a AndroidController hierarchy and finally on the Android View to be
//                  used on ListViews.
package org.dimensinfin.android.mvc.interfaces;

import android.app.Activity;
import android.view.View;

import org.dimensinfin.android.mvc.render.AbstractRender;

import java.beans.PropertyChangeListener;
import java.util.List;


// - CLASS IMPLEMENTATION ...................................................................................
public interface IAndroidAndroidController extends IAndroidController {
	public void addPropertyChangeListener( final PropertyChangeListener newListener );

	public void collaborate2View( List<IAndroidAndroidController> contentCollector );

	public Activity getActivity();

	public AbstractRender getRenderer( Activity context );

	public View getView();

	public void setView( View convertView );

	public void invalidate();

	public void needsRedraw();

	// --- P U B L I C   M E T H O D S   T O   I M P L E M E N T
	public boolean runDependencies ();

	/**
	 * Returns a numeric identifier for this part model item that should be unique from all other system wide
	 * parts to allow for easy management of the corresponding parts and views.
	 * @return <code>long</code> identifier with the model number.
	 */
	public long getModelId();

	public AbstractRender selectRenderer();
}

// - UNUSED CODE ............................................................................................

