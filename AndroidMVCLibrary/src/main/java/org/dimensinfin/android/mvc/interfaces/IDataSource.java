//	PROJECT:        NeoCom.Android (NEOC.A)
//	AUTHORS:        Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2013-2016 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:		Android API16.
//	DESCRIPTION:		Application to get access to CCP api information and help manage industrial activities
//									for characters and corporations at Eve Online. The set is composed of some projects
//									with implementation for Android and for an AngularJS web interface based on REST
//									services on Sprint Boot Cloud.
package org.dimensinfin.android.mvc.interfaces;

import org.dimensinfin.core.model.RootNode;

import java.beans.PropertyChangeListener;
import java.util.List;

// - CLASS IMPLEMENTATION ...................................................................................
public interface IDataSource extends PropertyChangeListener {
	public void addPropertyChangeListener (final PropertyChangeListener newListener);

	public RootNode collaborate2Model ();

	//	public void createContentHierarchy();

	public List<IAndroidPart> getBodyParts ();

	public IDataSource setVariant (final String variant);

	//	public DataSourceLocator getDataSourceLocator();
	//
	//	public ArrayList<AbstractAndroidPart> getHeaderParts();
	//
	//	public int getItemsCount();
	//
	//	public void updateContentHierarchy();
}

// - UNUSED CODE ............................................................................................
