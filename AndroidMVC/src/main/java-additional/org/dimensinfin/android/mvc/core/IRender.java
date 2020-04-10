package org.dimensinfin.android.mvc.core;

import android.view.View;

public interface IRender {
	int accessLayoutReference();

	void initializeViews();

	void updateContent();

	View getView();
}
