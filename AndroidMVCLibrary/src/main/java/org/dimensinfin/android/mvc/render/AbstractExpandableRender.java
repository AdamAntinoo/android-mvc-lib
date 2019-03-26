package org.dimensinfin.android.mvc.render;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import org.dimensinfin.android.mvc.R;
import org.dimensinfin.android.mvc.controller.AbstractExpandableAndroidController;
import org.dimensinfin.android.mvc.controller.IAndroidController;
import org.dimensinfin.android.mvc.interfaces.ICollaboration;
import org.dimensinfin.android.mvc.model.IExpandable;

/**
 * @author Adam Antinoo
 */
public abstract class AbstractExpandableRender extends AbstractRender {
	// - F I E L D - S E C T I O N
	// - L A Y O U T   F I E L D S
	protected ImageView _rightArrow = null;

	protected ImageView nodeIcon = null;
	protected TextView name = null;
	protected TextView childCount = null;

	// - L A Y O U T   L A B E L S
	protected TextView classLabel = null;
	protected TextView categoryLabel = null;

	// - C O N S T R U C T O R - S E C T I O N
	public AbstractExpandableRender(@NonNull final IAndroidController controller, @NonNull final Context context) {
		super(controller, context);
	}

	public AbstractExpandableAndroidController getController() {
		return (AbstractExpandableAndroidController) super.getController();
	}

	@Override
	public void initializeViews() {
		logger.info(">< [AbstractExpandableRender.initializeViews]");
		_rightArrow = this.getView().findViewById(R.id.rightArrow);

		nodeIcon = this.getView().findViewById(R.id.nodeIcon);
		name = this.getView().findViewById(R.id.name);
		childCount = this.getView().findViewById(R.id.childCount);

		classLabel = this.getView().findViewById(R.id.classLabel);
		categoryLabel = this.getView().findViewById(R.id.categoryLabel);
	}

	/**
	 * Check if the expandable nodes have a click active. During the click show the spinner until the action finishes with
	 * a new <code>updateContent()</code>.
	 */
	@Override
	public void updateContent() {
		logger.info(">< [AbstractExpandableRender.updateContent]");
		// Check if the model is expandable to show or hide the arrow.
		ICollaboration targetModel = (ICollaboration) this.getController().getModel();
		if (null != _rightArrow) {
			if (targetModel instanceof IExpandable) {
				_rightArrow.setVisibility(View.VISIBLE);
				logger.info(">< [AbstractExpandableRender.updateContent]> Setting right arrow");
				if (((IExpandable) targetModel).isExpanded()) {
					_rightArrow.setImageResource(R.drawable.arrowdown);
				} else {
					_rightArrow.setImageResource(R.drawable.arrowright);
				}

				// Check if the click is running and then replace the right arrow by an spinner.
				if (getController().clickRunning()) {
					if (null != _rightArrow) {
						logger.info("-- [AbstractExpandableRender.updateContent]> Activating spinner animation.");
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

//	//--- D E L E G A T E D   M E T H O D S
//	@Override
//	public String toString() {
//		StringBuffer buffer = new StringBuffer("AbstractExpandableRender [ ");
//		buffer.append("model: ").append(getController().getModel().toString());
//		buffer.append("]");
//		//		buffer.append("->").append(super.toString());
//		return buffer.toString();
//	}
}
