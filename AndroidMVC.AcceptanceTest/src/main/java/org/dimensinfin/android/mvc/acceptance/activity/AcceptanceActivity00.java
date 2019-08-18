package org.dimensinfin.android.mvc.acceptance.activity;

import android.app.Activity;
import android.os.Bundle;

import org.dimensinfin.android.mvc.acceptance.R;
import org.dimensinfin.android.mvc.acceptance.TestIdentifiers;
import org.dimensinfin.android.mvc.acceptance.TitlePanel;
import org.dimensinfin.android.mvc.activity.MVCMultiPageActivity;
import org.dimensinfin.android.mvc.activity.MVCPagerFragment;
import org.dimensinfin.android.mvc.datasource.IDataSource;
import org.dimensinfin.android.mvc.datasource.MVCDataSource;
import org.dimensinfin.android.mvc.interfaces.IControllerFactory;

public class AcceptanceActivity00 extends MVCMultiPageActivity {
	private boolean onResumeReached = false;

	public boolean isOnResumeReached() {
		return onResumeReached;
	}

	@Override
	protected void onCreate( final Bundle savedInstanceState ) {
		super.onCreate(savedInstanceState);
		this.addPage(new MVC00Fragment().setVariant(TestIdentifiers.MVC00.name()));
	}

	@Override
	protected void onResume() {
		super.onResume();
		this.onResumeReached = true;
	}

	public static class MVC00Fragment extends MVCPagerFragment {
		@Override
		public IControllerFactory createFactory() {
			return new AcceptanceControllerFactory(this.getVariant());
		}

		@Override
		public IDataSource createDS() {
			return new MVC00DataSource.Builder()
					       .addIdentifier(this.getVariant())
					       .withVariant(this.getVariant())
					       .withExtras(this.getExtras())
					       .withFactory(this.getFactory())
					       .withActivity(this.getActivityContext())
					       .build();
		}

		public static class MVC00DataSource extends MVCDataSource {
			private Activity activity;

			@Override
			public void prepareModel() { }

			@Override
			public void collaborate2Model() {
				final String appName = this.activity.getResources().getString(R.string.appname);
//				this.addHeaderContents(new TitlePanel.Builder().withTitle(appName).build());
				this.addModelContents(new TitlePanel.Builder().withTitle(appName).build());
			}

			public static class Builder extends MVCDataSource.Builder<MVC00DataSource, Builder> {
				private MVC00DataSource onConstruction;

				@Override
				protected MVC00DataSource getActual() {
					if (null == this.onConstruction) this.onConstruction = new MVC00DataSource();
					return this.onConstruction;
				}

				@Override
				protected Builder getActualBuilder() {
					return this;
				}

				public MVC00DataSource.Builder withActivity( final Activity activity ) {
					this.onConstruction.activity = activity;
					return this;
				}
			}
		}
	}
}