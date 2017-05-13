package nomble.java.LeNuXruti;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import net.milkbowl.vault.chat.Chat;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.RegisteredServiceProvider;

public class Main extends JavaPlugin implements Listener{
	private Nick nick = null;
	private Saber saber = null;

	@Override
	public void onEnable(){
		this.getConfig().addDefault("nick", false);
		this.getConfig().addDefault("lightsaber", false);
		this.getConfig().addDefault("sabers", new ArrayList<String>());
		this.getConfig().options().copyDefaults(true);
		this.saveConfig();

		if(this.getConfig().getBoolean("nick", false)){
			nick = getNick();
		}
		if(this.getConfig().getBoolean("lightsaber", false)){
			saber = getSaber();
		}
	}

	@Override
	public void onDisable(){
		this.saveConfig();
	}

	public static ItemStack unbreak(ItemStack s, String n){
		ItemMeta im = s.getItemMeta();
		if(n != null){
			im.setDisplayName(n);
		}
		im.spigot().setUnbreakable(true);
		s.setItemMeta(im);
		return s;
	}

	public static boolean same(ItemStack sa, ItemStack sb){
		if(sa == null || sb == null){
			return false;
		}
		return sa.getType() == sb.getType() && sa.getDurability() == sb.getDurability();
	}

	private Nick getNick(){
		Chat c;
		try{
			RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
			c = rsp.getProvider();
		}
		catch(NoClassDefFoundError e){
			c = null;// Nothing to see here, folks.
		}
		Nick n = new Nick(this, c);
		this.getCommand("prefix").setExecutor(n);
		this.getCommand("nick").setExecutor(n);
		this.getCommand("suffix").setExecutor(n);
		this.getCommand("uprefix").setExecutor(n);
		this.getCommand("unick").setExecutor(n);
		this.getCommand("usuffix").setExecutor(n);
		this.getServer().getPluginManager().registerEvents(n, this);
		return n;
	}

	private Saber getSaber(){
		List<SaberPart> l = new ArrayList<SaberPart>();

		for(String s : this.getConfig().getStringList("sabers")){
			ItemStack g = unbreak(new ItemStack(Material.getMaterial(this.getConfig().getString(s + "-guiname")), 1, (short)this.getConfig().getInt(s + "-guidata")), null);
			ItemStack r = unbreak(new ItemStack(Material.getMaterial(this.getConfig().getString(s + "-resname")), 1, (short)this.getConfig().getInt(s + "-resdata")), null);

			LinkedHashMap<Integer, ItemStack> h = new LinkedHashMap<Integer, ItemStack>();
			List<String> ln = this.getConfig().getStringList(s + "-name");
			List<Short> ld = this.getConfig().getShortList(s + "-data");
			List<Integer> ls = this.getConfig().getIntegerList(s + "-slot");
			for(int i = 0; i < ln.size(); i++){
				h.put(ls.get(i), unbreak(new ItemStack(Material.getMaterial(ln.get(i)), 1, ld.get(i)), null));
			}

			l.add(new SaberPart(g, h, r));
		}

		Saber s = new Saber(this, l);
		this.getServer().getPluginManager().registerEvents(s, this);
		return s;
	}
}
