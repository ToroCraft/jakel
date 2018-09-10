package net.torocraft.jakel.spells;

import net.torocraft.jakel.nbt.NbtField;

public class SpellData {

  @NbtField
  public Spells type;

  @NbtField(genericType = SpellDataInventory.class)
  public SpellDataInventory inventory = new SpellDataInventory();

}
