package net.torocraft.jakel.traits;

import java.util.List;
import net.torocraft.jakel.nbt.NbtField;

public class TraitStore {

  @NbtField(genericType = Trait.class)
  public List<Trait> traits;

}
