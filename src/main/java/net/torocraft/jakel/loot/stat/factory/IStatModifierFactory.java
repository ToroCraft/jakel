package net.torocraft.jakel.loot.stat.factory;

import net.torocraft.jakel.loot.stat.StatModifierData;

public interface IStatModifierFactory {

  StatModifierData build(int level);
}
