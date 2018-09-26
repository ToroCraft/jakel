package net.torocraft.jakel.api;

import java.util.ArrayList;
import net.torocraft.jakel.loot.ItemData;
import net.torocraft.jakel.loot.stat.StatModifierData;
import net.torocraft.jakel.stats.Stats;
import net.torocraft.jakel.loot.stat.StatModifiers;
import org.junit.Assert;
import org.junit.Test;

public class LootApiTest {

  @Test
  public void applyItem() {
    ItemData item = new ItemData();
    Stats stats = new Stats();
    stats.damage = 0;
    item.modifiers = new ArrayList<>();
    item.modifiers.add(create(StatModifiers.DAMAGE, 10));
    item.modifiers.add(create(StatModifiers.DAMAGE, 2));
    item.modifiers.add(create(StatModifiers.FIRE_RESIST, 10));
    LootApi.applyItem(item, stats);
    Assert.assertEquals(12f, stats.damage, 0.00001);
    Assert.assertEquals(0.1f, stats.fireResist, 0.000001);
  }

  private static StatModifierData create(StatModifiers type, int amount) {
    StatModifierData data = new StatModifierData();
    data.amount = amount;
    data.type = type;
    return data;
  }

}