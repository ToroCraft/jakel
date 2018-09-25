package net.torocraft.jakel.loot.hit;

import net.torocraft.jakel.stats.Stats;

public enum HitHandlerType {

  DAMAGE((stats, amount) -> {
    stats.damage += amount;
  });

  private static float toPercent(int amount) {
    return ((float) amount) / 100;
  }

  private final Applier applier;

  HitHandlerType(Applier applier) {
    this.applier = applier;
  }

  public void apply(Stats stats, int amount) {
    applier.apply(stats, amount);
  }

  @FunctionalInterface
  public interface Applier {

    void apply(Stats stats, int amount);
  }
}
