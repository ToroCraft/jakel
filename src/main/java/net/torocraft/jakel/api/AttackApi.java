package net.torocraft.jakel.api;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.torocraft.jakel.api.LootApi.SpellSlot;
import net.torocraft.jakel.capabilites.CapabilityPlayerData;
import net.torocraft.jakel.entities.EntityMagicMissile;
import net.torocraft.jakel.items.ISpellCaster;
import net.torocraft.jakel.loot.Element;
import net.torocraft.jakel.spells.SpellData;
import net.torocraft.jakel.spells.SpellTarget;
import net.torocraft.jakel.spells.SpellTarget.Type;
import net.torocraft.jakel.stats.PlayerData;
import net.torocraft.jakel.stats.Stats;
import net.torocraft.jakel.util.MagicDamageSource;

public class AttackApi {

  private static final Random rand = new Random();

  public static boolean attackWithMagic(EntityLivingBase attacker, float damageMultiplier, Element element, Entity target, @Nullable Entity missile) {
    if (!(attacker instanceof EntityPlayer)) {
      // TODO add cap to entities to allow them to attack with magic
      return false;
    }

    EntityPlayer player = (EntityPlayer) attacker;
    Stats stats = CapabilityPlayerData.get(player).stats;

    float baseDamage = stats.damage * damageMultiplier;
    float damage = applyElementalDamageBuff(stats, baseDamage, element);

    if (damage <= 0) {
      return false;
    }

    DamageSource source = new MagicDamageSource(element, missile, attacker);
    System.out.println(element + " damage " + damage + " to " + target.getName());
    return target.attackEntityFrom(source, damage);
  }

  private static float applyElementalDamageBuff(Stats stats, float baseDamage, Element element) {
    float damage = 1f;
    switch (element) {
      case FIRE:
        return (baseDamage + stats.fire) * stats.fireMultiplier;
      case LIGHTNING:
        return (baseDamage + stats.lightning) * stats.lightningMultiplier;
      case WITHER:
        return (baseDamage + stats.wither) * stats.witherMultiplier;
      case COLD:
        return (baseDamage + stats.cold) * stats.coldMultiplier;
      case POISON:
        return (baseDamage + stats.poison) * stats.poisonMultiplier;
    }
    return baseDamage;
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

  public static void castSpell(EntityPlayerMP player, SpellSlot slot, SpellTarget target) {
    ItemStack spellItemStack = LootApi.getEquippedSpell(player, slot);
    if (spellItemStack.isEmpty() || !(spellItemStack.getItem() instanceof ISpellCaster)) {
      handleCastSpellFailed(player);
      return;
    }

    PlayerData playerData = CapabilityPlayerData.get(player);
    ISpellCaster spell = (ISpellCaster) spellItemStack.getItem();

    if (!playerData.applyCooldown(slot, spell.cooldown())){
      handleCastSpellFailed(player);
      return;
    }

    if(!playerData.spendMana(spell.manaCost())){
      handleCastSpellFailed(player);
      return;
    }

    spell.cast(player, spellItemStack, target);
  }

  private static void handleCastSpellFailed(EntityPlayerMP player) {
    // TODO play fail sound
  }
}
