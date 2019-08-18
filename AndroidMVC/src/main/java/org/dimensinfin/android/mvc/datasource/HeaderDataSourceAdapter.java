package org.dimensinfin.android.mvc.datasource;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import org.dimensinfin.android.mvc.activity.IPagerFragment;

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
		logger.info(">> [HeaderDataSourceAdapter.collaborateData]");
		this.headerContainer.removeAllViews();
		logger.info("<< [HeaderDataSourceAdapter.collaborateData]");
	}

	@Override
	public void notifyDataSetChanged() {
		this.contentControllerList.clear();
		this.contentControllerList.addAll(dataSource.getHeaderSectionContents());
		this.headerContainer.removeAllViews();
		for (int i = 0; i < this.contentControllerList.size(); i++) {
			final View view = this.getView(i, null, this.headerContainer);
			this.headerContainer.addView(view);
		}
		this.headerContainer.setVisibility(View.VISIBLE);
	}

//	/**
//	 * This method extracts the view from the parameter controller and generates the final View element that it is able to
//	 * be inserted on the ui ViewGroup container.
//	 *
//	 * @param target the AndroidController to render to a View.
//	 */
//	private void addView2Header( final IAndroidController target ) {
//		logger.info(">> [MVCPagerFragment.addView2Header]");
//		try {
//			final IRender holder = target.buildRender(this.getActivityContext());
//			final View hv = holder.getView();
//			holder.updateContent();
//			_headerContainer.addView(hv);
//			// Add the connection to the click listener
//			if (target instanceof View.OnClickListener) {
//				hv.setClickable(true);
//				hv.setOnClickListener((View.OnClickListener) target);
//			}
//			_headerContainer.setVisibility(View.VISIBLE);
//		} catch (final RuntimeException rtex) {
//			logger.info("RTEX [MVCPagerFragment.addView2Header]> Problem generating view for: {}",
//			            target.getClass().getCanonicalName());
//			logger.info("RTEX [MVCPagerFragment.addView2Header]> RuntimeException. {}", rtex.getMessage());
//			rtex.printStackTrace();
//			Toast.makeText(this.getActivityContext()
//					, "RTEX [MVCPagerFragment.addView2Header]> RuntimeException. " + rtex.getMessage()
//					, Toast.LENGTH_LONG).show();
//		}
//		logger.info("<< [MVCPagerFragment.addView2Header]");
//	}
}
