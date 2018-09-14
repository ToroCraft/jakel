package net.torocraft.jakel.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.torocraft.jakel.Jakel;
import net.torocraft.jakel.capabilites.CapabilitySpell;

public class GuiHandler implements IGuiHandler {

  public static final int SPELL_GUI = 0;

  public static void init() {
    NetworkRegistry.INSTANCE.registerGuiHandler(Jakel.INSTANCE, new GuiHandler());
  }

  @Override
  public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
    if (ID == SPELL_GUI) {
      ItemStack spell = player.getHeldItem(EnumHand.MAIN_HAND);
      return new ContainerSpell(player, CapabilitySpell.get(spell), world);
    }
    return null;
  }

  @Override
  public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
    if (ID == SPELL_GUI) {
      ItemStack spell = player.getHeldItem(EnumHand.MAIN_HAND);
      return new GuiContainerSpell(player, CapabilitySpell.get(spell), world);
    }
    return null;
  }
}
