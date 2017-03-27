package testex;

import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.Keys;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebTest {

    @Before
    public void before() {
        System.setProperty("webdriver.gecko.driver", "c:\\dev-tools\\drivers\\geckodriver.exe");
        System.setProperty("webdriver.chrome.driver", "c:\\dev-tools\\drivers\\chromedriver.exe");

        com.jayway.restassured.RestAssured.given().get("http://localhost:3000/reset");
    }

    private static WebDriver createDriver(boolean ensureLoaded) {
        // Create a new instance of the Firefox driver
        // Notice that the remainder of the code relies on the interface, 
        // not the implementation.
        WebDriver driver = new ChromeDriver();

        // And now use this to visit Google
        driver.get("http://localhost:3000/");

        if (ensureLoaded) {
            (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver d) {
                    WebElement element = d.findElement(By.id("tbodycars"));

                    return element.findElements(By.tagName("tr")).size() == 5;
                }
            });
        }

        return driver;
    }

    @Test
    public void isDOMLoaded() {
        WebDriver driver = createDriver(false);

        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                WebElement element = d.findElement(By.id("tbodycars"));

                return element.findElements(By.tagName("tr")).size() == 5;
            }
        });

        //Close the browser
        driver.quit();
    }

    //   @Test
    public void isFiltering() {
        WebDriver driver = createDriver(true);

        WebElement element = driver.findElement(By.id("filter"));

        element.sendKeys("2002");

        (new WebDriverWait(driver, 3)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                WebElement element = d.findElement(By.id("tbodycars"));

                return element.findElements(By.tagName("tr")).size() == 2;
            }
        });

        //Close the browser
        driver.quit();
    }

    //  @Test
    public void isFilteringAndClearing() {
        WebDriver driver = createDriver(true);

        WebElement element = driver.findElement(By.id("filter"));

        element.sendKeys("2002");

        (new WebDriverWait(driver, 3)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                WebElement e = d.findElement(By.id("tbodycars"));

                return e.findElements(By.tagName("tr")).size() == 2;
            }
        });

        element.sendKeys(Keys.CONTROL + "a");
        element.sendKeys(Keys.DELETE);

        (new WebDriverWait(driver, 3)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                WebElement e = d.findElement(By.id("tbodycars"));

                return e.findElements(By.tagName("tr")).size() == 5;
            }
        });

        //Close the browser
        driver.quit();
    }

    @Test
    public void isSorting() {
        WebDriver driver = createDriver(true);

        WebElement element = driver.findElement(By.id("h_year"));

        element.click();

        WebElement tbody = driver.findElement(By.id("tbodycars"));

        String firstId = tbody.findElement(By.cssSelector("tr:first-child"))
                .findElement(By.cssSelector("td:first-child")).getText();

        String lastId = tbody.findElement(By.cssSelector("tr:last-child")).findElement(By.cssSelector("td:first-child"))
                .getText();

        driver.quit();

        assertEquals("938", firstId);
        assertEquals("940", lastId);
    }

    @Test
    public void isUpdating() {
        WebDriver driver = createDriver(true);

        WebElement tbody = driver.findElement(By.id("tbodycars"));

        WebElement editBtn = tbody.findElement(By.cssSelector("tr:nth-child(2)"))
                .findElement(By.cssSelector("td:last-child")).findElement(By.cssSelector("a:first-child"));

        editBtn.click();

        WebElement descriptionTxt = driver.findElement(By.id("description"));
        descriptionTxt.sendKeys(Keys.CONTROL + "a");
        descriptionTxt.sendKeys(Keys.DELETE);
        descriptionTxt.sendKeys("Cool car");

        WebElement saveBtn = driver.findElement(By.id("save"));
        saveBtn.click();

        assertEquals("Cool car", tbody.findElement(By.cssSelector("tr:nth-child(2)"))
                .findElement(By.cssSelector("td:nth-child(6)")).getText());

        driver.quit();
    }

    @Test
    public void isDisplayingErrorMessage() {
        WebDriver driver = createDriver(true);

        WebElement createBtn = driver.findElement(By.id("new"));
        createBtn.click();

        WebElement saveBtn = driver.findElement(By.id("save"));
        saveBtn.click();

        assertEquals("All fields are required", driver.findElement(By.id("submiterr")).getText());

        driver.quit();
    }

    @Test
    public void isCreating() {
        WebDriver driver = createDriver(true);

        WebElement createBtn = driver.findElement(By.id("new"));
        createBtn.click();

        WebElement txtYear = driver.findElement(By.id("year"));
        txtYear.sendKeys("2008");

        WebElement txtRegistered = driver.findElement(By.id("registered"));
        txtRegistered.sendKeys("2002-5-5");

        WebElement txtMake = driver.findElement(By.id("make"));
        txtMake.sendKeys("Kia");

        WebElement txtModel = driver.findElement(By.id("model"));
        txtModel.sendKeys("Rio");

        WebElement txtDescription = driver.findElement(By.id("description"));
        txtDescription.sendKeys("As new");

        WebElement txtPrice = driver.findElement(By.id("price"));
        txtPrice.sendKeys("31000");

        WebElement saveBtn = driver.findElement(By.id("save"));
        saveBtn.click();

        (new WebDriverWait(driver, 3)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                WebElement e = d.findElement(By.id("tbodycars"));

                return e.findElements(By.tagName("tr")).size() == 6;
            }
        });

        driver.quit();
    }
}