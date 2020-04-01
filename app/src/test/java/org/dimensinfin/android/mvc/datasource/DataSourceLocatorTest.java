package org.dimensinfin.android.mvc.datasource;

import org.junit.Assert;
import org.junit.Test;
public class DataSourceLocatorTest {

	@Test
	public void addIdentifier() {
		// Given
		final DataSourceLocator locator = new DataSourceLocator();

		// Test
		locator.addIdentifier(123);
		 String expected = "123/";

		// Asserts
		Assert.assertEquals("The identifier should match value.", expected , locator.getIdentity());

		// Test
		locator.addIdentifier(123456L);
		expected = "123/123456/";

		// Asserts
		Assert.assertEquals("The identifier should match value.", expected , locator.getIdentity());

		// Test
		locator.addIdentifier("TEST");
		expected = "123/123456/TEST/";

		// Asserts
		Assert.assertEquals("The identifier should match value.", expected , locator.getIdentity());
	}

	@Test
	public void testToString() {
		// Given
		final DataSourceLocator locator = new DataSourceLocator();

		// Test
		locator.addIdentifier(123);
		locator.addIdentifier(123456L);
		locator.addIdentifier("TEST");
		final String expected = "DataSourceLocator[123/123456/TEST/]";

		// Asserts
		Assert.assertEquals("The string should match value.", expected , locator.toString());
	}
}
