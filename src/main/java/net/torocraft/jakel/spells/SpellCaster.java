package net.torocraft.jakel.spells;

import net.minecraft.entity.player.EntityPlayer;

@FunctionalInterface
public interface SpellCaster {

  void cast(EntityPlayer player, SpellData spell, SpellTarget target);
}
