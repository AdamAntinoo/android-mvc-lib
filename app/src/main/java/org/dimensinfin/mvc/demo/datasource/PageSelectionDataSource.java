package org.dimensinfin.mvc.demo.datasource;

import org.dimensinfin.android.mvc.datasource.MVCDataSource;
import org.dimensinfin.mvc.demo.activity.PageDefinitions;
import org.dimensinfin.mvc.demo.domain.PageButton;

public class PageSelectionDataSource extends MVCDataSource {

	private PageSelectionDataSource() {}

	@Override
	public void prepareModel() throws Exception { }

	@Override
	public void collaborate2Model() {
		this.addHeaderContents( new PageButton.Builder()
				                        .withLabel( "One Page Simple Label List" )
				                        .withPageName( PageDefinitions.MVCDEMOLIST_ITEMS.name() )
				                        .build() );
		this.addHeaderContents( new PageButton.Builder()
				                        .withLabel( "Multi Page Different Aggregations" )
				                        .withPageName( PageDefinitions.BYGROUP.name() )
				                        .build() );
	}

	// - B U I L D E R
	public static class Builder extends MVCDataSource.Builder<PageSelectionDataSource, PageSelectionDataSource.Builder> {
		private PageSelectionDataSource onConstruction;

		public Builder() {
			this.onConstruction = new PageSelectionDataSource();
		}

		@Override
		protected PageSelectionDataSource getActual() {
			if (null == this.onConstruction) this.onConstruction = new PageSelectionDataSource();
			return this.onConstruction;
		}

		@Override
		protected Builder getActualBuilder() {
			return this;
		}

		public PageSelectionDataSource build() {
			super.build();
			return this.onConstruction;
		}
	}
}
