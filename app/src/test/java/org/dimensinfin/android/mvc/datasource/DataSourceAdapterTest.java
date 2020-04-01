package org.dimensinfin.android.mvc.datasource;

import android.app.Activity;
import android.view.View;

import org.dimensinfin.android.mvc.activity.MVCFragment;
import org.dimensinfin.android.mvc.activity.MVCPagerFragment;
import org.dimensinfin.android.mvc.controller.ControllerFactory;
import org.dimensinfin.android.mvc.controller.IAndroidController;
import org.dimensinfin.android.mvc.domain.Spacer;
import org.dimensinfin.android.mvc.support.SpacerController;
import org.dimensinfin.core.domain.EEvents;
import org.dimensinfin.core.domain.IntercommunicationEvent;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;

public class DataSourceAdapterTest {
	private final ControllerFactory factory = Mockito.mock(ControllerFactory.class);
	private final List<IAndroidController> controllers = new ArrayList<>();
	private Spacer item1;

	@Before
	public void setUp() {
		// Initialize the list of controllers
		this.item1 = new Spacer.Builder().withLabel("Test 1").build();
		this.controllers.add(new SpacerController(this.item1, this.factory));
		this.controllers.add(new SpacerController(new Spacer.Builder().withLabel("Test 2").build(), this.factory));
		this.controllers.add(new SpacerController(new Spacer.Builder().withLabel("Test 3").build(), this.factory));
	}

	@Test
	public void builder() {
		final MVCPagerFragment fragment = Mockito.mock(MVCPagerFragment.class);
		final MVCDataSource dataSource = Mockito.mock(MVCDataSource.class);
		final DataSourceAdapter adapter = new DataSourceAdapter(fragment, dataSource);
		Assert.assertNotNull(adapter);
	}

	@Test(expected = NullPointerException.class)
	public void builderIncomplete() {
		final DataSourceAdapter adapter = new DataSourceAdapter(null, null);
	}

	@Test
	public void collaborateData() {
		final MVCPagerFragment fragment = Mockito.mock(MVCPagerFragment.class);
		final MVCDataSource dataSource = Mockito.mock(MVCDataSource.class);
		final DataSourceAdapter adapter = new DataSourceAdapter(fragment, dataSource);
		Assert.assertNotNull(adapter);
		adapter.collaborateData();
		Mockito.verify(dataSource, times(1)).collaborate2Model();
	}

	@Test
	public void notifyDataSetChanged() {
		final MVCFragment fragment = Mockito.mock(MVCPagerFragment.class);
		final MVCDataSource dataSource = Mockito.mock(MVCDataSource.class);
		Mockito.when(dataSource.getDataSectionContents()).thenReturn(this.controllers);
		final DataSourceAdapter dsAdapter = new DataSourceAdapter(fragment, dataSource);
		dsAdapter.notifyDataSetChanged();
		final IAndroidController expected = this.controllers.get(1);
		final Object obtained = dsAdapter.getItem(1);
		Assert.assertEquals("The selected element should match.", expected, obtained);
		Assert.assertEquals("The contents should have 3 elements.", 3, dsAdapter.getCount());
		Assert.assertEquals("The selected item should match the identifier.", item1.hashCode(), dsAdapter.getItemId(0));
	}

	@Test
	public void getCount() {
		final MVCFragment fragment = Mockito.mock(MVCPagerFragment.class);
		final MVCDataSource dataSource = Mockito.mock(MVCDataSource.class);
		Mockito.when(dataSource.getDataSectionContents()).thenReturn(this.controllers);
		final DataSourceAdapter dsAdapter = new DataSourceAdapter(fragment, dataSource);
		dsAdapter.notifyDataSetChanged();

		Assert.assertEquals(3, dsAdapter.getCount());
	}

	@Test
	public void getItem() {
		final MVCFragment fragment = Mockito.mock(MVCPagerFragment.class);
		final MVCDataSource dataSource = Mockito.mock(MVCDataSource.class);
		Mockito.when(dataSource.getDataSectionContents()).thenReturn(this.controllers);
		final DataSourceAdapter dsAdapter = new DataSourceAdapter(fragment, dataSource);
		dsAdapter.notifyDataSetChanged();

		Assert.assertNotNull(dsAdapter.getItem(0));
		Assert.assertNotNull(dsAdapter.getItem(2));
	}

	@Test
	public void getItemId() {
		final MVCFragment fragment = Mockito.mock(MVCPagerFragment.class);
		final MVCDataSource dataSource = Mockito.mock(MVCDataSource.class);
		Mockito.when(dataSource.getDataSectionContents()).thenReturn(this.controllers);
		final DataSourceAdapter dsAdapter = new DataSourceAdapter(fragment, dataSource);
		dsAdapter.notifyDataSetChanged();

		final int expected = item1.hashCode();
		Assert.assertEquals(expected, dsAdapter.getItemId(0));
	}

	@Test
	public void adapterMethods() {
		final MVCFragment fragment = Mockito.mock(MVCPagerFragment.class);
		final MVCDataSource dataSource = Mockito.mock(MVCDataSource.class);
		Mockito.when(dataSource.getDataSectionContents()).thenReturn(this.controllers);
		final DataSourceAdapter dsAdapter = new DataSourceAdapter(fragment, dataSource);
		Assert.assertTrue(dsAdapter.areAllItemsEnabled());
		Assert.assertTrue(dsAdapter.isEnabled(0));
		Assert.assertEquals(0, dsAdapter.getItemViewType(0));
		Assert.assertEquals(1, dsAdapter.getViewTypeCount());
	}

	@Test
	public void receiveEvent() throws InterruptedException {
		final MVCFragment fragment = Mockito.mock(MVCPagerFragment.class);
		final MVCDataSource dataSource = Mockito.mock(MVCDataSource.class);
		Mockito.when(dataSource.getDataSectionContents()).thenReturn(this.controllers);
		final DataSourceAdapter dsAdapter = new DataSourceAdapter(fragment, dataSource);
		final DataSourceAdapter spyAdapter = Mockito.spy(dsAdapter);
		dsAdapter.receiveEvent(new IntercommunicationEvent(this, EEvents.EVENT_NEWDATA.name(),
		                                                   this, this));
		dsAdapter.receiveEvent(new IntercommunicationEvent(this, EEvents.EVENT_ACTIONEXPANDCOLLAPSE.name(),
		                                                   this, this));
		dsAdapter.receiveEvent(new IntercommunicationEvent(this, EEvents.EVENT_ADAPTER_REQUESTNOTIFYCHANGES.name(),
		                                                   this, this));
	}

//	@Test
	public void getView() {
		final MVCFragment fragment = Mockito.mock(MVCPagerFragment.class);
		final MVCDataSource dataSource = Mockito.mock(MVCDataSource.class);
		final Activity context = Mockito.mock(Activity.class);
		Mockito.when(dataSource.getDataSectionContents()).thenReturn(this.controllers);
		Mockito.when(fragment.getActivityContext()).thenReturn(context);
		final DataSourceAdapter dsAdapter = new DataSourceAdapter(fragment, dataSource);
		dsAdapter.notifyDataSetChanged();

		final View obtained = dsAdapter.getView(0, null, null);
		Assert.assertNotNull(obtained);
	}
}
