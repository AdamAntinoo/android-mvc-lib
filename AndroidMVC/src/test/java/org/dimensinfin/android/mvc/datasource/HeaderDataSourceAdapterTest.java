package org.dimensinfin.android.mvc.datasource;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import org.dimensinfin.android.mvc.activity.MVCFragment;
import org.dimensinfin.android.mvc.activity.MVCPagerFragment;
import org.dimensinfin.android.mvc.controller.ControllerFactory;
import org.dimensinfin.android.mvc.controller.IAndroidController;
import org.dimensinfin.android.mvc.domain.Spacer;
import org.dimensinfin.android.mvc.render.MVCRender;
import org.dimensinfin.android.mvc.support.SpacerController;
import org.dimensinfin.core.domain.EEvents;
import org.dimensinfin.core.domain.IntercommunicationEvent;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;

public class HeaderDataSourceAdapterTest {
	private final ControllerFactory factory = Mockito.mock(ControllerFactory.class);
	private final List<IAndroidController> controllers = new ArrayList<>();
	private Spacer item1;

	@BeforeEach
	void beforeEach() {
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
		final ViewGroup container = Mockito.mock(ViewGroup.class);
		final DataSourceAdapter adapter = new HeaderDataSourceAdapter(fragment, dataSource);
		Assertions.assertNotNull(adapter);
	}

	public void builderIncomplete() {
		Assertions.assertThrows( NullPointerException.class, () -> {
			final DataSourceAdapter adapter = new DataSourceAdapter(null, null);
		} );
	}

	@Test
	public void collaborateData() {
		final MVCPagerFragment fragment = Mockito.mock(MVCPagerFragment.class);
		final MVCDataSource dataSource = Mockito.mock(MVCDataSource.class);
		final HeaderDataSourceAdapter adapter = new HeaderDataSourceAdapter(fragment, dataSource);
		Assertions.assertNotNull(adapter);
		adapter.requestDataModel();
		Mockito.verify(dataSource, times(1)).collaborate2Model();
	}

//	@Test
	public void notifyDataSetChanged() {
		final MVCFragment fragment = Mockito.mock(MVCPagerFragment.class);
		final ViewGroup container = Mockito.mock(ViewGroup.class);
		final MVCDataSource dataSource = Mockito.mock(MVCDataSource.class);
		final Activity context = Mockito.mock(Activity.class);
		final MVCRender render = Mockito.mock(MVCRender.class);
		final View view = Mockito.mock(View.class);
		final DataSourceAdapter adapter = Mockito.mock(DataSourceAdapter.class);
		Mockito.when(fragment.getActivityContext()).thenReturn(context);
		Mockito.when(dataSource.getHeaderSectionContents()).thenReturn(this.controllers);
		Mockito.when(render.getView()).thenReturn(view);
		Mockito.when(adapter.getView(Mockito.anyInt(), Mockito.any(View.class), Mockito.any(ViewGroup.class))).thenReturn(view);
		final HeaderDataSourceAdapter dsAdapter = new HeaderDataSourceAdapter(fragment, dataSource);

		dsAdapter.notifyDataSetChanged();
		final IAndroidController expected = this.controllers.get(1);
		final Object obtained = dsAdapter.getItem(1);
		Assertions.assertEquals( expected, obtained,"The selected element should match.");
		Assertions.assertEquals( 3, dsAdapter.getCount(),"The contents should have 3 elements.");
		Assertions.assertEquals( item1.hashCode(), dsAdapter.getItemId(0),"The selected item should match the identifier.");
	}

	@Test
	public void getCount() {
		final MVCFragment fragment = Mockito.mock(MVCPagerFragment.class);
		final MVCDataSource dataSource = Mockito.mock(MVCDataSource.class);
		Mockito.when(dataSource.getHeaderSectionContents()).thenReturn(this.controllers);
		final HeaderDataSourceAdapter dsAdapter = new HeaderDataSourceAdapter(fragment, dataSource);
		dsAdapter.notifyDataSetChanged();

		Assertions.assertEquals(3, dsAdapter.getCount());
	}

	@Test
	public void getItem() {
		final MVCFragment fragment = Mockito.mock(MVCPagerFragment.class);
		final MVCDataSource dataSource = Mockito.mock(MVCDataSource.class);
		Mockito.when(dataSource.getHeaderSectionContents()).thenReturn(this.controllers);
		final HeaderDataSourceAdapter dsAdapter = new HeaderDataSourceAdapter(fragment, dataSource);
		dsAdapter.notifyDataSetChanged();

		Assertions.assertNotNull(dsAdapter.getItem(0));
		Assertions.assertNotNull(dsAdapter.getItem(2));
	}

	@Test
	public void getItemId() {
		final MVCFragment fragment = Mockito.mock(MVCPagerFragment.class);
		final MVCDataSource dataSource = Mockito.mock(MVCDataSource.class);
		Mockito.when(dataSource.getHeaderSectionContents()).thenReturn(this.controllers);
		final HeaderDataSourceAdapter dsAdapter = new HeaderDataSourceAdapter(fragment, dataSource);
		dsAdapter.notifyDataSetChanged();

		final int expected = item1.hashCode();
		Assertions.assertEquals(expected, dsAdapter.getItemId(0));
	}

	@Test
	public void adapterMethods() {
		final MVCFragment fragment = Mockito.mock(MVCPagerFragment.class);
		final MVCDataSource dataSource = Mockito.mock(MVCDataSource.class);
		Mockito.when(dataSource.getHeaderSectionContents()).thenReturn(this.controllers);
		final HeaderDataSourceAdapter dsAdapter = new HeaderDataSourceAdapter(fragment, dataSource);
		Assertions.assertTrue(dsAdapter.areAllItemsEnabled());
		Assertions.assertTrue(dsAdapter.isEnabled(0));
		Assertions.assertEquals(0, dsAdapter.getItemViewType(0));
		Assertions.assertEquals(1, dsAdapter.getViewTypeCount());
	}

	@Test
	public void receiveEvent() throws InterruptedException {
		final MVCFragment fragment = Mockito.mock(MVCPagerFragment.class);
		final MVCDataSource dataSource = Mockito.mock(MVCDataSource.class);
		Mockito.when(dataSource.getHeaderSectionContents()).thenReturn(this.controllers);
		final HeaderDataSourceAdapter dsAdapter = new HeaderDataSourceAdapter(fragment, dataSource);
		final HeaderDataSourceAdapter spyAdapter = Mockito.spy(dsAdapter);
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
		Mockito.when(dataSource.getHeaderSectionContents()).thenReturn(this.controllers);
		Mockito.when(fragment.getActivityContext()).thenReturn(context);
		final HeaderDataSourceAdapter dsAdapter = new HeaderDataSourceAdapter(fragment, dataSource);
		dsAdapter.notifyDataSetChanged();

		final View obtained = dsAdapter.getView(0, null, null);
		Assertions.assertNotNull(obtained);
	}
}
