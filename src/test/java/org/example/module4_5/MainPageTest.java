package org.example.module4_5;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.time.Duration;
import java.util.ArrayList;

public class MainPageTest {
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        // Fix the issue https://github.com/SeleniumHQ/selenium/issues/11750
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
        driver.get("https://www.habr.com/");
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    @DisplayName("TestCase_1 - переход на сайт \"Хабр Q&A\"")
    public void goToHabrQA() {
        ArrayList<String> tabs_windows;
        WebElement openList = driver.findElement(By.xpath("//*[text()=\"Открыть список\"]/ancestor::button"));
        openList.click();
        WebElement habrQA = driver.findElement(By.xpath("//a[contains(@href,'qna')]"));
        habrQA.click();
        tabs_windows = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs_windows.get(1));
        WebElement searchInput = driver.findElement(By.xpath("//input[@type=\"search\"]"));
        assertTrue(searchInput.isDisplayed());
    }

    @Test
    @DisplayName("TestCase_2 - смена языка интерфейса на английский и обратно на русский")
    public void switchLang() {
        WebElement optLangRus = driver.findElement(By.xpath("//button[contains(text(),\"Настройка языка\")]"));
        optLangRus.click();
        WebElement isEng = driver.findElement(By.xpath("//input[@id=\"uiEnglish\"]/following-sibling::span"));
        isEng.click();
        WebElement optSaveEng = driver.findElement(By.xpath("//button[text()=\"Save preferences\"]"));
        optSaveEng.click();
        WebElement myFeedEng = driver.findElement(By.xpath("//h1[text()=\"My feed\"]"));
        assertTrue(myFeedEng.isDisplayed());
        WebElement optLangEng = driver.findElement(By.xpath("//button[contains(text(),\"Language settings\")]"));
        optLangEng.click();
        WebElement isRus = driver.findElement(By.xpath("//input[@id=\"uiRussian\"]/following-sibling::span"));
        isRus.click();
        WebElement optSaveRus = driver.findElement(By.xpath("//button[text()=\"Сохранить настройки\"]"));
        optSaveRus.click();
        WebElement myFeedRus = driver.findElement(By.xpath("//h1[text()=\"Моя лента\"]"));
        assertTrue(myFeedRus.isDisplayed());
    }
}
