//  PROJECT:     Android.MVC (A.MVC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Android API16.
//  DESCRIPTION: Library that defines a generic Model View Controller core classes to be used
//               on Android projects. Defines the Part factory and the Part core methods to manage
//               a generic converter from a Graph Model to a hierarchical Part model that finally will
//               be converted to a Part list to be used on a BaseAdapter tied to a ListView.
package org.dimensinfin.android.mvc.part;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.dimensinfin.android.mvc.core.AbstractAndroidPart;
import org.dimensinfin.android.mvc.core.AbstractRender;
import org.dimensinfin.android.mvc.demo.R;
import org.dimensinfin.android.mvc.model.DemoHeaderTitle;

import java.util.GregorianCalendar;

// - CLASS IMPLEMENTATION ...................................................................................
public class DemoHeaderTitlePart extends AbstractAndroidPart {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static final long serialVersionUID = -7103273035430243825L;

	// - F I E L D - S E C T I O N ............................................................................
	private int iconReference = R.drawable.defaulticonplaceholder;

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public DemoHeaderTitlePart (final DemoHeaderTitle node) {
		super(node);
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	public DemoHeaderTitle getCastedModel () {
		return (DemoHeaderTitle) this.getModel();
	}

	public int getIconReference () {
		return iconReference;
	}

	public DemoHeaderTitlePart setIconReference (final int resourceIdentifier) {
		logger.info("-- [DemoHeaderTitlePart.setIconReference]> setting icon ref: " + resourceIdentifier);
		iconReference = resourceIdentifier;
		return this;
	}

	@Override
	public long getModelID () {
		return GregorianCalendar.getInstance().getTimeInMillis();
	}

	@Override
	public String toString () {
		StringBuffer buffer = new StringBuffer("DemoHeaderTitlePart [");
		buffer.append("icon ref id: ").append(iconReference);
		buffer.append("]");
		return buffer.toString();
	}

	@Override
	protected AbstractRender selectRenderer () {
		return new DemoHeaderTitleRender(this, _activity);
	}
}

// - CLASS IMPLEMENTATION ...................................................................................
final class DemoHeaderTitleRender extends AbstractRender {
	// - S T A T I C - S E C T I O N ..........................................................................

	// - F I E L D - S E C T I O N ............................................................................
	private ImageView nodeIcon = null;
	private TextView applicationName = null;
	private TextView applicationVersion = null;

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public DemoHeaderTitleRender (final AbstractAndroidPart target, final Activity context) {
		super(target, context);
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	@Override
	public DemoHeaderTitlePart getPart () {
		return (DemoHeaderTitlePart) super.getPart();
	}

	@Override
	public void initializeViews () {
		super.initializeViews();
		nodeIcon = (ImageView) _convertView.findViewById(R.id.nodeIcon);
		applicationName = (TextView) _convertView.findViewById(R.id.applicationName);
		applicationVersion = (TextView) _convertView.findViewById(R.id.applicationVersion);
	}

	@Override
	public void updateContent () {
		super.updateContent();
		nodeIcon.setImageResource(getPart().getIconReference());
		applicationName.setText(getPart().getCastedModel().getName());
		applicationName.setVisibility(View.GONE);
		applicationVersion.setText(getPart().getCastedModel().getVersion());
		applicationVersion.setVisibility(View.GONE);
	}

	@Override
	protected void createView () {
		_convertView = inflateView(R.layout.demoheadertitle4header);
		_convertView.setTag(this);
	}
}

// - UNUSED CODE ............................................................................................
