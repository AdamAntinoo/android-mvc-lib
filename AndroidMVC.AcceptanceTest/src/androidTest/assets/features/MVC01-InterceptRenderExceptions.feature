@MVC01 @AndroidMVC
Feature: [MVC01] Intercept render phase exceptions and replace by explanation message panel.

  During the processing of a new view from a render instance we can get some runtime exceptions not detected at compilation
  time. Null pointers, casting, network access from main thread and others are the most common exceptions. Replace the
  intended render panel by a new exception panel with the exception name, message and location to be reported.

  Scenario: [MVC01.01] The creating a new title panel there is an exception and the panel is replaces by a exception panel
	Given a TitlePanel render
	When creating the view
	Then get a NullPointerException
	And replace the panel by a new ExceptionRender
	And show the next information on the ExceptionRender
	  | exceptionName        | exceptionMessage     | exceptionOriginClass | exceptionOriginMethod |
	  | NullPointerException | NullPointerException | TitlePanelRender     | updateContent         |