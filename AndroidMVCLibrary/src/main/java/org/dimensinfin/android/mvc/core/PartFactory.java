//  PROJECT:     Android.MVC (A.MVC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Android API16.
//  DESCRIPTION: Library that defines a generic Model View Controller core classes to be used
//               on Android projects. Defines the AndroidController factory and the AndroidController core methods to manage
//               a generic converter from a Graph Model to a hierarchical AndroidController model that finally will
//               be converted to a AndroidController list to be used on a BaseAdapter tied to a ListView.
//               The new implementation performs the model to list transformation on the fly each time
//               a model change is detected so the population of the displayed view should be done in
//               real time while processing the model sources. This should allow for search and filtering.
package org.dimensinfin.android.mvc.core;

import org.dimensinfin.android.mvc.activity.AbstractPagerFragment;
import org.dimensinfin.android.mvc.interfaces.IAndroidController;
import org.dimensinfin.android.mvc.interfaces.IPartFactory;
import org.dimensinfin.android.mvc.model.PanelException;
import org.dimensinfin.android.mvc.part.PanelExceptionAndroidController;
import org.dimensinfin.android.mvc.part.SeparatorAndroidController;
import org.dimensinfin.core.interfaces.ICollaboration;
import org.dimensinfin.core.model.Separator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


// - CLASS IMPLEMENTATION ...................................................................................
public class PartFactory implements IPartFactory {
	// - S T A T I C - S E C T I O N ..........................................................................
	protected static Logger logger = LoggerFactory.getLogger("PartFactory");

	// - F I E L D - S E C T I O N ............................................................................
	private final String variant;

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public PartFactory ( final String selectedVariant ) {
		variant = selectedVariant;
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	public IAndroidController createPart (final ICollaboration node ) {
		// Associate the default classes defined at the MVC.
		if ( node instanceof Separator ) {
			IAndroidController part = new SeparatorAndroidController((Separator) node).setFactory(this)
			                                                .setRenderMode(this.getVariant());
			return part;
		}
		if ( node instanceof PanelException ) {
			IAndroidController part = new PanelExceptionAndroidController((PanelException) node).setFactory(this)
					.setRenderMode(this.getVariant());
			return part;
		}
		if ( node instanceof AbstractPagerFragment.EmptyNotVisibleNode) {
			IAndroidController part = new AbstractPagerFragment.EmptyAndroidController((AbstractPagerFragment.EmptyNotVisibleNode) node).setFactory(this)
					.setRenderMode(this.getVariant());
			return part;
		}
		// If no part is trapped then result a NOT FOUND mark
		return new SeparatorAndroidController(new Separator("-NO Model-AndroidController match-[" + node.getClass().getSimpleName() + "]-"));
	}

	public String getVariant () {
		return variant;
	}
}

// - UNUSED CODE ............................................................................................
