@MVC01 @AndroidMVC
Feature: [MVC01] Detect and control all possible coding exceptions.

  During the processing of a new view from a render instance we can get some runtime exceptions not detected at compilation
  time. Null pointers, casting, network access from main thread and others are the most common exceptions. Replace the
  intended render panel by a new exception panel with the exception name, message and location to be reported.

  At coding there are cases where the compiler generated a valid application that cannot run because there are missing
  elements that make impossible to generate the contents. That failures usually do not leave clues after the application
  crashes. Detect them and replace the exceptions by a render message explanation.

  Scenario: [MVC01.01] The creating a new title panel there is an exception and the panel is replaces by a exception panel
	Given a TitlePanel render
	When creating the view
	Then show the next information on the ExceptionRender
	  | exceptionName        | exceptionMessage     | exceptionOriginClass            |
	  | NullPointerException | NullPointerException | .TitlePanelRender.updateContent |

  Scenario: [MVC01.02] When the fragment does not declare a data source the exception is not detected by the compiler and the application crashes.
	Given the activity "AcceptanceActivity02"
	And a Fragment that do not defines a DataSource
	When reaching the onResume phase
	Then get an exception at the onCreateView phase
	And deactivate data panels
	And render exception to page

  Scenario: [MVC01.03] Detect and render the exception produced during the 'prepare' phase on a data source. Add the exceptions to the header.
	Given the activity "AcceptanceActivity01" on the page "3"
	When reaching the onResume phase
	Then check that the header contains 1 panel
	And the panel renders the next information
	  | exceptionName        | exceptionMessage                                   | exceptionOriginClass          |
	  | NullPointerException | Exception generated while on 'prepareModel' phase. | .MVC02DataSource.prepareModel |