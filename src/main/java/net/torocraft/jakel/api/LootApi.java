package net.torocraft.jakel.api;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.torocraft.jakel.capabilites.CapabilityItemData;
import net.torocraft.jakel.items.ItemSpell;
import net.torocraft.jakel.loot.ItemData;
import net.torocraft.jakel.loot.stat.StatModifierData;
import net.torocraft.jakel.stats.Stats;

public class LootApi {

  public static boolean isMagicalItem(ItemData data) {
    return data != null;
  }

  public static boolean isMagicalConduit(ItemStack stack) {
    if (stack == null || stack.isEmpty()) {
      return false;
    }
    ItemData data = CapabilityItemData.get(stack);
    return data != null && data.isMagicalConduit;
  }

  public static void applyItem(ItemStack item, Stats stats) {
    applyItem(CapabilityItemData.get(item), stats);
  }

  public static void applyItem(ItemData item, Stats stats) {
    if (isMagicalItem(item) && item.modifiers != null) {
      item.modifiers.forEach(data -> applyModifier(stats, data));
    }
  }

  public static void applyModifier(Stats stats, StatModifierData data) {
    if (data == null || data.type == null) {
      return;
    }
    data.type.apply(stats, (float) data.amount);
  }

  public static boolean notASpell(ItemStack item) {
    return item == null || item.isEmpty() || !(item.getItem() instanceof ItemSpell);
  }

  public static boolean isASpell(ItemStack item) {
    return !notASpell(item);
  }

  public enum SpellSlot {MAIN, SECONDARY, MAIN_ALT, SECONDARY_ALT}

  public static ItemStack getEquippedSpell(EntityPlayer player, SpellSlot slot) {
    InventoryPlayer inv = player.inventory;
    List<ItemStack> items = new ArrayList<>();
    ItemStack selectedItem = inv.getStackInSlot(5 + slot.ordinal());
    if (notASpell(selectedItem)) {
      return ItemStack.EMPTY;
    }
    return selectedItem;
  }
}
