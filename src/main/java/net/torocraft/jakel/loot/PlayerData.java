package net.torocraft.jakel.loot;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.torocraft.jakel.capabilites.CapabilityItemData;
import net.torocraft.jakel.capabilites.CapabilityPlayerData;
import net.torocraft.jakel.loot.modifiers.Data;
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

    Stats stats = CapabilityPlayerData.get(player).stats;
    for (ItemStack item : inv.armorInventory) {
      applyItem(item, stats);
    }

    for (ItemStack item : inv.offHandInventory) {
      applyItem(item, stats);
    }

    applyItem(inv.mainInventory.get(inv.currentItem), stats);
  }

  private static boolean isMagicalItem (ItemData data) {
    return data != null && data.modifiers != null;
  }

  private static void applyItem(ItemStack item, Stats stats) {
    System.out.println("BEFORE: " + item + "   " + stats);
    ItemData data = CapabilityItemData.get(item);
    if (!isMagicalItem(data)) {
      return;
    }
    for (Data modifier : data.modifiers) {
      modifier.type.apply(stats, modifier);
    }
    System.out.println("BEFORE: " + item + "   " + stats);
  }

}
