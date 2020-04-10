package org.dimensinfin.mvc.demo.acceptance.steps;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.mockito.Mockito;

import org.dimensinfin.android.mvc.factory.ControllerFactory;
import org.dimensinfin.android.mvc.controller.IAndroidController;
import org.dimensinfin.android.mvc.datasource.DataSourceManager;
import org.dimensinfin.android.mvc.datasource.IDataSource;
import org.dimensinfin.android.mvc.datasource.MVCDataSource;
import org.dimensinfin.android.mvc.domain.Spacer;
import org.dimensinfin.mvc.demo.acceptance.support.core.MVCWorld;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class MVC04RunDataSource {
	private static final String CONTROLLER_CLASS = "controllerClass";
	private static final String CONTROLLER_MODEL_TITLE = "controllerModelTitle";

	private MVCWorld world;
	//	private AcceptanceActivity04 activity;
//	private MVC04Fragment fragment;
	private MVC04DataSource dataSource;
	private IDataSource registeredDataSouce;

	public MVC04RunDataSource( final MVCWorld world ) {
		this.world = world;
	}

	@And("a new data source")
	public void aNewDataSource() {
		// TODO - This is already osolete because the new acceptance test definitions
//		this.activity = (AcceptanceActivity04) this.world.getActiveActivity();
//		Assert.assertNotNull(this.activity);
//		this.mActivityRule.launchActivity(null);
//		this.activity = this.mActivityRule.getActivity();
//		Assert.assertNotNull(this.activity);
//		this.fragment = (MVC04Fragment) this.activity.accessPageAdapter().getItem(0);
//		Assert.assertNotNull(this.fragment);
		final ControllerFactory factory = Mockito.mock( ControllerFactory.class );
		final String variant = "-MVC04RUNDATASOURCE-";
		this.dataSource = new MVC04DataSource.Builder()
				                  .addIdentifier( "-MVC04-" )
				                  .withVariant( variant )
				                  .withFactory( factory )
				                  .build();
		Assert.assertNotNull( this.dataSource );
	}

	@When("registering the data source on the manager")
	public void registeringTheDataSourceOnTheManager() {
		this.registeredDataSouce = DataSourceManager.registerDataSource( this.dataSource );
	}

	@Then("check that the header containers have the next data")
	public void checkThatTheHeaderContainersHaveTheNextData( final List<Map<String, String>> cucumberTable ) {
		final String controllerClass = cucumberTable.get( 0 ).get( CONTROLLER_CLASS );
		Assert.assertEquals( "SeparatorController", controllerClass );
		final String controllerModelTitle = cucumberTable.get( 0 ).get( CONTROLLER_MODEL_TITLE );
//		Assert.assertEquals("SeparatorController", controllerModelTitle);
	}

	@Then("check that the header has the next list of controllers")
	public void checkThatTheHeaderHasTheNextListOfControllers( final List<Map<String, String>> cucumberTable ) {
		this.dataSource.collaborate2Model();
		final List<IAndroidController> controllers = this.dataSource.getHeaderSectionContents();
		Assert.assertNotNull( controllers );
		Assert.assertEquals( 1, controllers.size() );
		Assert.assertEquals( controllers.get( 0 ).getModel().getClass().getSimpleName(),
				cucumberTable.get( 0 ).get( CONTROLLER_CLASS ) );
		Assert.assertEquals( ((Spacer) controllers.get( 0 ).getModel()).getLabel(),
				cucumberTable.get( 0 ).get( CONTROLLER_MODEL_TITLE ) );
	}
}

final class MVC04DataSource extends MVCDataSource {

	@Override
	public void prepareModel() throws Exception {

	}

	@Override
	public void collaborate2Model() {

	}

	public static class Builder extends MVCDataSource.Builder<MVC04DataSource, MVC04DataSource.Builder> {
		private MVC04DataSource onConstruction;

		@Override
		protected MVC04DataSource getActual() {
			if (null == this.onConstruction) this.onConstruction = new MVC04DataSource();
			return this.onConstruction;
		}

		@Override
		protected Builder getActualBuilder() {
			return this;
		}
	}
}