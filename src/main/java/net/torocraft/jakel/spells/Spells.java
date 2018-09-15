package net.torocraft.jakel.spells;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import net.torocraft.jakel.api.AttackApi;
import net.torocraft.jakel.spells.SpellTarget.Type;

public enum Spells {

  LIGHTNING((player, spell, target) -> {
    Vec3d pos = targetLocation(target);
    if (pos != null) {
      AttackApi.lightning(player, spell.element, player.world, pos);
    }
  }),

  FIREBALL_LARGE((player, spell, target) -> {
    AttackApi.largeMagicMissile(player, spell.element, player.world, target.casterPosition, target.casterLook, 1);
  }),

  FIREBALL_SMALL((player, spell, target) -> {
    AttackApi.smallMagicMissile(player, spell.element, player.world, target.casterPosition, target.casterLook);
  }),

  METEORS((player, spell, target) -> {
    Vec3d pos = targetLocation(target);
    if (pos != null) {
      AttackApi.meteors(player, spell.element, player.world, pos, 4d);
    }
  }),

  ASTEROID((player, spell, target) -> {
    Vec3d pos = targetLocation(target);
    if (pos != null) {
      AttackApi.asteroid(player, spell.element, player.world, pos);
    }
  });

  private static Vec3d targetLocation(SpellTarget target) {
    Vec3d pos = null;
    if (Type.BLOCK.equals(target.type)) {
      pos = new Vec3d(target.block.getX() + 0.5, target.block.getY(), target.block.getZ() + 0.5);
    }
    if (Type.ENTITY.equals(target.type)) {
      pos = target.entity.getPositionVector();
    }
    return pos;
  }

  private final SpellCaster caster;

  Spells(SpellCaster caster) {
    this.caster = caster;
  }

  public void cast(EntityPlayer player, SpellData spell, SpellTarget target) {
    //Stats stats = CapabilityPlayerData.get(player).stats;
    caster.cast(player, spell, target);
  }

}
