package net.torocraft.jakel.loot;

import java.util.List;
import net.torocraft.jakel.nbt.NbtField;

public class ItemData {

  @NbtField
  public Boolean isMagicalConduit;

  @NbtField
  public Integer coolDown;

  @NbtField(genericType = Modifier.class)
  public List<Modifier> modifiers;

}
