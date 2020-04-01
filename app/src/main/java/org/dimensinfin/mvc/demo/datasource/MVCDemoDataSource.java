package org.dimensinfin.mvc.demo.datasource;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.dimensinfin.android.mvc.datasource.MVCDataSource;
import org.dimensinfin.android.mvc.domain.IControllerFactory;
import org.dimensinfin.mvc.demo.activity.PageDefinitions;
import org.dimensinfin.mvc.demo.domain.ApplicationHeaderTitle;
import org.dimensinfin.mvc.demo.domain.DemoLabel;
import org.dimensinfin.mvc.demo.domain.TitleLabel;
import org.dimensinfin.mvc.demo.services.LabelGenerator;

public class MVCDemoDataSource extends MVCDataSource {
	private String applicationName;
	private String applicationVersion;
	public LabelGenerator labelGenerator = new LabelGenerator.Builder().build();
	private List<String> labels = new ArrayList<>();

	// - I D A T A S O U R C E
	@Override
	public void prepareModel() {
		this.labels = this.labelGenerator.generateLabels( 20 );
	}

	@Override
	public void collaborate2Model() {
		// - H E A D E R
		this.addHeaderContents( new ApplicationHeaderTitle.Builder()
				                        .withApplicationName( this.applicationName )
				                        .withApplicationVersion( this.applicationVersion )
				                        .build() );
		this.addHeaderContents( new TitleLabel.Builder()
				                        .withTitle( "NON EXPANDABLE SECTION" )
				                        .build() );

		// - D A T A S E C T I O N
		if (this.getVariant() == PageDefinitions.MVCDEMOLIST_ITEMS.name()) {
			// Wait a delay of 6 seconds to allow to watch the counter.
			try {
				Thread.sleep( TimeUnit.SECONDS.toMillis( 2 ) );
			} catch (final InterruptedException ex) {
			}
			this.addModelContents( new DemoLabel.Builder().withTitle( "-PREDEFINED-VALUE-" ).build() );
			for (String label : this.labels)
				this.addModelContents( new DemoLabel.Builder().withTitle( label ).build() );
		}
	}

	// - B U I L D E R
	public static class Builder extends MVCDataSource.Builder<MVCDemoDataSource, MVCDemoDataSource.Builder> {
		private MVCDemoDataSource onConstruction;

		public Builder( final IControllerFactory factory ) {
			Objects.requireNonNull( factory );
			this.onConstruction = new MVCDemoDataSource();
			this.withFactory( factory );
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
			super.build();
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
