package io.swag;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class SauceDemoTest {

  public static void main(String[] args) {
    // Set up WebDriver
    WebDriverManager.chromedriver().setup();
    WebDriver driver = new ChromeDriver();

    try {
      // Step 1: Navigate to the SauceDemo website
      driver.get("https://www.saucedemo.com/");
      driver.manage().window().maximize();

      // Step 2: Log in using known credentials
      driver.findElement(By.id("user-name")).sendKeys("standard_user");
      driver.findElement(By.id("password")).sendKeys("secret_sauce");
      driver.findElement(By.id("login-button")).click();

      // Step 3: Find the highest-priced item
      List<WebElement> prices = driver.findElements(By.className("inventory_item_price"));
      System.out.println(prices);
      List<WebElement> addToCartButtons = driver.findElements(
          By.xpath("//button[text()='Add to cart']"));

      double maxPrice = 0;
      int maxPriceIndex = -1;

      // loop through the items to find max price item
      for (int i = 0; i < prices.size(); i++) {
        double price = Double.parseDouble(prices.get(i).getText().replace("$", ""));
        if (price > maxPrice) {
          maxPrice = price;
          maxPriceIndex = i;
        }
      }

      // Step 4: Add the highest-priced item to the cart
      if (maxPriceIndex != -1) {
        addToCartButtons.get(maxPriceIndex).click();
        System.out.println("Added highest price item: $" + maxPrice + " to cart.");
        driver.findElement(By.className("shopping_cart_link")).click();
      } else {
        System.out.println("No items found!");
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
