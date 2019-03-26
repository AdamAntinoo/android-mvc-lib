package org.dimensinfin.android.mvc.controller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import org.dimensinfin.android.mvc.demo.R;
import org.dimensinfin.android.mvc.interfaces.IControllerFactory;
import org.dimensinfin.android.mvc.interfaces.IRender;
import org.dimensinfin.android.mvc.model.DemoItem;
import org.dimensinfin.android.mvc.model.DemoLabel;
import org.dimensinfin.android.mvc.render.AbstractRender;

/**
 * @author Adam Antinoo
 */
public class DemoItemAndroidController extends AAndroidController<DemoLabel> {
	// - F I E L D - S E C T I O N
	// - C O N S T R U C T O R - S E C T I O N
	public DemoItemAndroidController(@NonNull final GenericController<?> controller, @NonNull final IControllerFactory factory) {
		super((GenericController<DemoLabel>) controller,factory);
	}
	public DemoItemAndroidController(@NonNull final DemoLabel model, @NonNull final IControllerFactory factory) {
		super(new GenericController<DemoLabel>(model),factory);
	}

	// - O V E R R I D E - A A N D R O I D C O N T R O L L E R
	@Override
	public long getModelId() {
		return this.getModel().hashCode();
	}

	@Override
	public IRender buildRender(final Context context) {
		if (getRenderMode() == "-LABEL-") return new DemoLabelRender(this, context);
		if (getRenderMode() == "-ITEM-") return new DemoItemRender(this, context);
		return new DemoLabelRender(this, context);
	}

	// - C O M P A R A B L E   I N T E R F A C E
	@Override
	public int compareTo(@NonNull final Object o) {
		if (o instanceof DemoItemAndroidController) {
			final DemoItemAndroidController target = (DemoItemAndroidController) o;
			final DemoLabel m = this.getModel();
			final DemoLabel t = target.getModel();
			return m.getTitle().compareTo(t.getTitle());
		} else return -1;
	}

	@Override
	public boolean isVisible() {
		return true;
	}

	// - G E T T E R S   &   S E T T E R S
	public int getIconReference() {
		if (this.getModel() instanceof DemoItem)
			return ((DemoItem) getModel()).getIconIdentifier();
		return R.drawable.defaulticonplaceholder;
	}

	public static class DemoLabelRender extends AbstractRender {
		private TextView nodeName = null;

		public DemoLabelRender(@NonNull final DemoItemAndroidController target, @NonNull final Context context) {
			super(target, context);
		}

		@Override
		public DemoItemAndroidController getController() {
			return (DemoItemAndroidController) super.getController();
		}

		@Override
		public void initializeViews() {
			nodeName = this.getView().findViewById(R.id.nodeName);
			assert (nodeName != null);
		}

		@Override
		public void updateContent() {
			nodeName.setText(this.getController().getModel().getTitle());
			nodeName.setVisibility(View.VISIBLE);
		}

		@Override
		public int accessLayoutReference() {
			return R.layout.item4list;
		}
	}

	public static class DemoItemRender extends DemoLabelRender {
		// - F I E L D - S E C T I O N
		private ImageView nodeIcon = null;

		// - C O N S T R U C T O R - S E C T I O N
		public DemoItemRender(@NonNull final DemoItemAndroidController target, @NonNull final Context context) {
			super(target, context);
		}

		// - M E T H O D - S E C T I O N
		@Override
		public DemoItemAndroidController getController() {
			return super.getController();
		}

		@Override
		public void initializeViews() {
			super.initializeViews();
			nodeIcon = this.getView().findViewById(R.id.nodeIcon);
			assert (nodeIcon != null);
		}

		@Override
		public void updateContent() {
			super.updateContent();
			nodeIcon.setImageResource(getController().getIconReference());
		}
	}
}
