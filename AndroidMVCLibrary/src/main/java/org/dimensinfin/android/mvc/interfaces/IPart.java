//	PROJECT:        Android.MVC (A.MVC)
//	AUTHORS:        Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2013-2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:		Android API22.
//	DESCRIPTION:		Library that defines a generic Model View Controller core classes to be used
//									on Android projects. Defines the Part factory and the Part core methods to manage
//									a generic data graph into a Part hierarchy and finally on the Android View to be
//                  used on ListViews.
package org.dimensinfin.android.mvc.interfaces;

import org.dimensinfin.android.mvc.core.RootPart;
import org.dimensinfin.core.interfaces.ICollaboration;

import java.beans.PropertyChangeListener;
import java.util.List;

// - CLASS IMPLEMENTATION ...................................................................................
public interface IPart<T> extends PropertyChangeListener {
	RootPart getRootPart();

	IPart createNewPart(final ICollaboration model);

	List<IPart> getChildren();

	T getModel();

	IPart getParentPart();

	IPartFactory getPartFactory();

//	String getRenderMode();

//	RootPart getRoot();

//	boolean isEmpty();

//	boolean isExpanded();

//	boolean isRenderWhenEmpty();

	void refreshChildren();

//	List<IPart> runPolicies(List<IPart> targets);

//	IPart setFactory(final IPartFactory partFactory);

//	void setModel(final ICollaboration model);

//	void setParent(final IPart parent);

	IPart setRenderMode(final String renderMode);
}

// - UNUSED CODE ............................................................................................
