package net.torocraft.jakel.entities;

import net.minecraft.client.renderer.entity.RenderFireball;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.torocraft.jakel.Jakel;

public class EntityMagicFireball extends EntityFireball {

  public static String NAME = Jakel.MODID.toLowerCase() + "_" + Entities.MAGIC_FIREBALL.toString().toLowerCase();

  public static void init() {
    EntityRegistry.registerModEntity(
        new ResourceLocation(Jakel.MODID, NAME),
        EntityMagicFireball.class, NAME, Entities.MAGIC_FIREBALL.ordinal(),
        Jakel.INSTANCE, 60, 2,
        true, 0xFFFFFF, 0x000000);
  }

  public static void initRender() {
    RenderingRegistry.registerEntityRenderingHandler(EntityMagicFireball.class, RenderMagicFireball::new);
  }

  public EntityMagicFireball(World worldIn) {
    super(worldIn);
    this.setSize(0.3125F, 0.3125F);
  }

  public EntityMagicFireball(World worldIn, EntityLivingBase shooter, double accelX, double accelY, double accelZ) {
    super(worldIn, shooter, accelX, accelY, accelZ);
    this.setSize(0.3125F, 0.3125F);
  }

  public EntityMagicFireball(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
    super(worldIn, x, y, z, accelX, accelY, accelZ);
    this.setSize(0.3125F, 0.3125F);
  }

  public static void registerFixesSmallFireball(DataFixer fixer) {
    EntityFireball.registerFixesFireball(fixer, "MagicFireball");
  }

  protected void onImpact(RayTraceResult result) {
    //System.out.println("IMPACT!" + result.entityHit + " " + result.hitVec);
    if (!this.world.isRemote) {
      if (result.entityHit != null) {

        handleEntityHit(result.entityHit);

      } else {
        handleGroundEffects(result.getBlockPos(), result.sideHit);
      }

      this.setDead();
    }
  }

  private void handleEntityHit(Entity entity) {
    // TODO apply player stats
    boolean attacked = entity.attackEntityFrom(DamageSource.causeFireballDamage(this, shootingEntity), 10.0F);
    if (attacked) {
      applyEnchantments(shootingEntity, entity);
      entity.setFire(5);
    }
  }

  private void handleGroundEffects(BlockPos pos, EnumFacing sideHit) {

    handleFireGroundEffects(pos, sideHit);

    // TODO handle other elements

//    if (world.getBlockState(pos).getBlock() == Blocks.WATER) {
//      world.setBlockState(pos, Blocks.SNOW_LAYER.getDefaultState());
//    } else if (world.getBlockState(pos).getBlock() == Blocks.SNOW_LAYER) {
//      // DO NOTHING?
//    } else {
//      BlockPos blockpos = pos.offset(sideHit);
//      if (this.world.isAirBlock(blockpos)) {
//        //this.world.setBlockState(blockpos, Blocks.FIRE.getDefaultState());
//        world.setBlockState(blockpos, Blocks.SNOW_LAYER.getDefaultState());
//      }
//    }
  }

  private void handleFireGroundEffects(BlockPos pos, EnumFacing sideHit) {
    BlockPos blockpos = pos.offset(sideHit);
    if (world.isAirBlock(blockpos)) {
      world.setBlockState(blockpos, Blocks.FIRE.getDefaultState());
    }
  }

  public boolean canBeCollidedWith() {
    return false;
  }

  public boolean attackEntityFrom(DamageSource source, float amount) {
    return false;
  }
}