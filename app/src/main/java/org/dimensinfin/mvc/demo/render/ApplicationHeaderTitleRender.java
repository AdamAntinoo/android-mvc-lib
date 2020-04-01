package org.dimensinfin.mvc.demo.render;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.Objects;

import org.dimensinfin.android.mvc.controller.IAndroidController;
import org.dimensinfin.mvc.demo.R;
import org.dimensinfin.mvc.demo.controller.ApplicationHeaderTitleController;
import org.dimensinfin.android.mvc.render.MVCRender;

public class ApplicationHeaderTitleRender extends MVCRender {
	private TextView applicationName;
	private TextView applicationVersion;

	public ApplicationHeaderTitleRender( @NonNull final IAndroidController controller, @NonNull final Context context ) {
		super( controller, context );
	}

	@Override
	public ApplicationHeaderTitleController getController() {
		return (ApplicationHeaderTitleController) super.getController();
	}

	// - I R E N D E R
	@Override
	public int accessLayoutReference() {
		return R.layout.headertitle4header;
	}

	@Override
	public void initializeViews() {
		this.applicationName = Objects.requireNonNull( this.getView().findViewById( R.id.applicationName));
		this.applicationVersion = Objects.requireNonNull( this.getView().findViewById(R.id.applicationVersion));
	}

	@Override
	public void updateContent() {
		this.applicationName.setText(this.getController().getModel().getName());
		this.applicationName.setVisibility( View.VISIBLE);
		this.applicationVersion.setText(getController().getModel().getVersion());
		this.applicationVersion.setVisibility(View.VISIBLE);
	}
}
