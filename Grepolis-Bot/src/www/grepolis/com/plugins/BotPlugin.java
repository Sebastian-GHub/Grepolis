package www.grepolis.com.plugins;

public abstract class BotPlugin implements Runnable{
	String name;
	String version;
	
	public abstract void startUp();
	public abstract void run();
	
	public String getInfo() {
		return "Plugin: "+ name + " Version: " + version;
	}
	
	public void exitMenu() {
		//TODO
	}
}
