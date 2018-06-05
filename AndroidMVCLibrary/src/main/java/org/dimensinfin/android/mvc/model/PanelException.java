//  PROJECT:     Android.MVC (A.MVC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Android API16.
//  DESCRIPTION: Library that defines a generic Model View Controller core classes to be used
//               on Android projects. Defines the Part factory and the Part core methods to manage
//               a generic converter from a Graph Model to a hierarchical Part model that finally will
//               be converted to a Part list to be used on a BaseAdapter tied to a ListView.
//               The new implementation performs the model to list transformation on the fly each time
//               a model change is detected so the population of the displayed view should be done in
//               real time while processing the model sources. This should allow for search and filtering.
package org.dimensinfin.android.mvc.model;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.dimensinfin.core.interfaces.ICollaboration;

/**
 * @author Adam Antinoo
 */

// - CLASS IMPLEMENTATION ...................................................................................
public class PanelException implements ICollaboration {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger logger = LoggerFactory.getLogger("PanelException");

	// - F I E L D - S E C T I O N ............................................................................
	private Exception wrappedException = null;

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public PanelException( final Exception newexception ) {
		super();
		this.wrappedException = newexception;
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	@Override
	public List<ICollaboration> collaborate2Model( final String variation ) {
		return new ArrayList<>();
	}

	public String getExceptionMessage() {
		return wrappedException.getMessage();
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer("PanelException [ ");
		if ( null != wrappedException ) buffer.append(wrappedException.getMessage()).append(" ");
		buffer.append("]");
		//		buffer.append("->").append(super.toString());
		return buffer.toString();
	}
}
// - UNUSED CODE ............................................................................................
//[01]
