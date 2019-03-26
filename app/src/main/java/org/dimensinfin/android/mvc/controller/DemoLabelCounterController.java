package org.dimensinfin.android.mvc.controller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;
import org.dimensinfin.android.mvc.demo.R;
import org.dimensinfin.android.mvc.interfaces.IControllerFactory;
import org.dimensinfin.android.mvc.interfaces.IRender;
import org.dimensinfin.android.mvc.model.DemoLabel;
import org.dimensinfin.android.mvc.model.DemoLabelCounter;

/**
 * @author Adam Antinoo
 */

public class DemoLabelCounterController extends DemoItemAndroidController implements View.OnClickListener {
	// - F I E L D - S E C T I O N
//	private GenericController<DemoLabelCounter> delegatedController;

	// - C O N S T R U C T O R - S E C T I O N
	public DemoLabelCounterController(@NonNull final DemoLabelCounter model, @NonNull final IControllerFactory factory) {
		super(new GenericController<DemoLabelCounter>(model),factory);
	}

	// - D E L E G A T E D - A A N D R O I D C O N T R O L L E R
	public DemoLabelCounter getModel() {
		return (DemoLabelCounter) super.getModel();
	}

	// - O V E R R I D E - A A N D R O I D C O N T R O L L E R
	@Override
	public long getModelId() {
		return this.getModel().hashCode();
	}

	@Override
	public IRender buildRender(final Context context) {
		return new DemoLabelCounterRender(this, context);
	}

	// - C O M P A R A B L E   I N T E R F A C E
	@Override
	public int compareTo(@NonNull final Object o) {
		return this.getModel().compareTo(((IAndroidController)o).getModel());
	}

	// - V I E W . O N C L I C K L I S T E N E R
	@Override
	public void onClick(final View v) {
		// Increase the counter and see it it updates.
		this.getModel().setCounter(this.getModel().getCounter() + 1);
		this.notifyDataModelChange();
	}

	// - R E N D E R   C L A S S
	public static class DemoLabelCounterRender extends DemoItemAndroidController.DemoLabelRender {
		// - F I E L D - S E C T I O N
		private TextView labelCounter = null;

		// - C O N S T R U C T O R - S E C T I O N
		public DemoLabelCounterRender(final DemoLabelCounterController target, final Context context) {
			super(target, context);
		}

		// - M E T H O D - S E C T I O N
		@Override
		public DemoLabelCounterController getController() {
			return (DemoLabelCounterController) super.getController();
		}

		@Override
		public int accessLayoutReference() {
			return R.layout.labelcounter4list;
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
			labelCounter.setText(Integer.valueOf(this.getController().getModel().getCounter()).toString());
		}
	}
}
