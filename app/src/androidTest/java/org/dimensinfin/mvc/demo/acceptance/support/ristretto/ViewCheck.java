package org.dimensinfin.mvc.demo.acceptance.support.ristretto;

import android.view.View;

@FunctionalInterface
public interface ViewCheck {
	boolean check( final View view );
}
