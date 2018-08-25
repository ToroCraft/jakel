package net.torocraft.jakel.loot.modifiers;

import net.torocraft.jakel.loot.Stats;

@FunctionalInterface
public interface Modifier {

  void apply(Stats stats, Data data);
}
