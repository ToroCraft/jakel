package net.torocraft.jakel.api;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class AttackApi {

  public static Vec3d inFrontOf(EntityLivingBase entity, double distance) {
    Vec3d pos = entity.getPositionVector().add(new Vec3d(0d, (double)entity.getEyeHeight(), 0d));
    Vec3d look = entity.getLookVec();
    return inFrontOf(pos, look, distance);
  }

  public static Vec3d inFrontOf(Vec3d pos, Vec3d look, double distance) {
    return look.scale(distance).add(pos);
  }

  public static Vec3d inFrontOf(EntityLivingBase entity) {
    return inFrontOf(entity, 2);
  }

  public static void largeFireBall(World world, Vec3d pos, Vec3d vel, int explosionPower) {
    EntityLargeFireball fireball = new EntityLargeFireball(world, pos.x, pos.y, pos.z, vel.x, vel.y, vel.z);
    fireball.explosionPower = explosionPower;
    world.spawnEntity(fireball);
    world.playEvent(null, 1016, new BlockPos(pos), 0);
  }

  public static void smallFireBall(World world, Vec3d pos, Vec3d vel) {
    EntitySmallFireball fireball = new EntitySmallFireball(world, pos.x, pos.y, pos.z, vel.x, vel.y, vel.z);
    world.spawnEntity(fireball);
    world.playEvent(null, 1016, new BlockPos(pos), 0);
  }

  public static void lightning(World world, Vec3d pos) {
    world.addWeatherEffect(new EntityLightningBolt(world, pos.x, pos.y, pos.z, false));
  }

}
