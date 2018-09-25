package net.torocraft.jakel.api;

import java.util.ArrayList;
import net.torocraft.jakel.loot.ItemData;
import net.torocraft.jakel.loot.Modifier;
import net.torocraft.jakel.stats.Stats;
import net.torocraft.jakel.loot.stat.StatModifierType;
import org.junit.Assert;
import org.junit.Test;

public class LootApiTest {

  @Test
  public void applyItem() {
    ItemData item = new ItemData();
    Stats stats = new Stats();
    stats.damage = 0;
    item.modifiers = new ArrayList<>();
    item.modifiers.add(new Modifier(StatModifierType.DAMAGE, 10));
    item.modifiers.add(new Modifier(StatModifierType.DAMAGE, 2));
    item.modifiers.add(new Modifier(StatModifierType.FIRE_RESIST, 10));
    LootApi.applyItem(item, stats);
    Assert.assertEquals(12, stats.damage);
    Assert.assertEquals(0.1f, stats.fireResist, 0.000001);
  }

}