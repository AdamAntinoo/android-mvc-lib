//  PROJECT:     Android.MVC (A.MVC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2019 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Android API16.
//  DESCRIPTION: Library that defines a generic Model View Controller core classes to be used
//               on Android projects. Defines the AndroidController factory and the AndroidController core methods to manage
//               a generic converter from a Graph Model to a hierarchical AndroidController model that finally will
//               be converted to a AndroidController list to be used on a BaseAdapter tied to a ListView.
package org.dimensinfin.android.mvc.controller;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import org.dimensinfin.android.mvc.R;
import org.dimensinfin.android.mvc.interfaces.IRender;
import org.dimensinfin.android.mvc.render.AbstractRender;
import org.dimensinfin.core.model.Separator;
import org.joda.time.Instant;

import java.beans.PropertyChangeListener;

public class SeparatorAndroidController extends AAndroidController<Separator> {
	private static final long serialVersionUID = -7108273035439243825L;

	// - F I E L D - S E C T I O N
	// - C O N S T R U C T O R - S E C T I O N
	// - M E T H O D - S E C T I O N


	public String getTitle() {
		return this.getModel().getTitle();
	}


	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer("SeparatorAndroidController [");
		buffer.append(getModel().toString()).append(" ");
		buffer.append("]");
		return buffer.toString();
	}


//	@Override
//	public AbstractRender selectRenderer () {

	// - I A N D R O I D C O N T R O L L E R   I N T E R F A C E

	/**
	 * This method is required by the Adapter to get a unique identifier for each node to be render on a Viewer.
	 * @return a unique number identifier.
	 */
	@Override
	public long getModelId() {
		return Instant.now().getMillis();
	}

	@Override
	public IRender getRenderer(final Context context) {
		final AbstractRender render = new SeparatorRender.Builder(this, context)
				.controller(this)
				.build();
	}

	@Override
	public void addPropertyChangeListener(final PropertyChangeListener listener) {

	}
}

final class SeparatorRender extends AbstractRender {
	// - F I E L D - S E C T I O N
	private TextView title = null;

	// - C O N S T R U C T O R - S E C T I O N
	protected SeparatorRender(final SeparatorRender.Builder builder) {
		super(builder);
	}

	@Override
	protected void initializeViews() {
		title = this.getView().findViewById(R.id.title);
	}

	// - M E T H O D - S E C T I O N
	@Override
	public SeparatorAndroidController getController() {
		return (SeparatorAndroidController) super.getController();
	}

	// - I R E N D E R   I N T E R F A C E
	@Override
	public void updateContent() {
//		super.updateContent();
		String tt = this.getController().getTitle();
		switch (this.getController().getCastedModel().getType()) {
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
		}
		_convertView.invalidate();
	}

	/**
	 * Thia method is the responsible to return the layout to be used on the view inflation. On the separator particularly this
	 * layout depends on the model contents so at this call we should access the Model and calculate the right layout to use.
	 * @return the layout unique identifier to be used on the view genertion.
	 */
	@Override
	protected int accessLayoutReference() {
		// Separator can be rendered in many ways. Set the default and then calculate the right one depending on the Model type.
		int layoutRef = R.layout.separatororangeline;
		// Select the rendering depending on the Separator type.
		switch (this.getController().getCastedModel().getType()) {
			case DEFAULT:
				layoutRef = R.layout.separatororangeline;
				break;
			case LINE_WHITE:
				layoutRef = R.layout.separatorwhiteline;
				break;
			case LINE_RED:
				layoutRef = R.layout.separatorredline;
				// Collapse the expansion.
				//			this.getController().getCastedModel().collapse();
				break;
			case LINE_ORANGE:
				layoutRef = R.layout.separatororangeline;
				// Collapse the expansion.
				//				this.getController().getCastedModel().setExpanded(false);
				break;
			case LINE_YELLOW:
				layoutRef = R.layout.separatoryellowline;
				// Collapse the expansion.
				//				this.getController().getCastedModel().setExpanded(false);
				break;
			case LINE_GREEN:
				layoutRef = R.layout.separatorgreenline;
				// Collapse the expansion.
				//				this.getController().getCastedModel().setExpanded(false);
				break;
			case LINE_DARKBLUE:
				layoutRef = R.layout.separatordarkblueline;
				// Collapse the expansion.
				//				this.getController().getCastedModel().setExpanded(false);
				break;
			case EMPTY_SIGNAL:
				layoutRef = R.layout.separatorredline;
				// Collapse the expansion.
				//				this.getController().getCastedModel().setExpanded(false);
				break;

			default:
				break;
		}
	}

	@Override
	protected void createView() {
		final LayoutInflater mInflater = (LayoutInflater) this.getContext()
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		_convertView = mInflater.inflate(renderer, null);
		_convertView.setTag(this);
	}

	// - B U I L D E R
	public static class Builder extends AbstractRender.Builder<SeparatorAndroidController> {
		public Builder(final SeparatorAndroidController controller, final Context context) {
			super(controller, context);
		}

		public SeparatorRender build() {
			return new SeparatorRender(this);
		}
	}

}
