//  PROJECT:     Android.MVC (A.MVC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2019 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Android API16.
//  DESCRIPTION: Library that defines a generic Model View Controller core classes to be used
//               on Android projects. Defines the Part factory and the Part core methods to manage
//               a generic converter from a Graph Model to a hierarchical Part model that finally will
//               be converted to a Part list to be used on a BaseAdapter tied to a ListView.
package org.dimensinfin.android.mvc.render;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import org.dimensinfin.android.mvc.R;
import org.dimensinfin.android.mvc.controller.AAndroidController;
import org.dimensinfin.android.mvc.model.Separator;

/**
 * @author Adam Antinoo
 */

public class SeparatorRender extends AbstractRender<Separator> {
	// - F I E L D - S E C T I O N
	private TextView title;

	// - C O N S T R U C T O R - S E C T I O N
	public SeparatorRender(final AAndroidController<Separator> controller, final Context context) {
		super(controller, context);
	}

	@Override
	protected void initializeViews() {
		title = this.getView().findViewById(R.id.title);
	}

	// - M E T H O D - S E C T I O N
	// - I R E N D E R   I N T E R F A C E
	@Override
	public void updateContent() {
		String tt = this.getController().getModel().getTitle();
		switch (this.getController().getModel().getType()) {
			case DEFAULT:
				if (null != tt) {
					title.setText(tt);
					title.setVisibility(View.VISIBLE);
				} else title.setVisibility(View.GONE);
				break;
			case LINE_WHITE:
				title.setVisibility(View.GONE);
				break;
			case LINE_RED:
				title.setVisibility(View.GONE);
				break;
			case LINE_ORANGE:
				title.setVisibility(View.GONE);
				break;
			case LINE_YELLOW:
				title.setVisibility(View.GONE);
				break;
			case LINE_GREEN:
				title.setVisibility(View.GONE);
				break;
			case LINE_DARKBLUE:
				title.setVisibility(View.GONE);
				break;
			case EMPTY_SIGNAL:
				// Set the predefined text.
				title.setText("-EMPTY CONTENTS-");
				title.setVisibility(View.VISIBLE);
				break;
			default:
				title.setVisibility(View.GONE);
				break;
		}
		this.getView().invalidate(); // Force the adapter to refresh the view port.
	}

	/**
	 * Thia method is the responsible to return the layout to be used on the view inflation. On the separator particularly
	 * this layout depends on the model contents so at this call we should access the Model and calculate the right layout
	 * to use.
	 * @return the layout
	 */
	@Override
	protected int accessLayoutReference() {
		// Separator can be rendered in many ways. Set the default and then calculate the right one depending on the Model type.
		int layoutRef = R.layout.separatorwhiteline;
		// Select the rendering depending on the Separator type.
		switch (this.getController().getModel().getType()) {
			case DEFAULT:
				layoutRef = R.layout.separatororangeline;
				break;
			case LINE_WHITE:
				layoutRef = R.layout.separatorwhiteline;
				break;
			case LINE_RED:
				layoutRef = R.layout.separatorredline;
				break;
			case LINE_ORANGE:
				layoutRef = R.layout.separatororangeline;
				break;
			case LINE_YELLOW:
				layoutRef = R.layout.separatoryellowline;
				break;
			case LINE_GREEN:
				layoutRef = R.layout.separatorgreenline;
				break;
			case LINE_DARKBLUE:
				layoutRef = R.layout.separatordarkblueline;
				break;
			case EMPTY_SIGNAL:
				layoutRef = R.layout.separatorredline;
				break;
			default:
				break;
		}
		return layoutRef;
	}

	// - B U I L D E R
//	public static class Builder extends AbstractRender.Builder<Separator> {
//		public Builder(final AAndroidController<Separator> controller, final Context context) {
//			super(controller, context);
//		}
//
//		public SeparatorRender build() {
//			return new SeparatorRender(this);
//		}
//	}
}
