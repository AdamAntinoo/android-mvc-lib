package org.dimensinfin.android.mvc.acceptance.renders;

import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;

import org.dimensinfin.android.mvc.R;
import org.dimensinfin.android.mvc.acceptance.controller.TitlePanelController;
import org.dimensinfin.android.mvc.controller.IAndroidController;
import org.dimensinfin.android.mvc.render.MVCRender;

public class ExceptionTitlePanelRender extends MVCRender {
	private TextView title;

	public ExceptionTitlePanelRender( @NonNull final IAndroidController controller, @NonNull final Context context ) {
		super(controller, context);
	}

	@Override
	public TitlePanelController getController() {
		return (TitlePanelController) super.getController();
	}

	@Override
	public int accessLayoutReference() {
		return R.layout.titlepanel4list;
	}

	@Override
	public void initializeViews() {
		this.title = null;
	}

	@Override
	public void updateContent() {
		this.title.setText("Failure");
	}
}
