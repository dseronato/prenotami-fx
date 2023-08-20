package br.com.dlqs.prenotamifx.thread;

import br.com.dlqs.prenotamifx.player.Mp3Player;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PrenotaThread implements Runnable {
    private WebDriver driver;
    private boolean reservationFound = false;
    private Map<String, Object> vars;
    JavascriptExecutor js;
    private WebDriverWait wait;
    private static final String MESSAGE = "Given the high demand, the places available for the chosen service are sold out";

    private final String username;
    private final String password;
    private final String chromePath;
    private final String musicPath;
    private final String type;

    public PrenotaThread(String username, String password, String chromePath, String musicPath, String type) {
        this.username = username;
        this.password = password;
        this.chromePath = chromePath;
        this.musicPath = musicPath;
        this.type = type;
    }

    @Override
    public void run() {
        initDriver();
        operatePrenotami();
    }

    private void operatePrenotami() {
        By reserveButton;
        if("Passaporte".equals(type))
            reserveButton = By.cssSelector(".odd .button");
        else
            reserveButton = By.cssSelector(".even .button");
        wait.until(ExpectedConditions.presenceOfElementLocated(reserveButton));
        driver.findElement(reserveButton).click();

        try {
            By reservationFailed = By.cssSelector(".jconfirm-content");
            wait.until(ExpectedConditions.presenceOfElementLocated(reservationFailed));
            var value = driver.findElement(reservationFailed).getText();
            driver.findElement(By.cssSelector(".btn")).click();
            System.out.println(value);
            if (!value.isEmpty()) {
                System.out.printf("Reservation failed.%n");
                operatePrenotami();
            } else {
                System.out.printf("Reservation found.%n");
                var source = driver.getPageSource();
                System.out.printf("Response page: %s%n", source);
                reservationFound = true;
                playVictory();
            }
        } catch (TimeoutException e) {
            System.out.println("Reservation found.");
            var source = driver.getPageSource();
            System.err.printf("Error Response page: %s, Error: %s%n", source, e);
            reservationFound = true;
            playVictory();
        }
    }

    private void playVictory() {
        Mp3Player mp3 = new Mp3Player();
        mp3.play(musicPath);
    }

    private void initDriver() {
        var chromeDriver = this.getClass().getClassLoader().getResource(chromePath);
        System.out.println(chromeDriver.getPath());
        System.setProperty("webdriver.chrome.driver",chromeDriver.getPath());
        ChromeOptions options = new ChromeOptions();
        //options.addArguments("--remote-allow-origins=*","--disable-blink-features=AutomationControlled","--user-data-dir=C:/Selenium");
        options.addArguments("--remote-allow-origins=*");
//        options.setExperimentalOption("useAutomationExtension", false);
//        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        driver = new ChromeDriver(options);
        js = (JavascriptExecutor) driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20), Duration.ofSeconds(1));
        vars = new HashMap<String, Object>();
        driver.get("https://prenotami.esteri.it");
        try {
            Thread.sleep(4587L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.manage().window().setSize(new Dimension(945, 1060));
        driver.findElement(By.cssSelector("#login-email")).sendKeys(username);
        try {
            Thread.sleep(453);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.findElement(By.cssSelector("#login-password")).sendKeys(password);
        try {
            Thread.sleep(178);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.findElement(By.cssSelector("#login-form > button")).click();
        try {
            Thread.sleep(233);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.findElement(By.cssSelector("#advanced > span")).click();
    }

    public void finish() {
        try {
            System.out.printf("Finishing prenota execution%n");
//            By disconnect = By.cssSelector("#logoutForm > button");
//            wait.until(ExpectedConditions.presenceOfElementLocated(disconnect));
//            driver.findElement(disconnect).click();
            driver.quit();
        } catch (Exception e) {
            e.printStackTrace();
            driver.quit();
        }
    }

    public boolean isReservationFound() {
        return reservationFound;
    }
}
