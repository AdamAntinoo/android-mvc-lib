package org.dimensinfin.android.mvc.demo.render;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;

import java.util.Objects;

import org.dimensinfin.android.mvc.R;
import org.dimensinfin.android.mvc.controller.IAndroidController;
import org.dimensinfin.android.mvc.demo.controller.DemoItemController;
import org.dimensinfin.android.mvc.render.MVCRender;

public class DemoLabelRender extends MVCRender {
	private TextView nodeName = null;

	public DemoLabelRender( @NonNull final IAndroidController controller, @NonNull final Context context ) {
		super( controller, context );
	}

	@Override
	public DemoItemController getController() {
		return (DemoItemController) super.getController();
	}

	// - I R E N D E R
	@Override
	public int accessLayoutReference() {
		return R.layout.item4list;
	}

	@Override
	public void initializeViews() {
		this.nodeName = Objects.requireNonNull(this.getView().findViewById( R.id.nodeName));
	}

	@Override
	public void updateContent() {
		this.nodeName.setText(this.getController().getModel().getTitle());
		this.nodeName.setVisibility( View.VISIBLE);
	}
}
