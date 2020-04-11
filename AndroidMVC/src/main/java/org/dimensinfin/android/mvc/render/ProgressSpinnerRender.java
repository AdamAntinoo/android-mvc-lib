package org.dimensinfin.android.mvc.render;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;

import java.util.Objects;

import org.dimensinfin.android.mvc.R;
import org.dimensinfin.android.mvc.controller.ProgressSpinnerController;

public class ProgressSpinnerRender extends TypedRender<ProgressSpinnerController> {
	private TextView taskName;
	private Chronometer elapsedTime;
	private ImageView spinner;

	public ProgressSpinnerRender( @NonNull final ProgressSpinnerController controller, @NonNull final Context context ) {
		super( controller, context );
	}

	// - I R E N D E R
	@Override
	public int accessLayoutReference() {
		return R.layout.progressspinner4header;
	}

	@Override
	public void initializeViews() {
		this.taskName = Objects.requireNonNull( this.getView().findViewById( R.id.taskName ) );
		this.elapsedTime = Objects.requireNonNull( this.getView().findViewById( R.id.elapsedTime ) );
		this.spinner = Objects.requireNonNull( this.getView().findViewById( R.id.spinner ) );
		this.taskName.setVisibility( View.INVISIBLE );
		this.elapsedTime.setVisibility( View.VISIBLE );
		this.spinner.setVisibility( View.VISIBLE );
	}

	@Override
	public void updateContent() {
		Animation rotation = AnimationUtils.loadAnimation( getContext(), org.dimensinfin.android.mvc.R.anim.clockwise_rotation );
		rotation.setRepeatCount( Animation.INFINITE );
		this.spinner.startAnimation( rotation );
		this.elapsedTime.start();
		this.showLabel();
	}

	private void showLabel() {
		if (null != this.getController().getModel().getLabel()) {
			this.taskName.setText( this.getController().getModel().getLabel() );
			this.taskName.setVisibility( View.VISIBLE );
		}
	}
}
