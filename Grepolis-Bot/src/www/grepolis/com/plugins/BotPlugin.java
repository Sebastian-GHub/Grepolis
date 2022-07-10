package www.grepolis.com.plugins;

public abstract class BotPlugin {
	String name;
	String version;
	
	public abstract void startUp();
	
	public String getInfo() {
		return "Plugin: "+ name + " Version: " + version;
	}
}
