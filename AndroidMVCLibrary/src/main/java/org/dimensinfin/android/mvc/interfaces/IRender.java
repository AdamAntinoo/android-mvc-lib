package org.dimensinfin.android.mvc.interfaces;

import android.view.View;

public interface IRender {
	int accessLayoutReference();
	void initializeViews();
	void updateContent();
	View getView();
}
