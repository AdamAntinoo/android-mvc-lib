package org.dimensinfin.android.mvc.activity;

import android.os.Bundle;

import org.dimensinfin.android.mvc.controller.ControllerFactory;
import org.dimensinfin.android.mvc.datasource.IDataSource;
import org.dimensinfin.android.mvc.datasource.MVCDataSource;
import org.dimensinfin.android.mvc.domain.Spacer;
import org.dimensinfin.android.mvc.interfaces.IControllerFactory;

public class InstrumentationActivity extends MVCMultiPageActivity {
	@Override
	protected void onCreate( final Bundle savedInstanceState ) {
		super.onCreate(savedInstanceState);
		this.addPage(new InstrumentationFragment());
	}

	public static class InstrumentationFragment extends MVCPagerFragment {

		@Override
		public IControllerFactory createFactory() {
			return new ControllerFactory("-INSTRUMENTATION-TEST-");
		}

		@Override
		public IDataSource createDS() {
			return new InstrumentationDataSource.Builder()
					       .addIdentifier("-INSTRUMENTATION-TEST-")
					       .withVariant(this.getVariant())
					       .withFactory(this.getFactory())
					       .build();
		}

		public static class InstrumentationDataSource extends MVCDataSource {

			@Override
			public void prepareModel() { }

			@Override
			public void collaborate2Model() {
				this.addModelContents(new Spacer.Builder().withLabel("Test Label 1").build());
			}

			public static class Builder extends MVCDataSource.Builder<InstrumentationDataSource, InstrumentationDataSource.Builder> {

				@Override
				protected InstrumentationDataSource getActual() {
					return null;
				}

				@Override
				protected Builder getActualBuilder() {
					return null;
				}
			}
		}
	}
}
