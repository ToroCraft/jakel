package net.torocraft.jakel.loot.modifiers;

import net.torocraft.jakel.loot.Stats;

@FunctionalInterface
public interface Modifier {

  enum Type {
    FIRE((stats, data) -> {
      stats.fire += data.amount;
    }),

    WITHER((stats, data) -> {
      stats.wither += data.amount;
    }),

    LIGHTNING((stats, data) -> {
      stats.lightning += data.amount;
    }),

    COLD((stats, data) -> {
      stats.cold += data.amount;
    }),

    HOLY((stats, data) -> {
      stats.holy += data.amount;
    }),

    XP((stats, data) -> {
    }),

    MOVEMENT_SPEED((stats, data) -> {
    }),

    JUMP((stats, data) -> {
    }),

    HP((stats, data) -> {
    }),

    REGEN((stats, data) -> {
    }),

    DEFENSE((stats, data) -> {
    }),

    INCREASED_XP_GAIN((stats, data) -> {
    }),

    CRITICAL_HIT_CHANCE((stats, data) -> {
    }),

    CRITICAL_HIT_DAMAGE((stats, data) -> {
    }),

    SOCKET((stats, data) -> {
    }),

    MANA_COST((stats, data) -> {
    }),

    FIRE_RESIST((stats, data) -> {
    }),

    WITHER_RESIST((stats, data) -> {
    }),

    LIGHTNING_RESIST((stats, data) -> {
    }),

    COLD_RESIST((stats, data) -> {
    }),

    HOLY_RESIST((stats, data) -> {
    }),

    CONTROL_IMPAIRMENT((stats, data) -> {
    }),

    ATTACK_SPEED((stats, data) -> {
    }),

    AREA_DAMAGE((stats, data) -> {
    }),

    LIFE_PER_HIT((stats, data) -> {
    }),

    LIFE_PER_KILL((stats, data) -> {
    }),

    THORNS((stats, data) -> {
      stats.thorns += data.amount;
    });

    private final Modifier modifier;

    Type(Modifier modifier) {
      this.modifier = modifier;
    }

    public void apply(Stats stats, Data data) {
      modifier.apply(stats, data);
    }
  }

  void apply(Stats stats, Data data);
}
