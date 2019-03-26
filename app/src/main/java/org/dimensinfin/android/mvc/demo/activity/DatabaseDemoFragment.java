package org.dimensinfin.android.mvc.demo.activity;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Hashtable;
import java.util.Hashtable;
import java.util.List;

import androidx.room.Room;
import org.dimensinfin.android.mvc.activity.AbstractPagerFragment;
import org.dimensinfin.android.mvc.datasource.AMVCDataSource;
import org.dimensinfin.android.mvc.datasource.DataSourceLocator;
import org.dimensinfin.android.mvc.demo.R;
import org.dimensinfin.android.mvc.demo.database.DemoData;
import org.dimensinfin.android.mvc.demo.factory.DemoControllerFactory;
import org.dimensinfin.android.mvc.interfaces.ICollaboration;
import org.dimensinfin.android.mvc.interfaces.IControllerFactory;
import org.dimensinfin.android.mvc.interfaces.IDataSource;
import org.joda.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Adam Antinoo
 */
public class DatabaseDemoFragment extends AbstractPagerFragment {
	@Override
	public String getSubtitle() {
		return getAppContext().getResources().getString(R.string.activity_subtitle_DemoFragment_Database);
	}

	@Override
	public String getTitle() {
		return getAppContext().getResources().getString(R.string.activity_title_DemoFragment_Database);
	}

	@Override
	public IControllerFactory createFactory() {
		return new DemoControllerFactory(this.getVariant());
	}

	@Override
	protected List<ICollaboration> registerHeaderSource() {
		return new ArrayList<>();
	}

	@Override
	protected IDataSource registerDataSource() {
		AbstractPagerFragment.logger.info(">> [DatabaseDemoFragment.registerDataSource]");
		IDataSource ds = null;
		try {
			final DataSourceLocator identifier = new DataSourceLocator()
					.addIdentifier(this.getVariant())
					.addIdentifier("DATABASE");
			ds = new DatabaseRoomDataSource(identifier, this.getFactory())
					.setVariant(getVariant())
					.setExtras(getExtras())
					.shouldBeCached(true);
			return ds;
		} finally {
			AbstractPagerFragment.logger.info("<< [DatabaseDemoFragment.registerDataSource]");
		}
	}
	public static class DatabaseRoomDataSource extends AMVCDataSource{
		public DatabaseRoomDataSource(final DataSourceLocator locator, final IControllerFactory controllerFactory) {
			super(locator, controllerFactory);
		}

		@Override
		public void collaborate2Model() {
	final		DemoData db = Room.databaseBuilder(this.get(),
					DemoData.class, "database-name").build();
		}
	}
}
