package net.torocraft.jakel.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.torocraft.jakel.loot.Element;

public class EntityMagicLightningball extends EntityMagicMissile {

  public static void initRender() {
    RenderingRegistry.registerEntityRenderingHandler(EntityMagicLightningball.class, RenderMagicMissile::new);
  }

  public EntityMagicLightningball(World worldIn) {
    super(worldIn);
    elemental = Element.LIGHTNING;
  }

  public EntityMagicLightningball(World worldIn, EntityLivingBase shooter, double x, double y, double z, double accX, double accY, double accZ) {
    super(worldIn, shooter, x, y, z, accX, accY, accZ);
    elemental = Element.LIGHTNING;
  }

  @Override
  protected EnumParticleTypes getParticleType() {
    return EnumParticleTypes.FIREWORKS_SPARK;
  }

}