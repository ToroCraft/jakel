package net.torocraft.jakel.loot;

import java.util.List;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.torocraft.jakel.loot.modifiers.Data;
import net.torocraft.jakel.nbt.NbtField;

public class ItemData {

  @NbtField
  public Boolean isMagicalConduit;

  @NbtField
  public Integer coolDown;

  @NbtField(genericType = Data.class)
  public List<Data> modifiers;
  ModelRegistryEvent
}
