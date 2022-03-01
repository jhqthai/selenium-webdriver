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

public class ExceptionTests {
  private WebDriver driver;

  @Test
  public void notVisibleTest() {
    System.out.println("Starting notVisibleTest");

    // Click on start button
    WebElement startButton = driver.findElement(By.xpath("//div[@id='start']/button"));
    startButton.click();



    // Verifiy Hello World Prompt
    String expectedMessage = "Hello World!";


    // Get message
    WebElement getMessage = driver.findElement(By.cssSelector("#finish"));
    
    // Wait for message to appears - explicit wait
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    wait.until(ExpectedConditions.visibilityOf(getMessage));
    
    // Set message to text
    String actualMessage = getMessage.getText();

    // Compare actualMessage with expectedMessage
    Assert.assertTrue(actualMessage.contains(expectedMessage),
        "Actual does not contain expected message. \nActual Message: " + actualMessage + "\nExpected Message: "
            + expectedMessage);

  }

  @Parameters({ "browser" })
  @BeforeMethod(alwaysRun = true)
  private void setup(@Optional("chrome") String browser) {
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
    String url = "http://the-internet.herokuapp.com/dynamic_loading/1";
    driver.get(url);
    System.out.println("Page is opened.");

    // Implicit wait
    // driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

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
