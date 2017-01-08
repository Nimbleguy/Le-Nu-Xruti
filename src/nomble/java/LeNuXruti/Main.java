package nomble.java.LeNuXruti;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class Main extends JavaPlugin implements Listener{
	public HashMap<UUID, String> suffix = new HashMap<UUID, String>();
	public HashMap<UUID, String> nick = new HashMap<UUID, String>();
	public HashMap<UUID, String> prefix = new HashMap<UUID, String>();
	@Override
	public void onEnable(){
		this.getCommand("prefix").setExecutor(this);
		this.getCommand("nick").setExecutor(this);
		this.getCommand("suffix").setExecutor(this);
		this.getCommand("uprefix").setExecutor(this);
		this.getCommand("unick").setExecutor(this);
		this.getCommand("usuffix").setExecutor(this);
	}

	@Override
	public void onDisable(){}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event){
		setNick(event.getPlayer());
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
			Player p = this.getServer().getPlayerExact(args[0]);
			if(label.equalsIgnoreCase("uprefix")){
				prefix.put(p.getUniqueId(), str);
			}
			else if(label.equalsIgnoreCase("unick")){
				nick.put(p.getUniqueId(), str);
			}
			else if(label.equalsIgnoreCase("usuffix")){
				suffix.put(p.getUniqueId(), str);
			}
			else{
				return false;
			}
			setNick(p);
		}
		else if(sender instanceof Player && args.length > 0){
			StringBuilder s = new StringBuilder();
			s.append(args[0]);
			for(int i = 1; i < args.length; i++){
				s.append(" ");
				s.append(args[i]);
			}
			String str = s.toString();
			Player p = (Player)sender;
			if(label.equalsIgnoreCase("prefix")){
				prefix.put(p.getUniqueId(), str);
			}
			else if(label.equalsIgnoreCase("nick")){
				nick.put(p.getUniqueId(), str);
			}
			else if(label.equalsIgnoreCase("suffix")){
				suffix.put(p.getUniqueId(), str);
			}
			else{
				return false;
			}
			setNick(p);
		}
		else{
			return false;
		}
		return true;
	}

	private String getNoNull(String v, String def){
		return v == null ? def : v;
	}

	private void setNick(Player p){
		String pref = "[" + getNoNull(prefix.get(p.getUniqueId()), "") + "]";
		String cage = "(" + getNoNull(nick.get(p.getUniqueId()), "") + ")";
		if(cage == "()"){
			cage = p.getPlayerListName();
		}
		String suff = getNoNull(suffix.get(p.getUniqueId()), "");
		p.setDisplayName(pref + cage + suff);
	}
}
