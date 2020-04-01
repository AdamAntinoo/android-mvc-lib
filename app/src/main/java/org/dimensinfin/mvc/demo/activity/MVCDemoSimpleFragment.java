package org.dimensinfin.mvc.demo.activity;

import org.dimensinfin.android.mvc.activity.MVCPagerFragment;
import org.dimensinfin.android.mvc.datasource.IDataSource;
import org.dimensinfin.mvc.demo.R;
import org.dimensinfin.mvc.demo.datasource.MVCDemoDataSource;
import org.dimensinfin.android.mvc.domain.IControllerFactory;

public class MVCDemoSimpleFragment extends MVCPagerFragment {
	// - M V C F R A G M E N T
	@Override
	public IControllerFactory createFactory() {
		return new DemoControllerFactory( this.getVariant() );
	}

	@Override
	public IDataSource createDS() {
		return new MVCDemoDataSource.Builder( this.getFactory() )
				       .addIdentifier( this.getVariant() )
				       .addIdentifier( "DEMO" )
				       .withExtras( this.getExtras() )
				       .withVariant( this.getVariant() )
				       .withApplicationName( this.getContext().getResources().getString( R.string.appname ) )
				       .withApplicationVersion( this.getContext().getResources().getString( R.string.appversion ) )
				       .build();
	}
}

//	/**
//	 * This is the core methods all DataSources should implement to generate the model to be used. This demo will generate
//	 * a <b>DemoLabel</b> item followed by a set of icon items. For the expandable page the dataSource will generate a
//	 * DemoLabel and a container with some more items.
//	 * @return
//	 */
//	public void collaborate2Model() {
//		MVCDataSourcev3.logger.info(">> [DemoDataSource.collaborate2Model]");
//		this.cleanup(); // Clear the model before creating it again to remove duplicates.
//		// Check if we should use the cached version.
////		if (!isCached()) {
//		if (getVariant() == AndroidMVCDemoActivity.EDemoVariants.NON_EXPANDABLE_SECTION.name()) {
//			// Initialize the Adapter data structures.
////				this.setDataModel(new RootNode());
//
//			// Wait a delay of 6 seconds to allow to watch the counter.
//			try {
//				Thread.sleep(TimeUnit.SECONDS.toMillis(2));
//			} catch (InterruptedException ex) {
//			}
//			// Add manually each of the demo model nodes.
//			addModelContents(new DemoLabel("NON EXPANDABLE SECTION"));
//			addModelContents(new DemoLabelCounter()
////					.setIcon(R.drawable.criticalstate)
//					.setTitle("STOP"));
//			addModelContents(new DemoItem()
//					.setIcon(R.drawable.corpmap)
//					.setTitle("Maps"));
//			addModelContents(new DemoItem()
//					.setIcon(R.drawable.industry)
//					.setTitle("Industrial"));
//		}
//		if (getVariant() == AndroidMVCDemoActivity.EDemoVariants.EXPANDABLE_SECTION.name()) {
//			// Initialize the Adapter data structures.
////				this.setDataModel(new RootNode());
//
//			// Wait a delay of 6 seconds to allow to watch the counter.
//			try {
//				Thread.sleep(TimeUnit.SECONDS.toMillis(4));
//			} catch (InterruptedException ex) {
//			}
//			addModelContents(new DemoLabel("EXPANDABLE SECTION"));
//			final DemoContainer container = new DemoContainer()
//					.setName("STATES");
//			container.addContent(new DemoItem()
//					.setIcon(R.drawable.info)
//					.setTitle("Critical"));
//			container.addContent(new DemoItem()
//					.setIcon(R.drawable.info)
//					.setTitle("Danger"));
//			container.addContent(new DemoItem()
//					.setIcon(R.drawable.info)
//					.setTitle("Warning"));
//			container.addContent(new DemoItem()
//					.setIcon(R.drawable.info)
//					.setTitle("Normal"));
//			container.addContent(new DemoItem()
//					.setIcon(R.drawable.info)
//					.setTitle("Nominal"));
//			container.addContent(new DemoItem()
//					.setIcon(R.drawable.info)
//					.setTitle("Stop"));
//			addModelContents(container);
//		}
////		}
//		logger.info("<< [DemoDataSource.collaborate2Model]");
//	}
//
////	private void threadWait(final long milliseconds) {
////		try {
////			Thread.sleep(milliseconds);
////		} catch (InterruptedException ex) {
////		}
////	}
//}
