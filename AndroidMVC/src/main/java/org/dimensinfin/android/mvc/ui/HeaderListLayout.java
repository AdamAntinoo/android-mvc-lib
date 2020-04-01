package org.dimensinfin.android.mvc.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;

import java.util.List;
import java.util.Objects;

import org.dimensinfin.android.mvc.controller.IAndroidController;
import org.dimensinfin.android.mvc.datasource.HeaderDataSourceAdapter;
import org.dimensinfin.android.mvcannotations.logging.LoggerWrapper;

public class HeaderListLayout extends LinearLayout {
	private HeaderDataSourceAdapter headerSectionAdapter;

	public HeaderListLayout( final Context context ) {
		super( context );
	}

	public HeaderListLayout( final Context context, @Nullable final AttributeSet attrs ) {
		super( context, attrs );
	}

	public HeaderListLayout( final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr ) {
		super( context, attrs, defStyleAttr );
	}

	public HeaderListLayout( final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes ) {
		super( context, attrs, defStyleAttr, defStyleRes );
	}

	public void setAdapter( final HeaderDataSourceAdapter headerSectionAdapter ) {
		this.headerSectionAdapter = Objects.requireNonNull( headerSectionAdapter );
		this.headerSectionAdapter.registerLayout(this);
	}

	/**
	 * When the adapter is notified that there are changes on the header contents it will call this method to clean and recreate the list of views
	 * to be rendered.
	 */
	public void notifyDataSetChanged(  ) {
		final List<IAndroidController> contentControllerList = this.headerSectionAdapter.getControllerList();
		this.removeAllViews();
		for (int i = 0; i < contentControllerList.size(); i++)
			try {
				this.addView( Objects.requireNonNull( this.headerSectionAdapter.getView( i, null, this ) ) );
			} catch (final NullPointerException npe) {
				LoggerWrapper.error( npe );
			}
		this.setVisibility( View.VISIBLE );
			this.invalidate();
	}
}
