package net.torocraft.jakel.loot;

public enum ModifierType {

  DAMAGE((stats, amount) -> {
    stats.damage += amount;
  }),

  FIRE((stats, amount) -> {
    stats.fire += amount;
  }),

  WITHER((stats, amount) -> {
    stats.wither += amount;
  }),

  LIGHTNING((stats, amount) -> {
    stats.lightning += amount;
  }),

  COLD((stats, amount) -> {
    stats.cold += amount;
  }),

  HOLY((stats, amount) -> {
    stats.holy += amount;
  }),

  XP((stats, amount) -> {
  }),

  MOVEMENT_SPEED((stats, amount) -> {
  }),

  JUMP((stats, amount) -> {
  }),

  HP((stats, amount) -> {
  }),

  REGEN((stats, amount) -> {
  }),

  DEFENSE((stats, amount) -> {
  }),

  INCREASED_XP_GAIN((stats, amount) -> {
  }),

  CRITICAL_HIT_CHANCE((stats, amount) -> {
  }),

  CRITICAL_HIT_DAMAGE((stats, amount) -> {
  }),

  SOCKET((stats, amount) -> {
  }),

  MANA_COST((stats, amount) -> {
  }),

  FIRE_RESIST((stats, amount) -> {
    stats.fireResist += toPercent(amount);
  }),

  WITHER_RESIST((stats, amount) -> {
  }),

  LIGHTNING_RESIST((stats, amount) -> {
  }),

  COLD_RESIST((stats, amount) -> {
  }),

  HOLY_RESIST((stats, amount) -> {
  }),

  CONTROL_IMPAIRMENT((stats, amount) -> {
  }),

  ATTACK_SPEED((stats, amount) -> {
  }),

  AREA_DAMAGE((stats, amount) -> {
  }),

  LIFE_PER_HIT((stats, amount) -> {
  }),

  LIFE_PER_KILL((stats, amount) -> {
  }),

  THORNS((stats, amount) -> {
    stats.thorns += amount;
  });

  private static float toPercent(int amount) {
    return ((float) amount) / 100;
  }

  private final Applier applier;

  ModifierType(Applier applier) {
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
