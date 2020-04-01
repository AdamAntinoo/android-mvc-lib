package org.dimensinfin.mvc.demo.render;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;

import java.util.Objects;

import org.dimensinfin.android.mvc.controller.IAndroidController;
import org.dimensinfin.mvc.demo.R;
import org.dimensinfin.mvc.demo.controller.TitleLabelController;
import org.dimensinfin.android.mvc.render.MVCRender;

public class TitleLabelRender extends MVCRender {
	private TextView title = null;

	public TitleLabelRender( @NonNull final IAndroidController controller, @NonNull final Context context ) {
		super( controller, context );
	}

	@Override
	public TitleLabelController getController() {
		return (TitleLabelController) super.getController();
	}

	// - I R E N D E R
	@Override
	public int accessLayoutReference() {
		return R.layout.titlelabel4header;
	}

	@Override
	public void initializeViews() {
		this.title = Objects.requireNonNull( this.getView().findViewById( R.id.title ) );
	}

	@Override
	public void updateContent() {
		this.title.setText( this.getController().getModel().getTitle() );
		this.title.setVisibility( View.VISIBLE );
	}
}
