package net.torocraft.jakel.loot.stat.factory;

import net.torocraft.jakel.loot.stat.StatModifierData;
import net.torocraft.jakel.loot.stat.StatModifiers;

public class WeaponModifierFactory implements IStatModifierFactory {

  private static final StatModifiers[] TYPES = {
      StatModifiers.DAMAGE
  };

  @Override
  public StatModifierData build(int level) {
    StatModifierData data = new StatModifierData();
    data.amount = amount;
    data.type = StatModifiers.values()[rand.nextInt(StatModifiers.values().length)];
    return data;
  }
}
