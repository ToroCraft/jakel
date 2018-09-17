package net.torocraft.jakel.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.torocraft.jakel.Jakel;
import net.torocraft.jakel.capabilites.CapabilitySpell;
import net.torocraft.jakel.items.ItemSpell;
import net.torocraft.jakel.spells.SpellData;

public class GuiHandler implements IGuiHandler {

  public static final int SPELL_GUI = 0;

  public static void init() {
    NetworkRegistry.INSTANCE.registerGuiHandler(Jakel.INSTANCE, new GuiHandler());
  }

  @Override
  public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
    if (ID == SPELL_GUI) {
      ItemStack spell = getHeldSpell(player);
      if (spell == null) {
        return null;
      }
      return new ContainerSpell(player, spell, world);
    }
    return null;
  }

  @Override
  public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
    if (ID == SPELL_GUI) {
      ItemStack spell = getHeldSpell(player);
      if (spell == null) {
        return null;
      }
      return new GuiContainerSpell(player, spell, world);
    }
    return null;
  }

  private ItemStack getHeldSpell(EntityPlayer player) {
    ItemStack stack = player.getHeldItem(EnumHand.MAIN_HAND);
    if (stack.isEmpty() || !(stack.getItem() instanceof ItemSpell) || CapabilitySpell.get(stack) == null) {
      return null;
    }
    return stack;
  }
}
