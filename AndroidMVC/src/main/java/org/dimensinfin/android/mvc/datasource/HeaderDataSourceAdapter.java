package org.dimensinfin.android.mvc.datasource;

import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;

import java.util.Objects;

import org.dimensinfin.android.mvc.activity.IPagerFragment;
import org.dimensinfin.android.mvc.core.domain.MVCNode;
import org.dimensinfin.android.mvcannotations.logging.LoggerWrapper;

public class HeaderDataSourceAdapter extends DataSourceAdapter {
	private ViewGroup headerContainer;

	public HeaderDataSourceAdapter( @NonNull final IPagerFragment fragment, @NonNull final IDataSource datasource ) {
		super( fragment, datasource );
	}

	public HeaderDataSourceAdapter setHeaderContainer( final ViewGroup headerContainer ) {
		this.headerContainer = Objects.requireNonNull( headerContainer );
		this.activateCounterSpinner(); // Insert the spinner on the header and start counting the time.
		return this;
	}

	@Deprecated
	@Override
	public void requestDataModel() {
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
		this.contentControllerList.addAll( this.dataSource.getHeaderSectionContents() );
		this.headerContainer.removeAllViews();
		for (int i = 0; i < this.contentControllerList.size(); i++)
			try {
				this.headerContainer.addView( Objects.requireNonNull( this.getView( i, null, this.headerContainer ) ) );
			} catch (final NullPointerException npe) {
				LoggerWrapper.error( npe );
			}
		this.headerContainer.setVisibility( View.VISIBLE );
	}

	public void clean() {
		this.dataSource.cleanHeaderModel();
	}

	private void activateCounterSpinner() {
		this.clean();
		this.dataSource.addHeaderContents( new MVCNode.Builder().build() );
		this.notifyDataSetChanged();
	}
}
