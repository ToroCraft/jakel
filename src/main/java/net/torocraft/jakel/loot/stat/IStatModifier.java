package net.torocraft.jakel.loot.stat;

import net.torocraft.jakel.stats.Stats;

@FunctionalInterface
public interface IStatModifier {

  void apply(Stats stats, float amount);
}
