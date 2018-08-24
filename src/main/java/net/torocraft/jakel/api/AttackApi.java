package net.torocraft.jakel.api;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class AttackApi {

  public static Vec3d atTarget(Vec3d targetLocation) {
    // implement
    return null;
  }

  public static boolean isMagicalItem

  public static Vec3d inFrontOf(EntityLivingBase entity, double distance) {
    Vec3d start = entity.getPositionVector().add(new Vec3d(0d, (double)entity.getEyeHeight(), 0d));
    return entity.getLookVec().scale(distance).add(start);
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


}
