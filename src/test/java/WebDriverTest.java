import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.apache.hc.core5.util.Timeout.ofSeconds;

public class WebDriverTest {
    WebDriver driver;
    private Logger log = (Logger) LogManager.getLogger(WebDriverTest.class);
    private final String LOGIN = "towoc78451@sparkroi.com";
    private final String PASSWORD = "Megere11+";
    @BeforeAll
    public static void webDriverSetup(){
        WebDriverManager.chromedriver().setup();
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
    public void thirdTest(){
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

    private void auth(){
        driver.findElement(By.cssSelector(".sc-mrx253-0")).click();
        driver.findElement(By.cssSelector("#__PORTAL__ > div > div > div.sc-1alnis6-1.ejcuap > div.sc-1alnis6-4.iVBbVz > div > div.sc-10p60tv-1.eDzhKh > div.sc-10p60tv-2.bQGCmu > div > div.sc-19qj39o-0.iLmCeO > div > div.sc-rq8xzv-1.hGvqzc.sc-11ptd2v-1.liHMCp > div > input"))
                .sendKeys(LOGIN);
        driver.findElement(By.cssSelector("#__PORTAL__ > div > div > div.sc-1alnis6-1.ejcuap > div.sc-1alnis6-4.iVBbVz > div > div.sc-10p60tv-1.eDzhKh > div.sc-10p60tv-2.bQGCmu > div > div.sc-19qj39o-0.iLmCeO > div > div.sc-rq8xzv-1.hGvqzc.sc-11ptd2v-1-Component.ciraFX > div > input"))
                .sendKeys(PASSWORD);
        driver.findElement(By.cssSelector("#__PORTAL__ > div > div > div.sc-1alnis6-1.ejcuap > div.sc-1alnis6-4.iVBbVz > div > div.sc-10p60tv-1.eDzhKh > div.sc-10p60tv-2.bQGCmu > div > button > div"))
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

}


