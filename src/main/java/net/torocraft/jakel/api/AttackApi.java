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
import net.torocraft.jakel.capabilites.CapabilityPlayerData;
import net.torocraft.jakel.entities.EntityMagicMissile;
import net.torocraft.jakel.loot.Element;
import net.torocraft.jakel.loot.IElemental;
import net.torocraft.jakel.loot.Stats;
import net.torocraft.jakel.spells.SpellData;
import net.torocraft.jakel.spells.SpellTarget;
import net.torocraft.jakel.spells.SpellTarget.Type;

public class AttackApi {

  private static final Random rand = new Random();

  public static boolean attackWithMagic(EntityLivingBase attacker, float damageMultiplier, Element element, Entity missile, Entity target) {
    DamageSource source = getMagicalDamageSource(attacker, missile);

    float damage = 1;
    if (attacker instanceof EntityPlayer) {
      damage = applyDamageModifiers((EntityPlayer) attacker, damageMultiplier, element);
    }

    return target.attackEntityFrom(source, damage);
  }

  private static float applyDamageModifiers(EntityPlayer attacker, float damageMultiplier, Element element) {
    Stats stats = CapabilityPlayerData.get(attacker).stats;

    float damage = stats.damage * damageMultiplier;

    switch (element) {
      case FIRE:
        damage *= stats.fire;
        break;
      case LIGHTNING:
        damage *= stats.lightning;
        break;
      case WITHER:
        damage *= stats.wither;
        break;
      case COLD:
        damage *= stats.cold;
        break;
      case POISON:
        damage *= stats.poison;
        break;
    }

    System.out.println("attacking with STATS: " + stats);

    System.out.println("attacking with base damage modifier: " + damageMultiplier + " " + element);

    System.out.println("FINAL DAMAGE: " + damage);

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

  public static void magicMissile(Element element, float damageMultiplier, EntityLivingBase shooter, Vec3d pos, Vec3d vel, int explosionPower, float size) {
    EntityMagicMissile missile = EntityMagicMissile.getElementalMissile(element, shooter, pos.x, pos.y, pos.z, vel.x, vel.y, vel.z);
    missile.setExplosionPower(explosionPower);
    missile.setSize(size);
    missile.setDamageMultiplier(damageMultiplier);
    shooter.world.spawnEntity(missile);
    shooter.world.playEvent(null, 1016, new BlockPos(pos), 0);
  }

  public static void lightning(EntityLivingBase shooter, SpellData spell, Vec3d pos) {
    shooter.world.addWeatherEffect(new EntityLightningBolt(shooter.world, pos.x, pos.y, pos.z, false));
  }

  public static void meteors(Element element, float damageMultiplier, EntityLivingBase shooter, Vec3d pos, double radius) {
    double tempRadius = radius / 2;
    for (int i = 0; i < 100; i++) {
      tempRadius += 0.005d * radius;
      Vec3d end = randomSpotInRadius(pos, tempRadius);
      Vec3d start = end.addVector(0, (rand.nextDouble() * 100) + 20, 0);
      magicMissile(element, damageMultiplier, shooter, start, end.subtract(start), 0, 0.3f);
    }
  }

  public static void asteroid(Element element, float damageMultiplier, EntityLivingBase shooter, Vec3d pos) {
    Vec3d start = pos.addVector(0, 50, 0);
    magicMissile(element, damageMultiplier, shooter, start, pos.subtract(start), 10, 2f);
  }

  public static Vec3d targetLocation(SpellTarget target) {
    Vec3d pos = null;
    if (Type.BLOCK.equals(target.type)) {
      pos = new Vec3d(target.block.getX() + 0.5, target.block.getY(), target.block.getZ() + 0.5);
    }
    if (Type.ENTITY.equals(target.type)) {
      pos = target.entity.getPositionVector();
    }
    return pos;
  }
}
