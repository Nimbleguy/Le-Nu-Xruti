package nomble.java.LeNuXruti;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Saber implements Listener{
	private final ItemStack larrow;
	private final ItemStack rarrow;

	private List<SaberPart> part;
	private JavaPlugin plug;
	private Inventory[] inv;

	public Saber(JavaPlugin p, List<SaberPart> l){
		part = l;
		plug = p;
		larrow = Main.unbreak(new ItemStack(Material.IRON_AXE, 1, (short)5), "Previous Lightsaber");
		rarrow = Main.unbreak(new ItemStack(Material.IRON_AXE, 1, (short)4), "Next Lightsaber");

		inv = new Inventory[l.size()];
		for(int i = 0; i < inv.length; i++){
			inv[i] = Bukkit.createInventory(null, 27, "Lightsaber Forge");
			inv[i].setItem(0, Main.unbreak(l.get(i).getGui(), ""));
			inv[i].setItem(11, larrow);
			inv[i].setItem(12, rarrow);
			inv[i].setItem(18, Main.unbreak(new ItemStack(Material.IRON_AXE, 1, (short)10), ""));
		}
	}

	@EventHandler
	public void onClick(PlayerInteractEvent event){
		if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
			if(event.getClickedBlock().getType().equals(Material.ENDER_PORTAL_FRAME)){
				for(int x = event.getClickedBlock().getX() - 1; x < event.getClickedBlock().getX() + 2; x++){
					for(int z = event.getClickedBlock().getZ() - 1; z < event.getClickedBlock().getZ() + 2; z++){
						if(!event.getClickedBlock().getWorld().getBlockAt(x, event.getClickedBlock().getY(), z).getType().equals(Material.BARRIER)){
							if(!(x == event.getClickedBlock().getX() && z == event.getClickedBlock().getZ())){
								return;
							}
						}
					}
				}

				Inventory i = Bukkit.createInventory(null, 27, "Lightsaber Forge");
				i.setContents(inv[0].getStorageContents());
				event.getPlayer().openInventory(i);
				event.setCancelled(true);
			}
			else if(event.getClickedBlock().getType().equals(Material.BARRIER)){
				for(int x = event.getClickedBlock().getX() - 1; x < event.getClickedBlock().getX() + 2; x++){
					for(int z = event.getClickedBlock().getZ() - 1; z < event.getClickedBlock().getZ() + 2; z++){
						if(event.getClickedBlock().getWorld().getBlockAt(x, event.getClickedBlock().getY(), z).getType().equals(Material.ENDER_PORTAL_FRAME)){
							Block b = event.getClickedBlock().getWorld().getBlockAt(x, event.getClickedBlock().getY(), z);
							PlayerInteractEvent pie = new PlayerInteractEvent(event.getPlayer(), event.getAction(), event.getItem(), b, event.getBlockFace(), event.getHand());
							onClick(pie);
							if(pie.isCancelled()){
								event.setCancelled(true);
							}
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void onMove(InventoryClickEvent event){
		if(event.getRawSlot() < 27 && event.getClickedInventory().getItem(0) != null){
			int s = -1;
			for(int i = 0; i < part.size(); i++){
				if(event.getClickedInventory().getItem(0).equals(part.get(i).getGui())){
					s = i;
					break;
				}
			}
			if(s == -1){
				return;
			}

			boolean b = true;
			for(int i = 0; i < part.get(s).getLength(); i++){
				if(part.get(s).getLocation(i) == event.getSlot()){
					b = false;
					break;
				}
			}
			if(b){
				event.setCancelled(true);

				if(event.getSlot() == 11 && s > 0){
					s--;
					event.getClickedInventory().setItem(0, part.get(s).getGui());
				}
				else if(event.getSlot() == 12 && s < part.size() - 1){
					s++;
					event.getClickedInventory().setItem(0, part.get(s).getGui());
				}
			}

			b = true;
			for(int i = 0; i < part.get(s).getLength(); i++){
				ItemStack is = event.getClickedInventory().getItem(part.get(s).getLocation(i));
				if(event.getSlot() == part.get(s).getLocation(i)){
					is = event.getCursor();
				}
				if(is == null || !is.equals(part.get(s).getItem(i))){
					b = false;
				}
			}
			if(b){
				for(int i = 0; i < part.get(s).getLength(); i++){
					event.getClickedInventory().setItem(part.get(s).getLocation(i), new ItemStack(Material.AIR, 0, (short)0));
				}
				event.getWhoClicked().setItemOnCursor(new ItemStack(Material.AIR, 0, (short)0));

				BukkitRunnable r = new SaberRun(event.getWhoClicked(), part.get(s).getResult());
				r.runTask(plug);
			}
		}
	}

	@EventHandler
	public void onClose(InventoryCloseEvent event){
		if(event.getInventory().getItem(0) != null){
			int s = -1;
			for(int i = 0; i < part.size(); i++){
				if(event.getInventory().getItem(0).equals(part.get(i).getGui())){
					s = i;
					break;
				}
			}
			if(s == -1){
				return;
			}

			for(int i = 0; i < part.get(s).getLength(); i++){
				ItemStack is = event.getInventory().getItem(part.get(s).getLocation(i));
				if(is != null){
					event.getPlayer().getWorld().dropItemNaturally(event.getPlayer().getLocation(), is);
				}
			}
		}
	}
}
