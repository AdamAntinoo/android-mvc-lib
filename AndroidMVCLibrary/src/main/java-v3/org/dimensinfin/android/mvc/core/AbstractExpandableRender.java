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
package org.dimensinfin.android.mvc.core;

import android.app.Activity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import org.dimensinfin.android.mvc.R;
import org.dimensinfin.core.interfaces.ICollaboration;
import org.dimensinfin.core.interfaces.IExpandable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Adam Antinoo
 */

// - CLASS IMPLEMENTATION ...................................................................................
public class AbstractExpandableRender extends AbstractRender {
	// - S T A T I C - S E C T I O N ..........................................................................

	// - F I E L D - S E C T I O N ............................................................................
	// - L A Y O U T   F I E L D S
	protected ImageView _rightArrow = null;

	protected ImageView nodeIcon = null;
	protected TextView name = null;
	protected TextView childCount = null;

	// - L A Y O U T   L A B E L S
	protected TextView classLabel = null;
	protected TextView categoryLabel = null;

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public AbstractExpandableRender ( final AbstractAndroidPart target, final Activity context ) {
		super(target, context);
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	//--- G E T T E R S   &   S E T T E R S
	@Override
	public void initializeViews () {
//		super.initializeViews();
		// Get the view of the rightArrow if found.
		_rightArrow = (ImageView) _convertView.findViewById(R.id.rightArrow);

		nodeIcon = (ImageView) _convertView.findViewById(R.id.nodeIcon);
		name = (TextView) _convertView.findViewById(R.id.name);
		childCount = (TextView) _convertView.findViewById(R.id.childCount);

		classLabel = (TextView) _convertView.findViewById(R.id.classLabel);
		categoryLabel = (TextView) _convertView.findViewById(R.id.categoryLabel);
	}

	/**
	 * Check if the expandable nodes have a click active. During the click show the spinner until the action finishes
	 * with a new <code>updateContent()</code>.
	 */
	@Override
	public void updateContent () {
//		super.updateContent();
		// Check if the model is expandable to show or hide the arrow.
		final ICollaboration targetModel = getPart().getModel();
		if ( null != _rightArrow ) {
			if ( targetModel instanceof IExpandable ) {
				_rightArrow.setVisibility(View.VISIBLE);
				if ( this.getPart().isExpanded() ) {
					_rightArrow.setImageResource(R.drawable.arrowdown);
				} else {
					_rightArrow.setImageResource(R.drawable.arrowright);
				}

				// Check if the click is running and then replace the right arrow by an spinner.
				if ( getPart().clickRunning() ) {
					if ( null != _rightArrow ) {
						logger.info("-- [NeoComRender.updateContent]> Activating spinner animation.");
						_rightArrow.setImageDrawable(getDrawable(R.drawable.progress_spinner_orange));
						Animation rotation = AnimationUtils.loadAnimation(getContext(), R.anim.clockwise_rotation);
						rotation.setRepeatCount(Animation.INFINITE);
						_rightArrow.startAnimation(rotation);
					}
				}
			} else _rightArrow.setVisibility(View.GONE);
		}
	}

	@Override
	public int accessLayoutReference() {
		return R.layout.baseexpandablenode;
	}

	//--- D E L E G A T E D   M E T H O D S
	@Override
	public String toString () {
		StringBuffer buffer = new StringBuffer("AbstractExpandableRender [ ");
		buffer.append("model: ").append(getPart().getModel().toString());
		buffer.append("]");
		//		buffer.append("->").append(super.toString());
		return buffer.toString();
	}
}
// - UNUSED CODE ............................................................................................
//[01]
