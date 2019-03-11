package org.dimensinfin.android.mvc.interfaces;

import android.content.Context;
import android.view.View;

import java.util.List;

public interface IAndroidController<M extends ICollaboration> extends IEventEmitter, Comparable {
	M getModel();
	void refreshChildren();
	View getViewCache();
	IAndroidController<M> setViewCache(final View targetView);
	boolean isOrderedActive();
	IAndroidController<M> setOrderedActive(final boolean orderedActive);
	List<IAndroidController<M>> orderingFeature(final List<IAndroidController<M>> children);
	void collaborate2View(final List<IAndroidController> contentCollector);
	boolean isVisible();

	// - A B S T R A C T
	IRender buildRender(final Context context);
	long getModelId();
}
