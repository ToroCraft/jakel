package net.torocraft.jakel.api;

import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.torocraft.jakel.capabilites.CapabilityPlayerData;
import net.torocraft.jakel.entities.EntityMagicMissile;
import net.torocraft.jakel.loot.Element;
import net.torocraft.jakel.loot.IElemental;
import net.torocraft.jakel.loot.Stats;

public class AttackApi {

  private static final Random rand = new Random();

  public static boolean attackWithMagic(EntityLivingBase attacker, Entity missile, Entity target) {
    DamageSource source = getMagicalDamageSource(attacker, missile);

    float damage = 1f;

    if (attacker instanceof EntityPlayer) {
      damage = applyDamageModifiers((EntityPlayer) attacker, damage);
    }
    return target.attackEntityFrom(source, damage);
  }

  private static float applyDamageModifiers(EntityPlayer attacker, float damage) {
    Stats stats = CapabilityPlayerData.get(attacker).stats;
    System.out.println("attacking with STATS: " + stats);
    return damage;
  }

  private static DamageSource getMagicalDamageSource(EntityLivingBase attacker, Entity missile) {
    Element element = Element.PHYSICAL;
    if (missile instanceof IElemental) {
      element = ((IElemental) missile).getElemental();
    }
    String type = element.toString().toLowerCase() + "_magic";
    return new EntityDamageSourceIndirect(type, missile, attacker);
  }

  public static Vec3d inFrontOf(EntityLivingBase entity, double distance) {
    Vec3d pos = entity.getPositionVector().add(new Vec3d(0d, (double) entity.getEyeHeight(), 0d));
    Vec3d look = entity.getLookVec();
    return inFrontOf(pos, look, distance);
  }

  public static Vec3d inFrontOf(Vec3d pos, Vec3d look, double distance) {
    return look.scale(distance).add(pos);
  }

  public static Vec3d inFrontOf(EntityLivingBase entity) {
    return inFrontOf(entity, 2);
  }

  private static Vec3d randomSpotInRadius(Vec3d pos, double radius) {
    double distance = rand.nextDouble() * radius;
    double degrees = rand.nextDouble() * 360;
    double x = distance * Math.sin(Math.toRadians(degrees));
    double z = distance * Math.cos(Math.toRadians(degrees));
    return new Vec3d(pos.x + x, pos.y, pos.z + z);
  }

  public static void largeMagicMissile(EntityLivingBase shooter, Element element, World world, Vec3d pos, Vec3d vel, int explosionPower) {
    EntityMagicMissile missile = EntityMagicMissile.getElementalMissile(element, world, shooter, pos.x, pos.y, pos.z, vel.x, vel.y, vel.z);
    missile.setExplosionPower(explosionPower);
    missile.setSize(1, 1);
    world.spawnEntity(missile);
    world.playEvent(null, 1016, new BlockPos(pos), 0);
  }

  public static void smallMagicMissile(EntityLivingBase shooter, Element element, World world, Vec3d pos, Vec3d vel) {
    EntityMagicMissile missile = EntityMagicMissile.getElementalMissile(element, world, shooter, pos.x, pos.y, pos.z, vel.x, vel.y, vel.z);
    world.spawnEntity(missile);
    world.playEvent(null, 1016, new BlockPos(pos), 0);
  }

  public static void lightning(EntityLivingBase shooter, Element element, World world, Vec3d pos) {
    world.addWeatherEffect(new EntityLightningBolt(world, pos.x, pos.y, pos.z, false));
  }

  public static void meteors(EntityLivingBase shooter, Element element, World world, Vec3d pos, double radius) {
    double tempRadius = radius / 2;
    for (int i = 0; i < 100; i++) {
      tempRadius += 0.005d * radius;
      Vec3d end = randomSpotInRadius(pos, tempRadius);
      Vec3d start = end.addVector(0, (rand.nextDouble() * 100) + 20, 0);
      smallMagicMissile(shooter, element, world, start, end.subtract(start));
    }
  }

  public static void asteroid(EntityLivingBase shooter, Element element, World world, Vec3d pos) {
    Vec3d start = pos.addVector(0, 50, 0);
    largeMagicMissile(shooter, element, world, start, pos.subtract(start), 10);
  }
}
