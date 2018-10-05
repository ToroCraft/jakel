package net.torocraft.jakel.entities;

import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.torocraft.jakel.api.AttackApi;
import net.torocraft.jakel.loot.Element;
import net.torocraft.jakel.loot.IElemental;
import net.torocraft.jakel.util.MagicDamageSource;

public class EntityMagicMissile extends Entity implements IElemental {

  protected Element elemental = Element.PHYSICAL;
  public EntityLivingBase shootingEntity;
  private int ticksAlive;
  private int ticksInAir;
  public double accelerationX;
  public double accelerationY;
  public double accelerationZ;

  private static final DataParameter<Float> DAMAGE_MULTIPLIER = EntityDataManager.createKey(EntityMagicMissile.class, DataSerializers.FLOAT);
  private static final DataParameter<Float> GRAVITY = EntityDataManager.createKey(EntityMagicMissile.class, DataSerializers.FLOAT);
  private static final DataParameter<Float> SIZE = EntityDataManager.createKey(EntityMagicMissile.class, DataSerializers.FLOAT);

  /*
   * will be only server only
   */
  protected int explosionPower = 0;

  public static void initRender() {
    RenderingRegistry.registerEntityRenderingHandler(EntityMagicMissile.class, RenderMagicMissile::new);
  }

  public EntityMagicMissile(World worldIn) {
    super(worldIn);
    setSize(0.3125F, 0.3125F);
  }

  @Override
  protected void entityInit() {
    getDataManager().register(DAMAGE_MULTIPLIER, 1f);
    getDataManager().register(GRAVITY, 0f);
    getDataManager().register(SIZE, 0.3f);
    setSize(getSize(), getSize());
  }

  public void setDamageMultiplier(float damage) {
    getDataManager().set(DAMAGE_MULTIPLIER, damage);
  }

  public float getDamageMultiplier() {
    return getDataManager().get(DAMAGE_MULTIPLIER);
  }

  public void setGravity(float gravity) {
    getDataManager().set(GRAVITY, gravity);
  }

  public float getGravity() {
    return getDataManager().get(GRAVITY);
  }

  public void setSize(float size) {
    getDataManager().set(SIZE, size);
    setSize(size, size);
  }

  public float getSize() {
    return getDataManager().get(SIZE);
  }

  protected double getSpeed() {
    return 0.1d;
  }

  public EntityMagicMissile(EntityLivingBase shooter, double x, double y, double z, double accX, double accY, double accZ) {
    super(shooter.world);
    this.shootingEntity = shooter;
    this.setSize(1.0F, 1.0F);
    this.setLocationAndAngles(x, y, z, this.rotationYaw, this.rotationPitch);
    this.setPosition(x, y, z);

    Vec3d vec = new Vec3d(0, 0, 0);
    double d0 = (double) MathHelper.sqrt(accX * accX + accY * accY + accZ * accZ);
    this.accelerationX = accX / d0 * getSpeed();
    this.accelerationY = accY / d0 * getSpeed();
    this.accelerationZ = accZ / d0 * getSpeed();
  }

  public boolean canBeCollidedWith() {
    return true;
  }

  public float getCollisionBorderSize() {
    return 1.0F;
  }

  public static EntityMagicMissile getElementalMissile(Element element, EntityLivingBase shooter, double x, double y, double z, double accX, double accY, double accZ) {
    switch (element) {
      case FIRE:
        return new EntityMagicFireball(shooter, x, y, z, accX, accY, accZ);
      case LIGHTNING:
        return new EntityMagicLightningball(shooter, x, y, z, accX, accY, accZ);
      case WITHER:
        return new EntityMagicWitherball(shooter, x, y, z, accX, accY, accZ);
      case COLD:
        return new EntityMagicIceball(shooter, x, y, z, accX, accY, accZ);
      case POISON:
        return new EntityMagicSlimeball(shooter, x, y, z, accX, accY, accZ);
      default:
        return new EntityMagicMissile(shooter, x, y, z, accX, accY, accZ);
    }
  }

  /**
   * Gets how bright this entity is.
   */
  public float getBrightness() {
    return 1.0F;
  }

  @SideOnly(Side.CLIENT)
  public int getBrightnessForRender() {
    return 15728880;
  }

  @Override
  public void onUpdate() {
    if (world.isRemote || (shootingEntity == null || !shootingEntity.isDead) && world.isBlockLoaded(new BlockPos(this))) {
      super.onUpdate();

      if (isFireballFiery()) {
        setFire(1);
      }

      ++this.ticksInAir;
      RayTraceResult rayTraceResult = forwardsRaycast(this, true, ticksInAir >= 25, shootingEntity);
      //RayTraceResult rayTraceResult = ProjectileHelper.forwardsRaycast(this, true, ticksInAir >= 25, shootingEntity);

      if (rayTraceResult != null) {
        onImpact(rayTraceResult);
      }

      posX += motionX;
      posY += motionY;
      posZ += motionZ;
      ProjectileHelper.rotateTowardsMovement(this, 0.2F);
      float f = getMotionFactor();

      if (isInWater()) {
        f = handleInWater(f);
      }

      motionX += accelerationX;
      motionY += accelerationY;
      motionZ += accelerationZ;
      motionX *= (double) f;
      motionY *= (double) f;
      motionZ *= (double) f;
      world.spawnParticle(getParticleType(), posX, posY, posZ, 0.0D, 0.0D, 0.0D);
      setPosition(posX, posY, posZ);
    } else {
      setDead();
    }
  }

  /**
   * returns the adjusted motion factor
   */
  protected float handleInWater(float motionFactor) {
    for (int i = 0; i < 4; ++i) {
      float f1 = 0.25F;
      world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, posX - motionX * 0.25D, posY - motionY * 0.25D, posZ - motionZ * 0.25D, motionX, motionY,
          motionZ);
    }
    return 0.8F;
  }

  public void writeEntityToNBT(NBTTagCompound compound) {
    compound.setTag("direction", newDoubleNBTList(motionX, motionY, motionZ));
    compound.setTag("power", newDoubleNBTList(accelerationX, accelerationY, accelerationZ));
    compound.setInteger("life", ticksAlive);
  }

  public void readEntityFromNBT(NBTTagCompound compound) {
    if (compound.hasKey("power", 9)) {
      NBTTagList nbttaglist = compound.getTagList("power", 6);

      if (nbttaglist.tagCount() == 3) {
        accelerationX = nbttaglist.getDoubleAt(0);
        accelerationY = nbttaglist.getDoubleAt(1);
        accelerationZ = nbttaglist.getDoubleAt(2);
      }
    }

    ticksAlive = compound.getInteger("life");

    if (compound.hasKey("direction", 9) && compound.getTagList("direction", 6).tagCount() == 3) {
      NBTTagList nbttaglist1 = compound.getTagList("direction", 6);
      motionX = nbttaglist1.getDoubleAt(0);
      motionY = nbttaglist1.getDoubleAt(1);
      motionZ = nbttaglist1.getDoubleAt(2);
    } else {
      setDead();
    }
  }

  protected float getMotionFactor() {
    return 0.95F;
  }

  @Override
  public void setSize(float width, float height) {
    super.setSize(width, height);
  }

  protected boolean isFireballFiery() {
    return false;
  }

  protected void onImpact(RayTraceResult result) {
    if (!world.isRemote) {
      if (result == null) {
        return;
      }
      if (result.entityHit != null) {
        handleEntityImpact(result.entityHit);
      } else {
        handleGroundImpact(result.getBlockPos(), result.sideHit);
      }
      handleExplosion();
      setDead();
    }
  }

  @Override
  public boolean isInRangeToRenderDist(double distance) {
    return distance < 10000;
  }

  protected void handleEntityImpact(Entity entity) {
    DamageSource source = new MagicDamageSource(elemental, this, shootingEntity);

    boolean attacked = AttackApi.attackWithMagic(shootingEntity, getDamageMultiplier(), elemental, source, entity);
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

  protected EnumParticleTypes getParticleType() {
    return EnumParticleTypes.SMOKE_NORMAL;
  }

  public boolean attackEntityFrom(DamageSource source, float amount) {
    return false;
  }

  public int getExplosionPower() {
    return explosionPower;
  }

  public void setExplosionPower(int explosionPower) {
    this.explosionPower = explosionPower;
  }

  public Element getElemental() {
    return elemental;
  }


  /**
   * Ray trace code from ProjectileHelper, with added option to detect liquids
   */
  public RayTraceResult forwardsRaycast(Entity projectile, boolean includeEntities, boolean ignoreExcludedEntity, Entity excludedEntity) {
    Vec3d pos = new Vec3d(posX, posY, posZ);
    Vec3d direction = new Vec3d(posX + motionX, posY + motionY, posZ + motionZ);
    RayTraceResult result1 = world.rayTraceBlocks(pos, direction, getStopOnLiquid(), false, false);

    if (includeEntities) {
      if (result1 != null) {
        direction = new Vec3d(result1.hitVec.x, result1.hitVec.y, result1.hitVec.z);
      }

      Entity entity = null;
      List<Entity> entitiesInContact = world.getEntitiesWithinAABBExcludingEntity(projectile, projectile.getEntityBoundingBox().expand(motionX, motionY, motionZ).grow(1.0D));
      double d6 = 0.0D;

      for (Entity entity1 : entitiesInContact) {
        if (entity1.canBeCollidedWith() && !(entity1 instanceof EntityMagicMissile) && (ignoreExcludedEntity || !entity1.isEntityEqual(excludedEntity)) && !entity1.noClip) {
          AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().grow(0.30000001192092896D);
          RayTraceResult result2 = axisalignedbb.calculateIntercept(pos, direction);

          if (result2 != null) {
            double d7 = pos.squareDistanceTo(result2.hitVec);

            if (d7 < d6 || d6 == 0.0D) {
              entity = entity1;
              d6 = d7;
            }
          }
        }
      }

      if (entity != null) {
        result1 = new RayTraceResult(entity);
      }
    }

    return result1;
  }

  protected boolean getStopOnLiquid() {
    return false;
  }
}