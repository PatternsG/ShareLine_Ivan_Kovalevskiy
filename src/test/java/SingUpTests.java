import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class SingUpTests {

    private  WebDriver driver;

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void zipCodeNegativeFourNumberTest(){

        driver.get("https://www.sharelane.com/cgi-bin/register.py");
        driver.manage().window().maximize();
        WebElement zipCodeInput = driver.findElement(By.xpath("//input[@name='zip_code']"));
        zipCodeInput.click();
        zipCodeInput.sendKeys("1234");
        WebElement buttonContinue = driver.findElement(By.xpath("//input[@value='Continue']"));
        buttonContinue.click();
        WebElement errorMessage = driver.findElement(By.xpath("//span[@class='error_message']"));
        Assert.assertEquals(errorMessage.getText(), "Oops, error on page. ZIP code should have 5 digits",
                "Limit on the minimum number of numbers does not work");
    }

    @Test
    public void zipCodeNegativeLettersTest(){

        driver.get("https://www.sharelane.com/cgi-bin/register.py");
        driver.manage().window().maximize();
        WebElement zipCodeInput = driver.findElement(By.xpath("//input[@name='zip_code']"));
        zipCodeInput.click();
        zipCodeInput.sendKeys("ABCDEFG");
        WebElement buttonContinue = driver.findElement(By.xpath("//input[@value='Continue']"));
        buttonContinue.click();
        WebElement errorMessage = driver.findElement(By.xpath("//span[@class='error_message']"));
        Assert.assertEquals(errorMessage.getText(), "Oops, error on page. ZIP code should have 5 digits",
                "Character limit not working");
    }

    @Test
    public void singUpPositiveTest(){

        driver.get("https://www.sharelane.com/cgi-bin/register.py?page=1&zip_code=12345");
        driver.manage().window().maximize();
        driver.findElement(By.xpath("//input[@name='first_name']")).sendKeys("Ivan");
        driver.findElement(By.xpath("//input[@name='last_name']")).sendKeys("Kovalevsky");
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys("kto-to@gmail.com");
        driver.findElement(By.xpath("//input[@name='password1']")).sendKeys("12345678");
        driver.findElement(By.xpath("//input[@name='password2']")).sendKeys("12345678");
        WebElement buttonRegister = driver.findElement(By.xpath("//input[@value='Register']"));
        buttonRegister.click();
        WebElement confirmationMessage = driver.findElement(By.xpath("//span[@class='confirmation_message']"));
        Assert.assertEquals(confirmationMessage.getText(), "Account is created!",
                "The registration form is not working correctly");driver.quit();
    }

    @Test
    public void singUpNegativeTest(){

        driver.get("https://www.sharelane.com/cgi-bin/register.py?page=1&zip_code=12345");
        WebElement buttonRegister = driver.findElement(By.xpath("//input[@value='Register']"));
        buttonRegister.click();
        WebElement errorMessage = driver.findElement(By.xpath("//span[@class='error_message']"));
        Assert.assertEquals(errorMessage.getText(), "Oops, error on page. Some of your fields have invalid " +
                "data or email was previously used", "The registration form is not working correctly");
    }
}

