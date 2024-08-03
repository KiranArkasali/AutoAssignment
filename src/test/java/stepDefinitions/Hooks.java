package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import utils.ConfigReader;
import utils.WebBasePage;

public class Hooks {

    static WebDriver driver;

    @Before
    public void setUp(Scenario scenario) {
        String browser = ConfigReader.getProperty("browser");
        String baseUrl = ConfigReader.getProperty("applicationUrl");
        String driverPath = System.getProperty("user.dir") + "\\src\\main\\resources\\drivers\\";

        try {
            if (browser.equalsIgnoreCase("chrome")) {
                System.setProperty("webdriver.chrome.driver", driverPath + "chromedriver.exe");
                driver = new ChromeDriver();
            } else {
                throw new IllegalArgumentException("Browser not supported: " + browser);
            }

            driver.manage().window().maximize();
            driver.get(baseUrl);

            System.out.println("Scenario Name: " + scenario.getName());
        } catch (Exception e) {
            System.err.println("Failed to initialize WebDriver: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize WebDriver", e);
        }
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", scenario.getName());
        }

        if (driver != null) {
            driver.quit();
        }
    }

    @AfterStep
    public void afterStep() {
        WebBasePage wait = new WebBasePage(driver);
        wait.staticWait();
    }

    public static WebDriver getDriver() {
        if (driver == null) {
            throw new IllegalStateException("WebDriver has not been initialized.");
        }
        return driver;
    }
}
