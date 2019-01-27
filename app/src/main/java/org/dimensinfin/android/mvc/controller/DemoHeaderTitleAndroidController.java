//  PROJECT:     Android.MVC (A.MVC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2019 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Android API16.
//  DESCRIPTION: Library that defines a generic Model View Controller core classes to be used
//               on Android projects. Defines the Part factory and the Part core methods to manage
//               a generic converter from a Graph Model to a hierarchical Part model that finally will
//               be converted to a Part list to be used on a BaseAdapter tied to a ListView.
package org.dimensinfin.android.mvc.controller;

import android.app.Activity;
import android.content.Context;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import lombok.ToString;
import org.dimensinfin.android.mvc.demo.R;
import org.dimensinfin.android.mvc.interfaces.IControllerFactory;
import org.dimensinfin.android.mvc.interfaces.IMenuActionTarget;
import org.dimensinfin.android.mvc.interfaces.IRender;
import org.dimensinfin.android.mvc.model.DemoHeaderTitle;
import org.dimensinfin.android.mvc.render.AbstractRender;
import org.dimensinfin.android.mvc.render.DemoHeaderTitleRender;
import org.dimensinfin.core.interfaces.ICollaboration;

/**
 * @author Adam Antinoo
 */
@ToString
public class DemoHeaderTitleAndroidController<M extends ICollaboration> extends AAndroidController<M> implements IMenuActionTarget {
	// - F I E L D - S E C T I O N
	private DemoHeaderTitleRender render; // Holds the main render for the visible component of the model.
	private int iconReference = R.drawable.defaulticonplaceholder;
//	private Activity activity;

	// - C O N S T R U C T O R - S E C T I O N
	public DemoHeaderTitleAndroidController(final M model, final IControllerFactory factory) {
		super(model, factory);
		// Create the rendered from the renderer type.
		this.render = new DemoHeaderTitleRender.Builder(this, this.getActivity()).build();
	}

	// - M E T H O D - S E C T I O N
	// - G E T T E R S   &   S E T T E R S
	public int getIconReference() {
		return iconReference;
	}

	public DemoHeaderTitleAndroidController setIconReference(final int resourceIdentifier) {
		logger.info("-- [DemoHeaderTitleAndroidController.setIconReference]> setting icon ref: " + resourceIdentifier);
		iconReference = resourceIdentifier;
		return this;
	}

	@Override
	public IRender buildRender(final Context context) {
		return null;
	}
	//	@Override
	public AbstractRender selectRenderer() {
		if (null == this.render) this.render = new DemoHeaderTitleRender.Builder(this, this.getActivity()).build();
		return this.render;
	}

	@Override
	public boolean onContextItemSelected(final MenuItem item) {
		logger.info(">< [DemoHeaderTitleAndroidController.onContextItemSelected]");
		Toast.makeText(this.getActivity()
				, item.getTitle()
				, Toast.LENGTH_LONG).show();
		return true;
	}

	public Activity getActivity() {
		return null;
	}

	@Override
	public void onCreateContextMenu(final ContextMenu menu, final View v, final ContextMenu.ContextMenuInfo menuInfo) {
		logger.info(">> [DemoHeaderTitleAndroidController.onCreateContextMenu]");
		menu.setHeaderTitle("Context Menu");
		menu.add(0, v.getId(), 0, "Action 1");
		menu.add(0, v.getId(), 0, "Action 2");
		logger.info("<< [DemoHeaderTitleAndroidController.onCreateContextMenu]");
	}


	@Override
	public long getModelId() {
		return 0;
	}

//	public static <DemoHeaderTitle> DemoHeaderTitleAndroidController<DemoHeaderTitle> builderDH() {
//		return new DemoHeaderTitleAndroidController<DemoHeaderTitle>();
//	}

//	// - B U I L D E R
//	public static class Builder extends AAndroidController.Builder<DemoHeaderTitle> {
//		public DemoHeaderTitleAndroidController build() {
//			return new DemoHeaderTitleAndroidController(this);
//		}
//	}
}
