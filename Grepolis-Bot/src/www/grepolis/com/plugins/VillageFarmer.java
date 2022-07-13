package www.grepolis.com.plugins;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.By.ByClassName;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class VillageFarmer extends BotPlugin implements Runnable{
	private WebDriver driver;
	public int mode;
	
	public VillageFarmer(WebDriver driver) {
		this.driver = driver;
		this.name = "VillageFarmer";
		this.version = "0.1";
	}
	
	@Override
	public void startUp() {
		System.out.println("Select the plugins mode:\n1. Basic mode");
		Scanner s = new Scanner(System.in);
		mode = s.nextInt();
		s.close();

	}
	
	@Override
	public void run() {
		if(mode == 1) {
			basicMode();
		}else {
			System.out.println("Not a mode, terminating");
			System.exit(0);
		}
	}
	
	/**
	 * Get status checks for all available islands and their respective amount of cities you could farm
	 * the villages with. Helps you create a file to save your choices.
	 */
	public void getStatus() {
		//TODO check method description
	}
	
	public void openMenu() {
		Actions builder = new Actions(driver);
		WebElement overview = driver.findElement(By.xpath("/html/body/div[1]/div[14]/div[3]/div"));
		builder.moveToElement(overview).perform();
		WebElement villageOverview = driver.findElement(By.xpath("/html/body/div[8]/div[2]/div/div/ul/li[2]/ul/li[2]/a"));
		villageOverview.click();	
	}
	
	public void basicMode() {
		while(true) {
			openMenu();
			WebElement leftFrame = driver.findElement(By.id("fto_town_wrapper"));
			WebElement rightFrame = driver.findElement(By.id("farm_town_wrapper"));
			WebElement checkBox = leftFrame.findElement(By.cssSelector(".checkbox.select_all"));
			checkBox.click();
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			WebElement button = rightFrame.findElement(By.id("fto_claim_button"));
			button.click();
			try {
				Random rn = new Random();
				int rndmDelay = rn.nextInt(315000 - 300000 + 1) + 300000;
				Thread.sleep(rndmDelay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	
	public void testing() {
		//Debug and testing section starts here:
		//Current testing goal: Find the amount of islands/
		List<WebElement> el = driver.findElements(By.tagName("div"));
		WebElement bauernDörfer = null;
		for(WebElement e : el) {
			if(e.getAccessibleName().equals("Bauerndörfer")) {
				bauernDörfer = e;
				break;
			}
		}
		el.clear();
		//el = bauernDörfer.findElements(By.tagName("div"));
		WebElement sideSelect = bauernDörfer.findElement(By.id("fto_town_wrapper"));
		WebElement subSideSelect = sideSelect.findElement(By.id("fto_town_list"));
		el = subSideSelect.findElements(By.tagName("li"));
		int islands = 0;	
			
		//Lists all elements in the subSideSelect which contains: islands and cities
		//Think of a way to distinguish each from another
		for(WebElement e : el) {
			System.out.println(e.getText());
			System.out.println("");
		}
	}



	
}
