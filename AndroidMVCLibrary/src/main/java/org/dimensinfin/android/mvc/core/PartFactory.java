//	PROJECT:        Android.MVC (A.MVC)
//	AUTHORS:        Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2013-2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:		Android API22.
//	DESCRIPTION:		Library that defines a generic Model View Controller core classes to be used
//									on Android projects. Defines the Part factory and the Part core methods to manage
//									a generic data graph into a Part hierarchy and finally on the Android View to be
//                  used on ListViews.
package org.dimensinfin.android.mvc.core;

import org.dimensinfin.android.mvc.interfaces.IPart;
import org.dimensinfin.android.mvc.interfaces.IPartFactory;
import org.dimensinfin.android.mvc.part.SeparatorPart;
import org.dimensinfin.core.interfaces.ICollaboration;
import org.dimensinfin.core.model.Separator;

import java.util.logging.Logger;

// - CLASS IMPLEMENTATION ...................................................................................
public class PartFactory implements IPartFactory {
	// - S T A T I C - S E C T I O N ..........................................................................
	protected static Logger logger = Logger.getLogger("PartFactory");

	// - F I E L D - S E C T I O N ............................................................................
	private final String variant;

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public PartFactory(final String selectedVariant) {
		variant = selectedVariant;
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	public IPart createPart(final ICollaboration node) {
		// If no part is trapped then result a NOT FOUND mark
		return new SeparatorPart(new Separator("-NO data-[" + node.getClass().getName() + "]-"));
	}

	public String getVariant() {
		return variant;
	}
}

// - UNUSED CODE ............................................................................................
