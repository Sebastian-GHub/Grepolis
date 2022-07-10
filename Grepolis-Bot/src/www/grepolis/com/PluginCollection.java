package www.grepolis.com;

import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.WebDriver;

import www.grepolis.com.plugins.BotPlugin;
import www.grepolis.com.plugins.VillageFarmer;

public class PluginCollection {
	private static List<BotPlugin> plugins = new LinkedList<BotPlugin>();
	
	public static void load() {
		WebDriver driver = GrepoBot.getDriver();
		plugins.add(new VillageFarmer(driver));
	}
	
	public static void clear() {
		plugins.clear();
	}
	
	public static int listPlugins() {
		int counter = 1;
		for(BotPlugin bp: plugins) {
			System.out.println(counter + ". " + bp.getInfo());
			counter++;
		}
		return plugins.size();
	}
	
	public static void startPlugNum(int i) throws IndexOutOfBoundsException{
		BotPlugin plugin = plugins.get(i-1);
		plugin.startUp();
	}
}
