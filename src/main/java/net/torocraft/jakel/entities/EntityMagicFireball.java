package net.torocraft.jakel.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.torocraft.jakel.Jakel;
import net.torocraft.jakel.loot.Element;

public class EntityMagicFireball extends EntityFireball {

  public static String NAME = Jakel.MODID.toLowerCase() + "_" + Entities.MAGIC_FIREBALL.toString().toLowerCase();

  public float explosionPower = 0;
  public Element elemental = Element.PHYSICAL;

  public static void init() {

    ResourceLocation registryName = new ResourceLocation(Jakel.MODID, NAME);
    Class<? extends Entity> entityClass = EntityMagicFireball.class;
    String entityName = NAME;
    int id = Entities.MAGIC_FIREBALL.ordinal();
    Object mod = Jakel.INSTANCE;
    int trackingRange = 100;
    int updateFrequency = 1;
    boolean sendsVelocityUpdates = true;

    EntityRegistry.registerModEntity(registryName, entityClass, entityName, id, mod, trackingRange, updateFrequency, sendsVelocityUpdates);
  }

  public static void initRender() {
    RenderingRegistry.registerEntityRenderingHandler(EntityMagicFireball.class, RenderMagicFireball::new);
  }

  public EntityMagicFireball(World worldIn) {
    super(worldIn);
    setSize(0.3125F, 0.3125F);
  }

  public EntityMagicFireball(World worldIn, EntityLivingBase shooter, double x, double y, double z, double accelX, double accelY, double accelZ) {
    super(worldIn, x, y, z, accelX, accelY, accelZ);
    shootingEntity = shooter;
    setSize(0.3125F, 0.3125F);
  }

  @Override
  public void setSize(float width, float height) {
    super.setSize(width, height);
  }

  public static void registerFixesSmallFireball(DataFixer fixer) {
    EntityFireball.registerFixesFireball(fixer, "MagicFireball");
  }

  @Override
  protected boolean isFireballFiery() {
    return false;
  }

  protected void onImpact(RayTraceResult result) {
    if (!world.isRemote) {
      if (result.entityHit != null) {
        handleEntityHit(result.entityHit);
      } else {
        handleGroundEffects(result.getBlockPos(), result.sideHit);
      }
      handleExplosion();
      setDead();
    }
  }

  private void handleExplosion() {
    if (explosionPower > 0) {
      boolean isFlaming = false;
      boolean damagesTerrain = true;
      world.newExplosion(shootingEntity, posX, posY, posZ, explosionPower, isFlaming, damagesTerrain);
    }
  }

  @Override
  protected EnumParticleTypes getParticleType() {
    if (elemental.equals(Element.COLD)) {
      return EnumParticleTypes.SNOW_SHOVEL;

    } else if (elemental.equals(Element.FIRE)) {
      return EnumParticleTypes.FLAME;

    } else if (elemental.equals(Element.POISON)) {
      return EnumParticleTypes.SLIME;

    } else if (elemental.equals(Element.LIGHTNING)) {
      return EnumParticleTypes.CRIT_MAGIC;

    } else if (elemental.equals(Element.WITHER)) {
      return EnumParticleTypes.DRAGON_BREATH;

    } else {
      return EnumParticleTypes.SMOKE_NORMAL;
    }
  }

  private void handleEntityHit(Entity entity) {
    // TODO apply player stats
    boolean attacked = entity.attackEntityFrom(DamageSource.causeFireballDamage(this, shootingEntity), 10.0F);
    if (attacked) {
      applyEnchantments(shootingEntity, entity);
      if (elemental.equals(Element.FIRE)) {
        entity.setFire(5);
      }
      //((EntityLivingBase)entity).addPotionEffect(//);
    }
  }

  private void handleGroundEffects(BlockPos pos, EnumFacing sideHit) {
    if (elemental.equals(Element.COLD)) {
      handleIceGroundEffects(pos, sideHit);
    } else if (elemental.equals(Element.FIRE)) {
      handleFireGroundEffects(pos, sideHit);
    }
  }

  private void handleFireGroundEffects(BlockPos pos, EnumFacing sideHit) {
    BlockPos blockpos = pos.offset(sideHit);
    if (world.isAirBlock(blockpos)) {
      world.setBlockState(blockpos, Blocks.FIRE.getDefaultState());
    }
  }

  private void handleIceGroundEffects(BlockPos pos, EnumFacing sideHit) {
    if (world.getBlockState(pos).getBlock() == Blocks.SNOW_LAYER) {
      return;
    }
    BlockPos blockpos = pos.offset(sideHit);
    if (this.world.isAirBlock(blockpos)) {
      world.setBlockState(blockpos, Blocks.SNOW_LAYER.getDefaultState());
    }
  }

  public float getBrightness() {
    return 1.0F;
  }

  @SideOnly(Side.CLIENT)
  public int getBrightnessForRender() {
    return 15728880;
  }

  public boolean canBeCollidedWith() {
    return false;
  }

  public boolean attackEntityFrom(DamageSource source, float amount) {
    return false;
  }
}