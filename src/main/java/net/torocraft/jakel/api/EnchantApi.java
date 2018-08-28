package net.torocraft.jakel.api;

import java.util.ArrayList;
import net.minecraft.item.ItemStack;
import net.torocraft.jakel.capabilites.CapabilityItemData;
import net.torocraft.jakel.loot.ItemData;
import net.torocraft.jakel.loot.modifiers.Data;
import net.torocraft.jakel.loot.modifiers.Modifier;

public class EnchantApi {

  public static void enchant(ItemStack item) {
    ItemData data = CapabilityItemData.get(item);
    data.modifiers = new ArrayList<>();
    data.modifiers.add(new Data(Modifier.Type.FIRE, 5));
    data.modifiers.add(new Data(Modifier.Type.FIRE_RESIST, 10));
    data.isMagicalConduit = true;
  }

}
