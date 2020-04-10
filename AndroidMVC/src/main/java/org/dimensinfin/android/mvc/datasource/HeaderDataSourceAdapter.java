package org.dimensinfin.android.mvc.datasource;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Objects;

import org.dimensinfin.android.mvc.activity.IPagerFragment;
import org.dimensinfin.android.mvc.core.IAndroidController;
import org.dimensinfin.android.mvc.core.IDataSource;
import org.dimensinfin.core.domain.Node;
import org.dimensinfin.android.mvc.ui.HeaderListLayout;
import org.dimensinfin.logging.LogWrapper;

public class HeaderDataSourceAdapter extends DataSourceAdapter {
	private HeaderListLayout headerContainer;

	public HeaderDataSourceAdapter( @NonNull final IPagerFragment fragment, @NonNull final IDataSource datasource ) {
		super( fragment, datasource );
	}

	public void clean() {
		this.dataSource.cleanHeaderModel();
	}

	/**
	 * This method should fix the bug when requesting the controller list. That list is virtual and created when required from the model data and
	 * it is not stored on the adapter differently than the other adapters. The contents come from the data source ever.
	 */
	@Override
	public List<IAndroidController> accessContents() {
		return this.contentControllerList;
	}

	/**
	 * Will clean the current content for the header linear layout and then generate a new list of views from the current list of controllers.
	 * During the refill of the layout if we found any null pointer exception we skip that view but leave a trace of that fact.
	 * This method should be used whenever the model list has any change.
	 */
	@Override
	public void notifyDataSetChanged() {
		LogWrapper.enter();
		super.notifyDataSetChanged(); // Teh parent code is wrong because it will clear the controller list.
		this.contentControllerList.clear();
		this.contentControllerList.addAll( this.dataSource.getHeaderSectionContents() );
		if (null != this.headerContainer) this.headerContainer.notifyDataSetChanged();
		LogWrapper.exit();
	}

	/**
	 * When registering the graphical layout we can then start sowing the spinner.
	 *
	 * @param headerContainer the graphical element where to render the adapter contents.
	 */
	public void registerLayout( final HeaderListLayout headerContainer ) {
		this.headerContainer = Objects.requireNonNull( headerContainer );
		this.activateCounterSpinner(); // Insert the spinner on the header and start counting the time.
	}

	private void activateCounterSpinner() {
		this.clean();
		this.dataSource.addHeaderContents( new Node.Builder().build() );
		this.notifyDataSetChanged();
	}
}
