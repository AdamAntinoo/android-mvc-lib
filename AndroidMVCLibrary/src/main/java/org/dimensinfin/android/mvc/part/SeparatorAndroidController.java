//  PROJECT:     Android.MVC (A.MVC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2019 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Android API16.
//  DESCRIPTION: Library that defines a generic Model View Controller core classes to be used
//               on Android projects. Defines the AndroidController factory and the AndroidController core methods to manage
//               a generic converter from a Graph Model to a hierarchical AndroidController model that finally will
//               be converted to a AndroidController list to be used on a BaseAdapter tied to a ListView.
package org.dimensinfin.android.mvc.part;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import lombok.Builder;
import org.dimensinfin.android.mvc.R;
import org.dimensinfin.android.mvc.controller.AAndroidController;
import org.dimensinfin.android.mvc.core.AbstractAndroidAndroidController;
import org.dimensinfin.android.mvc.core.AbstractRender;
import org.dimensinfin.android.mvc.core.AndroidController;
import org.dimensinfin.android.mvc.interfaces.IAndroidAndroidController;
import org.dimensinfin.core.interfaces.ICollaboration;
import org.dimensinfin.core.model.Separator;
import org.joda.time.Instant;

import java.util.GregorianCalendar;
@Builder
public class SeparatorAndroidController extends AAndroidController<Separator,SeparatorRender> {
	private static final long serialVersionUID = -7108273035439243825L;

	// - F I E L D - S E C T I O N
	// - C O N S T R U C T O R - S E C T I O N
	// - M E T H O D - S E C T I O N
//	public Separator getCastedModel () {
//		return (Separator) this.getModel();
//	}

	/**
	 * This method is required by the Adapter to get a unique identifier for each node to be render on a Viewer.
	 * @return a unique number identifier.
	 */
	public long getModelId () {
		return Instant.now().getMillis();
	}

	public String getTitle () {
		return this.getModel().getTitle();
	}

	@Override
	public String toString () {
		StringBuffer buffer = new StringBuffer("SeparatorAndroidController [");
		buffer.append(getModel().toString()).append(" ");
		buffer.append("]");
		return buffer.toString();
	}


	@Override
	public AbstractRender selectRenderer () {
		return new SeparatorRender(this, _activity);
	}
}

// - CLASS IMPLEMENTATION ...................................................................................
final class SeparatorRender extends AbstractRender {
	// - S T A T I C - S E C T I O N ..........................................................................

	// - F I E L D - S E C T I O N ............................................................................
	private TextView title = null;

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public SeparatorRender (final AbstractAndroidAndroidController target, final Activity context ) {
		super(target, context);
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	@Override
	public SeparatorAndroidController getPart () {
		return (SeparatorAndroidController) super.getPart();
	}

	// --- I R E N D E R   I N T E R F A C E
	@Override
	public void initializeViews () {
//		super.initializeViews();
		title = (TextView) _convertView.findViewById(R.id.title);
	}

	@Override
	public void updateContent () {
//		super.updateContent();
		String tt = this.getPart().getTitle();
		switch (this.getPart().getCastedModel().getType()) {
			case DEFAULT:
				if ( null != tt ) {
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
		}
		_convertView.invalidate();
	}

	/**
	 * Method not used because this Render implements a programatically generated layout reference.
	 * @return
	 */
	@Override
	public int accessLayoutReference() {
		return R.layout.separatorwhiteline;
	}

	@Override
	protected void createView () {
		final LayoutInflater mInflater = (LayoutInflater) this.getContext()
		                                                      .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		// Separator can be rendered in many ways. Set the default and then calculate the right one depending on the Model type.
		int renderer = R.layout.separatororangeline;
		// Select the rendering depending on the Separator type.
		switch (this.getPart().getCastedModel().getType()) {
			case DEFAULT:
				renderer = R.layout.separatororangeline;
				break;
			case LINE_WHITE:
				renderer = R.layout.separatorwhiteline;
				break;
			case LINE_RED:
				renderer = R.layout.separatorredline;
				// Collapse the expansion.
				//			this.getPart().getCastedModel().collapse();
				break;
			case LINE_ORANGE:
				renderer = R.layout.separatororangeline;
				// Collapse the expansion.
				//				this.getPart().getCastedModel().setExpanded(false);
				break;
			case LINE_YELLOW:
				renderer = R.layout.separatoryellowline;
				// Collapse the expansion.
				//				this.getPart().getCastedModel().setExpanded(false);
				break;
			case LINE_GREEN:
				renderer = R.layout.separatorgreenline;
				// Collapse the expansion.
				//				this.getPart().getCastedModel().setExpanded(false);
				break;
			case LINE_DARKBLUE:
				renderer = R.layout.separatordarkblueline;
				// Collapse the expansion.
				//				this.getPart().getCastedModel().setExpanded(false);
				break;
			case EMPTY_SIGNAL:
				renderer = R.layout.separatorredline;
				// Collapse the expansion.
				//				this.getPart().getCastedModel().setExpanded(false);
				break;

			default:
				break;
		}
		_convertView = mInflater.inflate(renderer, null);
		_convertView.setTag(this);
	}
}

// - UNUSED CODE ............................................................................................
