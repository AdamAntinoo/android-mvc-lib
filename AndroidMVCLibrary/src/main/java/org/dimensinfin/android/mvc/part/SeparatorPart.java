//  PROJECT:     Android.MVC (A.MVC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Android API16.
//  DESCRIPTION: Library that defines a generic Model View Controller core classes to be used
//               on Android projects. Defines the Part factory and the Part core methods to manage
//               a generic converter from a Graph Model to a hierarchical Part model that finally will
//               be converted to a Part list to be used on a BaseAdapter tied to a ListView.
//               The new implementation performs the model to list transformation on the fly each time
//               a model change is detected so the population of the displayed view should be done in
//               real time while processing the model sources. This should allow for search and filtering.
package org.dimensinfin.android.mvc.part;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import org.dimensinfin.android.mvc.R;
import org.dimensinfin.android.mvc.core.AbstractAndroidPart;
import org.dimensinfin.android.mvc.core.AbstractRender;
import org.dimensinfin.android.mvc.interfaces.IAndroidPart;
import org.dimensinfin.core.model.Separator;

import java.util.GregorianCalendar;

// - CLASS IMPLEMENTATION ...................................................................................
public class SeparatorPart extends AbstractAndroidPart {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static final long serialVersionUID = -7108273035439243825L;

	// - F I E L D - S E C T I O N ............................................................................

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public SeparatorPart ( final Separator node ) {
		super(node);
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	public Separator getCastedModel () {
		return (Separator) this.getModel();
	}

	@Override
	public long getModelId () {
		return GregorianCalendar.getInstance().getTimeInMillis();
	}

	public String getTitle () {
		return this.getCastedModel().getTitle();
	}

	@Override
	public String toString () {
		StringBuffer buffer = new StringBuffer("SeparatorPart [");
		buffer.append(getCastedModel().toString()).append(" ");
		buffer.append("]");
		return buffer.toString();
	}

	public IAndroidPart setRenderMode ( final String renderMode ) {
		this.renderMode = renderMode;
		return this;
	}

	@Override
	protected AbstractRender selectRenderer () {
		return new SeparatorRender(this, _activity);
	}
}

// - CLASS IMPLEMENTATION ...................................................................................
final class SeparatorRender extends AbstractRender {
	// - S T A T I C - S E C T I O N ..........................................................................

	// - F I E L D - S E C T I O N ............................................................................
	private TextView title = null;

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public SeparatorRender ( final AbstractAndroidPart target, final Activity context ) {
		super(target, context);
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	@Override
	public SeparatorPart getPart () {
		return (SeparatorPart) super.getPart();
	}

	@Override
	public void initializeViews () {
		super.initializeViews();
		title = (TextView) _convertView.findViewById(R.id.title);
	}

	@Override
	public void updateContent () {
		super.updateContent();
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
			case LINE_BLUE:
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
			case LINE_BLUE:
				renderer = R.layout.separatorblueline;
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
