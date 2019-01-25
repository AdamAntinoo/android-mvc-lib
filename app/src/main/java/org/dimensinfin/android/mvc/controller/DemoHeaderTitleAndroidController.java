//  PROJECT:     Android.MVC (A.MVC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2019 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Android API16.
//  DESCRIPTION: Library that defines a generic Model View Controller core classes to be used
//               on Android projects. Defines the Part factory and the Part core methods to manage
//               a generic converter from a Graph Model to a hierarchical Part model that finally will
//               be converted to a Part list to be used on a BaseAdapter tied to a ListView.
package org.dimensinfin.android.mvc.controller;

import java.util.Hashtable;
import java.util.Hashtable;
import java.util.Hashtable;

import android.app.Activity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import lombok.Builder;
import org.dimensinfin.android.mvc.demo.R;
import org.dimensinfin.android.mvc.interfaces.IMenuActionTarget;
import org.dimensinfin.android.mvc.model.DemoHeaderTitle;
import org.dimensinfin.android.mvc.render.AbstractRender;
import org.dimensinfin.android.mvc.render.DemoHeaderTitleRender;
import org.joda.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Adam Antinoo
 */
@Builder
public class DemoHeaderTitleAndroidController extends AAndroidController<DemoHeaderTitle, DemoHeaderTitleRender> implements IMenuActionTarget {
	// - F I E L D - S E C T I O N
	private int iconReference = R.drawable.defaulticonplaceholder;
	private Activity _activity;

	// - C O N S T R U C T O R - S E C T I O N
	// - M E T H O D - S E C T I O N
	public int getIconReference() {
		return iconReference;
	}

	public DemoHeaderTitleAndroidController setIconReference(final int resourceIdentifier) {
		logger.info("-- [DemoHeaderTitleAndroidController.setIconReference]> setting icon ref: " + resourceIdentifier);
		iconReference = resourceIdentifier;
		return this;
	}
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer("DemoHeaderTitleAndroidController [");
		buffer.append("icon ref id: ").append(iconReference);
		buffer.append("]");
		return buffer.toString();
	}

	//	@Override
	public AbstractRender selectRenderer() {
		return null;
//		return new DemoHeaderTitleRender(this, _activity);
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
}
