//  PROJECT:        net.sf.vgap4.assistant.ui
//  FILE NAME:      $Id: ProcessorApp.java,v $
//  LAST UPDATE:    $Date: 2000/06/28 11:52:36 $
//  RELEASE:        $Revision: 1.4 $
//  AUTHORS:        Lima Delta Delta (LDD) - boneymen@netscape.com
//  COPYRIGHT:      (c) 2008 by LDD Game Development Spain, all rights reserved.

package org.dimensinfin.core.parser;

import java.io.Serializable;

import org.dimensinfin.core.interfaces.IModelStore;

// - INTERFACE IMPLEMENTATION ...............................................................................
public interface IPersistentHandler extends Serializable {
	// - F I E L D - S E C T I O N ............................................................................

	//	public void clearUpdate();

	// - M E T H O D - S E C T I O N ..........................................................................
	//	public String getLocation();

	//	public InputTypes getType();

	//	public boolean loadContents();

	public IModelStore getStore ();

	//	public ObjectInput prepareStorageInput();
	//
	//	public ObjectOutput prepareStorageOutput();

	public boolean restore ();

	public boolean save ();

	public void setStore (final IModelStore newStore);

	//	public boolean uploadContents();
}

// - UNUSED CODE ............................................................................................
