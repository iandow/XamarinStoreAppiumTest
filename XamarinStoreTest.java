package com.saucelabs.appium;

import io.appium.java_client.AppiumDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Simple <a href="https://github.com/appium/appium">Appium</a> test which runs against a local Appium instance deployed
 * with the 'XamarinStore' iPhone project which is included in the Xamarin Studio distribution.
 *
 * @author Ian Downard
 */
public class XamarinStoreTest {

    private AppiumDriver driver;

    private List<Integer> values;

    private static final int MINIMUM = 0;
    private static final int MAXIMUM = 10;

    @Before
    public void setUp() throws Exception {
        System.out.println("[INFO] Beginning test suite.");

        // NOTE: relative path is required here. Full path doesn't work. WTF???
        File appDir = new File(System.getProperty("user.dir"), "../../../../../../Projects/xamarin-store-app-master/XamarinStore.iOS/bin/iPhoneSimulator/Release/");
        File app = new File(appDir, "XamarinStoreiOS.app");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
        capabilities.setCapability("platformVersion", "7.0");
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("deviceName", "iPhone Simulator");
        capabilities.setCapability("app", app.getAbsolutePath());
        driver = new AppiumDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        values = new ArrayList<Integer>();
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

    @Test
    public void testAddToBasket() throws Exception {
        System.out.println("[INFO] Beginning test " + Thread.currentThread().getStackTrace()[1].getMethodName() + "...");


        System.out.println("\twaiting 5s");
        Thread.sleep(5000);

        long start = System.currentTimeMillis();

        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.elementToBeClickable(By.name("Women's C# t-shirt, free")));
        System.out.println("\t[INFO] Women's shirt is present (" + (System.currentTimeMillis() - start) + "s)");
        WebElement mens_shirt_element = driver.findElement(By.name("Men's C# t-shirt, free"));

        // NOTE: an example of selecting an element by name
        WebElement womens_shirt_element = driver.findElement(By.name("Women's C# t-shirt, free"));
        // NOTE: an example of selecting an element by xpath
        WebElement element = driver.findElement(By.xpath("//UIAApplication[1]/UIAWindow[1]/UIATableView[1]/UIATableCell[2]"));
        //INFO: Both of these elements are the same  --ian
        assertTrue(element.isDisplayed());

        System.out.println("\tscrolling down");
        JavascriptExecutor js = driver;
        HashMap<String, String> scrollObject = new HashMap<String, String>();
        scrollObject.put("direction", "down");
        js.executeScript("mobile: scroll", scrollObject);
        Thread.sleep(1000);

// NOTE: I don't seem to see TouchActions update in the simulator:
//        System.out.println("\tTesting touch action");
//        TouchAction x = new TouchAction(driver);
//        x.press(womens_shirt_element).moveTo(mens_shirt_element).release();
//        x.perform();
//
//        System.out.println("\ttouchaction tap");
//        x.tap(womens_shirt_element);
//        //x.perform();
//        driver.performTouchAction(x.tap(womens_shirt_element));

// NOTE: Flick does nothing.
//        System.out.println("\tflicking");
//        HashMap<String, Double> flickObject = new HashMap<String, Double>();
//        flickObject.put("endX", 0.0);
//        flickObject.put("endY", 0.0);
//        flickObject.put("touchCount", 2.0);
//        js.executeScript("mobile: flick", flickObject);

// NOTE: Swiping is broken in iOS7 because of a bug in Apple’s frameworks. For iOS7, use mobile: scroll as a workaround that works for most cases.
// Citation - http://appium.io/slate/en/0.18.x/?java#flick
//      driver.swipe(x2, y2, x1, y1, 2000);

        System.out.println("\twaiting 2s");
        Thread.sleep(2000);
        System.out.println("\tclicking shirt");
        womens_shirt_element.click();
        System.out.println("\twaiting 5s");
        Thread.sleep(5000);

        System.out.println("tapping Add To Basket");
        WebElement button = driver.findElement(By.xpath("//UIAApplication[1]/UIAWindow[1]/UIATableView[1]/UIATableCell[3]"));
        //driver.tap(1, button, 1);
        driver.tap(1,170,531,1);

        System.out.println("\twaiting 5s");
        Thread.sleep(5000);

        WebElement cart_size_indicator = driver.findElement(By.xpath("//UIAApplication[1]/UIAWindow[1]/UIANavigationBar[1]/UIAStaticText[1]"));
        System.out.println("\tcart size = " + cart_size_indicator.getAttribute("value"));

        System.out.println("\twaiting 2s");
        Thread.sleep(2000);

        //size small:
        //UIAApplication[1]/UIAWindow[1]/UIATableView[1]/UIATableCell[2]

        //view cart:
        System.out.println("\tview cart");
        driver.tap(1,296,43,1);
//        WebElement cart_window = driver.findElement(By.xpath("//UIAApplication[1]/UIAWindow[1]/UIATableView[1]"));
        //asert name = "Your basket is empty"
//        WebElement cart_window = driver.findElement(By.xpath("//UIAApplication[1]/UIAWindow[1]/UIATableView[1]"));
        //asert value == "row 1 of 1"

        System.out.println("\twaiting 4s");
        Thread.sleep(4000);

        System.out.println("\tclicking back button");
        WebElement back_button = driver.findElement(By.name("Back"));
        //UIAApplication[1]/UIAWindow[1]/UIANavigationBar[1]/UIAButton[2]
        back_button.click();

        System.out.println("\twaiting 4s");
        Thread.sleep(4000);

        System.out.println("\tclicking back button again");
//        back_button = driver.findElement(By.xpath("//UIAApplication[1]/UIAWindow[1]/UIANavigationBar[1]/UIAButton[1]"));
        back_button = driver.findElement(By.name("UINavigationBarBackIndicatorDefault.png"));
        back_button.click();

// NOTE: Zooming causes app to crash
//        System.out.println("zooming");
//        driver.zoom(womens_shirt_element);

        System.out.println("\twaiting 4s");
        Thread.sleep(4000);

        System.out.println("\tclicking shirt");
        mens_shirt_element.click();
        System.out.println("\twaiting 3s");
        Thread.sleep(3000);

        System.out.println("tapping Add To Basket");
        button = driver.findElement(By.xpath("//UIAApplication[1]/UIAWindow[1]/UIATableView[1]/UIATableCell[3]"));
        //driver.tap(1, button, 1);
        driver.tap(1,170,531,1);

        System.out.println("\twaiting 3s");
        Thread.sleep(3000);

        //view cart
        System.out.println("\tview cart");
        driver.tap(1,296,43,1);

        //remove an item
        System.out.println("Removing an item");
        driver.executeScript("mobile: swipe", new HashMap<String, Double>() {{ put("touchCount", 2.0); put("startX", 290.0); put("startY", 111.0); put("endX", 100.0); put("endY", 111.0); put("duration", 1.0); }});

        System.out.println("\twaiting 3s");
        Thread.sleep(3000);
        driver.tap(1,277,111,1);

        System.out.println("\twaiting 4s");
        Thread.sleep(4000);

        System.out.println("\tCheckout");
        driver.tap(1,170,531,1);

        System.out.println("\twaiting 5s");
        Thread.sleep(5000);

        WebElement passwordfield = driver.findElement(By.xpath("//UIAApplication[1]/UIAWindow[1]/UIAScrollView[1]/UIASecureTextField[1]"));
        passwordfield.sendKeys("PJUG Rocks!!!");
        System.out.println("\twaiting 2s");
        Thread.sleep(2000);
        System.out.println("\tLogin");
        driver.tap(1, 281, 543, 1);

        System.out.println("\twaiting 4s");
        Thread.sleep(4000);
        Alert alert = driver.switchTo().alert();
        Thread.sleep(1000);
        alert.accept();

        passwordfield.clear();
        passwordfield.sendKeys("PJUG Rocks!!!");
        System.out.println("\twaiting 2s");
        Thread.sleep(2000);
        System.out.println("\tLogin");
        driver.tap(1, 281, 543, 1);

        System.out.println("\twaiting 3s");
        Thread.sleep(3000);
        alert = driver.switchTo().alert();
        //assertEquals("Cool title this alert is so cool.", alert.getText());
        Thread.sleep(1000);
        alert.accept();

        // Go back to main page.
        System.out.println("\tclicking back button again");
        back_button = driver.findElement(By.name("UINavigationBarBackIndicatorDefault.png"));
        back_button.click();
        System.out.println("\twaiting 2s");
        Thread.sleep(2000);
        back_button = driver.findElement(By.name("Back"));
        back_button.click();
        System.out.println("\twaiting 2s");
        Thread.sleep(2000);
        back_button = driver.findElement(By.name("UINavigationBarBackIndicatorDefault.png"));
        back_button.click();
        System.out.println("\twaiting 4s");
        Thread.sleep(4000);

// NOTE: Locking the screen doesn't unlock.
//        System.out.println("locking screen");
//        driver.lockScreen(10);
//        Thread.sleep(2000);

        System.out.println("[INFO] Finished test " + Thread.currentThread().getStackTrace()[1].getMethodName() + ".");
    }

}
