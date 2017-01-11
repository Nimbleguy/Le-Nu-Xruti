package nomble.java.LeNuXruti;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{

	@Override
	public void onEnable(){
		this.getCommand("prefix").setExecutor(this);
		this.getCommand("nick").setExecutor(this);
		this.getCommand("suffix").setExecutor(this);
		this.getCommand("uprefix").setExecutor(this);
		this.getCommand("unick").setExecutor(this);
		this.getCommand("usuffix").setExecutor(this);
		this.getServer().getPluginManager().registerEvents(this, this);
	}

	@Override
	public void onDisable(){
		this.saveConfig();
	}

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event){
		event.setFormat(getNick(event.getPlayer()) + ": %2$s");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(label.startsWith("u") && args.length > 1){
			StringBuilder s = new StringBuilder();
			s.append(args[1]);
			for(int i = 2; i < args.length; i++){
				s.append(" ");
				s.append(args[i]);
			}
			String str = s.toString();
			str = str.replaceAll("[&\\$](?=[\\dabcdefABCDEF])", Character.toString((char)167));
			Player p = this.getServer().getPlayerExact(args[0]);
			if(label.equalsIgnoreCase("uprefix")){
                                this.getConfig().set(p.getUniqueId().toString() + "-prefix", str);
				p.sendMessage("Prefix set to " + str + Character.toString((char)167) + "f.");
			}
			else if(label.equalsIgnoreCase("unick")){
                                this.getConfig().set(p.getUniqueId() + "-nicinacage", str);
				p.sendMessage("Nickname set to " + str + Character.toString((char)167) + "f.");
			}
			else if(label.equalsIgnoreCase("usuffix")){
                                this.getConfig().set(p.getUniqueId() + "-suffix", str);
				p.sendMessage("Suffix set to " + str + Character.toString((char)167) + "f.");
			}
			else{
				return false;
			}
			this.saveConfig();
		}
		else if(sender instanceof Player && args.length > 0){
			StringBuilder s = new StringBuilder();
			s.append(args[0]);
			for(int i = 1; i < args.length; i++){
				s.append(" ");
				s.append(args[i]);
			}
			String str = s.toString();
			str = str.replaceAll("[&\\$](?=[\\dabcdefABCDEF])", Character.toString((char)167));
			Player p = (Player)sender;
			if(label.equalsIgnoreCase("prefix")){
				this.getConfig().set(p.getUniqueId().toString() + "-prefix", str);
				p.sendMessage("Prefix set to " + str + Character.toString((char)167) + "f.");
			}
			else if(label.equalsIgnoreCase("nick")){
				this.getConfig().set(p.getUniqueId() + "-nicinacage", str);
				p.sendMessage("Nickname set to " + str + Character.toString((char)167) + "f.");
			}
			else if(label.equalsIgnoreCase("suffix")){
				this.getConfig().set(p.getUniqueId() + "-suffix", str);
				p.sendMessage("Suffix set to " + str + Character.toString((char)167) + "f.");
			}
			else{
				return false;
			}
			this.saveConfig();
		}
		else if(label.startsWith("u") && args.length > 0){
			Player p = this.getServer().getPlayerExact(args[0]);
			if(label.equalsIgnoreCase("uprefix")){
                                this.getConfig().set(p.getUniqueId().toString() + "-prefix", "");
				p.sendMessage("Prefix reset.");
			}
			else if(label.equalsIgnoreCase("unick")){
                                this.getConfig().set(p.getUniqueId() + "-nicinacage", "");
				p.sendMessage("Nickname reset.");
			}
			else if(label.equalsIgnoreCase("usuffix")){
                                this.getConfig().set(p.getUniqueId() + "-suffix", "");
				p.sendMessage("Suffix reset.");
			}
			else{
				return false;
			}
			this.saveConfig();
		}
		else if(sender instanceof Player){
			Player p = (Player)sender;
			if(label.equalsIgnoreCase("prefix")){
				this.getConfig().set(p.getUniqueId().toString() + "-prefix", "");
				p.sendMessage("Prefix reset.");
			}
			else if(label.equalsIgnoreCase("nick")){
				this.getConfig().set(p.getUniqueId() + "-nicinacage", "");
				p.sendMessage("Nickname reset.");
			}
			else if(label.equalsIgnoreCase("suffix")){
				this.getConfig().set(p.getUniqueId() + "-suffix", "");
				p.sendMessage("Suffix reset.");
			}
			else{
				return false;
			}
			this.saveConfig();
		}
		else{
			return false;
		}
		return true;
	}

	private String getNoNull(String v, String def){
		return v == null ? def : v;
	}

	private String getNick(Player p){
		String pref = Character.toString((char)167) + "f" + this.getConfig().getString(p.getUniqueId().toString() + "-prefix", "") + Character.toString((char)167) + "f";
		String cage = this.getConfig().getString(p.getUniqueId() + "-nicinacage", "") + Character.toString((char)167) + "f";
		if(cage.equals(Character.toString((char)167) + "f")){
			cage = p.getPlayerListName();
		}
		String suff = this.getConfig().getString(p.getUniqueId().toString() + "-suffix", "") + Character.toString((char)167) + "f";
		return (pref + " " + cage + " " + suff).trim();
	}
}
