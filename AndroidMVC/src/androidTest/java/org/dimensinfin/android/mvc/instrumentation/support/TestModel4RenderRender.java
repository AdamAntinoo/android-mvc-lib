package org.dimensinfin.android.mvc.instrumentation.support;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;

import java.util.Objects;

import org.dimensinfin.android.mvc.R;
import org.dimensinfin.android.mvc.render.MVCRender;

public class TestModel4RenderRender extends MVCRender {
	private TextView label;

	public TestModel4RenderRender( @NonNull final TestModel4RenderController controller, @NonNull final Context context ) {
		super( controller, context );
	}

	@Override
	public TestModel4RenderController getController() {
		return (TestModel4RenderController) super.getController();
	}

	@Override
	public int accessLayoutReference() {
		return R.layout.mvcrender4test;
	}

	@Override
	public void initializeViews() {
		this.label = Objects.requireNonNull( this.getView().findViewById( R.id.label ) );
		this.label.setVisibility( View.INVISIBLE );
	}

	@Override
	public void updateContent() {
		this.label.setText( this.getController().getModel().getName() );
	}
}
