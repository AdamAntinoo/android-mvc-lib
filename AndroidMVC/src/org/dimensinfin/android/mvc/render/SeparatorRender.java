//	PROJECT:        NeoCom.Android (NEOC.A)
//	AUTHORS:        Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2013-2016 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:		Android API16.
//	DESCRIPTION:		Application to get access to CCP api information and help manage industrial activities
//									for characters and corporations at Eve Online. The set is composed of some projects
//									with implementation for Android and for an AngularJS web interface based on REST
//									services on Sprint Boot Cloud.
package org.dimensinfin.android.mvc.render;

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
	//	private static Logger	logger	= Logger.getLogger("SeparatorHolder");

	// - F I E L D - S E C T I O N ............................................................................
	private TextView title = null;

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
	}

	@Override
	public void updateContent() {
		super.updateContent();
		String tt = this.getPart().getTitle();
		if (null != tt) {
			title.setText(tt);
			title.setVisibility(View.VISIBLE);
		}
		switch (this.getPart().getCastedModel().getType()) {
			case LINE_RED:
				title.setVisibility(View.GONE);
				break;
			case LINE_ORANGE:
				title.setVisibility(View.GONE);
				break;
			case LINE_GREEN:
				title.setVisibility(View.GONE);
				break;
			case EMPTY_SIGNAL:
				// Set the predefined text.
				title.setText("-EMPTY CONTENTS-");
				title.setVisibility(View.VISIBLE);
				break;
		}
		_convertView.invalidate();
	}

	@Override
	protected void createView() {
		final LayoutInflater mInflater = (LayoutInflater) this.getContext()
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		// Separator can be rendered in many ways. Set the default and then calculate the right one depending on the Model type.
		int renderer = R.layout.separatororangeline;
		// Select the rendering depending on the Separator type.
		switch (this.getPart().getCastedModel().getType()) {
			case LINE_RED:
				renderer = R.layout.separatorredline;
				// Collapse the expansion.
				this.getPart().getCastedModel().setExpanded(false);
				break;
			case LINE_ORANGE:
				renderer = R.layout.separatororangeline;
				// Collapse the expansion.
				this.getPart().getCastedModel().setExpanded(false);
				break;

			case LINE_GREEN:
				renderer = R.layout.separatorgreenline;
				// Collapse the expansion.
				this.getPart().getCastedModel().setExpanded(false);
				break;
			case EMPTY_SIGNAL:
				renderer = R.layout.separatorredline;
				// Collapse the expansion.
				this.getPart().getCastedModel().setExpanded(false);
				break;

			default:
				break;
		}
		_convertView = mInflater.inflate(renderer, null);
		_convertView.setTag(this);
	}
}

// - UNUSED CODE ............................................................................................
