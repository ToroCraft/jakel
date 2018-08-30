package net.torocraft.jakel.loot;

import net.torocraft.jakel.nbt.NbtField;

public class Modifier {

  @NbtField
  public ModifierType type;

  @NbtField
  public int amount;


  public Modifier() {

  }

  public Modifier(ModifierType type, int amount) {
    this.type = type;
    this.amount = amount;
  }

  public void apply (Stats stats) {
    type.apply(stats, amount);
  }
}
