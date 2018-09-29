package net.torocraft.jakel.loot.stat.modifiers;

import java.lang.reflect.Field;
import net.torocraft.jakel.loot.stat.IStatModifier;
import net.torocraft.jakel.stats.Stats;

public class FlatModifier implements IStatModifier {

  private final Field field;

  public FlatModifier(String statFieldName) {
    try {
      field = Stats.class.getField(statFieldName);
    } catch (NoSuchFieldException e) {
      throw new RuntimeException("Stat field not found [" + statFieldName + "]", e);
    }
  }


  @Override
  public void apply(Stats stats, float amount) {
    try {
      field.setFloat(stats, field.getFloat(stats) + amount);
    } catch (IllegalAccessException e) {
      throw new RuntimeException("Unable to access stat field [" + field.getName() + "]", e);
    }
  }
}
