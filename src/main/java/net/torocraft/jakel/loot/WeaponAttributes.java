package net.torocraft.jakel.loot;

import java.util.List;
import net.torocraft.jakel.loot.modifiers.Data;
import net.torocraft.jakel.nbt.NbtField;

public class WeaponAttributes {

  public enum AttackType {LARGE_FIREBALL}

  @NbtField
  public AttackType type;

  @NbtField
  public Element damageType;

  @NbtField(genericType = Data.class)
  public List<Data> modifiers;

}
