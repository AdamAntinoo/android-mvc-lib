package org.dimensinfin.android.mvc.activity;

import android.app.Application;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.test.ApplicationTestCase;
import org.dimensinfin.android.mvc.core.AbstractFragmentPagerAdapter;
import org.dimensinfin.android.mvc.interfaces.IDataSource;
import org.dimensinfin.android.mvc.interfaces.IPartFactory;
import org.dimensinfin.core.interfaces.ICollaboration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class AbstractPagerActivityTest extends ApplicationTestCase<Application> {
	private class PagerActivityTest extends AbstractPagerActivity {
	}
	private class TestingPagerFragment extends AbstractPagerFragment {
		@Override
		public IPartFactory createFactory() {
			return null;
		}

		@Override
		public String getSubtitle() {
			return null;
		}

		@Override
		public String getTitle() {
			return null;
		}

		@Override
		protected List<ICollaboration> registerHeaderSource() {
			return null;
		}

		@Override
		protected IDataSource registerDataSource() {
			return null;
		}
//		@Override
//		public void addPage( @NonNull AbstractPagerFragment newFrag ) {
//		}
//	public FragmentManager	getFragmentManager(){
//
//		}
	}

	public AbstractPagerActivityTest() {
		super(Application.class);
	}

	PagerActivityTest mainActivity;
	TestingPagerFragment mainActivityFragment;

	@Before
	public void setUp() {
		mainActivity = Robolectric.setupActivity(PagerActivityTest.class);
		mainActivityFragment = new TestingPagerFragment();
		startFragment(mainActivityFragment);
	}

	private void startFragment(Fragment fragment) {
		FragmentManager fragmentManager = mainActivity.getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.add(fragment, null);
		fragmentTransaction.commit();
	}
//}
////@RunWith(PowerMockRunner.class)
////@PrepareForTest({ AbstractPagerActivity.class, LoggerFactory.class})
//public class AbstractPagerActivityTest {


	@Test(expected = RuntimeException.class)
	public void addPage_null() {
		// Given
		final FragmentManager fragmentManager = mock(FragmentManager.class);
		final TestingPagerFragment fragment = mock(TestingPagerFragment.class);

		// When
		when(fragmentManager.findFragmentByTag(any(String.class))).thenReturn(fragment);

		// Tests
		final PagerActivityTest pagerActivityTest = new PagerActivityTest();
		pagerActivityTest.addPage(null);

	}

	@Test
	public void addPage_page() {
		// Given
		final FragmentManager fragmentManager = mock(FragmentManager.class);
		final TestingPagerFragment fragment = mock(TestingPagerFragment.class);
		final AbstractPagerFragment abstractPagerFragment = mock(AbstractPagerFragment.class);
		final AbstractFragmentPagerAdapter abstractFragmentPagerAdapter = mock(AbstractFragmentPagerAdapter.class);

		// When
		when(fragment.getFragmentManager()).thenReturn(fragmentManager);
		when(fragmentManager.findFragmentByTag(any(String.class))).thenReturn(fragment);

		// Tests
		final PagerActivityTest pagerActivityTest = new PagerActivityTest();
		pagerActivityTest.addPage(abstractPagerFragment);

	}
}
