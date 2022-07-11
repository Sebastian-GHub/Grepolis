package www.grepolis.com;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import exceptions.FalseLoginCredentialsException;
import exceptions.NotAWorldException;

public class Login {
	private WebDriver driver;
	
	public Login(WebDriver driver) {
		this.driver = driver;
	}
	
	public void login(String a, String b) {
		WebElement username = driver.findElement(By.xpath("/html/body/div[3]/div/div[1]/div[1]/div[1]/div/form/input[1]"));
		WebElement password = driver.findElement(By.xpath("/html/body/div[3]/div/div[1]/div[1]/div[1]/div/form/input[2]"));
		username.sendKeys(a);
		password.sendKeys(b);
		WebElement button = driver.findElement(By.xpath("/html/body/div[3]/div/div[1]/div[1]/div[1]/div/form/button"));
		button.click();
		
	}
	
	/**
	 * 
	 * @param loginTo world you wanna use the bot on.
	 * @throws NotAWorldException If the bot fails to find the world you're looking for
	 * @throws FalseLoginCredentialsException if the bot wasn't able to login to your account
	 */
	public void world(String loginTo) throws NotAWorldException, FalseLoginCredentialsException{
		try {
			WebElement worldContainer = driver.findElement(By.xpath("/html/body/div[2]/div/div[1]/div[1]/div[2]/div[5]/form/div[2]/div/ul"));
			List<WebElement> world = worldContainer.findElements(By.tagName("li"));
			int counter = 0;
			int length = world.size();
			for(WebElement e : world) {
				counter++;
				if(e.getText().equalsIgnoreCase(loginTo)) {
					e.click();
					break;
				}
				if(counter == length) {
					throw new NotAWorldException();
				}
				
			}
			
		}catch (NoSuchElementException e){
			throw new FalseLoginCredentialsException();
		}
		
		
	}
}
