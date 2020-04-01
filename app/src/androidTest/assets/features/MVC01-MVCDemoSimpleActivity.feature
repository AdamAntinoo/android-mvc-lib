@MVC01 @MVCDemoSimpleActivity
Feature: [MVC01]-Validate the simplest demo activity.

  Check the size of the list of panels rendered and that size matches with the number of model elements in the feed list.

  @MVC01 @MVC01.01
  Scenario: [MVC01.01]-When the application is started the injectable elements are properly created and the header contains the correct application name.
	Given the MVCDemoSimpleActivity
	When the MVCDemoSimpleActivity activity lifecycle completes for page "0"
#	Then there is a action bar of type "AppVersion" with the next fields
#	  | AppName       | AppVersion |
#	  | MVCAcceptance | <version>  |
	Then the MVCDemoSimpleActivity has "1" pages
	And "2" panels on the "HeaderSection"
	And validate a "ApplicationHeaderTitle" panel with the next contents
	  | applicationName  | applicationVersion |
	  | Android-MVC-Demo | <version>          |
	And validate a "TitleLabel" panel with the next contents
	  | title                  |
	  | NON EXPANDABLE SECTION |
	And "21" panels on the "DataSection"
	And validate a "DemoLabel" panel with the next contents
	  | title                  |
	  | -PREDEFINED-VALUE- |
	And stop on debug point

#	And "12" panels on the LinearContainer
#	And the LinearContainer has a "InitializationException" panel with the next information
#	  | Message                                                            |
#	  | The network is disabled. The application cannot continue properly. |
#	And the LinearContainer has a "Retry" button with the next information
#	  | Message                    |
#	  | Restart the check process. |
