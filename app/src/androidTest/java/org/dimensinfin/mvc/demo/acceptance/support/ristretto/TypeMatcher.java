package org.dimensinfin.mvc.demo.acceptance.support.ristretto;

import java.util.ArrayList;
import java.util.List;

import org.dimensinfin.android.mvc.controller.IAndroidController;

public class TypeMatcher<C> {
	private Class<C> targetClass;

	public TypeMatcher( final Class<C> targetClass ) {
		this.targetClass = targetClass;
	}

	public List<C> match( final List<IAndroidController> controllers/*, final Class type */ ) {
		final List<C> results = new ArrayList<>();
		try {
			for (IAndroidController controller : controllers)
				if (this.targetClass.isInstance( controller ))
					results.add( (C) controller );
		} catch (final RuntimeException rte) {
		}
		return results;
	}
}
