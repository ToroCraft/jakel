package net.torocraft.jakel.entities;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.torocraft.jakel.Jakel;

public enum Entities {
  MAGIC_MISSILE(EntityMagicMissile.class),
  MAGIC_FIREBALL(EntityMagicFireball.class),
  MAGIC_ICEBALL(EntityMagicIceball.class),
  MAGIC_WITHERBALL(EntityMagicWitherball.class),
  MAGIC_LIGHTNINGBALL(EntityMagicLightningball.class),
  MAGIC_SLIMEBALL(EntityMagicSlimeball.class);

  private final Class<? extends Entity> clazz;

  Entities(Class<? extends Entity> clazz) {
    this.clazz = clazz;
  }

  public void init() {
    String entityName = Jakel.MODID.toLowerCase() + "_" + this.toString().toLowerCase();
    ResourceLocation registryName = new ResourceLocation(Jakel.MODID, entityName);
    int id = this.ordinal();
    int trackingRange = 100;
    int updateFrequency = 1;
    boolean sendsVelocityUpdates = true;
    EntityRegistry.registerModEntity(registryName, clazz, entityName, id, Jakel.INSTANCE, trackingRange, updateFrequency, sendsVelocityUpdates);
  }

  public void initRender() {
    try {
      clazz.getMethod("initRender").invoke(null);
    } catch (Exception e) {
      System.out.println("Failed to register entity render [" + clazz + "].initRender()");
      e.printStackTrace();
    }
  }

  public static void initAllEntities() {
    for (Entities code : values()) {
      code.init();
    }
  }

  public static void initAllRenders() {
    for (Entities code : values()) {
      code.initRender();
    }
  }

}
