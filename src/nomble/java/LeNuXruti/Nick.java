package nomble.java.LeNuXruti;

import net.milkbowl.vault.chat.Chat;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.RegisteredServiceProvider;

public class Nick implements Listener, CommandExecutor{

	private Chat chat;
	private JavaPlugin plug;

	public Nick(JavaPlugin p, Chat c){
		chat = c;
		plug = p;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
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
			Player p = plug.getServer().getPlayerExact(args[0]);
			if(label.equalsIgnoreCase("uprefix")){
                                plug.getConfig().set(p.getUniqueId().toString() + "-prefix", str + " ");
				p.sendMessage("Prefix set to " + str + Character.toString((char)167) + "f.");
			}
			else if(label.equalsIgnoreCase("unick")){
                                plug.getConfig().set(p.getUniqueId() + "-nicinacage", str);
				p.sendMessage("Nickname set to " + str + Character.toString((char)167) + "f.");
			}
			else if(label.equalsIgnoreCase("usuffix")){
                                plug.getConfig().set(p.getUniqueId() + "-suffix", " " + str);
				p.sendMessage("Suffix set to " + str + Character.toString((char)167) + "f.");
			}
			else{
				return false;
			}
			plug.saveConfig();
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
				plug.getConfig().set(p.getUniqueId().toString() + "-prefix", str + " ");
				p.sendMessage("Prefix set to " + str + Character.toString((char)167) + "f.");
			}
			else if(label.equalsIgnoreCase("nick")){
				plug.getConfig().set(p.getUniqueId() + "-nicinacage", str);
				p.sendMessage("Nickname set to " + str + Character.toString((char)167) + "f.");
			}
			else if(label.equalsIgnoreCase("suffix")){
				plug.getConfig().set(p.getUniqueId() + "-suffix", " " + str);
				p.sendMessage("Suffix set to " + str + Character.toString((char)167) + "f.");
			}
			else{
				return false;
			}
			plug.saveConfig();
		}
		else if(label.startsWith("u") && args.length > 0){
			Player p = plug.getServer().getPlayerExact(args[0]);
			if(label.equalsIgnoreCase("uprefix")){
                                plug.getConfig().set(p.getUniqueId().toString() + "-prefix", "");
				p.sendMessage("Prefix reset.");
			}
			else if(label.equalsIgnoreCase("unick")){
                                plug.getConfig().set(p.getUniqueId() + "-nicinacage", "");
				p.sendMessage("Nickname reset.");
			}
			else if(label.equalsIgnoreCase("usuffix")){
                                plug.getConfig().set(p.getUniqueId() + "-suffix", "");
				p.sendMessage("Suffix reset.");
			}
			else{
				return false;
			}
			plug.saveConfig();
		}
		else if(sender instanceof Player){
			Player p = (Player)sender;
			if(label.equalsIgnoreCase("prefix")){
				plug.getConfig().set(p.getUniqueId().toString() + "-prefix", "");
				p.sendMessage("Prefix reset.");
			}
			else if(label.equalsIgnoreCase("nick")){
				plug.getConfig().set(p.getUniqueId() + "-nicinacage", "");
				p.sendMessage("Nickname reset.");
			}
			else if(label.equalsIgnoreCase("suffix")){
				plug.getConfig().set(p.getUniqueId() + "-suffix", "");
				p.sendMessage("Suffix reset.");
			}
			else{
				return false;
			}
			plug.saveConfig();
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
		String vpref = chat == null ? "" : chat.getPlayerPrefix(p).replaceAll("&", Character.toString((char)167)) + " ";
		if(vpref.equals(" ")){
			vpref = " ";
		}
		String pref = Character.toString((char)167) + "f" + vpref + plug.getConfig().getString(p.getUniqueId().toString() + "-prefix", "") + Character.toString((char)167) + "f";

		String cage = plug.getConfig().getString(p.getUniqueId() + "-nicinacage", "") + Character.toString((char)167) + "f";
		if(cage.equals(Character.toString((char)167) + "f")){
			cage = Character.toString((char)167) + "7" + p.getPlayerListName() + Character.toString((char)167) + "f";
		}

		String vsuff = chat == null ? "" : " " + chat.getPlayerSuffix(p).replaceAll("&", Character.toString((char)167));
		if(vsuff.equals(" ")){
			vsuff = "";
		}
		String suff = plug.getConfig().getString(p.getUniqueId().toString() + "-suffix", "") + vsuff + Character.toString((char)167) + "f";

		return (pref + cage + suff).trim();
	}
}
