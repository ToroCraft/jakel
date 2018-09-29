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
  damageMultiplier(new MultiplierModifier("damage")),
  fireMultiplier(new MultiplierModifier("fire")),
  lightningMultiplier(new MultiplierModifier("lightning")),
  witherMultiplier(new MultiplierModifier("wither")),
  coldMultiplier(new MultiplierModifier("cold")),
  poisonMultiplier(new MultiplierModifier("poison")),

  /*
   * Resistances (Percent)
   */
  defense(new MultiplierModifier("defense")),
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

}