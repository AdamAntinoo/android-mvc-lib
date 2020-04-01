package org.dimensinfin.android.mvc.datasource;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import java.util.Objects;

import org.apache.commons.lang3.ObjectUtils;

import org.dimensinfin.android.mvc.activity.IPagerFragment;
import org.dimensinfin.android.mvcannotations.logging.LoggerWrapper;

public class HeaderDataSourceAdapter extends DataSourceAdapter {
	private ViewGroup headerContainer;

	public HeaderDataSourceAdapter( @NonNull final IPagerFragment fragment, @NonNull final IDataSource datasource ) {
		super(fragment, datasource);
	}

	public HeaderDataSourceAdapter setHeaderContainer( final ViewGroup headerContainer ) {
		this.headerContainer = headerContainer;
		return this;
	}

	@Override
	public void collaborateData() {
		LoggerWrapper.enter();
		this.headerContainer.removeAllViews();
		LoggerWrapper.exit();
	}

	/**
	 * Will clean the current content for the header linear layout and then generate a new list of views from the current list of controllers.
	 * During the refill of the layout if we found any null pointer exception we skip that view but leave a trace of that fact.
	 * This method should be used whenever the model list has any change.
	 */
	@Override
	public void notifyDataSetChanged() {
		this.contentControllerList.clear();
		this.contentControllerList.addAll(dataSource.getHeaderSectionContents());
		this.headerContainer.removeAllViews();
		for (int i = 0; i < this.contentControllerList.size(); i++)
			try {
				this.headerContainer.addView( Objects.requireNonNull( this.getView( i, null, this.headerContainer ) ) );
			} catch ( final NullPointerException npe){
				LoggerWrapper.error(npe);
			}
		this.headerContainer.setVisibility(View.VISIBLE);
	}
}
