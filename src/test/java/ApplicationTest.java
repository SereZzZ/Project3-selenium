import dev.failsafe.internal.util.Assert;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest {
    private WebDriver driver; //создаем драйвер на основе вебдрайвера


    @BeforeAll
    public static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

   /* @BeforeEach  //перед каждым тестом инициализируем в тестах драйвер хром и видим что происходит
    void setUp(){
        driver = new ChromeDriver();
    }
    */

   @BeforeEach  // перед каждым тестом то же самое только в режиме хедрс для Ci чтобы не было видно интерфейса не видим что происходит
    void setUP() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

   @AfterEach  // после каждого теста выйти и обнулить
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldBeSuccessfulForm() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Полина");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79969236311");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button[type='button']")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText().trim();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim()); //trim удаляет пробелы с текста с переди и с зади
        assertTrue(driver.findElement(By.cssSelector("[data-test-id='order-success']")).isDisplayed());
    }
}





