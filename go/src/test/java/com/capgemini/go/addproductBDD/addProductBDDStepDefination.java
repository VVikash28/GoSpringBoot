package com.capgemini.go.addproductBDD;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class addProductBDDStepDefination {
	String chromeDriverPath = "C:\\Users\\agchandr\\Documents\\GitHub\\GoSpringBoot\\chromedriver.exe";
	WebDriver driver = null;

	@Given("^I am on Add Product Form in Great Outdoors Product page$")
	public void goToGoAdminOnChrome() {
		System.setProperty("webdriver.chrome.driver", chromeDriverPath);
		driver = new ChromeDriver();
		driver.navigate().to("http://localhost:4200/Product/allproducts/");
		driver.findElement(By.id("add-product")).click();
		System.out.println("Title: " + driver.getTitle());
	}
	
	
	@When("^I enter product id as \"(.*)\"$")
	public void enterProductId (String productId) {
		System.out.println ("Product Id entered: " + productId);
		driver.findElement(By.id("add-prod-id")).sendKeys(productId);
	}
	
	@When("^I enter product name as \"(.*)\"$")
	public void enterProductName (String productName) {
		System.out.println ("Product Name entered: " + productName);
		driver.findElement(By.id("add-prod-name")).sendKeys(productName);
	}
	
	

	@When("^I enter product brand as \"(.*)\"$")
	public void enterProductBrand (String productBrand) {
		System.out.println ("Product Brand entered: " + productBrand);
		driver.findElement(By.id("add-prod-brand")).sendKeys(productBrand);
		
	}
	
	@When("^I enter product dimension as \"(.*)\"$")
	public void enterProductDimension (String productDim) {
		System.out.println ("Product Dimension entered: " + productDim);
		driver.findElement(By.id("add-prod-dim")).sendKeys(productDim);
		
	}
	
	@When("^I enter product Specification as \"(.*)\"$")
	public void enterProductSpecification (String productspec) {
		System.out.println ("Product Specification entered: " + productspec);
		driver.findElement(By.id("add-prodspec")).sendKeys(productspec);
		
	}
	
	
	@When("^I enter quantity as \"(.*)\"$")
	public void enterQuantity (String productQty) {
		System.out.println ("Product Quantity entered: " + productQty);
		driver.findElement(By.id("add-prod-qty")).sendKeys(productQty);
		
	}
	
	@When("^I enter price as \"(.*)\"$")
	public void enterProductPrice (String price) {
		System.out.println ("Product price entered: " + price);
		driver.findElement(By.id("add-prod-price")).sendKeys(price);
		
	}
	
	@When("^I  select Category as \"(.*)\"$")
	public void enterCategory (String category) {
		System.out.println ("Product category entered: " + category);
		driver.findElement(By.id("add-prod-cat")).sendKeys(category);
		
	}
	
	@When("^I enter color as \"(.*)\"$")
	public void enterProductColor (String color) {
		System.out.println ("Product color entered: " + color);
		driver.findElement(By.id("add-prod-col")).sendKeys(color);
		
	}
	
	@When("^I click on submit button$")
	public void clickSubmitButton () {
		System.out.println ("Add Product button clicked");
		WebElement we = driver.findElement(By.xpath("//*[@id=\"addprodbtn\"]"));
		we.click();
	
	}
	
	@Then("^\"(.*)\" and \"(.*)\" Add Product Success Message Popped Up$")
    public void AddProductSuccess(String productId, String productName) throws InterruptedException {
        String Message = driver.findElement(By.xpath("//*[@id=\"msg\"]")).getText();
        String msg = "PRODUCT("+productName.toUpperCase()+") PRODUCT ID : "+productId.toUpperCase()+" HAS BEEN SUCCESSFULLY REGISTERED" ;
        assertEquals(Message, msg);
	}
	
	@Then("Add Product Failure Message Popped Up$")
    public void AddProductFailure() throws InterruptedException {
        String Message = driver.findElement(By.xpath("//*[@id=\"err\"]")).getText();
        String msg = "ERROR IN ADDING PRODUCT >> PRODUCT ID ALREADY EXISTS" ;
        assertEquals(Message, msg);
	}
	
	
	
	
	
	@After
	public void close() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Closing the driver.");
		if (driver != null) {
			driver.quit();
		}
	}
}
