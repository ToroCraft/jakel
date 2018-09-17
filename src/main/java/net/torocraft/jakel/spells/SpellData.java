package net.torocraft.jakel.spells;

import net.minecraft.nbt.NBTTagCompound;
import net.torocraft.jakel.loot.Element;
import net.torocraft.jakel.nbt.NbtField;
import net.torocraft.jakel.nbt.NbtSerializer;

public class SpellData {

  @NbtField
  public Element element = Element.PHYSICAL;

  @NbtField(genericType = SpellDataInventory.class)
  public SpellDataInventory inventory = new SpellDataInventory();

  @Override
  public String toString() {
    NBTTagCompound c = new NBTTagCompound();
    NbtSerializer.write(c, this);
    return c.toString();
  }

}
