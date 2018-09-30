package net.torocraft.jakel.loot.stat;

import org.junit.Test;

public class ModifierRangeTest {

  @Test
  public void roll() throws Exception {
    ModifierRange r = new ModifierRange(9, 10);
    for (int i = 0; i < 10; i++) {
      System.out.println(r.roll());
    }
  }

}