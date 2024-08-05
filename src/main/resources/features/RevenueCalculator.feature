Feature: Validation of record calculator

  Scenario: Test to validate the behaviour of revenue calculator slider, CPT codes, and recurring reimbursement.
    Given Open the web browser and navigate to FitPeo homepage
    When the user navigates to Revenue Calculator page
    Then adjust the slider under Medicare Eligible Patients to 820
    And update the slider value to 560 in the provided text field
    And I verify the updated slider value to be 560
    And update the slider value to 820 in the provided text field
    Then select the below CPT codes
    |CPT-99091|
    |CPT-99453|
    |CPT-99454|
    |CPT-99474|
    And validate the "Total Recurring Reimbursement for all Patients Per Month" of "$110700"

