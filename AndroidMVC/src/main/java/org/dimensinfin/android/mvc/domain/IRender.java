package org.dimensinfin.android.mvc.domain;

import android.view.View;

public interface IRender {
	int accessLayoutReference();

	void initializeViews();

	void updateContent();

	View getView();
}
