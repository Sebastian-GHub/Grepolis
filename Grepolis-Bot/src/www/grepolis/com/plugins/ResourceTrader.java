package www.grepolis.com.plugins;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ResourceTrader extends BotPlugin implements Runnable{
	private WebDriver driver;
	private List<Unit> barracks = new LinkedList<Unit>();
	private List<Unit> port = new LinkedList<Unit>();
	private WebElement trading;
	
	public ResourceTrader(WebDriver driver) {
		this.driver = driver;
		this.name = "Ressource Trader";
		this.version = "0.1";
	}
	
	@Override
	public void startUp() {
		loadBarracks();
		loadPort();
		openTrade();
		Scanner s = new Scanner(System.in);
		System.out.println("In welchem Gebäude möchtest du Einheiten bauen?\n1. Kaserne\n2. Hafen");
		if(s.nextInt()==1) {
			barracksMode(s);
		}else {
			portMode(s);
		}
		s.close();
		
	}

	@Override
	public void run() {
	
	}
	
	public void openTrade() {
		List<WebElement> list = driver.findElements(By.className("own_town"));
		boolean found = false;
		WebElement city = null;
		WebElement contextMenu = null;
		while(!found) {
			city = list.remove(0);
			String id = city.getAttribute("id");
			id = id.replace("town_flag_", "");
			id = "town_"+id;
			city = driver.findElement(By.id(id));
			try {
				city.click();
			}catch(ElementNotInteractableException e) {
				continue;
			}
			contextMenu = driver.findElement(By.id("context_menu"));
			List<WebElement> amountOfContext = contextMenu.findElements(By.className("context_icon"));
			int i = amountOfContext.size();
			if(i == 4) {
				found = true;
			}
			
		}
		city.sendKeys(Keys.ARROW_RIGHT);
		contextMenu.findElement(By.id("jump_to")).click();
		city.click();
		contextMenu = driver.findElement(By.id("context_menu"));
		trading = contextMenu.findElement(By.id("trading"));
		sleep(1500);
		trading.click();
		trading = driver.findElement(By.id("trade_tab"));
		
	}
	
	public void barracksMode(Scanner s) {
		int counter = 1;
		System.out.println("Welche Einheit möchtest du produzieren?");
		for (Unit u : barracks) {
			System.out.println(counter+ ". " +u.getName());
			counter++;
		}
		int i = s.nextInt();
		double[] ratio = barracks.get(i-1).getRatioArr();
		inputResources(ratio);	
	}
	
	public void portMode(Scanner s) {
		int counter = 1;
		System.out.println("Welche Einheit möchtest du produzieren?");
		for (Unit u : port) {
			System.out.println(counter+ ". " +u.getName());
			counter++;
		}
		int i = s.nextInt();
		double[] ratio = port.get(i-1).getRatioArr();
		inputResources(ratio);	
	}
	
	public void inputResources(double[] ratio) {
		boolean done = false;
		while(!done) {
			try {
				trading = driver.findElement(By.id("trade_tab"));
				int woodOld = Integer.parseInt(driver.findElement(By.className("ui_resources_bar")).findElement(By.cssSelector(".indicator.wood")).findElement(By.cssSelector(".amount.ui-game-selectable")).getText());
				int stoneOld = Integer.parseInt(driver.findElement(By.className("ui_resources_bar")).findElement(By.cssSelector(".indicator.stone")).findElement(By.cssSelector(".amount.ui-game-selectable")).getText());
				int ironOld = Integer.parseInt(driver.findElement(By.className("ui_resources_bar")).findElement(By.cssSelector(".indicator.iron")).findElement(By.cssSelector(".amount.ui-game-selectable")).getText());
				sleep(3000);
				int capacity = Integer.parseInt(trading.findElement(By.className("curr")).getText());
				String[] resources = new String[3];
				resources[0] = ""+capacity*ratio[0];
				resources[1] = ""+capacity*ratio[1];
				resources[2] = ""+capacity*ratio[2];
				//This is resource selection and input for their respective values move to own method so it can be invoked for whatever unit using the parameters x,y,z for wood stone iron
				WebElement wood = trading.findElement(By.id("trade_type_wood"));
				sleep(1500);
				wood.findElement(By.tagName("input")).sendKeys(resources[0]);;
				
				WebElement stone = trading.findElement(By.id("trade_type_stone"));
				stone.findElement(By.tagName("input")).sendKeys(resources[1]);
				
				WebElement iron = trading.findElement(By.id("trade_type_iron"));
				iron.findElement(By.tagName("input")).sendKeys(resources[2]);
				sleep(500);
				trading.findElement(By.cssSelector(".btn_trade_button.button_new")).findElement(By.className("js-caption")).click();
				sleep(2000);
				int woodNew = Integer.parseInt(driver.findElement(By.className("ui_resources_bar")).findElement(By.cssSelector(".indicator.wood")).findElement(By.cssSelector(".amount.ui-game-selectable")).getText());
				int stoneNew = Integer.parseInt(driver.findElement(By.className("ui_resources_bar")).findElement(By.cssSelector(".indicator.stone")).findElement(By.cssSelector(".amount.ui-game-selectable")).getText());
				int ironNew = Integer.parseInt(driver.findElement(By.className("ui_resources_bar")).findElement(By.cssSelector(".indicator.iron")).findElement(By.cssSelector(".amount.ui-game-selectable")).getText());
				
				if(woodNew>=woodOld && stoneNew>=stoneOld && ironNew>=ironOld) {
					done = true;
					System.out.println("Done!");
				}else {
					driver.findElement(By.className("town_name_area")).findElement(By.cssSelector(".btn_next_town.button_arrow.right")).click();
					sleep(500);
					
				}
			}catch(Exception e) {
				driver.findElement(By.className("town_name_area")).findElement(By.cssSelector(".btn_next_town.button_arrow.right")).click();
				sleep(500);
			}
		}
		
		
	}
	
	public void sleep(int x) {
		try {
			Thread.sleep(x);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void loadBarracks() {
		barracks.add(new Unit("Schwertkämpfer", 0.52, 0.0, 0.48));
		barracks.add(new Unit("Schleuderer", 0.28, 0.51, 0.21));
	}
	
	private void loadPort() {
		port.add(new Unit("Transportboot", 0.36, 0.36, 0.28));
		port.add(new Unit("Bireme", 0.48, 0.42, 0.1));
		port.add(new Unit("Feuerschiff", 0.54, 0.13, 0.33));
	}
}


class Unit{
	private String name;
	private double ratioWood;
	private double ratioStone;
	private double ratioIron;
	
	public Unit(String name, double x, double y, double z) {
		this.name = name;
		this.ratioWood = x;
		this.ratioStone = y;
		this.ratioIron = z;
	}
	
	public String getName() {
		return this.name;
	}
	
	public double[] getRatioArr() {
		double[] arr = {ratioWood, ratioStone, ratioIron};
		return arr;
	}
}