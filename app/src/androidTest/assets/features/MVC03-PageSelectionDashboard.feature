@MVC03 @PageSelectionDashboardActivity
Feature: [MVC03]-Check the number and contents for the page selection buttons.

  Check that the page selection buttons are visible and than their selection moves to the correct page destination.

  Background:
	Given the PageSelectionDashboardActivity
	When the PageSelectionDashboardActivity activity lifecycle completes
	And the page "0" is the active page
	And the PageSelectionDashboardActivity has "1" pages

  @MVC03 @MVC03.01
  Scenario: [MVC03.01]-Verify the number and layout for the page selection buttons.
	And there is an action bar of type "TitledActionBar" with the next fields
	  | title               |
	  | TEST PAGE SELECTION |
	And "2" panels on the "HeaderSection"
	And validate a list of "PageButton" panels with the next contents
	  | label                             | PageCode          |
	  | ONE PAGE SIMPLE LABEL LIST        | MVCDEMOLIST_ITEMS |
	  | MULTI PAGE DIFFERENT AGGREGATIONS | BYGROUP           |

#  @MVC03 @MVC03.02
#  Scenario: [MVC03.02]-When there is a click on the first button we move to the DemoSimpleActivity.
#	When there is a click on "PageButton" panel "0" we move to activity "MVCDemoSimpleActivity"