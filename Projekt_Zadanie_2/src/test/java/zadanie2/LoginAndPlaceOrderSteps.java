package zadanie2;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;


public class LoginAndPlaceOrderSteps {
    private WebDriver driver;

    @Given("user has active account in MyStore web store")
    public void openMyStore() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://mystore-testlab.coderslab.pl/index.php?controller=authentication&back=my-account");
    }

    @When("user signs in with mail {string} and password {string}")
    public void userSignsInWithMailAndPassword(String mail, String password) {
        driver.findElement(By.id("field-email")).sendKeys(mail);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.id("submit-login")).click();
    }

    @And("user chooses Hummingbird Printed Sweater")
    public void userChoosesHummingbirdPrintedSweater() {
        driver.get("https://mystore-testlab.coderslab.pl/index.php?id_product=2&id_product_attribute=9&rewrite=brown-bear-printed-sweater&controller=product#/1-size-s");
    }

    @And("user chooses size M")
    public void userChoosesSize() {
        WebElement sizeDropdown = driver.findElement(By.id("group_1"));
        Select sizeSelect = new Select(sizeDropdown);
        sizeSelect.selectByVisibleText("M");
    }

    @And("user selects {string} pieces")
    public void userSelectsPieces(String quantity) throws InterruptedException {
        WebElement quantityInput = driver.findElement(By.id("quantity_wanted"));
        quantityInput.click();
        Thread.sleep(1000);
        quantityInput.sendKeys(Keys.chord(Keys.CONTROL, "A"));
        quantityInput.sendKeys(quantity);
    }

    @And("adds products to cart")
    public void addsProductsToCart() {
        driver.findElement(By.cssSelector(".add-to-cart")).click();
    }

    @And("user goes to Checkout")
    public void userGoesToCheckout() {
        driver.get("https://mystore-testlab.coderslab.pl/index.php?controller=cart&action=show");
        driver.navigate().refresh();
        driver.get("https://mystore-testlab.coderslab.pl/index.php?controller=order");
    }

    @And("user confirms address")
    public void userConfirmsAddress() {
        driver.findElement(By.name("confirm-addresses")).click();
    }

    @And("user chooses pick up method Pick up in store")
    public void userChoosesPickUpMethodPickUpInStore() {
        driver.findElement(By.id("delivery_option_6")).click();
        driver.findElement(By.id("delivery_option_8")).click();
        driver.findElement(By.name("confirmDeliveryOption")).click();
    }

    @And("user chooses payment method Pay by check and clicks Place Order")
    public void userChoosesPaymentMethodPayByCheckAndClicksPlaceOrder() {
        driver.findElement(By.id("payment-option-1")).click();
        driver.findElement(By.id("conditions_to_approve[terms-and-conditions]")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        WebElement placeOrderButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@class, 'btn-primary') and contains(text(), 'Place order')]")));
        placeOrderButton.click();
    }

    @Then("user takes a screenshot of order confirmation and payment amount")
    public void userTakesAScreenshotOfOrderConfirmation() {
        try {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File("scr_order_confirmation_" + System.currentTimeMillis() + ".jpg"));
        } catch (IOException e) {
            System.err.println("Failed to save screenshot: " + e.getMessage());
        }
    }
}