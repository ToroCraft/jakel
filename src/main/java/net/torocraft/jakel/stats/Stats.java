package net.torocraft.jakel.stats;

import net.minecraft.nbt.NBTTagCompound;
import net.torocraft.jakel.nbt.NbtField;
import net.torocraft.jakel.nbt.NbtSerializer;

public class Stats {

  @NbtField
  public float manaMaximum = 10f;

  @NbtField
  public float manaPerTick = 0.1f;

  @NbtField
  public float cooldownPerTick = 0.1f;

  @NbtField
  public float lifePerHit = 0f;

  @NbtField
  public float lifePerKill = 0f;

  @NbtField
  public float lifePerTick = 0f;

  @NbtField
  public float criticalHitDamage = 1.5f;

  @NbtField
  public float criticalHitChance = 0.05f;

  /*
   * Special Damage
   */

  @NbtField
  public float thorns = 0f;


  @NbtField
  public float areaDamage = 0f;

  /*
   * Damage
   */

  @NbtField
  public float damage = 0f;

  @NbtField
  public float fire = 0f;

  @NbtField
  public float lightning = 0f;

  @NbtField
  public float wither = 0f;

  @NbtField
  public float cold = 0f;

  @NbtField
  public float poison = 0f;

  /*
   * Resistance Multiplier
   */

  @NbtField
  public float defense = 1f;

  @NbtField
  public float fireResist = 0f;

  @NbtField
  public float lightningResist = 0f;

  @NbtField
  public float witherResist = 0f;

  @NbtField
  public float coldResist = 0f;

  @NbtField
  public float holyResist = 0f;

  @Override
  public String toString() {
    NBTTagCompound c = new NBTTagCompound();
    NbtSerializer.write(c, this);
    return c.toString();
  }

}
