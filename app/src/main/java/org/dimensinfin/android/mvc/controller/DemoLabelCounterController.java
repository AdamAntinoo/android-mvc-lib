package org.dimensinfin.android.mvc.controller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.TextView;
import org.dimensinfin.android.mvc.demo.R;
import org.dimensinfin.android.mvc.interfaces.IRender;
import org.dimensinfin.android.mvc.model.DemoLabel;
import org.dimensinfin.android.mvc.model.DemoNode;

/**
 * @author Adam Antinoo
 */

public class DemoLabelCounterController extends AAndroidController<DemoLabel> {
	// - F I E L D - S E C T I O N
	// - C O N S T R U C T O R - S E C T I O N
	// - G E T T E R S   &   S E T T E R S
	// - M E T H O D - S E C T I O N
	// - I A N D R O I D C O N T R O L L E R   I N T E R F A C E
	@Override
	public IRender buildRender(final Context context) {
		return null;
	}

	@Override
	public long getModelId() {
		return this.getModel().hashCode();
	}

	// - C O M P A R A B L E
	@Override
	public int compareTo(@NonNull final Object o) {
		return this.getModel().compareTo((DemoNode) o);
	}

	public static class DemoLabelCounterRender<DemoLabelCounter> extends DemoItemAndroidController.DemoLabelRender {
		// - F I E L D - S E C T I O N
		private TextView labelCounter = null;

		// - C O N S T R U C T O R - S E C T I O N
		public DemoLabelCounterRender(final AAndroidController target, final Context context) {
			super(target, context);
		}

		// - M E T H O D - S E C T I O N
		@Override
		public DemoItemAndroidController getController() {
			return (DemoItemAndroidController) super.getController();
		}

		@Override
		public void initializeViews() {
			super.initializeViews();
			labelCounter = this.getView().findViewById(R.id.labelCounter);
			assert (labelCounter != null);
		}

		@Override
		public void updateContent() {
			super.updateContent();
			labelCounter.setText(this.getController().getModel().get);
		}
	}
}
