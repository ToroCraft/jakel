package net.torocraft.jakel.spells;

import net.torocraft.jakel.nbt.NbtField;

public class SpellData {

  @NbtField
  public Spells type = Spells.FIREBALL_SMALL;

  @NbtField(genericType = SpellDataInventory.class)
  public SpellDataInventory inventory = new SpellDataInventory();

  public String getUnlocalizedName() {
    return "spell." + type.toString().toLowerCase() + ".name";
  }

}
