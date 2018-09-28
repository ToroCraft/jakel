package net.torocraft.jakel.loot.stat;

import net.torocraft.jakel.nbt.NbtField;

public class StatModifierData {

  @NbtField
  public StatModifiers type;

  @NbtField
  public int amount;

}
