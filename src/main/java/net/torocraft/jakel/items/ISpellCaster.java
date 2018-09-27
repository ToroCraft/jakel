package net.torocraft.jakel.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.torocraft.jakel.spells.SpellTarget;

public interface ISpellCaster {

  void cast(EntityPlayer player, ItemStack spell, SpellTarget target);

  float manaCost();

  float cooldown();
}
