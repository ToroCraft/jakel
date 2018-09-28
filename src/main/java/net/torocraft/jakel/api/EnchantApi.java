package net.torocraft.jakel.api;

import java.util.ArrayList;
import java.util.Random;
import net.minecraft.item.ItemStack;
import net.torocraft.jakel.capabilites.CapabilityItemData;
import net.torocraft.jakel.loot.ItemData;
import net.torocraft.jakel.loot.stat.IStatModifier;
import net.torocraft.jakel.loot.stat.StatModifierData;
import net.torocraft.jakel.loot.stat.StatModifiers;
import net.torocraft.jakel.loot.tick.TickHandlers;

public class EnchantApi {

  // number of magical properties

  // level of property

  // class, weapon or armor

  private static Random rand = new Random();

  public static void enchant(ItemStack item) {
    ItemData data = CapabilityItemData.get(item);
    data.modifiers = new ArrayList<>();
    data.modifiers.add(create(StatModifiers.DAMAGE, 5));
    data.modifiers.add(create(StatModifiers.FIRE_RESIST, 10));
    data.tick = TickHandlers.DAMAGE_BOOST_FROM_NEARBY_MOBS;
    data.isMagicalConduit = true;
  }

  private static StatModifierData randomModifier(int level) {
    StatModifierData data = new StatModifierData();
    data.amount = amount;
    data.type = StatModifiers.values()[rand.nextInt(StatModifiers.values().length)];
    return data;
  }

  private static StatModifierData create(StatModifiers type, int amount) {
    StatModifierData data = new StatModifierData();
    data.amount = amount;
    data.type = type;
    return data;
  }
}
