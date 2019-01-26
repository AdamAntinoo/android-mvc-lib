//  PROJECT:     Android.MVC (A.MVC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2019 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Android API16.
//  DESCRIPTION: Library that defines a generic Model View Controller core classes to be used
//               on Android projects. Defines the Part factory and the Part core methods to manage
//               a generic converter from a Graph Model to a hierarchical Part model that finally will
//               be converted to a Part list to be used on a BaseAdapter tied to a ListView.
package org.dimensinfin.android.mvc.render;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;
import lombok.Builder;
import org.dimensinfin.android.mvc.controller.DemoHeaderTitleAndroidController;
import org.dimensinfin.android.mvc.demo.R;

/**
 * @author Adam Antinoo
 */
@Builder
public class DemoHeaderTitleRender extends AbstractRender<DemoHeaderTitleAndroidController> {
	// - F I E L D - S E C T I O N
	private TextView applicationName = null;
	private TextView applicationVersion = null;


	// - C O N S T R U C T O R - S E C T I O N
	protected DemoHeaderTitleRender(final DemoHeaderTitleRender.Builder<DemoHeaderTitleAndroidController> builder) {
		super(builder);
	}

	// - M E T H O D - S E C T I O N
//	@Override
//	public DemoHeaderTitleAndroidController getController() {
//		return (DemoHeaderTitleAndroidController) super.getController();
//	}

	@Override
	public void initializeViews() {
		applicationName = (TextView) _convertView.findViewById(R.id.applicationName);
		applicationVersion = (TextView) _convertView.findViewById(R.id.applicationVersion);
	}

	@Override
	public void updateContent() {
		applicationName.setText(getController().getModel().getName());
		applicationName.setVisibility(View.VISIBLE);
		applicationVersion.setText(getController().getModel().getVersion());
		applicationVersion.setVisibility(View.VISIBLE);
	}

	@Override
	public int accessLayoutReference() {
		return R.layout.demoheadertitle4header;
	}

	// - B U I L D E R
	public static class Builder<DemoHeaderTitleAndroidController> extends AbstractRender.Builder {
		public DemoHeaderTitleRender build() {
			return new DemoHeaderTitleRender(this);
		}
	}
}
