package org.dimensinfin.android.mvc.interfaces;

import android.content.Context;
import android.view.View;

import java.util.List;

public interface IAndroidController<M extends ICollaboration> extends IEventEmitter, Comparable<M> {
	M getModel();
	void refreshChildren();
	IRender buildRender(final Context context);
	View getViewCache();
	void setViewCache(final View targetView);
	long getModelId();

	boolean isOrderedActive();
	void setOrderedActive();
	List<IAndroidController> orderingFeature(final List<IAndroidController> children);
	void collaborate2View(final List<IAndroidController> contentCollector);
	boolean isVisible();
}
