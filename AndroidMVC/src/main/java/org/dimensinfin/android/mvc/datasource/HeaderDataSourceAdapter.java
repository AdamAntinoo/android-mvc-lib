package org.dimensinfin.android.mvc.datasource;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Objects;

import org.dimensinfin.android.mvc.activity.IPagerFragment;
import org.dimensinfin.android.mvc.controller.IAndroidController;
import org.dimensinfin.android.mvc.domain.MVCNode;
import org.dimensinfin.android.mvc.ui.HeaderListLayout;

public class HeaderDataSourceAdapter extends DataSourceAdapter {
	private HeaderListLayout headerContainer;

	public HeaderDataSourceAdapter( @NonNull final IPagerFragment fragment, @NonNull final IDataSource datasource ) {
		super( fragment, datasource );
	}

//	public HeaderDataSourceAdapter setHeaderContainer( final ViewGroup headerContainer ) {
//		this.headerContainer = Objects.requireNonNull( headerContainer );
//		this.activateCounterSpinner(); // Insert the spinner on the header and start counting the time.
//		return this;
//	}

//	@Deprecated
//	@Override
//	public void requestDataModel() {
//		LoggerWrapper.enter();
//		this.headerContainer.removeAllViews();
//		LoggerWrapper.exit();
//	}

	/**
	 * Will clean the current content for the header linear layout and then generate a new list of views from the current list of controllers.
	 * During the refill of the layout if we found any null pointer exception we skip that view but leave a trace of that fact.
	 * This method should be used whenever the model list has any change.
	 */
	@Override
	public void notifyDataSetChanged() {
		this.contentControllerList.clear();
		this.contentControllerList.addAll( this.dataSource.getHeaderSectionContents() );
		if (null != this.headerContainer) this.headerContainer.notifyDataSetChanged();
		super.notifyDataSetChanged();
	}

	public List<IAndroidController> getControllerList() {
		return this.contentControllerList;
	}

	public void clean() {
		this.dataSource.cleanHeaderModel();
	}

	/**
	 * When registering the graphical layout we can then start sowing the spinner.
	 * @param headerContainer the graphical element where to render the adapter contents.
	 */
	public void registerLayout( final HeaderListLayout headerContainer ) {
		this.headerContainer = Objects.requireNonNull( headerContainer );
		this.activateCounterSpinner(); // Insert the spinner on the header and start counting the time.
	}

	private void activateCounterSpinner() {
		this.clean();
		this.dataSource.addHeaderContents( new MVCNode.Builder().build() );
		this.notifyDataSetChanged();
	}
}
