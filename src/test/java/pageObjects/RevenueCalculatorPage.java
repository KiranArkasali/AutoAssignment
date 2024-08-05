package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.ConfigReader;
import utils.WebBasePage;

import java.time.Duration;
import java.util.List;

public class RevenueCalculatorPage extends WebBasePage {

    private final WebDriver driver;

    private final By revenueCalculatorLocator = By.xpath("//*[contains(text(), 'Revenue Calculator')]");
    private final By sliderLocator = By.xpath("//*[@aria-orientation='horizontal']");
    private final By sliderInputLocator = By.xpath("//*[contains(@class, 'MuiInputBase-input MuiOutlinedInput-input MuiInputBase-inputSizeSmall')]");

    private final By totalRecurringReimbursementPerMonthLocator = By.xpath("//div[contains(@class, 'MuiToolbar-root MuiToolbar-gutters MuiToolbar-regular')]//p[contains(@class, 'MuiTypography-root MuiTypography-body2')]");

    public RevenueCalculatorPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }
    public void openUrl() {
        String url = ConfigReader.getProperty("applicationUrl");
        driver.get(url);
    }
    public void navigateToRevenueCalculatorPage() {
        clickElementVisible(revenueCalculatorLocator, "Revenue Calculator");
        verifyElement(revenueCalculatorLocator, "Revenue Calculator");
    }
    public void scrollTheSliderToTheCertainPosition() {
        scrollToElement(sliderLocator, "Slider been moved to position 820");
    }
    public void updateTheSliderToTheNewPosition(Integer newValue) {
        enterElementVisible(sliderInputLocator, newValue, "Input the new slider position to 560");
    }
    public void verifyUpdatedSliderValue(Integer expectedValue){
        WebElement element = findElementVisibility(sliderInputLocator);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.attributeToBe(sliderInputLocator, "value", String.valueOf(expectedValue)));
        String actualValue = element.getAttribute("value");
        Assert.assertEquals(actualValue, expectedValue.toString());
    }
    public void selectTheCPTCodes(List<String> cptCodesList) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        int maxAttempts = 10;
        int attempts = 0;
        boolean allCodesSelected = false;

        while (attempts < maxAttempts && !allCodesSelected) {
            List<WebElement> cptCodeElements = driver.findElements(By.xpath("//p[@class='MuiTypography-root MuiTypography-body1 inter css-1s3unkt']"));
            List<WebElement> cptCodeCheckBoxes = driver.findElements(By.xpath("//label[@class='MuiFormControlLabel-root MuiFormControlLabel-labelPlacementEnd inter css-1ml0yeg']//input[@type='checkbox']"));

            for (int i = 0; i < cptCodeElements.size(); i++) {
                String cptCode = cptCodeElements.get(i).getText();
                if (cptCodesList.contains(cptCode)) {
                    cptCodeCheckBoxes.get(i).click();
                    js.executeAsyncScript("window.setTimeout(arguments[arguments.length - 1], 2000);");
                }
            }

            allCodesSelected = cptCodesList.stream().allMatch(this::isCodeSelected);

            if (!allCodesSelected) {
                WebElement scrollableElement = driver.findElement(By.xpath("//*[@class='MuiBox-root css-1p19z09']"));
                js.executeScript("arguments[0].scrollIntoView(true);", scrollableElement);
                js.executeAsyncScript("window.setTimeout(arguments[arguments.length - 1], 2000);");

                attempts++;
            }
        }
        if (!allCodesSelected) {
            throw new RuntimeException("Not all CPT codes were selected after " + maxAttempts + " attempts.");
        }
    }
    private boolean isCodeSelected(String code) {
        List<WebElement> cptCodeElements = driver.findElements(By.xpath("//p[@class='MuiTypography-root MuiTypography-body1 inter css-1s3unkt']"));
        List<WebElement> cptCodeCheckBoxes = driver.findElements(By.xpath("//label[@class='MuiFormControlLabel-root MuiFormControlLabel-labelPlacementEnd inter css-1ml0yeg']//input[@type='checkbox']"));

        for (int i = 0; i < cptCodeElements.size(); i++) {
            String cptCode = cptCodeElements.get(i).getText();
            if (cptCode.equals(code) && cptCodeCheckBoxes.get(i).isSelected()) {
                return true;
            }
        }
        return false;
    }

    public void totalRecurringReimbursement(String reimbursementTxt, String reimbursementVal) {
        List<WebElement> totalRecurringReimbursementPerMonth = findElementsVisibility(totalRecurringReimbursementPerMonthLocator);
        for( WebElement totalRecurringReimbursement : totalRecurringReimbursementPerMonth ) {
            String expectedReimbursementPerMonthVal = reimbursementVal.replaceAll("[\",']", "").trim();
            String reimbursementPerMonthTxt = totalRecurringReimbursement.getText();
            String[] reimbursementPerMonthTxtVal = reimbursementPerMonthTxt.split(":\\s*");
            String finalActualReimbursementPerMonthTxt = reimbursementPerMonthTxtVal[0].trim();
            String actualReimbursementPerMonthVal = reimbursementPerMonthTxtVal[1].trim();
            if(finalActualReimbursementPerMonthTxt.contains(reimbursementTxt)) {
                Assert.assertEquals(actualReimbursementPerMonthVal,expectedReimbursementPerMonthVal);
            }
        }
    }
}
