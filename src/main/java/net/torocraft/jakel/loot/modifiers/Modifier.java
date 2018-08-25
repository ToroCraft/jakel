package net.torocraft.jakel.loot.modifiers;

import net.torocraft.jakel.loot.Stats;

public interface Modifier {

  //public float percent;
  //public int amount;

  void apply(Stats stats, Data data);

}
