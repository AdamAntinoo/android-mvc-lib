package org.dimensinfin.android.mvc.support;

import android.content.Context;
import androidx.annotation.NonNull;

import org.dimensinfin.android.mvc.controller.IAndroidController;
import org.dimensinfin.android.mvc.render.MVCRender;

public class TestRender4Render extends MVCRender {

	public TestRender4Render( @NonNull final IAndroidController controller, @NonNull final Context context ) {
		super( controller, context );
	}

	@Override
	public int accessLayoutReference() {
		return 0;
	}

	@Override
	public void initializeViews() {

	}

	@Override
	public void updateContent() {

	}
}
