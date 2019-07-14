package org.dimensinfin.android.mvc.controller;

import android.content.Context;
import android.view.View;

import org.dimensinfin.android.mvc.datasource.IDataSource;
import org.dimensinfin.android.mvc.interfaces.IEventEmitter;
import org.dimensinfin.android.mvc.interfaces.IRender;

import java.util.List;

public interface IAndroidController<M> extends IEventEmitter, Comparable {
	M getModel();

	View getViewCache();

	IAndroidController setViewCache( final View targetView );

	boolean isOrderedActive();

	IAndroidController setOrderedActive( final boolean orderedActive );

	String getRenderMode();

	AAndroidController setRenderMode( final String renderMode );

	void collaborate2View( final List<IAndroidController> contentCollector );

	List<IAndroidController> orderingFeature( final List<IAndroidController> children );

	boolean isVisible();

	void refreshChildren();

	long getModelId();

	IAndroidController setDataSource( IDataSource amvcDataSource );

	// - A B S T R A C T
	IRender buildRender( final Context context );

}
