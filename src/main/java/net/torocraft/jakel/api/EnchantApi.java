package net.torocraft.jakel.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.entity.EntityLiving;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.torocraft.jakel.capabilites.CapabilityItemData;
import net.torocraft.jakel.loot.ItemData;
import net.torocraft.jakel.loot.stat.ModifierRange;
import net.torocraft.jakel.loot.stat.StatModifierByEquip;
import net.torocraft.jakel.loot.stat.StatModifierData;
import net.torocraft.jakel.loot.stat.StatModifiers;
import net.torocraft.jakel.loot.stat.modifiers.MultiplierModifier;
import net.torocraft.jakel.loot.tick.TickHandlers;
import scala.reflect.internal.Trees.Modifiers;

public class EnchantApi {
  public static int LEVEL_CAP = 64;

  private static Random rand = new Random();

  public static void enchant(ItemStack item, int level, int randomProperties) {
    ItemData data = CapabilityItemData.get(item);

    if (data == null) {
      return;
    }

    EntityEquipmentSlot equipType = EntityLiving.getSlotForItemStack(item);

    data.modifiers = new ArrayList<>();

    int amount = damageRange(level).roll();

    switch(equipType) {
      case MAINHAND:
        data.modifiers.add(create(StatModifiers.damage, amount));
        break;
      case OFFHAND:
        data.modifiers.add(create(StatModifiers.defense, amount / 4d));
        break;
      case FEET:
        data.modifiers.add(create(StatModifiers.defense, amount / 8d));
        break;
      case LEGS:
        data.modifiers.add(create(StatModifiers.defense, amount / 4d));
        break;
      case CHEST:
        data.modifiers.add(create(StatModifiers.defense, amount / 2d));
        break;
      case HEAD:
        data.modifiers.add(create(StatModifiers.defense, amount / 8d));
        break;
    }

    for (int i = 0; i < randomProperties; i++) {
      data.modifiers.add(randomModifier(equipType, level));
    }

    // TODO build extras
    // data.tick = TickHandlers.DAMAGE_BOOST_FROM_NEARBY_MOBS;

    if (equipType.equals(EntityEquipmentSlot.MAINHAND)) {
      data.isMagicalConduit = true;
    }
  }

  private static StatModifierData create(StatModifiers type, double amount) {
    StatModifierData data = new StatModifierData();
    data.type = type;
    data.amount = amount;
    return data;
  }

  private static StatModifierData randomModifier(EntityEquipmentSlot equipType, int level) {
    StatModifierData data = new StatModifierData();

    List<StatModifiers> modifierTypes = StatModifierByEquip.INSTANCE.get(equipType);

    data.type = modifierTypes.get(rand.nextInt(modifierTypes.size()));

    if (data.type.getModifier() instanceof MultiplierModifier) {
      data.amount = 1 + (multiplierRange(level).roll() / 100d);
    } else {
      // TODO this should only be used for damage
      data.amount = damageRange(level).roll();
    }
    return data;
  }

  public static ModifierRange damageRange(int level) {
    return ModifierRange.fromAvgDmg(findAvgDmg(level));
  }

  public static ModifierRange multiplierRange(int level) {
    return ModifierRange.fromAvgMultiplier(findAvgMultiplier(level));
  }

  private static int findAvgMultiplier(int level) {
    double multiplier = (level / (double) LEVEL_CAP) * 25d;
    return MathHelper.clamp((int) Math.round(multiplier), 1, 25);
  }

  private static int findAvgDmg(int level) {
    double l = Math.min(level, LEVEL_CAP);
    double dmg = l * (5 + l);
    if (level > LEVEL_CAP) {
      double highMod = 100d * Math.pow((double) level - LEVEL_CAP, 0.5d);
      dmg += highMod;
    }
    return (int) Math.ceil(dmg);
  }
}
