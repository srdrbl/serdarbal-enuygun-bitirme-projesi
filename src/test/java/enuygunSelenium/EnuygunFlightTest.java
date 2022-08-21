package enuygunSelenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.testng.Assert.assertEquals;

public class EnuygunFlightTest {

    public static void main(String[] args) throws InterruptedException {

        // Bugünün tarihini almak için LocalDate.now() fonksiyonunu kullandım.
        LocalDate now = LocalDate.now();

        // Enuygun'da bilet için bir api isteği yaparken kullandığımız tarih formatına çevirmek için bu formatter pattern'inini kullandım.
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy");

        // İstek atarken kullanacağımız değişkenleri kod tekrarını önlemesi ve dinamik olması amacıyla burada tanımladım.
        String origin = "istanbul";
        String destination = "New York";
        int departureDay = 10;
        int returnDay = 5;
        String provider = "sabre";
        boolean isDirect = true;
        String URL = "https://www.enuygun.com/ucak-bileti/";

        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized", "--disable-notifications");
        ChromeDriver driver = new ChromeDriver(options);

        // WebDriverWait sınıfının kullanıldığı yerde maksimum bekleme süresini 60 saniye olarak tanımladım.
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

        // Departure ve return date değişkenlerini bugünün ve gidiş tarihine yukarıda kaç gün eklenerek oluşturulması isteniyorsa o şekilde oluşturdum.
        LocalDate departureDate = now.plusDays(departureDay);
        LocalDate returnDate = departureDate.plusDays(returnDay);

        // İstek yapılacak URL tanımlaması
        driver.get(URL);

        // Kalkış yerinin seçimi
        driver.findElement(By.id("OriginInput")).sendKeys(origin);
        driver.findElement(By.id("react-autowhatever-OriginInput-section-0-item-0")).click();

        // Gidilecek yerin seçimi
        driver.findElement(By.id("DestinationInput")).sendKeys(destination);
        driver.findElement(By.id("react-autowhatever-DestinationInput-section-0-item-0")).click();

        // Gidiş tarihinin seçimi
        driver.findElement(By.id("DepartureDate")).click();
        driver.findElement(By.xpath("//*[@aria-label=\""+departureDate.format(formatter)+"\"]")).click();

        // Dönüş tarihinin seçimi
        driver.findElement(By.id("ReturnDate")).click();
        driver.findElement(By.xpath("//*[@aria-label=\""+returnDate.format(formatter)+"\"]")).click();

        // isDirect değişkeni true ise Aktarmasız seçeneğini işaretler
        if (isDirect) {
            driver.findElement(By.id("transitFilter")).click();
        }

        // Ucuz bilet bul butonuna tıklar
        driver.findElement(By.xpath("//*[@class=\"primary-btn block\"]")).click();

        // Sayfada gidiş uçuşları yazısını görene kadar bekler, sonrasında aşağıdaki işlemleri yapar
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()=\"Gidiş Uçuşları\"]")));
        // provider değişkeni null değilse ve belirtilen provider'a ait bir gidiş uçuş listelenmiş ise listelenen ilk uçuşu seçer
        if ((provider != null || provider.length() != 0) && driver.findElements(By.xpath("//*[@data-booking-provider='"+provider+"']")).size() > 0) {
            driver.findElement(By.xpath("(//*[@data-booking-provider='"+provider+"'])[1]")).click();
            System.out.println(provider + " 'a ait gidiş uçuşu seçildi.");
        } else {
            // provider değişkeni null veya provider'a ait bir uçuş yoksa ilk sıradaki uçuşu seçer
            driver.findElement(By.xpath("(//span[@class='radioButton'])[1]")).click();
        }

        // Sayfada dönüş uçuşları yazısını görene kadar bekler, sonrasında aşağıdaki işlemleri yapar
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()=\"Dönüş Uçuşları\"]")));
        // provider değişkeni null değilse ve belirtilen provider'a ait bir dönüş uçuş listelenmiş ise listelenen ilk uçuşu seçer
        if ((provider != null || provider.length() != 0) && driver.findElements(By.xpath("//*[@data-booking-provider='"+provider+"']")).size() > 0) {
            driver.findElement(By.xpath("(//*[@data-booking-provider='"+provider+"'])[2]")).click();
            System.out.println(provider + " 'a ait dönüş uçuşu seçildi.");
        } else {
            // provider değişkeni null veya provider'a ait bir uçuş yoksa ilk sıradaki uçuşu seçer
            driver.findElement(By.xpath("(//span[@class='radioButton'])[2]")).click();
        }

        // 1 saniye bekledikten sonra Seç butonuna tıklar
        Thread.sleep(1000);
        driver.findElement(By.id("tooltipTarget_0")).click();

        if (driver.findElement(By.id("passenger-form")).isDisplayed()) {
            System.out.println("Payment page opened.");
        } else {
            System.out.println("Payment page not opened!");
        }

        WebElement contact = driver.findElement(By.xpath("//div[@class='card-header']"));
        assertEquals(contact.getText(), "İletişim Bilgileri");
    }
}
