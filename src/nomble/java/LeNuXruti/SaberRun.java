package nomble.java.LeNuXruti;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class SaberRun extends BukkitRunnable{
	private ItemStack give;
	private HumanEntity player;

	public SaberRun(HumanEntity p, ItemStack i){
		super();
		player = p;
		give = i;
	}

	@Override
	public void run(){
		if(player instanceof Player){
			((Player)player).updateInventory();
		}
		player.closeInventory();
		player.getWorld().dropItemNaturally(player.getLocation(), give);
	}
}
