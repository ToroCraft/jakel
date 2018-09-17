package net.torocraft.jakel.spells;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

@FunctionalInterface
public interface ISpellCaster {

  void cast(EntityPlayer player, ItemStack spell, SpellTarget target);
}
