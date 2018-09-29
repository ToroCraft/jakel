package net.torocraft.jakel.loot.stat.factory;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.util.ResourceLocation;
import net.torocraft.jakel.loot.stat.StatModifierData;

public class WeaponModifierFactory implements IStatModifierFactory {

  public static class Range {

    int min;
    int max;

    public Range(int min, int max) {
      this.min = min;
      this.max = max;
      //Gson

    }
  }


  private static final Map<Integer, Range> RANGES = new HashMap<>();

  static {
    RANGES.put(1, new Range(1, 5));
  }


  @Override
  public StatModifierData build(int level) {

    new ResourceLocation("jakel:textures/entity/mage/mage.png");

//    StatModifierData data = new StatModifierData();
//    data.amount = amount;
//    data.type = StatModifiers.values()[rand.nextInt(StatModifiers.values().length)];
//    return data;
    return null;
  }
}
