package com.herokuapp.theinternet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PositiveTests {

  @Test
  public void loginTest() {
    System.out.println("Starting loginTest");

    // Create driver
    System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
    WebDriver driver = new ChromeDriver();

    // maximise browser window
    driver.manage().window().maximize();

    // Open test page
    String url = "http://the-internet.herokuapp.com/login";
    driver.get(url);
    System.out.println("Page is opened.");

    // Enter user name
    WebElement username = driver.findElement(By.id("username"));
    username.sendKeys("tomsmith");

    // Enter password
    WebElement password = driver.findElement(By.id("password"));
    password.sendKeys("SuperSecretPassword!");

    // Click login button
    WebElement loginButton = driver.findElement(By.className("radius"));
    loginButton.click();
    sleep(1000);

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

    // Close browser
    driver.quit();

  }

  private void sleep(long m) {
    try {
      Thread.sleep(m);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

}
