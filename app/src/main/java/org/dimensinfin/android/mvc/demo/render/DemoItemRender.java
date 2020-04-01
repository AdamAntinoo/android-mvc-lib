package org.dimensinfin.android.mvc.demo.render;

import android.content.Context;
import android.widget.ImageView;
import androidx.annotation.NonNull;

import java.util.Objects;

import org.dimensinfin.android.mvc.R;
import org.dimensinfin.android.mvc.controller.IAndroidController;
import org.dimensinfin.android.mvc.demo.controller.DemoItemController;

public class DemoItemRender extends DemoLabelRender {
	private ImageView nodeIcon = null;

	public DemoItemRender( @NonNull final IAndroidController controller, @NonNull final Context context ) {
		super( controller, context );
	}
	@Override
	public DemoItemController getController() {
		return (DemoItemController) super.getController();
	}
	@Override
	public void initializeViews() {
		super.initializeViews();
		this.nodeIcon = (ImageView) Objects.requireNonNull(this.getView().findViewById( R.id.nodeIcon));
	}

	@Override
	public void updateContent() {
		super.updateContent();
		this.nodeIcon.setImageResource(this.getController().getIconReference());
	}
}
