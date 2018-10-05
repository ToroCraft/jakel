package net.torocraft.jakel.loot.stat;

import net.torocraft.jakel.loot.stat.modifiers.FlatModifier;
import net.torocraft.jakel.loot.stat.modifiers.MultiplierModifier;
import net.torocraft.jakel.stats.Stats;

public enum StatModifiers implements IStatModifier {

  manaMaximum(new FlatModifier(("manaMaximum"))),
  manaPerTick(new FlatModifier("manaPerTick")),
  cooldownPerTick(new FlatModifier("cooldownPerTick")),
  lifePerHit(new FlatModifier("lifePerHit")),
  lifePerKill(new FlatModifier("lifePerKill")),
  lifePerTick(new FlatModifier("lifePerTick")),
  criticalHitDamage(new FlatModifier("criticalHitDamage")),
  criticalHitChance(new FlatModifier("criticalHitChance")),

  /*
   * Special Damage (flat)
   */
  thorns(new FlatModifier("thorns")),
  areaDamage(new FlatModifier("areaDamage")),

  /*
   * Flat Damage
   */
  damage(new FlatModifier("damage")),
  fire(new FlatModifier("fire")),
  lightning(new FlatModifier("lightning")),
  wither(new FlatModifier("wither")),
  cold(new FlatModifier("cold")),
  poison(new FlatModifier("poison")),

  /*
   * Damage Multipliers
   */
  damageMultiplier(new MultiplierModifier("damageMultiplier")),
  fireMultiplier(new MultiplierModifier("fireMultiplier")),
  lightningMultiplier(new MultiplierModifier("lightningMultiplier")),
  witherMultiplier(new MultiplierModifier("witherMultiplier")),
  coldMultiplier(new MultiplierModifier("coldMultiplier")),
  poisonMultiplier(new MultiplierModifier("poisonMultiplier")),

  /*
   * Resistances (Percent)
   */
  defense(new FlatModifier("defense")),
  fireResist(new MultiplierModifier("fireResist")),
  lightningResist(new MultiplierModifier("lightningResist")),
  witherResist(new MultiplierModifier("witherResist")),
  coldResist(new MultiplierModifier("coldResist")),
  holyResist(new MultiplierModifier("holyResist"));

  private final IStatModifier modifier;

  StatModifiers(IStatModifier modifier) {
    this.modifier = modifier;
  }

  public void apply(Stats stats, float amount) {
    modifier.apply(stats, amount);
  }

  public IStatModifier getModifier() {
    return modifier;
  }
}
