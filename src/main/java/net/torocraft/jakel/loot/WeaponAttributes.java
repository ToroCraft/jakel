package net.torocraft.jakel.loot;

import java.util.List;
import net.torocraft.jakel.loot.modifiers.Type;

public class WeaponAttributes {

  public enum AttackType {LARGE_FIREBALL}

  public AttackType type;
  public String damageType;
  public List<Type> modifiers;

}
