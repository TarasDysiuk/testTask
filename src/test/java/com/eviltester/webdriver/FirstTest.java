package com.eviltester.webdriver;

import org.apache.commons.exec.LogOutputStream;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import sun.rmi.runtime.Log;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class FirstTest {
    WebDriver driwer;

    @Before
    public void TestcaseStart() {
        System.setProperty("webdriver.gecko.driver", "D:\\selenium\\geckodriver.exe");
        driwer = new FirefoxDriver();
    }

    @Test
    public void FirstTest() {
        driwer.navigate().to("http://google.com");
        driwer.findElement(By.id("lst-ib")).sendKeys("automation");
        driwer.findElement(By.id("lst-ib")).submit();

        WebElement waitForResult = (new WebDriverWait(driwer, 10)).until(ExpectedConditions.presenceOfElementLocated(By.id("resultStats")));

        List<WebElement> searches = driwer.findElements(By.xpath("//*[@id='rso']//h3/a"));

        searches.get(0).click();
        waitForResult = (new WebDriverWait(driwer, 10)).until(ExpectedConditions.presenceOfElementLocated(By.id("firstHeading")));

        Assert.assertTrue("title should start: Automation", driwer.getTitle().startsWith("Automation"));
    }

    @Test
    public void SecondTest() throws InterruptedException {
        String sSearchWord = "harvestai.com";//"testautomationday.com";
        driwer.navigate().to("http://google.com");
        driwer.findElement(By.id("lst-ib")).sendKeys("automation");
        driwer.findElement(By.id("lst-ib")).submit();
        Boolean bFound=false;

        for (int x = 1; x < 6; x = x + 1) {

            WebElement waitForResult = (new WebDriverWait(driwer, 10)).until(ExpectedConditions.presenceOfElementLocated(By.id("resultStats")));
            List<WebElement> searches = driwer.findElements(By.xpath("//*[@id='rso']//h3/a"));
            WebElement NextPage = (new WebDriverWait(driwer, 10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[@id='pnnext']")));

            for (WebElement lnk : searches) {
                System.out.println(lnk.getAttribute("href").toString());
                if (lnk.getAttribute("href").toString().contains(sSearchWord)) {
                    System.out.println("Expected link was found");
                    bFound=true;
                    break;
                }
                else
                {
                    System.out.println("Not found on page "+x);
                }

            }
            if (bFound)
            {
                break;
            }
            NextPage.click();
            Thread.sleep(3000);
        }

            Assert.assertTrue("Expexted link was not found",bFound );

    }

    @After
    public void TestcaseExit() {
        driwer.quit();
    }
}
