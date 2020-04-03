@MVC02 @DemoMultiPageActivity
Feature: [MVC02]-Create a multi page activity that presents the same information but with different renderings.

  Create a first page that will use expandable grouping by type so the items of the same type get aggregated to the same group.
  The second page will group this same elements but by category so the same list of elements are rendered
  but grouped by categories.

  @MVC02 @MVC02.01
  Scenario: [MVC02.01]-Check that there are two pages and validate the action bar for both pages.
	Given the DemoMultiPageActivity
	When the DemoMultiPageActivity activity lifecycle completes for page "0"
	Then the DemoMultiPageActivity has "2" pages
#	When the page "0" is the active page
	Then there is an action bar of type "MultiPageActionBar" with the next fields
	  | title                     |
	  | Items classified by Group |
#	When the page "1" is the active page
	Then there is an action bar of type "MultiPageActionBar" with the next fields
	  | title                        |
	  | Items classified by Category |
