package org.dimensinfin.android.mvc.demo.datasource;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.dimensinfin.android.mvc.datasource.MVCDataSource;
import org.dimensinfin.android.mvc.demo.R;
import org.dimensinfin.android.mvc.demo.activity.PageDefinitions;
import org.dimensinfin.android.mvc.demo.domain.ApplicationHeaderTitle;
import org.dimensinfin.android.mvc.domain.IControllerFactory;

public class MVCDemoDataSource extends MVCDataSource {
	private String applicationName;
	private String applicationVersion;

	// - I D A T A S O U R C E
	@Override
	public void prepareModel() {
	}

	@Override
	public void collaborate2Model() {
		// - H E A D E R
		this.addHeaderContents( new ApplicationHeaderTitle( this.applicationName,this.applicationVersion ) );
		this.addHeaderContents( new TitleLabel( "NON EXPANDABLE SECTION" ) );

		// - D A T A S E C T I O N
		if (this.getVariant() == PageDefinitions.MVCDEMOLIST_ITEMS.name()) {
			// Wait a delay of 6 seconds to allow to watch the counter.
			try {
				Thread.sleep( TimeUnit.SECONDS.toMillis( 2 ) );
			} catch (final InterruptedException ex) {
			}
			// Add manually each of the demo model nodes.
			addModelContents( new DemoLabelCounter()
//					.setIcon(R.drawable.criticalstate)
					                  .setTitle( "STOP" ) );
			addModelContents( new DemoItem()
					                  .setIcon( R.drawable.corpmap )
					                  .setTitle( "Maps" ) );
			addModelContents( new DemoItem()
					                  .setIcon( R.drawable.industry )
					                  .setTitle( "Industry" ) );
		}
	}

	// - B U I L D E R
	public static class Builder extends MVCDataSource.Builder<MVCDemoDataSource, MVCDemoDataSource.Builder> {
		private MVCDemoDataSource onConstruction;

		public Builder( final IControllerFactory factory ) {
			Objects.requireNonNull( factory );
			this.withFactory( factory );
			this.onConstruction = new MVCDemoDataSource();
		}

		@Override
		protected MVCDemoDataSource getActual() {
			if (null == this.onConstruction) this.onConstruction = new MVCDemoDataSource();
			return this.onConstruction;
		}

		@Override
		protected Builder getActualBuilder() {
			return this;
		}

		public MVCDemoDataSource build() {
			return this.onConstruction;
		}

		public MVCDemoDataSource.Builder withApplicationName( final String applicationName ) {
			Objects.requireNonNull( applicationName );
			this.onConstruction.applicationName = applicationName;
			return this;
		}
		public MVCDemoDataSource.Builder withApplicationVersion( final String applicationVersion ) {
			Objects.requireNonNull( applicationVersion );
			this.onConstruction.applicationVersion = applicationVersion;
			return this;
		}
	}
}
