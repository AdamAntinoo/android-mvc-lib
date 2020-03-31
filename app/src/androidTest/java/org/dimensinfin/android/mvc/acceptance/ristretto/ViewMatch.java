package org.dimensinfin.android.mvc.acceptance.ristretto;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class ViewMatch {
	private List<View> views=new ArrayList<>(  );

	public ViewMatch() {}

	public void add( final View view ) {
		this.views.add( view );
	}

	public int count() {
		return this.views.size();
	}

	public boolean check( final ViewCheck check ) {
		try {
			return check.check(this.views.get( 0 ));
		} catch (final Exception e) {
			return false;
		}
	}
}
