package net.torocraft.jakel.loot.stat;

import net.torocraft.jakel.Jakel;

public class ModifierRange {

  private int min;
  private int max;

  public static ModifierRange fromAvgDmg(int avg) {
    int diff = (int) Math.round(avg * 0.1d);
    int min = avg - diff;
    int max = avg + diff;
    return new ModifierRange(min, max);
  }

  public static ModifierRange fromAvgMultiplier(int avg) {
    int DIFF = 5;
    int MAX = 25;
    if (avg <= DIFF / 2 + 1) {
      return new ModifierRange(1, (int) Math.ceil(avg));
    }
    int min = Math.min(23, Math.round(avg - DIFF / 2));
    int max = Math.min(25, Math.round(avg + DIFF / 2));
    return new ModifierRange(min, max);
  }

  public ModifierRange(int min, int max) {
    this.min = min;
    this.max = max;
  }

  public int roll() {
    return Jakel.rand.nextInt(max - min + 1) + min;
  }

  @Override
  public String toString() {
    return min + " - " + max;
  }
}
