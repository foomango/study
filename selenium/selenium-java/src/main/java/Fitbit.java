import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.JavascriptExecutor;
import java.util.concurrent.TimeUnit;
import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.text.FieldPosition;
import java.util.Random;

public class Fitbit {
    public static void main(String[] args) {
        String username = System.getenv("username");
        String password = System.getenv("password");
        // Create Driver object for chrome browser
        System.setProperty("webdriver.chrome.driver", "chromedriver");
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        JavascriptExecutor js = (JavascriptExecutor)driver;

        driver.get("https://accounts.fitbit.com/login"); // Hit url on the browser

        WebDriverWait wait=new WebDriverWait(driver, 120);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ember651"))).sendKeys(username);

        //driver.findElement(By.id("ember651")).sendKeys(username);
        driver.findElement(By.id("ember652")).sendKeys(password);
        driver.findElement(By.id("ember692")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("Log"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[href=\"/activities\""))).click();

        Calendar target = Calendar.getInstance();
        Calendar current = Calendar.getInstance();

        target.set(2020, Calendar.JULY, 29);
        //current.set(2020, Calendar.AUGUST, 3);

        Random rand = new Random(8330);

        while (current.compareTo(target) > -1) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(current.getTime());
            current.get(Calendar.DAY_OF_MONTH);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button.running"))).click();

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".log-form-container input[name='date']")));

            StringBuffer stringBuffer = new StringBuffer();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            simpleDateFormat.format(current.getTime(), stringBuffer, new FieldPosition(0));
            String script = String.format("arguments[0].value='%s'", stringBuffer);

            js.executeScript(script, driver.findElement(By.cssSelector(".log-form-container input[name='date']")));


            driver.findElement(By.cssSelector(".log-form-container input[name='startTimeHours']")).sendKeys("7");
            driver.findElement(By.cssSelector(".log-form-container input[name='startTimeMinutes']")).sendKeys(Integer.toString(rand.nextInt(30)));

            driver.findElement(By.cssSelector(".log-form-container input[name='durationMinutes']")).sendKeys(Integer.toString(30 + rand.nextInt(20)));
            driver.findElement(By.cssSelector(".log-form-container input[name='durationSeconds']")).sendKeys(Integer.toString(rand.nextInt(60)));

            driver.findElement(By.cssSelector(".log-form-container input[name='distance']")).sendKeys(Double.toString(6.0 + 0.1 * rand.nextInt(20)));
            driver.findElement(By.cssSelector(".log-actions button[type='submit']")).click();
            current.add(Calendar.DATE, -1);
        }

        StringBuffer stringBuffer = new StringBuffer();
        Date now = new Date();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");
        simpleDateFormat.format(now, stringBuffer, new FieldPosition(0));

        driver.quit();
    }
}
