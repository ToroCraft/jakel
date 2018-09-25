package net.torocraft.jakel.loot.stat;

import net.torocraft.jakel.stats.Stats;

@FunctionalInterface
public interface StatApplier {

  void apply(Stats stats, int amount);
}
