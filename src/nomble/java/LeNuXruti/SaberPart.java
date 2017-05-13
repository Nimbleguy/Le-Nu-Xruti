package nomble.java.LeNuXruti;

import java.util.LinkedHashMap;

import org.bukkit.inventory.ItemStack;

public class SaberPart{

	private ItemStack gui;
	private LinkedHashMap<Integer, ItemStack> items;
	private ItemStack result;

	public SaberPart(ItemStack g, LinkedHashMap<Integer, ItemStack> i, ItemStack r){
		gui = g;
		items = i;
		result = r;
	}

	public ItemStack getGui(){
		return gui;
	}

	public ItemStack getResult(){
		return result;
	}

	public int getLength(){
		return items.size();
	}

	public ItemStack getSlot(int i){
		return items.get(i);
	}

	public int getLocation(int i){
		return (Integer)items.keySet().toArray()[i];
	}

	public ItemStack getItem(int i){
		return (ItemStack)items.values().toArray()[i];
	}
}
