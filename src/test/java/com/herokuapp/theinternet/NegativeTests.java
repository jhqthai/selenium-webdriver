package com.herokuapp.theinternet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class NegativeTests {
  
  @Test
  public void negativeUsernameTest() {
    System.out.println("Starting negativeUsernameTest");
    
    // Create driver
    System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
    WebDriver driver = new ChromeDriver();
    
    // Maximise window
    driver.manage().window().maximize();

    // Open test page
    String url = "http://the-internet.herokuapp.com/login";
    driver.get(url);
    System.out.println("Page is opened.");
    
    // Enter incorrect username
    WebElement username = driver.findElement(By.id("username"));
    username.sendKeys("incorrectusername");
    
    // Enter correct password
    WebElement password = driver.findElement(By.id("password"));
    password.sendKeys("SuperSecretPassword!");
    
    // Press login
    WebElement loginButton = driver.findElement(By.className("radius"));
    loginButton.click();
    
    // Check prompt - invalid login message
//    Your username is invalid!
    WebElement invalidMessage = driver.findElement(By.xpath("//div[@id='flash']"));
    String expectedMessage = "Your username is invalid!";
    String actualMessage = invalidMessage.getText();
    Assert.assertTrue(actualMessage.contains(expectedMessage), "Actual does not contain expected message. \nActual Message: " + actualMessage + "\nExpected Message: "
            + expectedMessage);
    
    // Close browser
    driver.quit();
    
  }
}
