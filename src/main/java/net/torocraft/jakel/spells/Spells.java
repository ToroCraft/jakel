package net.torocraft.jakel.spells;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import net.torocraft.jakel.api.AttackApi;
import net.torocraft.jakel.spells.SpellTarget.Type;

public enum Spells {

  LIGHTNING((player, target) -> {
    if (Type.BLOCK.equals(target.type)) {
      Vec3d pos = new Vec3d(target.block.getX() + 0.5, target.block.getY(), target.block.getZ() + 0.5);
      AttackApi.lightning(player.world, pos);
    }

    if (Type.ENTITY.equals(target.type)) {
      AttackApi.lightning(player.world, target.entity.getPositionVector());
    }
  }),

  FIREBALL_LARGE((player, target) -> {
    AttackApi.largeFireBall(player.world, target.casterPosition, target.casterLook, 2);
  }),

  FIREBALL_SMALL((player, target) -> {
    AttackApi.smallFireBall(player.world, target.casterPosition, target.casterLook);
  }),

  METEORS((player, target) -> {
    Vec3d pos = null;
    if (Type.BLOCK.equals(target.type)) {
      pos = new Vec3d(target.block.getX() + 0.5, target.block.getY(), target.block.getZ() + 0.5);
    }

    if (Type.ENTITY.equals(target.type)) {
      pos = target.entity.getPositionVector();
    }

    if (pos != null) {
      AttackApi.meteors(player.world, pos, 4d);
    }
  }),

  ASTEROID((player, target) -> {
    Vec3d pos = null;
    if (Type.BLOCK.equals(target.type)) {
      pos = new Vec3d(target.block.getX() + 0.5, target.block.getY(), target.block.getZ() + 0.5);
    }

    if (Type.ENTITY.equals(target.type)) {
      pos = target.entity.getPositionVector();
    }

    if (pos != null) {
      AttackApi.asteroid(player.world, pos);
    }
  });

  private final SpellCaster caster;

  Spells(SpellCaster caster) {
    this.caster = caster;
  }

  public void cast(EntityPlayer player, SpellTarget target) {
    caster.cast(player, target);
  }

}
