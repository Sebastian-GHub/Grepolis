package www.grepolis.com;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import exceptions.FalseLoginCredentialsException;
import exceptions.NotAWorldException;

public class GrepoBot {
	private static WebDriver driver;
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\chromedriver.exe");
		driver = null;
		try {
			driver = new ChromeDriver();
		}catch (Exception e) {
			System.out.println("Something went wrong. Make sure to place the chromedriver.exe in the following directory: " + System.getProperty("user.dir"+"."));
			System.out.println("Also make sure it's up to date!");
		}
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("https://grepolis.com");
		Scanner s = new Scanner(System.in);
		System.out.print("Username: ");
		String name = s.next();
		System.out.print("Password: ");
		String pass = s.next();
		System.out.print("World: ");
		String world = s.next();
		
		
		
		try {
			Login l = new Login(driver);
			l.login(name, pass);
			Thread.sleep(3000);
			l.world(world);
		} catch (NotAWorldException e) {
			System.out.println("Input world is not a selectable world");
			System.exit(0);
			
		} catch (FalseLoginCredentialsException e) {
			System.out.println("Your username or password is incorrect");
			System.exit(0);
			
		}
		
		
		try {
			PluginCollection.load();
			PluginCollection.listPlugins();
			System.out.print("Please enter the plugins number you wanna use: ");
			int pluginNumber = s.nextInt();
			
			PluginCollection.startPlugNum(pluginNumber);
			PluginCollection.runPlugNum(pluginNumber);
		}catch (InputMismatchException e) {
			System.out.println("Please input a number");
			s.close();
			Thread.sleep(10000);
			System.exit(0);
		}catch(IndexOutOfBoundsException e) {
			System.out.println("Your input number doesn't match a plugin");
			s.close();
			Thread.sleep(10000);
			System.exit(0);
		}
		s.close();

		
		/*
		 * Way to get the amount of wood in the current town. 
		WebElement wood = driver.findElement(By.xpath("/html/body/div[1]/div[6]/div[1]/div[1]"));
		System.out.println(wood.getText());
		*/
	}
	public static WebDriver getDriver() {
		return driver;
	}
}
