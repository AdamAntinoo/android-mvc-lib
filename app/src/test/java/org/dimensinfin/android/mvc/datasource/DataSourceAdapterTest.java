package org.dimensinfin.android.mvc.datasource;

import android.app.Application;
import android.widget.ListView;
import junit.framework.Assert;
import org.dimensinfin.android.mvc.R;
import org.dimensinfin.android.mvc.TestDataSource;
import org.dimensinfin.android.mvc.activity.AbstractPagerFragment;
import org.dimensinfin.android.mvc.factory.ControllerFactory;
import org.dimensinfin.android.mvc.interfaces.ICollaboration;
import org.dimensinfin.android.mvc.interfaces.IControllerFactory;
import org.dimensinfin.android.mvc.interfaces.IDataSource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Adam Antinoo
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 18)
public class DataSourceAdapterTest {
	final private class TestFragment extends AbstractPagerFragment {

		@Override
		public String getSubtitle() {
			return "Subtitle";
		}

		@Override
		public String getTitle() {
			return "Title";
		}

		@Override
		public IControllerFactory createFactory() {
			return new ControllerFactory("TEST");
		}

		@Override
		protected List<ICollaboration> registerHeaderSource() {
			return new ArrayList<>();
		}

		@Override
		protected IDataSource registerDataSource() {
			return new TestDataSource(new DataSourceLocator().addIdentifier("TEST"), this.createFactory());
		}
	}

	final private class TestApplication extends Application {

	}

	private TestFragment fragment;
//	private LinearLayoutManager mockLayoutManager;

//	@Inject
//	CandiesBroadcastReceiver mockBroadcastReceiver;
//	@Inject
//	CandiesListAdapter mockAdapter;

	@Before
	public void setUp() {
//		((TestApplication) RuntimeEnvironment.application).inject(this);

		//making a mock of the layout manager...
//		mockLayoutManager = Mockito.mock(LinearLayoutManager.class);
		fragment = new TestFragment();

		//setting it on our testable subclass...
//		fragment.setLayoutManager(mockLayoutManager);

		//Start the fragment!
//		SupportFragmentTestUtil.startFragment(fragment);
	}

	@Test
	public void defaultDisplay() {
		ListView recyclerView = (ListView) fragment.getView().findViewById(R.id.listContainer);
//		ListView.LayoutManager layoutManager = recyclerView.getLayoutManager();

		//assertThat(LayoutManager layoutManager) is provided to us by the assertj-android-recyclerview library.
//		assertThat(layoutManager).isEqualTo(mockLayoutManager);
		Assert.assertNotNull("The list view should be accesible.", recyclerView);
	}
}