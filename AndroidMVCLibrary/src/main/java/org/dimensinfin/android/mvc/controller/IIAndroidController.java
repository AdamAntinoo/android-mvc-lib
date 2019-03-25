package org.dimensinfin.android.mvc.controller;

import android.content.Context;
import org.dimensinfin.android.mvc.interfaces.IRender;

public interface IIAndroidController {
	long getModelId();
	String getRenderMode();
	IRender buildRender(final Context context);
}
