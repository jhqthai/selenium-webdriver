package com.herokuapp.theinternet;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class LoginTests {
  private WebDriver driver;

  @Test(priority = 1, groups = { "postiveTests", "smokeTests" })
  public void positiveLoginTest() {
    System.out.println("Starting positiveLoginTest");

    // Enter user name
    WebElement username = driver.findElement(By.id("username"));
    username.sendKeys("tomsmith");

    // Enter password
    WebElement password = driver.findElement(By.id("password"));
    password.sendKeys("SuperSecretPassword!");

    // Explicit wait
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    
    // Click login button
    WebElement loginButton = driver.findElement(By.className("radius"));
    wait.until(ExpectedConditions.elementToBeClickable(loginButton));
    loginButton.click();

    // Verifications
    // New url
    String expectedUrl = "http://the-internet.herokuapp.com/secure";
    String actualUrl = driver.getCurrentUrl();
    Assert.assertEquals(actualUrl, expectedUrl, "Actual page url is not the same as expected");

    // Logout button visible
    WebElement logOutButton = driver.findElement(By.xpath("//a[@class='button secondary radius']"));
    Assert.assertTrue(logOutButton.isDisplayed(), "Log out button is not visible");

    // sucessful login message
//    WebElement successMessage = driver.findElement(By.cssSelector("#flash"));
    WebElement successMessage = driver.findElement(By.xpath("//div[@id='flash']"));
    String expectedMessage = "You logged into a secure area!";
    String actualMessage = successMessage.getText();
    Assert.assertTrue(actualMessage.contains(expectedMessage),
        "Actual does not contain expected message. \nActual Message: " + actualMessage + "\nExpected Message: "
            + expectedMessage);
  }

  @Parameters({ "username", "password", "expectedMessage" })
  @Test(priority = 2, groups = { "negativeTests", "smokeTests" })
  public void negativeLoginTest(String username, String password, String expectedMessage) {
    System.out.println("Starting negativeLoginTest with " + username + " and " + password);

    // Enter incorrect username
    WebElement usernameElement = driver.findElement(By.id("username"));
    usernameElement.sendKeys(username);

    // Enter correct password
    WebElement passwordElement = driver.findElement(By.id("password"));
    passwordElement.sendKeys(password);
    
    // Press login
    WebElement loginButton = driver.findElement(By.className("radius"));
    loginButton.click();

    // Verification
    // Invalid login message
    WebElement invalidMessage = driver.findElement(By.xpath("//div[@id='flash']"));
    String actualMessage = invalidMessage.getText();
    Assert.assertTrue(actualMessage.contains(expectedMessage),
        "Actual does not contain expected message. \nActual Message: " + actualMessage + "\nExpected Message: "
            + expectedMessage);

  }

  @Parameters({ "browser" })
  @BeforeMethod(alwaysRun = true)
  private void setup(@Optional String browser) {
    // Create driver
    switch (browser) {
    case "chrome": {
      System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
      driver = new ChromeDriver();
      break;
    }
    case "firefox": {
      System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver.exe");
      driver = new FirefoxDriver();
      break;
    }
    default:
      System.out.println("Do not know how to start " + browser + ", starting Chrome Browser instead.");
      System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
      driver = new ChromeDriver();
      break;
    }

    // maximise browser window
//    driver.manage().window().maximize();

    // Open test page
    String url = "http://the-internet.herokuapp.com/login";
    driver.get(url);
    System.out.println("Page is opened.");
    
    // Implicit wait
//    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    
    // Explicit wait
    
  }

  @AfterMethod(alwaysRun = true)
  private void closeUp() {
    // Close browser
    driver.quit();
  }

//  private void sleep(long m) {
//    try {
//      Thread.sleep(m);
//    } catch (InterruptedException e) {
//      // TODO Auto-generated catch block
//      e.printStackTrace();
//    }
//  }

}
