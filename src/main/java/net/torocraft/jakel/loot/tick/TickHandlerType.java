package net.torocraft.jakel.loot.tick;

import net.torocraft.jakel.stats.Stats;

public enum TickHandlerType {

  DAMAGE((stats, amount) -> {
    stats.damage += amount;
  });

  private static float toPercent(int amount) {
    return ((float) amount) / 100;
  }

  private final Applier applier;

  TickHandlerType(Applier applier) {
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
