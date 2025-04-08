package selenium_tests;

import org.junit.jupiter.api.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebTests {
    private WebDriver driver;

    @BeforeEach
    public void setUp(){
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://ademas-fashion.ru/china-clothes/");
    }

    @Nested
    class TestLinks {

        @Test
        @DisplayName("Проверка перехода по ссылке в телеграмм")
        public void telegramLink() {
            System.out.println("Ссылка на переход в телеграм канал");
            System.out.println("----------------------------------------------------------------------------");
            WebElement telegramLink = driver.findElement(By.xpath("//a[@href='https://t.me/tkani_optom_ademas']"));
            String target = telegramLink.getAttribute("target");
            boolean isOpenedInNewTab = "_blank".equals(target);
            System.out.println("Ссылка открывается в новом окне: " + isOpenedInNewTab);
            Assert.assertTrue("Ссылка не открывается в новой вкладке", isOpenedInNewTab);
            System.out.println("\n");
        }

        @Test
        @DisplayName("Проверка перехода по ссылке в каталог")
        public void catalogLink(){
            System.out.println("Ссылка на переход в Каталог");
            System.out.println("----------------------------------------------------------------------------");
            driver.findElement(By.xpath("(//a[@class='dropdown-toggle'])[5]")).click();
            String expectedCatalogLink = "https://ademas-fashion.ru/catalog/";
            String actualCatalogLink = driver.getCurrentUrl();
            if (expectedCatalogLink.equals(actualCatalogLink)){
                System.out.println("Ссылка переходит на страницу Каталога");
            } else{
                System.out.println("Ссылка не переходит на страницу Каталога");
            }
            System.out.println("\n");
        }

        @Test
        @DisplayName("Проверка перехода по ссылке на главную страницу")
        public void mainPageLink(){
            System.out.println("Ссылка на главную страницу");
            System.out.println("----------------------------------------------------------------------------");
            WebElement mainLink = driver.findElement(By.xpath("//a[@class='breadcrumbs__link'][@href='/']"));
            mainLink.click();
            String expectedMainLink = "https://ademas-fashion.ru/";
            String actualMainLink = driver.getCurrentUrl();
            if (expectedMainLink.equals(actualMainLink)){
                System.out.println("Ссылка переходит на главную страницу");
            } else{
                System.out.println("Ссылка не переходит на главную страницу");
            }
        }
    }

    @Nested
    @DisplayName("Проверка тест дизайна")
    class TestAvailabilityAndDesign{

        @Test
        @DisplayName("Проверка фио")
        public void checkFio(){
            WebElement fioInput = driver.findElement(By.id("FIO_FID131"));
            System.out.println("Проверка поля: ФИО");
            System.out.println("----------------------------------------------------------------------------");
            System.out.println("Поле ввода фио отображается: " + fioInput.isDisplayed());
            System.out.println("Поле ввода фио доступно: " + fioInput.isEnabled());
            System.out.println("Цвет рамки поля ввода фио: " + fioInput.getCssValue("border-color"));
            System.out.println("Фоновый цвет поля ввода фио: " + fioInput.getCssValue("background-color"));
            System.out.println("Размер шрифта поля ввода фио: " + fioInput.getCssValue("font-size"));
        }

        @Test
        @DisplayName("Проверка заголовка")
        public void checkTitle(){
            WebElement titlePage = driver.findElement(By.id("pagetitle"));
            System.out.println("Заголовок сайта");
            System.out.println("----------------------------------------------------------------------------");
            System.out.println("Заголовок сайта отображается: " + titlePage.isDisplayed());
            System.out.println("Заголовок сайта доступно: " + titlePage.isEnabled());
            System.out.println("Цвет заголовока сайта: " + titlePage.getCssValue("border-color"));
            System.out.println("Фоновый цвет заголовка сайта: " + titlePage.getCssValue("background-color"));
            System.out.println("Размер шрифта заголовка сайта: " + titlePage.getCssValue("font-size"));
        }

        @Test
        @DisplayName("Проверка кнопки")
        public void checkButton(){
            WebElement priceButton = driver.findElement(By.id("find-prices_form"));
            System.out.println("Кнопка 'узнать цену'");
            System.out.println("----------------------------------------------------------------------------");
            System.out.println("Кнопка 'узнать цену' отображается: " + priceButton.isDisplayed());
            System.out.println("Кнопка 'узнать цену' доступно: " + priceButton.isEnabled());
            System.out.println("Цвет кнопки 'узнать цену': " + priceButton.getCssValue("border-color"));
            System.out.println("Фоновый цвет кнопки 'узнать цену': " + priceButton.getCssValue("background-color"));
            System.out.println("Размер шрифта кнопки 'узнать цену': " + priceButton.getCssValue("font-size"));
            System.out.println(" \n");
        }
    }

    @Nested
    @DisplayName("Проверка валидации")
    class TestValidationOfAttributes{

        @Test
        @DisplayName("Проверка формы - пустая")
        public void checkEmptyForm(){
            System.out.println("Проверка заполненности полей формы: отправка пустой формы");
            WebElement emptyForm = driver.findElement(By.id("fb_close_FID13"));
            emptyForm.click();

            WebElement errorMessage = driver.findElement(By.xpath("(//*[contains(@class, 'afbf_error_text')])"));
            String actualAnswer = errorMessage.getText();
            if (actualAnswer.equals("Это поле обязательно для заполнения")){
                System.out.println("Форма не заполнена");
            }
        }
        @Test
        @DisplayName("Проверка формы - валидная")
        public void checkFilledForm(){
            System.out.println("Проверка заполненности полей формы: отправка заполненной формы");
            WebElement filledForm = driver.findElement(By.xpath("//article[@class='feedback-info__form']"));
            filledForm.findElement(By.xpath(".//div[@class='afbf_item_pole required']//input")).sendKeys("Иванов Иван Иванович");
            filledForm.findElement(By.xpath(".//div[@id='afbf_phone_fid13']//input")).sendKeys("+7 (999) 456-72-36");
            filledForm.findElement(By.xpath(".//div[@id='afbf_metrs_fid13']//input")).sendKeys("10");
            filledForm.findElement(By.xpath(".//div[@id='afbf_question_fid13']//input")).sendKeys("Сколько будет стоить?");

            filledForm.findElement(By.id("fb_close_FID13")).click();

            WebDriverWait wait = new WebDriverWait(driver, 10);
            WebElement okMessage = wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("(//div[@class='mess'])"))));
            if(okMessage.getText().equals("Ваше сообщение было успешно отправлено")){
                System.out.println("Форма отправлена");
            }
        }

        @Test
        @DisplayName("Проверка формы - не валидацое фио")
        public void checkValidationOfFio(){
            System.out.println("Проверка валидации поля фио: ввод цифр");
            WebElement filledForm = driver.findElement(By.xpath("//article[@class='feedback-info__form']"));
            filledForm.findElement(By.xpath(".//div[@class='afbf_item_pole required']//input")).sendKeys("Иван11ов И54ван Ив32анович3");
            filledForm.findElement(By.xpath(".//div[@id='afbf_phone_fid13']//input")).sendKeys("+7 (999) 456-72-36");
            filledForm.findElement(By.xpath(".//div[@id='afbf_metrs_fid13']//input")).sendKeys("10");
            filledForm.findElement(By.xpath(".//div[@id='afbf_question_fid13']//input")).sendKeys("Сколько будет стоить?");
            filledForm.findElement(By.id("fb_close_FID13")).click();

            WebDriverWait wait = new WebDriverWait(driver, 10);
            WebElement okMessage = wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("(//div[@class='mess'])"))));
            if(okMessage.getText().equals("Ваше сообщение было успешно отправлено")){
                System.out.println("В поле 'ФИО' нет проверки на валидацию, можно ввести цифры");
            }
            else{
                System.out.println("В поле 'ФИО' есть проверка на валидацию, нельзя ввести цифры");
            }
        }

        @Test
        @DisplayName("Проверка формы - пустая")
        public void checkValidationOfTelephone(){
            System.out.println("Проверка валидации поля телефон: ввод букв");
            WebElement filledForm = driver.findElement(By.xpath("//article[@class='feedback-info__form']"));
            filledForm.findElement(By.xpath(".//div[@class='afbf_item_pole required']//input")).sendKeys("Иванов Иван Иванович");
            filledForm.findElement(By.xpath(".//div[@id='afbf_phone_fid13']//input")).sendKeys("number of telephone");
            filledForm.findElement(By.xpath(".//div[@id='afbf_metrs_fid13']//input")).sendKeys("10");
            filledForm.findElement(By.xpath(".//div[@id='afbf_question_fid13']//input")).sendKeys("Сколько будет стоить?");
            filledForm.findElement(By.id("fb_close_FID13")).click();

            WebDriverWait wait = new WebDriverWait(driver, 10);
            WebElement okMessage = wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("(//div[@class='mess'])"))));
            if(okMessage.getText().equals("Ваше сообщение было успешно отправлено")){
                System.out.println("В поле 'Телефон' нет проверки на валидацию, можно ввести буквы");
            }
            else{
                System.out.println("В поле 'Телефон' есть проверка на валидацию, нельзя ввести буквы");
            }
        }
    }

    @AfterEach
    public void setDown(){
        driver.quit();
    }
}
