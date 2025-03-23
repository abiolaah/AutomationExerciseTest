package pages;

import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

public class VideoTutorialsPage {
    private WebDriver driver;

    public VideoTutorialsPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getPageTitle(){
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver.getTitle();
    }

    public String getCurrentUrl(){
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver.getCurrentUrl();
    }
}
