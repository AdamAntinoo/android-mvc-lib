package org.dimensinfin.android.mvc.interfaces;

import android.content.Context;
import android.view.View;
import org.dimensinfin.android.mvc.controller.AAndroidController;

import java.util.List;

public interface IAndroidController<M extends ICollaboration> extends IEventEmitter, Comparable<M> {
	M getModel();
	void refreshChildren();
	View getViewCache();
	AAndroidController<M> setViewCache(final View targetView);
	boolean isOrderedActive();
	AAndroidController<M> setOrderedActive(final boolean orderedActive);
	List<IAndroidController> orderingFeature(final List<IAndroidController> children);
	void collaborate2View(final List<IAndroidController> contentCollector);
	boolean isVisible();

	// - A B S T R A C T
	IRender buildRender(final Context context);
	long getModelId();
}
