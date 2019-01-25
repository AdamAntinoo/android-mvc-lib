//  PROJECT:     Android.MVC (A.MVC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Android API16.
//  DESCRIPTION: Library that defines a generic Model View Controller core classes to be used
//               on Android projects. Defines the AndroidController factory and the AndroidController core methods to manage
//               a generic converter from a Graph Model to a hierarchical AndroidController model that finally will
//               be converted to a AndroidController list to be used on a BaseAdapter tied to a ListView.
package org.dimensinfin.android.mvc.part;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.dimensinfin.android.mvc.core.AbstractAndroidAndroidController;
import org.dimensinfin.android.mvc.core.AbstractRender;
import org.dimensinfin.android.mvc.demo.R;
import org.dimensinfin.core.model.Separator;

import java.util.GregorianCalendar;

// - CLASS IMPLEMENTATION ...................................................................................
public class DemoDetailSeparatorAndroidController extends AbstractAndroidAndroidController {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static final long serialVersionUID = -7103273035430243825L;

	// - F I E L D - S E C T I O N ............................................................................
	private int iconReference = R.drawable.defaulticonplaceholder;

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public DemoDetailSeparatorAndroidController(final Separator node) {
		super(node);
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	public Separator getCastedModel () {
		return (Separator) this.getModel();
	}

	public int getIconReference () {
		return iconReference;
	}

	public DemoDetailSeparatorAndroidController setIconReference (final int resourceIdentifier) {
		logger.info("-- [DemoDetailSeparatorAndroidController.setIconReference]> setting icon ref: " + resourceIdentifier);
		iconReference = resourceIdentifier;
		return this;
	}

	@Override
	public long getModelId () {
		return GregorianCalendar.getInstance().getTimeInMillis();
	}

	@Override
	public String toString () {
		StringBuffer buffer = new StringBuffer("DemoDetailSeparatorAndroidController [");
		buffer.append("icon ref id: ").append(iconReference);
		buffer.append("]");
		return buffer.toString();
	}

	@Override
	public AbstractRender selectRenderer () {
		return new DemoDetailSeparatorRender(this, _activity);
	}
}

// - CLASS IMPLEMENTATION ...................................................................................
final class DemoDetailSeparatorRender extends AbstractRender {
	// - S T A T I C - S E C T I O N ..........................................................................

	// - F I E L D - S E C T I O N ............................................................................
	private ImageView nodeIcon = null;
	private TextView title = null;

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public DemoDetailSeparatorRender (final AbstractAndroidAndroidController target, final Activity context) {
		super(target, context);
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	@Override
	public DemoDetailSeparatorAndroidController getPart () {
		return (DemoDetailSeparatorAndroidController) super.getPart();
	}

	@Override
	public void initializeViews () {
//		super.initializeViews();
		nodeIcon = (ImageView) _convertView.findViewById(R.id.nodeIcon);
		title = (TextView) _convertView.findViewById(R.id.title);
	}

	@Override
	public void updateContent () {
//		super.updateContent();
		nodeIcon.setImageResource(getPart().getIconReference());
		title.setText(getPart().getCastedModel().getTitle());
		title.setVisibility(View.GONE);
	}

	@Override
	public int accessLayoutReference() {
		return R.layout.demodetailseparator4activity;
	}
}

// - UNUSED CODE ............................................................................................
