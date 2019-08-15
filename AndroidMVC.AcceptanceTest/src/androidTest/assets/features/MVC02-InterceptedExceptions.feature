@MVC02 @AndroidMVC
Feature: [MVC02] Detect and control all possible coding exceptions.

  At coding there are cases where the compiler generated a valid application that cannot run because there are missing
  elements that make impossible to generate the contents. That failures usually do not leave clues after the application
  crashes. Detect them and replace the exceptions by a render message explanation.

  Scenario: [MVC02.01] When the fragment does not declare a data source the exception is not detected by the compiler and the application crashes.
	Given the activity "AcceptanceActivity02"
	And a Fragment that do not defines a DataSource
	When reaching the onResume phase
	Then get an exception at the onCreateView phase
	And deactivate data panels
	And render exception to page