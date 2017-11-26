//	PROJECT:        NeoCom.MVC (NEOC.MVC)
//	AUTHORS:        Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2013-2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:		Android API16.
//	DESCRIPTION:		Library that defines a generic Model View Controller core classes to be used
//									on Android projects. Defines the Part factory and the Part core methods to manage
//									the extended GEF model into the Android View to be used on ListViews.
package org.dimensinfin.android.mvc.activity;

// - IMPORT SECTION .........................................................................................
import android.app.DialogFragment;

// - CLASS IMPLEMENTATION ...................................................................................
public abstract class ADialogCallback {
	// - S T A T I C - S E C T I O N ..........................................................................

	// - F I E L D - S E C T I O N ............................................................................
	// - C O N S T R U C T O R - S E C T I O N ................................................................

	// - M E T H O D - S E C T I O N ..........................................................................
	public abstract void onDialogNegativeClick(DialogFragment dialog);

	public abstract void onDialogPositiveClick(DialogFragment dialog);
}

// - UNUSED CODE ............................................................................................
