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
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NegativeTest {
    private WebDriver driver;

    @BeforeAll
    public static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUP() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999/");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldBeSuccessfulForm() {
        driver.findElement(By.cssSelector("button[type='button']")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();
        assertEquals("Поле обязательно для заполнения", text.trim());
        driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).isDisplayed();
    }

    @Test
    void shouldBeFailedIncorrectPhoneInput() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Полина");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+7996923631111");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button[type='button']")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
        driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).isDisplayed();
    }

    @Test
    void inputPhoneShouldValidateLatin() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Полина");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("serezka");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button[type='button']")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
        driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).isDisplayed();
    }

    @Test
    void inputPhoneShouldValidateChar() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Полина");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("!@#$%^&*()_");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button[type='button']")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
        driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).isDisplayed();
    }

    @Test
    void  incomingPhoneShouldGiveAnErrorLessThan11() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Полина");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+7996923631");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button[type='button']")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
        driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).isDisplayed();

    }

    @Test
    void shouldBeFailedIncorrectNameInput() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Полина1314");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79969236311");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button[type='button']")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
        driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).isDisplayed();
    }

    @Test
    void inputNameShouldValidateLatin() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Polina");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79969236311");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button[type='button']")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
        driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).isDisplayed();
    }

    @Test
    void inputNameShouldValidateChar() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("!@#$%^*()");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79969236311");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button[type='button']")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
        driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).isDisplayed();
    }

    @Test
    void shouldBeFailedIncorrectCheckboxUsed() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Полина");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79969236311");
        driver.findElement(By.cssSelector("button[type='button']")).click();
        assertTrue(driver.findElement(By.cssSelector("[data-test-id='agreement'].input_invalid")).isDisplayed());
    }
}
