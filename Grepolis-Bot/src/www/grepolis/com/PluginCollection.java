package www.grepolis.com;

import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.WebDriver;

import www.grepolis.com.plugins.BotPlugin;
import www.grepolis.com.plugins.VillageFarmer;

public class PluginCollection {
	private static List<BotPlugin> plugins = new LinkedList<BotPlugin>();
	private static Thread thread;
	
	public static void load() {
		WebDriver driver = GrepoBot.getDriver();
		plugins.add(new VillageFarmer(driver));
	}
	
	public static void clear() {
		plugins.clear();
	}
	
	public static void listPlugins() {
		int counter = 1;
		System.out.println("Currently installed plugins:");
		for(BotPlugin bp: plugins) {
			System.out.println(counter + ". " + bp.getInfo());
			counter++;
		}
		System.out.println("");
	}
	
	public static void startPlugNum(int i) throws IndexOutOfBoundsException{
		BotPlugin plugin = plugins.get(i-1);
		plugin.startUp();
	}
	
	public static void runPlugNum(int i) {
		BotPlugin plugin = plugins.get(i-1);
		thread = new Thread(plugin);
		thread.start(); 
	}
}
