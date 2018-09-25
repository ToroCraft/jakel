package net.torocraft.jakel.loot.stat;

import net.torocraft.jakel.stats.Stats;

public enum StatModifierType {

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

  POISON((stats, amount) -> {
    stats.poison += amount;
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
    stats.defense += amount;
  }),

  INCREASED_XP_GAIN((stats, amount) -> {

  }),

  CRITICAL_HIT_CHANCE((stats, amount) -> {
    stats.criticalHitChance += amount;
  }),

  CRITICAL_HIT_DAMAGE((stats, amount) -> {
    stats.criticalHitDamage += amount;
  }),

  SOCKET((stats, amount) -> {

  }),

  MANA_COST((stats, amount) -> {

  }),

  FIRE_RESIST((stats, amount) -> {
    stats.fireResist += toPercent(amount);
  }),

  WITHER_RESIST((stats, amount) -> {
    stats.witherResist += toPercent(amount);
  }),

  LIGHTNING_RESIST((stats, amount) -> {
    stats.lightningResist += toPercent(amount);
  }),

  COLD_RESIST((stats, amount) -> {
    stats.coldResist += toPercent(amount);
  }),

  HOLY_RESIST((stats, amount) -> {
    stats.holyResist += toPercent(amount);
  }),

  CONTROL_IMPAIRMENT((stats, amount) -> {
  }),

  ATTACK_SPEED((stats, amount) -> {

  }),

  AREA_DAMAGE((stats, amount) -> {
    stats.areaDamage += amount;
  }),

  LIFE_PER_HIT((stats, amount) -> {
    stats.lifePerHit += amount;
  }),

  LIFE_PER_KILL((stats, amount) -> {
    stats.lifePerKill += amount;
  }),

  THORNS((stats, amount) -> {
    stats.thorns += amount;
  });

  private static float toPercent(int amount) {
    return ((float) amount) / 100;
  }

  private final StatApplier applier;

  StatModifierType(StatApplier applier) {
    this.applier = applier;
  }

  public void apply(Stats stats, int amount) {
    applier.apply(stats, amount);
  }

}
