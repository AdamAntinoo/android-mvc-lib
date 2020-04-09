package org.dimensinfin.mvc.demo.activity;

import android.view.View;

import org.dimensinfin.android.mvc.activity.MVCPagerFragment;
import org.dimensinfin.android.mvc.datasource.IDataSource;
import org.dimensinfin.android.mvc.domain.IControllerFactory;
import org.dimensinfin.android.mvc.render.RenderViewGenerator;
import org.dimensinfin.mvc.demo.datasource.PageSelectionDataSource;
import org.dimensinfin.mvc.demo.ui.TitledActionBar;

public class PageSelectionFragment extends MVCPagerFragment {

	@Override
	public IControllerFactory createFactory() {
		return new DemoControllerFactory(this.getVariant())
				       .registerActivity( PageDefinitions.MVCDEMOLIST_ITEMS.name(), MVCDemoSimpleActivity.class );
//				       .registerActivity( PageDefinitions.BYGROUP.name(), DemoMultiPageActivity.class );
	}

	@Override
	public IDataSource createDS() {
		return new PageSelectionDataSource.Builder()
				.addIdentifier( this.getVariant() )
				.withExtras( this.getExtras() )
				.withFactory( this.getFactory() )
				.withVariant( this.getVariant() )
				.build();
	}

	@Override
	public View generateActionBarView() {
		return new RenderViewGenerator.Builder<TitledActionBar>()
				       .withContext( this.getActivityContext() )
				       .withModel( new TitledActionBar.Builder()
						                   .withTitle( PageDefinitions.valueOf( this.getVariant() ).getPageTitle() )
						                   .build() )
				       .withFactory( this.getFactory() )
				       .generateView();
	}
}
