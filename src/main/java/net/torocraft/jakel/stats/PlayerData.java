package net.torocraft.jakel.stats;

import java.util.HashMap;
import java.util.Map;
import net.torocraft.jakel.api.LootApi.SpellSlot;
import net.torocraft.jakel.nbt.NbtField;

public class PlayerData {

  @NbtField
  public float mana;

  @NbtField(genericType = Float.class)
  public Map<String, Float> cooldowns = new HashMap<>();

  @NbtField
  public Stats stats = new Stats();

  /**
   * @return false if cooldown already set and new cooldown could not be applied
   */
  public boolean applyCooldown(SpellSlot slot, float amount) {
    if (cooldowns == null) {
      cooldowns = new HashMap<>();
    }
    Float cooldown = cooldowns.get(slot.toString());
    if (cooldown == null || cooldown <= 0) {
      cooldowns.put(slot.toString(), amount);
      return true;
    }
    return false;
  }

  /**
   * @return false if not enough mana, else return true and decrement mana
   */
  public boolean spendMana(float amount) {
    if (mana < amount) {
      return false;
    }
    mana -= amount;
    return true;
  }



}
