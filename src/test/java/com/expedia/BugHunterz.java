package com.expedia;

import io.github.bonigarcia.wdm.WebDriverManager;
import localrepository.ExpediaHomePage2;
import localrepository.SearchPage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class BugHunterz {

    static WebDriver driver;
    static WebDriverWait d;
    static ExpediaHomePage2 eh;
    static SearchPage sp;


    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeMethod
    public void navigate() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        eh = new ExpediaHomePage2(driver);
        sp = new SearchPage(driver);
        d = new WebDriverWait(driver, 15);
        driver.get("https://www.expedia.com/");

    }

    public void HamzaUtil(){
        eh.flightsButton().click();
        eh.setDeparture().sendKeys("   chi" + Keys.SHIFT);
        eh.setDeparture().sendKeys(Keys.DOWN, Keys.DOWN);
        eh.setDeparture().sendKeys(Keys.ENTER);

        eh.setDestination().sendKeys("   oma" + Keys.SHIFT);
        eh.setDestination().sendKeys(Keys.DOWN, Keys.DOWN);
        eh.setDestination().sendKeys(Keys.ENTER);


        eh.setDepartureDay().click();
        eh.pickMonthandDay("Dec","11");

        sp.setReturnDay().click();
        eh.pickMonthandDay("Dec","15");

        eh.passengers().click();

        List<WebElement> listOfPassengers = eh.listOfPassengers();
        listOfPassengers.get(0).click();
        listOfPassengers.get(0).click();
        listOfPassengers.get(1).click();

        Select s = new Select(eh.setChildren());
        s.selectByVisibleText("4");
        listOfPassengers.get(2).click();

        s = new Select(eh.setInfants());
        s.selectByVisibleText("1");

        eh.closePassengertab().click();
        eh.Search().click();
    }

    @AfterMethod
    public void tearDown() throws InterruptedException{
        Thread.sleep(2000);
        driver.close();
    }

    @Test(priority = 1)
    public void airportTransportationToHotelByKonstantin() throws InterruptedException{

//      When  I search for airport transportation to specific hotel User should see map of the route
//      with time to destination.

//      1. Locate and click on Things to Do link
        WebElement thingsToDo = driver.findElement(By.id("tab-activity-tab-hp"));
        thingsToDo.click();
        Thread.sleep(2000);
//      2. Due to dynamic structure of the web-site "Airport Transportation" link is dependent on "Event Tickets link
//             that couldn't be displayed all the time.
        List<WebElement> listOfLabels = driver.findElement(By.cssSelector("#gcw-activities-form-hp-activity")).findElements(By.tagName("label"));
        for(WebElement lable: listOfLabels){
            if(lable.getText().equalsIgnoreCase("Airport Transportation")){
                lable.click();
            }
        }
        Thread.sleep(2000);
//      3. Selecting and inserting Airport of arrival
        WebElement fromAirport = driver.findElement(By.cssSelector("#flight-origin-hp-activity"));
        fromAirport.sendKeys("ORD" + Keys.SHIFT);
        Thread.sleep(1000);
        fromAirport.sendKeys(Keys.DOWN, Keys.ENTER);
//      4. Selecting date of arrival
        WebElement flightArrivalDate = driver.findElement(By.cssSelector("#gt-origin-flight-arrival-date-hp-activity"));
        flightArrivalDate.clear();
        flightArrivalDate.sendKeys("09/11/2019");
//      5. Locating and selecting destination Hotel
        WebElement toHotel = driver.findElement(By.cssSelector("#hotel-hp-activity"));
        toHotel.sendKeys("Waldorf Astoria Chicago, Chicago, Illinois");
        toHotel.sendKeys(Keys.DOWN, Keys.ENTER);
//      6. Verifying the result
        WebElement durationToDestination = driver.findElement(By.xpath("//span[@class='map-duration-text alt']"));
        System.out.println(durationToDestination.getText() + " from ORD to Waldorf Astoria, Chicago.");

    }

    @Test(priority = 2)
    public static void searchForCruiseByTimeDestinationByKonstantin() throws InterruptedException{

//      Verifying how many cruises available for selection after specifying destination and date.

//      1. Locate and select Cruises
        WebElement cruisesHeader = driver.findElement(By.id("primary-header-cruise"));
        cruisesHeader.click();
        Thread.sleep(1000);
//      2. Locate and select destination of the cruise
        WebElement goingTo = driver.findElement(By.xpath("//select[@class='cruise-destination gcw-storeable']"));
        Select goingToDropDown = new Select(goingTo);
        Thread.sleep(1000);
        goingToDropDown.selectByVisibleText("Alaska");
//      3. Select date of departure
        WebElement departAsEarlyAs = driver.findElement(By.id("cruise-start-date-cruiselp"));
        departAsEarlyAs.sendKeys("08/11/2019");
        Thread.sleep(2000);
//      4. Submit the requiest
        WebElement submitButton = driver.findElement(By.xpath("//button[@class='btn-primary btn-action gcw-submit ab14933']"));
        submitButton.click();
//      5. Cheching the results of the search
        WebElement howManyCruisesFound = driver.findElement(By.xpath("//h1[@class='cruise-page-header universalFilterAriaHidden header']"));
        System.out.println(howManyCruisesFound.getText() + " found for your search criteria.");

    }

    @Test(priority = 3)
    public static void filteringCarsByTypeByKonstantin() throws InterruptedException{

//      Filtering car's search by car type

//      1. Locating and selscting Car link in header
        WebElement carsHeaderLink = driver.findElement(By.id("primary-header-car"));
        carsHeaderLink.click();
        Thread.sleep(2000);
//      2. Setting pickup lication
        WebElement pickingUpLocation = driver.findElement(By.xpath("//label[@class='text icon-before autocomplete-arrow']"));
        pickingUpLocation.sendKeys("ORD" + Keys.SHIFT);
        Thread.sleep(2000);
        pickingUpLocation.sendKeys(Keys.DOWN, Keys.ENTER);
        Thread.sleep(2000);
//      3. Setting dropping off loction
        WebElement dropingOfLocation = driver.findElement(By.xpath("//input[@class='clear-btn-input gcw-storeable text gcw-carDropOff']"));
        dropingOfLocation.sendKeys("BOS");
        Thread.sleep(2000);
        dropingOfLocation.sendKeys(Keys.DOWN, Keys.ENTER);
//      4. Selecting date of car's pickup
        WebElement pickUpDate = driver.findElement(By.xpath("//input[@id='car-pickup-date-clp']"));
        pickUpDate.sendKeys("07/22/2019");
//      5. Selecting date of car's drop of
        WebElement dropOfDate = driver.findElement(By.xpath("//input[@id='car-dropoff-date-clp']"));
        dropOfDate.sendKeys("08/08/2019");
        Thread.sleep(2000);
//      6. Selecting advanced options for search
        WebElement advancedOptions = driver.findElement(By.linkText("Advanced options"));
        advancedOptions.click();
        Thread.sleep(1000);
//      7. Locating and selecting car type
        WebElement carType = driver.findElement(By.xpath("//select[@name='kind']"));
        Select selectCarDropDown = new Select(carType);
        selectCarDropDown.selectByVisibleText("Minivan");
        Thread.sleep(1000);
//      8. Submitting the request
        WebElement submitButton = driver.findElement(By.id("gcw-submit-car"));
        submitButton.click();

    }


    @Test(priority = 4)
    public void David_ChangeingTheLanguage_Test() throws InterruptedException {
        WebElement supportButton = driver.findElement(By.id("header-support-menu"));
        supportButton.click();
        Thread.sleep(2000);
        WebElement customerSupportButton = driver.findElement(By.cssSelector("#support-cs"));
        customerSupportButton.click();
        Thread.sleep(2000);
        WebElement messageCustomerSupport = driver.findElement(By.xpath("//h3[@class='aside-prompt']"));
        if(messageCustomerSupport.isDisplayed()){
        String messageDisplayed = messageCustomerSupport.getText();
        String expectedMessage = "We're here to help.";
        Assert.assertEquals(messageDisplayed, expectedMessage, "Message displayed is different from requirement");
        } else{
            System.out.println("Expected message is not displayed");
        }

    }


    @Test(priority = 5)
    public void David_RentingACarByModel_Test() throws InterruptedException {
        WebElement carButton = driver.findElement(By.id("tab-car-tab-hp"));
        carButton.click();
        Thread.sleep(3000);
        WebElement pickuplocation = driver.findElement(By.id("car-pickup-hp-car"));
        pickuplocation.sendKeys("Chicago, IL");
        Thread.sleep(1000);
        WebElement dropofflocation = driver.findElement(By.id("car-dropoff-hp-car"));
        dropofflocation.sendKeys("Los Angeles, CA");
        Thread.sleep(3000);
        WebElement startDate = driver.findElement(By.id("car-pickup-date-hp-car"));
        startDate.sendKeys("07/15/2019" + Keys.ENTER);
        Thread.sleep(1000);
    }


    @Test(priority = 6)
    public void David_LookAHotelByGuestRating_Test() throws InterruptedException {
        WebElement hotelsButton = driver.findElement(By.id("tab-hotel-tab-hp"));
        hotelsButton.click();
        WebElement hoteldestination = driver.findElement(By.id("hotel-destination-hp-hotel"));
        hoteldestination.sendKeys("Chicago, Illinois");
        WebElement searchButton = driver.findElement(By.xpath("(//button[@data-gcw-change-submit-text='Search'])[1]"));
        searchButton.click();
        Thread.sleep(2000);
        WebElement GuestRatingRadioButton = driver.findElement(By.id("radio-guestRating-45"));
        GuestRatingRadioButton.click();

    }
    @Test(priority = 7)

    public void editMySearchResults(){
        HamzaUtil();
        //   Once i put my search preferences  i should be able to change it from the same page.

        String cityName = sp.setNewDestination().getAttribute("value");
        sp.setNewDestination().clear();
        sp.setNewDestination().sendKeys("  New");
        sp.setNewDestination().sendKeys(Keys.DOWN, Keys.DOWN, Keys.ENTER);
        sp.submit();


        String newCityName = sp.setNewDestination().getAttribute("value");
        System.out.println(cityName);
        System.out.println(newCityName);
        boolean check = cityName.equals(newCityName);
        Assert.assertFalse(check);
    }

    @Test(priority = 8)
    public void navigateToTheHomePageByClickingExpediaLogo(){
        HamzaUtil();
        //I want to be able to go to home page by clicking the Expedia icon and discart everything.

        String title = driver.getTitle();
        sp.expediaButton().click();

        String title2 = driver.getTitle();
        System.out.println(title);
        System.out.println(title2);
        boolean check2 = title.equals(title2);
        Assert.assertFalse(check2);

    }
    @Test(priority = 9)
    public void lookUpMySearchHistory(){
        HamzaUtil();
        // I want to be able to see the history of my searches.

        sp.setNewDestination().clear();
        sp.setNewDestination().sendKeys("  New");
        sp.setNewDestination().sendKeys(Keys.DOWN, Keys.DOWN, Keys.ENTER);
        sp.submit();

        sp.expediaButton().click();

        eh.searchHistory().click();

        eh.recentSearches().click();

        List<WebElement> listOfSearches = eh.getListOfPackages();

        boolean check = listOfSearches.size()>0;
        Assert.assertTrue(check);
    }

}
