import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FormsTests {

    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setupTest() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999/");
    }

    @AfterEach
    void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testOfTrueValidation() {
        driver.findElement(By.cssSelector("[data-test-id = 'name'] input")).sendKeys("Нетология");
        driver.findElement(By.cssSelector("[data-test-id = 'phone'] input")).sendKeys("+88007001234");
        driver.findElement(By.cssSelector("[data-test-id = 'agreement']")).click();
        driver.findElement(By.tagName("button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id = 'order-success']")).getText().trim();
        String expectedText = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        assertEquals(expectedText, actualText);
        driver.close();
    }

    @Test
    public void testNotTrueValidationOfName() {
        driver.findElement(By.cssSelector("[data-test-id = 'name'] input")).sendKeys("Netology");
        driver.findElement(By.cssSelector("[data-test-id = 'phone'] input")).sendKeys("+88007001234");
        driver.findElement(By.cssSelector("[data-test-id = 'agreement']")).click();
        driver.findElement(By.tagName("button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id = 'name'].input_invalid .input__sub")).getText().trim();
        String expectedText = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        assertEquals(expectedText, actualText);
        driver.close();
    }

    @Test
    public void testNotTrueValidationOfPhone() {
        driver.findElement(By.cssSelector("[data-test-id = 'name'] input")).sendKeys("Нетология");
        driver.findElement(By.cssSelector("[data-test-id = 'phone'] input")).sendKeys("123");
        driver.findElement(By.cssSelector("[data-test-id = 'agreement']")).click();
        driver.findElement(By.tagName("button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id = 'phone'].input_invalid .input__sub")).getText().trim();
        String expectedText = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        assertEquals(expectedText, actualText);
        driver.close();
    }

    @Test
    public void testIfNotEnterName() {
        driver.findElement(By.cssSelector("[data-test-id = 'phone'] input")).sendKeys("+88007001234");
        driver.findElement(By.cssSelector("[data-test-id = 'agreement']")).click();
        driver.findElement(By.tagName("button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id = 'name'].input_invalid .input__sub")).getText().trim();
        String expectedText = "Поле обязательно для заполнения";
        assertEquals(expectedText, actualText);
        driver.close();
    }

    @Test
    public void testIfNotEnterPhone() {
        driver.findElement(By.cssSelector("[data-test-id = 'name'] input")).sendKeys("Нетология");
        driver.findElement(By.cssSelector("[data-test-id = 'agreement']")).click();
        driver.findElement(By.tagName("button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id = 'phone'].input_invalid .input__sub")).getText().trim();
        String expectedText = "Поле обязательно для заполнения";
        assertEquals(expectedText, actualText);
        driver.close();
    }

    @Test
    public void testIfNotClickCheckbox() {
        driver.findElement(By.cssSelector("[data-test-id = 'name'] input")).sendKeys("Нетология");
        driver.findElement(By.cssSelector("[data-test-id = 'phone'] input")).sendKeys("+88007001234");
        driver.findElement(By.tagName("button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id = 'agreement'].input_invalid .checkbox__text")).getText().trim();
        String expectedText = "Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй";
        assertEquals(expectedText, actualText);
        driver.close();
    }
}