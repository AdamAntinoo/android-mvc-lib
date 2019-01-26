//  PROJECT:     Android.MVC (A.MVC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2019 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Android API16.
//  DESCRIPTION: Library that defines a generic Model View Controller core classes to be used
//               on Android projects. Defines the Part factory and the Part core methods to manage
//               a generic converter from a Graph Model to a hierarchical Part model that finally will
//               be converted to a Part list to be used on a BaseAdapter tied to a ListView.
package org.dimensinfin.android.mvc.factory;

import java.util.Hashtable;
import java.util.Hashtable;
import java.util.Hashtable;

import lombok.Builder;
import org.dimensinfin.android.mvc.controller.AAndroidController;
import org.dimensinfin.android.mvc.controller.DemoHeaderTitleAndroidController;
import org.dimensinfin.android.mvc.core.AndroidController;
import org.dimensinfin.android.mvc.demo.R;
import org.dimensinfin.android.mvc.interfaces.IAndroidController;
import org.dimensinfin.android.mvc.interfaces.IControllerFactory;
import org.dimensinfin.android.mvc.model.DemoContainer;
import org.dimensinfin.android.mvc.model.DemoHeaderTitle;
import org.dimensinfin.android.mvc.model.DemoItem;
import org.dimensinfin.android.mvc.model.DemoLabel;
import org.dimensinfin.android.mvc.part.DemoContainerAndroidController;
import org.dimensinfin.android.mvc.part.DemoItemAndroidController;
import org.dimensinfin.android.mvc.render.DemoHeaderTitleRender;
import org.dimensinfin.core.interfaces.ICollaboration;
import org.joda.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Adam Antinoo
 */
@Builder
public class DemoControllerFactory extends ControllerFactory implements IControllerFactory {
	// - F I E L D - S E C T I O N

	// - C O N S T R U C T O R - S E C T I O N
	public DemoControllerFactory(final String selectedVariant) {
		super(selectedVariant);
	}

	// - M E T H O D - S E C T I O N
		/**
	 * The method should create the matching part for the model received. We can use the variant to change at creation
	 * time the matching part or to replace parts when required.
	 */
		@Override
	public IAndroidController createController(final ICollaboration node) {
		logger.info("-- [DemoControllerFactory.createPart]> Node class: " + node.getClass().getSimpleName());
		if (node instanceof DemoHeaderTitle) {
			// These shows the selected Separator but with another rendering.
			 part = DemoHeaderTitleAndroidController.<DemoHeaderTitle, DemoHeaderTitleRender>builder().build();
//			pr = DemoHeaderTitleAndroidController<DemoHeaderTitle, DemoHeaderTitleRender >.b
//
//
//
//					prt= AndroidController.<DemoHeaderTitle>builder().model((DemoHeaderTitle) node).build();
//
//
//
//			final AndroidController newpart =  DemoHeaderTitleAndroidController.builder().build();    .Builder<DemoHeaderTitle>((DemoHeaderTitle) node, this.getRootPart())
//					.build();
//			IAndroidController part = new DemoHeaderTitleAndroidController((DemoHeaderTitle) node).setIconReference(R.drawable.arrowleft)
//					.setRenderMode(getVariant())
//					.setFactory(this);
			return part;
		}
		if (node instanceof DemoContainer) {
			// These shows the selected Separator but with another rendering.
			IAndroidController part = new DemoContainerAndroidController((DemoContainer) node).setRenderMode(getVariant())
					;
			return part;
		}

		// Demo classes and models
		if (node instanceof DemoItem) {
			// These shows the selected Separator but with another rendering.
			IAndroidController part = new DemoItemAndroidController((DemoItem) node).setRenderMode("-ITEM-")
					;
			return part;
		}
		// WARNING - When node classes have direct inheritance put the parent below their children.
		if (node instanceof DemoLabel) {
			// These shows the selected Separator but with another rendering.
			IAndroidController part = new DemoItemAndroidController((DemoLabel) node).setRenderMode("-LABEL-")
					;
			return part;
		}
		// If no part is trapped then call the parent chain until one is found.
		return super.createController(node);
	}
}
