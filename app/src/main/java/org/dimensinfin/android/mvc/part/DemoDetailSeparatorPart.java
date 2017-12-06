//	PROJECT:        Android.MVC (A.MVC)
//	AUTHORS:        Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2013-2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:		Android API22.
//	DESCRIPTION:		Library that defines a generic Model View Controller core classes to be used
//									on Android projects. Defines the Part factory and the Part core methods to manage
//									a generic data graph into a Part hierarchy and finally on the Android View to be
//                  used on ListViews.
package org.dimensinfin.android.mvc.part;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.dimensinfin.android.mvc.core.AbstractAndroidPart;
import org.dimensinfin.android.mvc.core.AbstractRender;
import org.dimensinfin.android.mvc.demo.R;
import org.dimensinfin.core.model.Separator;

import java.util.GregorianCalendar;

// - CLASS IMPLEMENTATION ...................................................................................
public class DemoDetailSeparatorPart extends AbstractAndroidPart {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static final long serialVersionUID = -7103273035430243825L;

	// - F I E L D - S E C T I O N ............................................................................
	private int iconReference = R.drawable.defaulticonplaceholder;

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public DemoDetailSeparatorPart (final Separator node) {
		super(node);
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	public Separator getCastedModel () {
		return (Separator) this.getModel();
	}

	public int getIconReference () {
		return iconReference;
	}

	public DemoDetailSeparatorPart setIconReference (final int resourceIdentifier) {
		logger.info("-- [DemoDetailSeparatorPart.setIconReference]> setting icon ref: " + resourceIdentifier);
		iconReference = resourceIdentifier;
		return this;
	}

	@Override
	public long getModelID () {
		return GregorianCalendar.getInstance().getTimeInMillis();
	}

	@Override
	public String toString () {
		StringBuffer buffer = new StringBuffer("DemoDetailSeparatorPart [");
		buffer.append("icon ref id: ").append(iconReference);
		buffer.append("]");
		return buffer.toString();
	}

	@Override
	protected AbstractRender selectRenderer () {
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
	public DemoDetailSeparatorRender (final AbstractAndroidPart target, final Activity context) {
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
		title = (TextView) _convertView.findViewById(R.id.title);
	}

	@Override
	public void updateContent () {
		super.updateContent();
		nodeIcon.setImageResource(getPart().getIconReference());
		title.setText(getPart().getCastedModel().getName());
		title.setVisibility(View.GONE);
	}

	@Override
	protected void createView () {
		_convertView=inflateView(R.layout.demodetailseparator4activity);
		_convertView.setTag(this);
	}
}

// - UNUSED CODE ............................................................................................
