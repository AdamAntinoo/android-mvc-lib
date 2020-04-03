package org.dimensinfin.mvc.demo.render;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;

import java.util.Objects;

import org.dimensinfin.android.mvc.render.TypedRender;
import org.dimensinfin.mvc.demo.R;
import org.dimensinfin.mvc.demo.controller.PageButtonController;

public class PageButtonRender extends TypedRender<PageButtonController> {
	private ImageView buttonIcon;
	private TextView buttonLabel;

	public PageButtonRender( @NonNull final PageButtonController controller, @NonNull final Context context ) {
		super( controller, context );
	}

	@Override
	public int accessLayoutReference() {
		return R.layout.pagebutton4header;
	}

	@Override
	public void initializeViews() {
		this.buttonIcon = Objects.requireNonNull( this.getView().findViewById( R.id.buttonIcon ) );
		this.buttonLabel = Objects.requireNonNull( this.getView().findViewById( R.id.buttonLabel ) );
		this.buttonIcon.setVisibility( View.GONE );
		this.buttonLabel.setVisibility( View.VISIBLE );
	}

	@Override
	public void updateContent() {
		this.buttonLabel.setText( this.getController().getModel().getLabel() );
	}
}
