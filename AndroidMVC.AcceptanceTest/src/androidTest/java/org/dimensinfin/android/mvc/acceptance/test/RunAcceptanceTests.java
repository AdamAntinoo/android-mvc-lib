package org.dimensinfin.android.mvc.acceptance.test;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = { "src/androidTest/assets/features" },
		glue = { "org.dimensinfin.android.mvc.acceptance.test" },
		plugin = { "pretty", "json:target/cucumber_report.json" },
		tags = { "not @skip_scenario", "not @front", "not @duplication", "not @Credential", "@MVC04.05" })
public class RunAcceptanceTests {
}