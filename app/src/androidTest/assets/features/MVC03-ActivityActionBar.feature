@MVC03B
Feature: [MVC03] Verify rendering for Action Bar.

  Each fragment may set an specific action bar with the contents relevant to the selected page. Or it cannot define any
  action bar and the rendering should use a default. Verify the contnts for the custom action bar and the correct
  set of the default.

  Scenario: [MVC03.01] Render a default action bar. Content default set on the manifest.
	Given a data
	When activating this data
	Then get the next result