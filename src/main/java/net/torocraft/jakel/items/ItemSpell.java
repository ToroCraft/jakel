package net.torocraft.jakel.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.torocraft.jakel.Jakel;
import net.torocraft.jakel.gui.GuiHandler;
import net.torocraft.jakel.spells.SpellTarget;

public abstract class ItemSpell extends Item implements ISpellCaster {

  public static final String NAME = "spell";

  public static ItemSpell INSTANCE;

  public ItemSpell() {
    setMaxDamage(1);
    setMaxStackSize(1);
    setCreativeTab(CreativeTabs.MISC);
  }

  @Override
  public ActionResult<ItemStack> onItemRightClick(final World world, final EntityPlayer player, final EnumHand hand) {
    player.openGui(Jakel.INSTANCE, GuiHandler.SPELL_GUI, world, 0, 0, 0);
    return super.onItemRightClick(world, player, hand);
  }

  @Override
  public float manaCost() {
    return 10f;
  }

  @Override
  public float cooldown() {
    return 0f;
  }

  public abstract void cast(EntityPlayer player, ItemStack spell, SpellTarget target);
}
