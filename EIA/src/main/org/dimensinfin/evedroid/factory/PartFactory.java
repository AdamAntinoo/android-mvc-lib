//	PROJECT:        NeoCom.Android (NEOC.A)
//	AUTHORS:        Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2013-2015 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:		Android API11.
//	DESCRIPTION:		Application to get access to CCP api information and help manage industrial activities
//									for characters and corporations at Eve Online. The set is composed of some projects
//									with implementation for Android and for an AngularJS web interface based on REST
//									services on Sprint Boot Cloud.
package org.dimensinfin.evedroid.factory;

// - IMPORT SECTION .........................................................................................
import org.dimensinfin.android.mvc.interfaces.IEditPart;
import org.dimensinfin.android.mvc.interfaces.IPartFactory;
import org.dimensinfin.core.model.IGEFNode;
import org.dimensinfin.evedroid.enums.EVARIANT;

// - CLASS IMPLEMENTATION ...................................................................................
public abstract class PartFactory implements IPartFactory {
	// - S T A T I C - S E C T I O N ..........................................................................

	// - F I E L D - S E C T I O N ............................................................................
	private final EVARIANT variant;

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public PartFactory(final EVARIANT selectedVariant) {
		variant = selectedVariant;
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	public abstract IEditPart createPart(final IGEFNode node);

	public String getVariant() {
		return variant.name();
	}
}

// - UNUSED CODE ............................................................................................
