package net.torocraft.jakel.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.torocraft.jakel.loot.Element;

public class EntityMagicFireball extends EntityMagicMissile {

  public static void initRender() {
    RenderingRegistry.registerEntityRenderingHandler(EntityMagicFireball.class, RenderMagicMissile::new);
  }

  public EntityMagicFireball(World worldIn) {
    super(worldIn);
    elemental = Element.FIRE;
  }

  public EntityMagicFireball(EntityLivingBase shooter, double x, double y, double z, double accX, double accY, double accZ) {
    super(shooter, x, y, z, accX, accY, accZ);
    elemental = Element.FIRE;
  }

  @Override
  protected boolean isFireballFiery() {
    return true;
  }

  @Override
  protected EnumParticleTypes getParticleType() {
    return EnumParticleTypes.FLAME;
  }

  @Override
  protected void handleEntityWasDamaged(Entity entity) {
    entity.setFire(5);
  }

  @Override
  protected void handleGroundImpact(BlockPos pos, EnumFacing sideHit) {
    BlockPos blockpos = pos.offset(sideHit);
    if (world.isAirBlock(blockpos)) {
      world.setBlockState(blockpos, Blocks.FIRE.getDefaultState());
    }
  }

}