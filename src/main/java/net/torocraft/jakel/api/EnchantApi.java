package net.torocraft.jakel.api;

import java.util.ArrayList;
import net.minecraft.item.ItemStack;
import net.torocraft.jakel.capabilites.CapabilityItemData;
import net.torocraft.jakel.loot.ItemData;
import net.torocraft.jakel.loot.stat.StatModifierData;
import net.torocraft.jakel.loot.stat.StatModifiers;

public class EnchantApi {

  public static void enchant(ItemStack item) {
    ItemData data = CapabilityItemData.get(item);
    data.modifiers = new ArrayList<>();
    data.modifiers.add(new StatModifierData(StatModifiers.DAMAGE, 5));
    data.modifiers.add(new StatModifierData(StatModifiers.FIRE_RESIST, 10));
    data.isMagicalConduit = true;
  }

}
