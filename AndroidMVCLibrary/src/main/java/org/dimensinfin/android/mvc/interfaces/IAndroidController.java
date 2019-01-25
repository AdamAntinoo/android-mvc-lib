//  PROJECT:     Android.MVC (A.MVC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2019 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Android API16.
//  DESCRIPTION: Library that defines a generic Model View Controller core classes to be used
//               on Android projects. Defines the AndroidController factory and the AndroidController core methods to manage
//               a generic converter from a Graph Model to a hierarchical AndroidController model that finally will
//               be converted to a AndroidController list to be used on a BaseAdapter tied to a ListView.
package org.dimensinfin.android.mvc.interfaces;

import java.util.List;

// - CLASS IMPLEMENTATION ...................................................................................
public interface IAndroidController<T> /*extends PropertyChangeListener*/ {
//	RootAndroidController getRootPart();

//	IAndroidController createNewPart(final ICollaboration model);

	List<IAndroidController> getChildren();

	T getModel();

	IAndroidController getParentPart();

	IPartFactory getPartFactory();

//	String getRenderMode();

//	RootAndroidController getRoot();

//	boolean isEmpty();

//	boolean isExpanded();

//	boolean isRenderWhenEmpty();

	void refreshChildren();

//	List<IAndroidController> runPolicies(List<IAndroidController> targets);

//	IAndroidController setFactory(final IPartFactory partFactory);

//	void setModel(final ICollaboration model);

//	void setParent(final IAndroidController parent);

//	IAndroidController setRenderMode(final String renderMode);
}

// - UNUSED CODE ............................................................................................
