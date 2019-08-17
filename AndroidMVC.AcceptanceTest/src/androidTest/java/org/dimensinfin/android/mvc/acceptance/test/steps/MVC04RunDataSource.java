package org.dimensinfin.android.mvc.acceptance.test.steps;

import org.dimensinfin.android.mvc.acceptance.activity.AcceptanceControllerFactory;
import org.dimensinfin.android.mvc.acceptance.datasource.MVC04DataSource;
import org.dimensinfin.android.mvc.datasource.DataSourceManager;
import org.dimensinfin.android.mvc.datasource.IDataSource;
import org.junit.Assert;

import java.util.List;
import java.util.Map;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class MVC04RunDataSource {
	private static final String CONTROLLER_CLASS = "controllerClass";
	private static final String CONTROLLER_MODEL_TITLE = "controllerModelTitle";
	private MVC04DataSource dataSource;
	private IDataSource registeredDataSouce;

	@Given("a new data source")
	public void aNewDataSource() {
		final String variant = "-MVC04RUNDATASOURCE-";
		this.dataSource = new MVC04DataSource.Builder()
				                  .addIdentifier("-MVC04-")
				                  .withVariant(variant)
				                  .withFactory(new AcceptanceControllerFactory(variant))
				                  .build();
		Assert.assertNotNull(this.dataSource);
	}

	@When("registering the data source on the manager")
	public void registeringTheDataSourceOnTheManager() {
		this.registeredDataSouce = DataSourceManager.registerDataSource(this.dataSource);
	}

	@Then("check that the header containers have the next data")
	public void checkThatTheHeaderContainersHaveTheNextData( final List<Map<String, String>> cucumberTable ) {
		final String controllerClass = cucumberTable.get(0).get(CONTROLLER_CLASS);
		Assert.assertEquals("SeparatorController", controllerClass);
		final String controllerModelTitle = cucumberTable.get(0).get(CONTROLLER_MODEL_TITLE);
//		Assert.assertEquals("SeparatorController", controllerModelTitle);
	}
}
