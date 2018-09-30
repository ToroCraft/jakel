package net.torocraft.jakel.loot;

import net.torocraft.jakel.api.EnchantApi;
import net.torocraft.jakel.loot.stat.ModifierRange;
import org.junit.Test;

public class DamageRangeTest {

  @Test
  public void temp() {
    testMultiplier(100);
  }

  @Test
  public void findMultiplierRanges() throws Exception {
    for (int i = 1; i < 70; i++) {
      testMultiplier(i);
    }
    testMultiplier(100);
    testMultiplier(500);
    testMultiplier(1000);
  }

  @Test
  public void findDmgRanges() throws Exception {
    for (int i = 1; i < 70; i++) {
      testDamage(i);
    }
    testDamage(100);
    testDamage(500);
    testDamage(1000);
  }

  private void testMultiplier(int level) {
    ModifierRange range = EnchantApi.multiplierRange(level);
    System.out.println(level + " -> " + range);
  }

  private void testDamage(int level) {
    ModifierRange range = EnchantApi.damageRange(level);
    System.out.println(level + " -> " + range);
  }

}