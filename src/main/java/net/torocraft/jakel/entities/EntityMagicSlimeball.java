package net.torocraft.jakel.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.torocraft.jakel.loot.Element;

public class EntityMagicSlimeball extends EntityMagicMissile {

  public static void initRender() {
    RenderingRegistry.registerEntityRenderingHandler(EntityMagicSlimeball.class, RenderMagicMissile::new);
  }

  public EntityMagicSlimeball(World worldIn) {
    super(worldIn);
    elemental = Element.POISON;
  }

  public EntityMagicSlimeball(World worldIn, EntityLivingBase shooter, double x, double y, double z, double accX, double accY, double accZ) {
    super(worldIn, shooter, x, y, z, accX, accY, accZ);
    elemental = Element.POISON;
  }

  @Override
  protected EnumParticleTypes getParticleType() {
    return EnumParticleTypes.SLIME;
  }

  @Override
  protected void handleEntityWasDamaged(Entity entity) {
    super.handleEntityWasDamaged(entity);
    if (entity instanceof EntityLivingBase) {
      ((EntityLivingBase)entity).addPotionEffect(new PotionEffect(MobEffects.POISON, 900, 0));
    }
  }

}