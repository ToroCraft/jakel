package net.torocraft.jakel.loot.stat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.ResourceLocation;
import net.torocraft.jakel.util.EnumAdapter;
import net.torocraft.jakel.util.ResourceUtil;

public class StatModifierByEquip {

  public static Map<EntityEquipmentSlot, List<StatModifiers>> INSTANCE;

  static {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(EntityEquipmentSlot.class, new EnumAdapter());
    builder.registerTypeAdapter(StatModifiers.class, new EnumAdapter());
    Gson gson = builder.enableComplexMapKeySerialization().create();

    Type type = new TypeToken<Map<EntityEquipmentSlot, List<StatModifiers>>>() {
    }.getType();

    ResourceLocation location = new ResourceLocation("jakel:enchants/modifier.json");

    try {
      INSTANCE = gson.fromJson(ResourceUtil.load(location), type);
    } catch (Exception e) {
      throw new RuntimeException("Failed to load the modifier to equipment map", e);
    }
  }

}
