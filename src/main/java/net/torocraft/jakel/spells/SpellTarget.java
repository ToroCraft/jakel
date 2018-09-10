package net.torocraft.jakel.spells;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class SpellTarget {

  public enum Type {BLOCK, ENTITY, DIRECTED_ONLY}

  public final Type type;
  public final BlockPos block;
  public final Entity entity;
  public final Vec3d casterPosition;
  public final Vec3d casterLook;

  public SpellTarget(BlockPos block, Vec3d pos, Vec3d look) {
    this.type = Type.BLOCK;
    this.block = block;
    this.entity = null;
    this.casterPosition = pos;
    this.casterLook = look;
  }

  public SpellTarget(Entity entity, Vec3d pos, Vec3d look) {
    this.type = Type.ENTITY;
    this.block = null;
    this.entity = entity;
    this.casterPosition = pos;
    this.casterLook = look;
  }

  public SpellTarget(Vec3d pos, Vec3d look) {
    this.type = Type.DIRECTED_ONLY;
    this.block = null;
    this.entity = null;
    this.casterPosition = pos;
    this.casterLook = look;
  }
}
