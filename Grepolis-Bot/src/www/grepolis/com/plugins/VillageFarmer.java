package www.grepolis.com.plugins;

import org.openqa.selenium.WebDriver;

public class VillageFarmer extends BotPlugin{
	private WebDriver driver;
	
	public VillageFarmer(WebDriver driver) {
		this.driver = driver;
		this.name = "VillageFarmer";
		this.version = "0.1";
	}
	
	@Override
	public void startUp() {
		//TODO actual code for this startup
		//Ehh just need to wait for my account to get 2 cities
		
		
	}
	
	/**
	 * Get status checks for all available islands and their respective amount of cities you could farm
	 * the villages with. Helps you create a file to save your choices.
	 */
	public void getStatus() {
		//TODO check method description
	}



	
}
