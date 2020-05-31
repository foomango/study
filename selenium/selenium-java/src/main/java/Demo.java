import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Demo {
    public static void main(String[] args) {
        // Create Driver object for chrome browser
        System.setProperty("webdriver.chrome.driver", "chromedriver");
        WebDriver driver = new ChromeDriver();
        driver.get("http://google.com"); // Hit url on the browser
        System.out.println(driver.getTitle()); // validate if your Page title is correct
        System.out.println(driver.getCurrentUrl()); // validate if you are landed on correct url
        //System.out.println(driver.getPageSource());

        driver.get("http://yahoo.com");
        driver.navigate().back();
        driver.navigate().forward();

        driver.close();
    }
}
