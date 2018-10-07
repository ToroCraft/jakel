package net.torocraft.jakel.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ContainerStats extends Container {

  public ContainerStats(EntityPlayer player, World world) {
    placeHotbarSlots(player);
    placeArmorSlots(player);
  }

  private void placeHotbarSlots(EntityPlayer player) {
    int X_POS = 8;
    int Y_POS = 142;
    int SLOT_SPACING = 18;

    for (int slotIndex = 0; slotIndex < 9; slotIndex++) {
      int x = X_POS + SLOT_SPACING * slotIndex;
      int y = Y_POS;
      addSlotToContainer(new Slot(player.inventory, slotIndex, x, y));
    }
  }

  private void placeArmorSlots(EntityPlayer player) {
    int X_POS = 8;
    int Y_POS = 8;
    int SLOT_SPACING = 18;

    for (int i = 0; i < 4; i++) {
      int y = Y_POS + (SLOT_SPACING * i);
      addSlotToContainer(new Slot(player.inventory, 39 - i, X_POS, y));
    }
    addSlotToContainer(new Slot(player.inventory, 40, X_POS, 87));
  }

  @Override
  public ItemStack transferStackInSlot(EntityPlayer player, int index) {
    return ItemStack.EMPTY;
  }

  @Override
  public boolean canInteractWith(EntityPlayer playerIn) {
    return true;
  }

  @Override
  public void onContainerClosed(EntityPlayer player) {
    super.onContainerClosed(player);
  }
}
