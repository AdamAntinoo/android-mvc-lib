package org.dimensinfin.mvc.demo.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;

import org.dimensinfin.android.mvc.activity.MVCMultiPageActivity;

public class PageSelectionDashboardActivity extends MVCMultiPageActivity {
	@Override
	protected void onCreate( @Nullable final Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		this.addPage( new PageSelectionFragment().setVariant( PageDefinitions.PAGE_SELECTION.name() ) );
	}
}
