package net.torocraft.jakel.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.torocraft.jakel.loot.Element;

public class EntityMagicWitherball extends EntityMagicMissile {

  public static void initRender() {
    RenderingRegistry.registerEntityRenderingHandler(EntityMagicWitherball.class, RenderMagicMissile::new);
  }

  public EntityMagicWitherball(World worldIn) {
    super(worldIn);
    elemental = Element.WITHER;
  }

  public EntityMagicWitherball(EntityLivingBase shooter, double x, double y, double z, double accX, double accY, double accZ) {
    super(shooter, x, y, z, accX, accY, accZ);
    elemental = Element.WITHER;
  }

  @Override
  protected EnumParticleTypes getParticleType() {
    return EnumParticleTypes.DRAGON_BREATH;
  }

  @Override
  protected void handleEntityWasDamaged(Entity entity) {
    super.handleEntityWasDamaged(entity);
    if (entity instanceof EntityLivingBase) {
      ((EntityLivingBase)entity).addPotionEffect(new PotionEffect(MobEffects.WITHER, 900, 0));
    }
  }

}