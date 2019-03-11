package org.dimensinfin.android.mvc.support;

import android.content.Context;
import android.support.annotation.NonNull;
import org.dimensinfin.android.mvc.controller.AAndroidController;
import org.dimensinfin.android.mvc.interfaces.IAndroidController;
import org.dimensinfin.android.mvc.interfaces.IControllerFactory;
import org.dimensinfin.android.mvc.interfaces.IRender;
import org.dimensinfin.android.mvc.model.EmptyNode;

public class TestController extends AAndroidController<EmptyNode> implements IAndroidController<EmptyNode> {
	private boolean visible = true;

	public TestController(final @NonNull EmptyNode model, final @NonNull IControllerFactory factory) {
		super(model, factory);
	}

	@Override
	public IRender buildRender(final Context context) {
		return null;
	}

	@Override
	public long getModelId() {
		return 0;
	}

	@Override
	public boolean isVisible() {
		return visible;
	}

	public TestController setVisible(final boolean visible) {
		this.visible = visible;
		return this;
	}

	@Override
	public int compareTo(@NonNull final Object o) {
		final TestController target = (TestController) o;
		return this.getModel().getName().compareTo(((TestController) o).getModel().getName());
	}
}
