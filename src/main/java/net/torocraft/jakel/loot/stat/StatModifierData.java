package net.torocraft.jakel.loot.stat;

import net.torocraft.jakel.loot.stat.modifiers.MultiplierModifier;
import net.torocraft.jakel.nbt.NbtField;

public class StatModifierData {

  @NbtField
  public StatModifiers type;

  @NbtField
  public double amount;

  @Override
  public String toString() {

    String sAmount;

    if (type.getModifier() instanceof MultiplierModifier) {
      sAmount = Math.round(amount * 100) + "%";
    } else {
      sAmount = "+" + Math.round(amount);
    }

    // TODO translate
    return type + " " + sAmount;
  }
}
