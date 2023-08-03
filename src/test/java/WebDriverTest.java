import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;



public class WebDriverTest {
    private WebDriver driver;
    private Logger log = (Logger) LogManager.getLogger(WebDriverTest.class);
    private final Properties prop = new Properties();
    private FileInputStream fileInputStream;
    private static final String PATH_TO_CONFIG_PROPERTIES = "src/test/resources/config.properties";

    private String LOGIN, PASSWORD;
    @BeforeAll
    public static void webDriverSetup(){
      WebDriverManager.chromedriver().setup();
       // System.setProperty("webdriver.chrome.driver", "C:\\Users\\yazhuk20\\Desktop\\chromedriver1.exe");
    }


    public void setUpHeadless(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");
        driver = new ChromeDriver(options);
    }

    public void setUpFullScreen(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-fullscreen");
        driver = new ChromeDriver(options);
    }
    public void setUpKiosk(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--kiosk");
        driver = new ChromeDriver(options);
    }
    public void setUpIncognito(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("---incognito");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    public void setDown(){
        if (driver != null){
            driver.close();
            driver.quit();
        }
    }

    @Test
    public void firstTest() {
        log.fatal("fatal");
//        Open Chrome in Headless mode
        setUpHeadless();
//       Open URL
        driver.get(" https://duckduckgo.com/");
//          Enter "ОТУС" in search field
        clearAndEnter(By.cssSelector("#search_form_input_homepage"), "ОТУС");
//         Press click
        clicker(By.cssSelector("#search_button_homepage"));
        String expectedResult = "Онлайн‑курсы для профессионалов, дистанционное обучение современным ...";
//      Check that the first result = expected Result
        assertThat(By.cssSelector("#r1-0 span.EKtkFWMYpwzMKOYr0GYm "), expectedResult);
    }
        @Test
        public  void secondTest() {
            log.error("error");
 //           Set kiosk mode
            setUpKiosk();
 //           Open URL
            driver.get(" https://demo.w3layouts.com/demos_new/template_demo/03-10-2020/photoflash-liberty-demo_Free/685659620/web/index.html?_ga=2.181802926.889871791.1632394818-2083132868.1632394818");
            clicker(By.cssSelector("li[data-id='id-1']>span>a>div"));
 //           Wait modal window open and take ref
            String pictureRef = new WebDriverWait(driver, 5)
                    .until(driver -> driver.findElement(By.cssSelector("#fullResImage"))).getAttribute("src");

            String expectedPictureRef = "https://p.w3layouts.com/demos_new/template_demo/03-10-2020/photoflash-liberty-demo_Free/685659620/web/assets/images/p1.jpg";
            Assertions.assertEquals(expectedPictureRef, pictureRef, "Refs are different");
        }
        @Test
    public void thirdTest() throws FileNotFoundException {
        log.debug("Тестовое сообщение");
//      Set incognito and full screen modes
        setUpIncognito();
        setUpFullScreen();
//      Open URL
       driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
       driver.get(" https://otus.ru");
//      Authorization
        auth();
//        Print cookies
       cookie();

    }

    public void cookie() {

        driver.get("https://ya.ru");
        driver.manage().addCookie((new Cookie("Cookie1", "Otus1")));
        driver.manage().addCookie((new Cookie("Cookie2", "Otus2")));
        Cookie cookie = new Cookie("Cooki3", "Otus3");
        driver.manage().addCookie((new Cookie("Cookie4", "Otus4")));
        System.out.println(driver.manage().getCookies());
    }

    private void auth() throws FileNotFoundException {
        driver.findElement(By.cssSelector(".sc-mrx253-0")).click();
        readProperties();
        driver.findElement(By.name("email")).sendKeys(LOGIN);
        driver.findElement(By.cssSelector("[type='password']")).sendKeys(PASSWORD);
        driver.findElement(By.xpath("//button/*[contains(text(),'Войти')]"))
                .click();
    }

        private void clearAndEnter(By by, String text){
        driver.findElement(by).clear();
        driver.findElement(by).sendKeys(text);
    }

    private void assertThat(By by, String text){
        Assertions.assertEquals(text, driver.findElement(by).getText());
    }
       private void clicker(By by){
        driver.findElement(by).click();
    }

    public void readProperties() throws FileNotFoundException {
        try{
            fileInputStream = new FileInputStream(PATH_TO_CONFIG_PROPERTIES);
            prop.load(fileInputStream);

            LOGIN = prop.getProperty("USER_LOGIN");
            PASSWORD = prop.getProperty("USER_PASS");

        }
        catch (IOException e) {
            System.out.println("Ошибка в программе: файл " + PATH_TO_CONFIG_PROPERTIES + " не обнаружен");
            e.printStackTrace();
        }

    }

}


