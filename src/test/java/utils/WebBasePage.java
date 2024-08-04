package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class WebBasePage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private static final Logger LOGGER = LoggerFactory.getLogger(WebBasePage.class);

    public WebBasePage(WebDriver webDriver) {
        this.driver = webDriver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    }

    public void enterElementVisible(By by, Integer value, String name) {
        WebElement element = findElementVisibility(by);
        if (element != null) {
            element.click();
            element.clear();
            sendKeysByJavascript(by, value);
            LOGGER.info(name);
        } else {
            LOGGER.error(name + " - element not found");
            Assert.fail(name + " - element not found");
        }
    }
    public void clickElementVisible(By by, String name) {
        WebElement element = findElementVisibility(by);
        if (element != null) {
            element.click();
            LOGGER.info(name + " clicked");
        } else {
            LOGGER.error(name + " - element not found");
            Assert.fail(name + " - element not found");
        }
    }
    public void verifyElement(By by, String name) {
        WebElement element = findElementVisibility(by);
        String value = element.getText();
        if (value.equals(name)) {
            LOGGER.info(name + " visible");
        } else {
            LOGGER.error(name + " is not visible");
            Assert.fail(name + " - element not found");
        }
    }
    public void scrollToElement(By by, String name) {
        WebElement element = findElementVisibility(by);
        if (element != null) {
            Actions actions = new Actions(driver);
            int width = element.getSize().getWidth();
            int offset = (int) (width * 4.65);

            actions.clickAndHold(element)
                    .moveByOffset(offset, 0)
                    .release()
                    .build()
                    .perform();
            LOGGER.info(name);
        } else {
            LOGGER.error("Unable to scroll to " + by.toString());
            Assert.fail("Unable to scroll to " + by);
        }
    }
    public void staticWait() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeAsyncScript("window.setTimeout(arguments[arguments.length - 1], 2000);");
    }
    public void sendKeysByJavascript(By by, Integer value) {
        WebElement element = driver.findElement(by);
        element.clear();
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click().perform();
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = '';", element);
        actions.moveToElement(element).sendKeys(value.toString()).perform();

    }
    public WebElement findElementVisibility(final By by) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            wait.until((ExpectedCondition<Boolean>) _webDriver -> {
                assert _webDriver != null;
                return (_webDriver.findElement(by) != null);
            });
        } catch (Exception e) {
            LOGGER.error("Element not found: " + by.toString(), e);
            return null;
        }
        return driver.findElement(by);
    }
    public List<WebElement> findElementsVisibility(final By by) {
        try {
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
            wait.until((ExpectedCondition<Boolean>) _webDriver -> {
                assert _webDriver != null;
                return (!_webDriver.findElements(by).isEmpty());
            });
        } catch (Exception e) {
            LOGGER.error("Elements not found: " + by.toString(), e);
            return List.of();
        }
        return driver.findElements(by);
    }
}
