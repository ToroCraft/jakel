package net.torocraft.jakel.items;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.torocraft.jakel.Jakel;

public enum Items {
  LIGHTNING(ItemLightningSpell.class),
  MISSILE(ItemMissileSpell.class),
  METEORS(ItemMeteorsSpell.class),
  ASTEROID(ItemAsteroidSpell.class);

  public final Class<? extends Item> clazz;

  Items(Class<? extends Item> clazz) {
    this.clazz = clazz;
  }

  public Item getInstance() {
    try {
      return (Item) clazz.getField("INSTANCE").get(null);
    } catch (Exception e) {
      throw new RuntimeException("Failed to get item instance object: [" + clazz + "].INSTANCE", e);
    }
  }

  public void initRender() {
    try {
      clazz.getMethod("registerRenders").invoke(null);
    } catch (Exception e) {
      System.out.println("Failed to register item render [" + clazz + "].registerRenders()");
      e.printStackTrace();
    }
  }

  public static void initAllRenders() {
    for (Items code : values()) {
      code.initRender();
    }
  }

}
