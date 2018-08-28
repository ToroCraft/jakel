package net.torocraft.jakel.loot;

import java.util.List;
import net.torocraft.jakel.loot.modifiers.Data;
import net.torocraft.jakel.nbt.NbtField;

public class ItemData {

  @NbtField
  public boolean isMagicalConduit;

  @NbtField
  public int coolDown;

  @NbtField(genericType = Data.class)
  public List<Data> modifiers;

}
