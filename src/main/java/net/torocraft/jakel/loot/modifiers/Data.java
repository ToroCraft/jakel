package net.torocraft.jakel.loot.modifiers;

import net.torocraft.jakel.loot.modifiers.Modifier.Type;
import net.torocraft.jakel.nbt.NbtField;

public class Data {

  public Data () {

  }

  public Data (Type type, int amount) {
    this.type = type;
    this.amount = amount;
  }

  @NbtField
  public Type type;

  @NbtField
  public int amount;
}
