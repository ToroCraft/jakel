package net.torocraft.jakel.api;

import net.minecraft.item.ItemStack;

public class EnchantApi {

  public static void enchant(ItemStack item) {
    item.getOrCreateSubCompound("jakel");
  }

}
