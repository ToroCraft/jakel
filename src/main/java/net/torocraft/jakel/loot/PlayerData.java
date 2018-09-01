package net.torocraft.jakel.loot;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.torocraft.jakel.api.LootApi;
import net.torocraft.jakel.capabilites.CapabilityPlayerData;
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

  // add tooltip to magical items

  // remove magical items left right click when spell is slotted in the corresponding hot bar slot

  // create spell item and inventory

  public void update(LivingUpdateEvent event) {
    if (event.getEntity().world.getTotalWorldTime() % 100 != 0) {
      return;
    }

    // keep cool downs by inventory slot in hot bar, 5 to 8

    // spells will have two inventory slots each, a rune and passive skill

    // only armor can change player stats, spells will only be able affect that one spell

    // scan through items, handle cool downs, sync data to client on important change

    mana++;
  }

  public void onEquipmentChange(EntityPlayer player) {
    InventoryPlayer inv = player.inventory;
    List<ItemStack> items = new ArrayList<>();

    Stats stats = new Stats();
    for (ItemStack item : inv.armorInventory) {
      LootApi.applyItem(item, stats);
    }

    for (ItemStack item : inv.offHandInventory) {
      LootApi.applyItem(item, stats);
    }

    LootApi.applyItem(inv.mainInventory.get(inv.currentItem), stats);

    System.out.println(stats);

    CapabilityPlayerData.get(player).stats = stats;
    return false;
  }

}
