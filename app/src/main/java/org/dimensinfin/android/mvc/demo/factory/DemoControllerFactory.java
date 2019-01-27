//  PROJECT:     Android.MVC (A.MVC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2019 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Android API16.
//  DESCRIPTION: Library that defines a generic Model View Controller core classes to be used
//               on Android projects. Defines the Part factory and the Part core methods to manage
//               a generic converter from a Graph Model to a hierarchical Part model that finally will
//               be converted to a Part list to be used on a BaseAdapter tied to a ListView.
package org.dimensinfin.android.mvc.demo.factory;

import lombok.Builder;
import lombok.NonNull;
import org.dimensinfin.android.mvc.controller.DemoHeaderTitleAndroidController;
import org.dimensinfin.android.mvc.demo.R;
import org.dimensinfin.android.mvc.factory.ControllerFactory;
import org.dimensinfin.android.mvc.interfaces.IAndroidController;
import org.dimensinfin.android.mvc.interfaces.IControllerFactory;
import org.dimensinfin.android.mvc.model.DemoContainer;
import org.dimensinfin.android.mvc.model.DemoHeaderTitle;
import org.dimensinfin.android.mvc.model.DemoItem;
import org.dimensinfin.android.mvc.model.DemoLabel;
import org.dimensinfin.android.mvc.part.DemoContainerAndroidController;
import org.dimensinfin.android.mvc.part.DemoItemAndroidController;
import org.dimensinfin.core.interfaces.ICollaboration;

/**
 * @author Adam Antinoo
 */
@Builder
public class DemoControllerFactory extends ControllerFactory implements IControllerFactory {
	// - F I E L D - S E C T I O N

	// - C O N S T R U C T O R - S E C T I O N
	public DemoControllerFactory(@NonNull final String selectedVariant) {
		super( selectedVariant );
	}

	// - M E T H O D - S E C T I O N

	/**
	 * The method should create the matching controller for the model received. We can use the variant to change at
	 * creation time the controller or to replace controllers when required.
	 */
	@Override
	public IAndroidController createController(final ICollaboration node) {
		logger.info( "-- [DemoControllerFactory.createController]> Node class: " + node.getClass().getSimpleName() );
		if (node instanceof DemoHeaderTitle) {
			// These shows the selected Separator but with a different render.
			return new DemoHeaderTitleAndroidController<DemoHeaderTitle>( node, this )
					.setRenderMode( this.getVariant() )
					.setIconReference( R.drawable.arrowleft );
		}
		if (node instanceof DemoContainer) {
			// These shows the selected Separator but with another rendering.
			IAndroidController part = new DemoContainerAndroidController( (DemoContainer) node ).setRenderMode( getVariant() );
			return part;
		}

		// Demo classes and models
		if (node instanceof DemoItem) {
			// These shows the selected Separator but with another rendering.
			IAndroidController part = new DemoItemAndroidController( (DemoItem) node ).setRenderMode( "-ITEM-" );
			return part;
		}
		// WARNING - When node classes have direct inheritance put the parent below their children.
		if (node instanceof DemoLabel) {
			// These shows the selected Separator but with another rendering.
			IAndroidController part = new DemoItemAndroidController( (DemoLabel) node ).setRenderMode( "-LABEL-" );
			return part;
		}
		// If no part is trapped then call the parent chain until one is found.
		return super.createController( node );
	}
}
