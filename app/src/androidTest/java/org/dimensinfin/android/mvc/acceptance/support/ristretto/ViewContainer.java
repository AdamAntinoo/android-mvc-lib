package org.dimensinfin.android.mvc.acceptance.ristretto;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

import org.dimensinfin.android.mvc.controller.IAndroidController;
import org.dimensinfin.android.mvc.domain.IRender;

public class ViewContainer {
	private List<View> views = new ArrayList<>();

	@Deprecated
	public ViewMatch onRender( final Class matcher ) {
		final ViewMatch viewMatch = new ViewMatch();
		for (View item : views) {
			final IAndroidController controller = (IAndroidController) item.getTag();
			final IRender render = controller.buildRender( item.getContext() );
			if (null != render)
				if (matcher.isInstance( render ))
					viewMatch.add( item );
		}
		return viewMatch;
	}

	public ViewMatch onController( final Class matcher ) {
		final ViewMatch viewMatch = new ViewMatch();
		for (View item : views) {
			final Object controller = item.getTag();
			if (null != controller)
				if (matcher.isInstance( controller ))
					viewMatch.add( item );
		}
		return viewMatch;
	}

	public void add( final View view ) {
		this.views.add( view );
	}
}
