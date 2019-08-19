@MVC04
Feature: [MVC04] Run the tests to check all the data sources features.

  Data sources will allow ht developer to store models into the data sources and then use the view adapter lifecycle
  to convert those models to the matching controller and then into views by the use of the correct renders. Variation
  strings will allow to select the right controller or event the right render from the set of available classes.

#  @MVC04.01
#  Scenario: [MVC04.01] Try to register an invalid data source and get the message to be render to the developer.
#	Given an invalid data source
#	When registering the data source on the manager
#	Then get a "NullPointerException" with the message "The data source cannot be a null reference. Please review the fragment code and implement the 'createDS' method."
#
#  @MVC04.01
#  Scenario: [MVC04.01] Create a new data source and identify it with a new locator
#	Given a new data source
#	When registering the data source on the manager
#	Then check this is a new data source on the manager
#	And identified by the correct locator
#
#  @MVC04.02
#  Scenario: [MVC04.02] When registering a new data source get the already registered copy if the locator matches an already present register.
#	Given a data source manager with a data source with locator "DS01" already registered
#	And a new data source with locator "DS01"
#	When registering the data source on the manager
#	Then get the already registered datasource and forget the new datasource
#
#  @MVC04.04
#  Scenario: [MVC04.04] After registering a new data source check that the models have been initialised by the 'prepare' phase.
#	Given a new data source
#	When registering the data source on the manager
#	Then check the header container has "1" elements
#	And check the data container has "1" elements

  @MVC04.05
  Scenario: [MVC04.05] After registering a new data source get the list of controllers for the header section.
	Given the activity "AcceptanceActivity04"
	And a new data source
	When registering the data source on the manager
	Then check that the header has the next list of controllers
	  | controllerClass | controllerModelTitle |
	  | Spacer          | -MVC04DataSource-    |

  @MVC04.06
  Scenario: [MVC04.06] After registering a new data source exceptions when converting models to controllers.
	Given a new data source
	When registering the data source on the manager
	Then check that the header containers have the next data
	  | controllerClass     | controllerModelTitle                                                   |
	  | SeparatorController | "-NO Model-Controller match-[" + node.getClass().getSimpleName() + "]- |
