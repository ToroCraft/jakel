package net.torocraft.jakel.entities;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.torocraft.jakel.loot.Element;

public class EntityMagicIceball extends EntityMagicMissile {

  public static void initRender() {
    RenderingRegistry.registerEntityRenderingHandler(EntityMagicIceball.class, RenderMagicMissile::new);
  }

  public EntityMagicIceball(World worldIn) {
    super(worldIn);
    elemental = Element.COLD;
  }

  public EntityMagicIceball(EntityLivingBase shooter, double x, double y, double z, double accX, double accY, double accZ) {
    super(shooter, x, y, z, accX, accY, accZ);
    elemental = Element.COLD;
  }

  @Override
  protected float handleInWater(float motionFactor) {
    //world.setBlockState(new BlockPos(this), Blocks.ICE.getDefaultState());
    setDead();
    return 0.1f;
  }

  @Override
  protected EnumParticleTypes getParticleType() {
    return EnumParticleTypes.SNOW_SHOVEL;
  }

  @Override
  protected boolean getStopOnLiquid() {
    return true;
  }

  @Override
  protected void handleGroundImpact(BlockPos pos, EnumFacing sideHit) {
    if (world.getBlockState(pos).getBlock() == Blocks.SNOW_LAYER) {
      return;
    }
    if (world.getBlockState(pos).getBlock() == Blocks.WATER) {
      world.setBlockState(pos, Blocks.ICE.getDefaultState());
      return;
    }
    BlockPos blockpos = pos.offset(sideHit);
    if (this.world.isAirBlock(blockpos)) {
      world.setBlockState(blockpos, Blocks.SNOW_LAYER.getDefaultState());
    }
  }

}