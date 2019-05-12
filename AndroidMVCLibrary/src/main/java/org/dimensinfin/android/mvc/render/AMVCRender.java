package org.dimensinfin.android.mvc.render;

import android.content.Context;

import org.dimensinfin.android.mvc.controller.IAndroidController;
import org.dimensinfin.android.mvc.interfaces.IRender;

import androidx.annotation.NonNull;

public abstract class AMVCRender extends MVCRender implements IRender {
	public AMVCRender( @NonNull final IAndroidController controller, @NonNull final Context context ) {
		super(controller, context);
	}
}
