package www.grepolis.com.plugins;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class VillageFarmer extends BotPlugin{
	private WebDriver driver;
	
	public VillageFarmer(WebDriver driver) {
		this.driver = driver;
		this.name = "VillageFarmer";
		this.version = "0.1";
	}
	
	@Override
	public void startUp() {
		openMenu();
		
		
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
