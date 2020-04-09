package org.dimensinfin.android.mvc.render;

import android.content.Context;
import androidx.annotation.NonNull;

import org.dimensinfin.android.mvc.core.IAndroidController;

public abstract class TypedRender<C extends IAndroidController> extends MVCRender {
	public TypedRender( @NonNull final C controller, @NonNull final Context context ) {
		super( controller, context );
	}

	public C getController() {
		return (C) super.getController();
	}
}
