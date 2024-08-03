package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.sl.In;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import pageObjects.RecordCalculatorPage;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import static stepDefinitions.Hooks.driver;

public class RecordCalculatorSteps {
    private final WebDriver webDriver;
    private RecordCalculatorPage recordCalculatorPage;

    public RecordCalculatorSteps() {
        this.webDriver = Hooks.getDriver();
    }

    @Given("Open the web browser and navigate to FitPeo homepage")
    public void open_the_web_browser_and_navigate_to_fit_peo_homepage() {
        recordCalculatorPage = new RecordCalculatorPage(webDriver);
        recordCalculatorPage.openUrl();
    }

    @When("the user navigates to Revenue Calculator page")
    public void the_user_navigates_to_revenue_calculator_page() {
        recordCalculatorPage.navigateToRevenueCalculatorPage();
    }

    @Then("adjust the slider under Medicare Eligible Patients to {int}")
    public void adjust_the_slider_under_medicare_eligible_patients_to(Integer int1) {
       recordCalculatorPage.scrollTheSliderToTheCertainPosition();
    }

    @Then("update the slider value to {int} in the provided text field")
    public void update_the_slider_value_to_in_the_provided_text_field(Integer newValue) {
        recordCalculatorPage.updateTheSliderToTheNewPosition(newValue);
    }

    @Then("select the below CPT codes")
    public void select_the_below_cpt_codes(io.cucumber.datatable.DataTable dataTable) {
        List<String> cptCodesList = dataTable.asLists(String.class).stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
        recordCalculatorPage.selectTheCPTCodes(cptCodesList);
    }

    @Then("validate the {string} of {string}")
    public void validate_the_total_recurring_reimbursement_(String reimbursementTxt, String reimbursementVal) {
        recordCalculatorPage.totalRecurringReimbursement(reimbursementTxt, reimbursementVal);
    }
}
