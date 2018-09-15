package net.torocraft.jakel.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.torocraft.jakel.loot.Element;

public class EntityMagicMissile extends EntityFireball {

  public int explosionPower = 0;
  public Element elemental = Element.PHYSICAL;

  public static void initRender() {
    RenderingRegistry.registerEntityRenderingHandler(EntityMagicMissile.class, RenderMagicMissile::new);
  }

  public EntityMagicMissile(World worldIn) {
    super(worldIn);
    setSize(0.3125F, 0.3125F);
  }

  public EntityMagicMissile(World worldIn, EntityLivingBase shooter, double x, double y, double z, double accX, double accY, double accZ) {
    super(worldIn, x, y, z, accX, accY, accZ);
    shootingEntity = shooter;
    setSize(0.3125F, 0.3125F);
  }

  public static EntityMagicMissile getElementalMissile(Element element, World worldIn, EntityLivingBase shooter, double x, double y, double z, double accX, double accY, double accZ) {
    switch (element) {
      case FIRE:
        return new EntityMagicFireball(worldIn, shooter, x, y, z, accX, accY, accZ);
      case LIGHTNING:
        return new EntityMagicMissile(worldIn, shooter, x, y, z, accX, accY, accZ);
      case WITHER:
        return new EntityMagicMissile(worldIn, shooter, x, y, z, accX, accY, accZ);
      case COLD:
        return new EntityMagicIceball(worldIn, shooter, x, y, z, accX, accY, accZ);
      case POISON:
        return new EntityMagicMissile(worldIn, shooter, x, y, z, accX, accY, accZ);
      default:
        return new EntityMagicMissile(worldIn, shooter, x, y, z, accX, accY, accZ);
    }
  }

  @Override
  public void setSize(float width, float height) {
    super.setSize(width, height);
  }

  @Override
  protected boolean isFireballFiery() {
    return false;
  }

  protected void onImpact(RayTraceResult result) {
    if (!world.isRemote) {
      if (result.entityHit != null) {
        handleEntityImpact(result.entityHit);
      } else {
        handleGroundImpact(result.getBlockPos(), result.sideHit);
      }
      handleExplosion();
      setDead();
    }
  }

  protected void handleEntityImpact(Entity entity) {
    // TODO apply player stats
    boolean attacked = entity.attackEntityFrom(DamageSource.causeFireballDamage(this, shootingEntity), 10.0F);

    if (attacked) {
      handleEntityWasDamaged(entity);
    }
  }

  protected void handleEntityWasDamaged(Entity entity) {

  }

  protected void handleGroundImpact(BlockPos pos, EnumFacing sideHit) {

  }

  protected void handleExplosion() {
    if (explosionPower > 0) {
      boolean isFlaming = false;
      boolean damagesTerrain = true;
      world.newExplosion(shootingEntity, posX, posY, posZ, explosionPower, isFlaming, damagesTerrain);
    }
  }

  @Override
  protected EnumParticleTypes getParticleType() {
    return EnumParticleTypes.SMOKE_NORMAL;
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