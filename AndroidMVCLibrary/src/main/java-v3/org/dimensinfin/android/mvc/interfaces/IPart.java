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
public interface IPart extends PropertyChangeListener {
	public IPart createNewPart( final ICollaboration model );

	public List<IPart> getChildren();

	public ICollaboration getModel();

	public IPart getParentPart();

	public IPartFactory getPartFactory();

	public String getRenderMode();

	public RootPart getRoot();

	public boolean isEmpty();

	public boolean isExpanded();

	public boolean isRenderWhenEmpty();

	public void refreshChildren();

	public List<IPart> runPolicies( List<IPart> targets );

	public IPart setFactory( final IPartFactory partFactory );

	public void setModel( final ICollaboration model );

	public void setParent( final IPart parent );

	public IPart setRenderMode( final String renderMode );
}

// - UNUSED CODE ............................................................................................
