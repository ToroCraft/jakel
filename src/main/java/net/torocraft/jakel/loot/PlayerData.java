package net.torocraft.jakel.loot;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.torocraft.jakel.nbt.NbtField;

public class PlayerData {

  @NbtField
  public int mana;

  @NbtField
  public Stats stats = new Stats();

  public PlayerData() {
    stats.thorns = 1;
    stats.wither = 100;
    mana = 0;
  }

  public void update(LivingUpdateEvent event) {
    if (event.getEntity().world.getTotalWorldTime() % 100 != 0) {
      return;
    }
    mana++;
  }

  public void onEquipmentChange(EntityPlayer player) {
    InventoryPlayer inv = player.inventory;
    List<ItemStack> items = new ArrayList<>();

    for (ItemStack item : inv.armorInventory) {
      System.out.println(item);
    }

    for (ItemStack item : inv.mainInventory) {
      System.out.println(item);
    }

    for (ItemStack item : inv.offHandInventory) {
      System.out.println(item);
    }

    System.out.println(inv.currentItem);

  }

}
