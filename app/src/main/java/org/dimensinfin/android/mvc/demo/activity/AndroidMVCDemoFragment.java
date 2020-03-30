package org.dimensinfin.android.mvc.demo.activity;

import org.dimensinfin.android.mvc.activity.MVCPagerFragment;
import org.dimensinfin.android.mvc.datasource.IDataSource;
import org.dimensinfin.android.mvc.demo.R;
import org.dimensinfin.android.mvc.demo.datasource.MVCDemoDataSource;
import org.dimensinfin.android.mvc.domain.IControllerFactory;

public class AndroidMVCDemoFragment extends MVCPagerFragment {
	public AndroidMVCDemoFragment() {
		this.setTag( this.getVariant() );
	}

	public void setTag( final String tag ) {
//		this.mTag = tag;
	}

	// - M V C F R A G M E N T
	@Override
	public IControllerFactory createFactory() {
		return new DemoControllerFactory( this.getVariant() );
	}

	@Override
	public IDataSource createDS() {
		return new MVCDemoDataSource.Builder( this.getFactory() )
				       .addIdentifier( this.getTag() )
				       .addIdentifier( "DEMO" )
				       .withExtras( this.getExtras() )
				       .withVariant( this.getVariant() )
				       .withApplicationName( this.getContext().getResources().getString( R.string.appname ) )
				       .withApplicationVersion( this.getContext().getResources().getString( R.string.appversion ) )
				       .build();
	}

//	/**
//	 * This should generate the list of model instances that should be rendered on the Header. We only use it for the Non
//	 * Expandable page section.
//	 * @return list of model data to be rendered on the Header of the page.
//	 */
//	@Override
//	protected List<ICollaboration> registerHeaderSource() {
//		List<ICollaboration> headerContents = new ArrayList<>();
//		if (getVariant() == AndroidMVCDemoActivity.EDemoVariants.NON_EXPANDABLE_SECTION.name()) {
//		}
//		return headerContents;
//	}

//	/**
//	 * Because on this demo Fragment we use a single fragment class for all the code we should differentiate using the
//	 * variant to construct the right <code>IDataSource</code> for each page. We have a page with <b>DemoItems</b> but one
//	 * page can expand items where the other not. We are going to use the <b>variant</b> at all the levels to reduce the
//	 * code. On this version we have removed the need for the ModelGenerators.
//	 * @return a DataSource instance able to generate the required model for each page variant.
//	 */
//	@Override
//	protected IPartsDataSource registerDataSource() {
//		AbstractPagerFragment.logger.info(">> [AndroidMVCDemoFragment.registerDataSource]");
//		DemoDataSource ds = null;
//		try {
//			final DataSourceLocator identifier = new DataSourceLocator()
//					.addIdentifier(this.getVariant())
//					.addIdentifier("DEMO");
//			ds = new DemoDataSource(identifier, getVariant(), this.getFactory(), getExtras());
//			return ds;
//		} finally {
//			AbstractPagerFragment.logger.info("<< [AndroidMVCDemoFragment.registerDataSource]");
//		}
//	}
}

//final class DemoDataSource extends MVCDataSourcev3 {
//	public DemoDataSource( final DataSourceLocator locator, final String variant, final IPartFactory factory, final Bundle extras ) {
//		super(locator, variant, factory, extras);
//	}
//
//	// - M E T H O D - S E C T I O N
//
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
