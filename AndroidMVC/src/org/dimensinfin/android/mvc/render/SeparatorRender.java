//	PROJECT:        NeoCom.Android (NEOC.A)
//	AUTHORS:        Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2013-2016 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:		Android API16.
//	DESCRIPTION:		Application to get access to CCP api information and help manage industrial activities
//									for characters and corporations at Eve Online. The set is composed of some projects
//									with implementation for Android and for an AngularJS web interface based on REST
//									services on Sprint Boot Cloud.
package org.dimensinfin.android.mvc.render;

// - IMPORT SECTION .........................................................................................
import java.util.logging.Logger;

import org.dimensinfin.android.model.Separator.ESeparatorType;
import org.dimensinfin.android.mvc.R;
import org.dimensinfin.android.mvc.core.AbstractAndroidPart;
import org.dimensinfin.android.mvc.core.AbstractRender;
import org.dimensinfin.android.mvc.part.SeparatorPart;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

// - CLASS IMPLEMENTATION ...................................................................................
public class SeparatorRender extends AbstractRender {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger	logger	= Logger.getLogger("SeparatorHolder");

	// - F I E L D - S E C T I O N ............................................................................
	private TextView			title		= null;
	//	private TextView			content	= null;

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public SeparatorRender(final AbstractAndroidPart target, final Activity context) {
		super(target, context);
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	@Override
	public SeparatorPart getPart() {
		return (SeparatorPart) super.getPart();
	}

	@Override
	public void initializeViews() {
		title = (TextView) _convertView.findViewById(R.id.title);
		//	content = (TextView) _convertView.findViewById(R.id.content);
	}

	@Override
	public void updateContent() {
		String tt = this.getPart().getTitle();
		if (null != tt) {
			title.setText(tt);
			title.setVisibility(View.VISIBLE);
		}
	}

	@Override
	protected void createView() {
		final LayoutInflater mInflater = (LayoutInflater) this.getContext()
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		int renderer = R.layout.separatororangeline;
		// Select the rendering depending on the Separator type.
		if (this.getPart().getCastedModel().getType() == ESeparatorType.LINE_ORANGE) {
			renderer = R.layout.separatororangeline;
		}
		if (this.getPart().getCastedModel().getType() == ESeparatorType.LINE_GREEN) {
			renderer = R.layout.separatorgreenline;
		}
		_convertView = mInflater.inflate(renderer, null);
		_convertView.setTag(this);
	}
}

// - UNUSED CODE ............................................................................................
