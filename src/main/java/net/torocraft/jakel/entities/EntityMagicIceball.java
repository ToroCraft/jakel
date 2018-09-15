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
    setSize(0.3125F, 0.3125F);
  }

  public EntityMagicIceball(World worldIn, EntityLivingBase shooter, double x, double y, double z, double accX, double accY, double accZ) {
    super(worldIn, shooter, x, y, z, accX, accY, accZ);
    elemental = Element.COLD;
  }

  @Override
  protected EnumParticleTypes getParticleType() {
    return EnumParticleTypes.SNOW_SHOVEL;
  }

  @Override
  protected void handleGroundImpact(BlockPos pos, EnumFacing sideHit) {
    if (world.getBlockState(pos).getBlock() == Blocks.SNOW_LAYER) {
      return;
    }
    BlockPos blockpos = pos.offset(sideHit);
    if (this.world.isAirBlock(blockpos)) {
      world.setBlockState(blockpos, Blocks.SNOW_LAYER.getDefaultState());
    }
  }

}