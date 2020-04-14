package org.dimensinfin.android.mvc.instrumentation.support;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;

import org.dimensinfin.android.mvc.R;
import org.dimensinfin.android.mvc.domain.IRender;

public class MVCRenderActivity extends Activity {
	private static final String VARIANT = "-TEST-VARIANT-";
	private ViewGroup container;
	private View testView;

	@Override
	protected void onCreate( @Nullable final Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		this.setContentView( R.layout.mvcrender_activity ); // Set the layout to the core context
		this.container = this.findViewById( R.id.container );
	}

	@Override
	protected void onStart() {
		final TestModel4RenderController controller = new TestModel4RenderController(
				new TestModel4Render.Builder().withName("-TEST-NAME-VALUE-").build(),
				new TestControllerFactory( VARIANT )
		);
		final IRender render = controller.buildRender( this );
		final View view = render.getView();
		this.container.removeAllViews();
		this.container.addView( view );
		this.container.invalidate();
		super.onStart();
	}
}
