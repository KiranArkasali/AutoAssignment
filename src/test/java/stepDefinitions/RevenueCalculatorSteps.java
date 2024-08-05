package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import pageObjects.RevenueCalculatorPage;

import java.util.List;
import java.util.stream.Collectors;

public class RevenueCalculatorSteps {
    private final WebDriver webDriver;
    private RevenueCalculatorPage revenueCalculatorPage;

    public RevenueCalculatorSteps() {
        this.webDriver = Hooks.getDriver();
    }

    @Given("Open the web browser and navigate to FitPeo homepage")
    public void open_the_web_browser_and_navigate_to_fit_peo_homepage() {
        revenueCalculatorPage = new RevenueCalculatorPage(webDriver);
        revenueCalculatorPage.openUrl();
    }

    @When("the user navigates to Revenue Calculator page")
    public void the_user_navigates_to_revenue_calculator_page() {
        revenueCalculatorPage.navigateToRevenueCalculatorPage();
    }

    @Then("adjust the slider under Medicare Eligible Patients to {int}")
    public void adjust_the_slider_under_medicare_eligible_patients_to(Integer int1) {
       revenueCalculatorPage.scrollTheSliderToTheCertainPosition();
    }

    @Then("update the slider value to {int} in the provided text field")
    public void update_the_slider_value_to_in_the_provided_text_field(Integer newValue) {
        revenueCalculatorPage.updateTheSliderToTheNewPosition(newValue);
    }

    @Then("I verify the updated slider value to be {int}")
    public void i_verify_the_updated_slider_value_to_be(Integer updatedValue) {
       revenueCalculatorPage.verifyUpdatedSliderValue(updatedValue);
    }

    @Then("select the below CPT codes")
    public void select_the_below_cpt_codes(io.cucumber.datatable.DataTable dataTable) {
        List<String> cptCodesList = dataTable.asLists(String.class).stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
        revenueCalculatorPage.selectTheCPTCodes(cptCodesList);
    }

    @Then("validate the {string} of {string}")
    public void validate_the_total_recurring_reimbursement_(String reimbursementTxt, String reimbursementVal) {
        revenueCalculatorPage.totalRecurringReimbursement(reimbursementTxt, reimbursementVal);
    }
}
