//  PROJECT:     Android.MVC (A.MVC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2019 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Android API16.
//  DESCRIPTION: Library that defines a generic Model View Controller core classes to be used
//               on Android projects. Defines the AndroidController factory and the AndroidController core methods to manage
//               a generic converter from a Graph Model to a hierarchical AndroidController model that finally will
//               be converted to a AndroidController list to be used on a BaseAdapter tied to a ListView.
package org.dimensinfin.android.mvc.controller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;
import org.dimensinfin.android.mvc.demo.R;
import org.dimensinfin.android.mvc.interfaces.IControllerFactory;
import org.dimensinfin.android.mvc.interfaces.IRender;
import org.dimensinfin.android.mvc.model.DemoContainer;
import org.dimensinfin.android.mvc.render.AbstractRender;

import java.text.DecimalFormat;

/**
 * @author Adam Antinoo
 */
public class DemoContainerController extends AAndroidController<DemoContainer> implements View.OnClickListener{
	private static DecimalFormat itemCountFormatter = new DecimalFormat("###,##0");

	// - F I E L D - S E C T I O N
//	private GenericController<DemoContainer> delegatedController;

	// - C O N S T R U C T O R - S E C T I O N
//	public DemoContainerController(@NonNull final IControllerFactory factory) {
//		super(new GenericController<>(model),factory);
//	}

	public DemoContainerController(@NonNull final DemoContainer model, @NonNull final IControllerFactory factory) {
		super(new GenericController<DemoContainer>(model),factory);
		// Connect the delegate.
//		this.delegatedController = new GenericController<>(model);
	}

	// - V I E W . O N C L I C K L I S T E N E R
	@Override
	public void onClick(final View v) {
		// Increase the counter and see it it updates.
//		this.getModel().setCounter(this.getModel().getCounter() + 1);
		this.getModel().toggleExpand();
		this.notifyDataModelChange();
	}

	// - D E L E G A T E D - A A N D R O I D C O N T R O L L E R
//	public DemoContainer getModel() {
//		return delegatedController.getModel();
//	}

	// - O V E R R I D E - A A N D R O I D C O N T R O L L E R
	@Override
	public long getModelId() {
		return this.getModel().getTitle().hashCode();
	}

	@Override
	public IRender buildRender(final Context context) {
		return new DemoContainerRender(this, context);
	}

	// - G E T T E R S   &   S E T T E R S
	public String getTContentCount() {
		return itemCountFormatter.format(this.getModel().getContentSize());
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer("DemoItemAndroidController [ ");
		buffer.append("name: ").append(0);
		buffer.append("]");
		buffer.append("->").append(super.toString());
		return buffer.toString();
	}

	@Override
	public boolean isVisible() {
		return true;
	}

//	@Override
//	public int compareTo(@NonNull final Object o) {
//		if (o instanceof DemoContainerController) {
//			final DemoContainerController target = (DemoContainerController) o;
//			return this.getModel().compareTo(target.getModel());
//		} else return -1;
//	}

	public static class DemoContainerRender extends AbstractRender {
		// - F I E L D - S E C T I O N
		private TextView name;
		private TextView count;
		private TextView classLabel;
		private TextView categoryLabel;

		// - C O N S T R U C T O R - S E C T I O N
		public DemoContainerRender(final DemoContainerController controller, final Context context) {
			super(controller, context);
		}

		// - I R E N D E R   I N T E R F A C E
		@Override
		public DemoContainerController getController() {
			return (DemoContainerController) super.getController();
		}

		@Override
		public int accessLayoutReference() {
			return R.layout.baseexpandablenode;
		}

		@Override
		public void initializeViews() {
			name = this.getView().findViewById(R.id.name);
			assert (name != null);
			count = this.getView().findViewById(R.id.childCount);
			assert (count != null);
			classLabel = this.getView().findViewById(R.id.classLabel);
			assert (classLabel != null);
			categoryLabel = this.getView().findViewById(R.id.categoryLabel);
			assert (categoryLabel != null);
			classLabel.setVisibility(View.INVISIBLE);
			categoryLabel.setVisibility(View.INVISIBLE);
		}

		@Override
		public void updateContent() {
			name.setText(getController().getModel().getTitle());
			count.setText(Long.valueOf(this.getController().getModel().getContentSize()).toString());
		}
	}
}
