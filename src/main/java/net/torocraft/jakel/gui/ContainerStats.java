package net.torocraft.jakel.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.torocraft.jakel.Jakel;
import net.torocraft.jakel.network.MessageSyncSpell;

public class ContainerStats extends Container {

  public ContainerStats(EntityPlayer player, World world) {
    placeSlots(player);
  }

  private void placeSlots(EntityPlayer player) {
    int X_POS = 3;
    int Y_POS = 84;
    int SLOT_SPACING = 18;

    for (int slotIndex = 0; slotIndex < 9; slotIndex++) {
      int x = X_POS + SLOT_SPACING * slotIndex;
      int y = Y_POS + (3 * SLOT_SPACING) + 4;
      addSlotToContainer(new Slot(player.inventory, slotIndex, x, y));
    }

    for (int row = 0; row < 3; row++) {
      for (int column = 0; column < 9; column++) {
        int slotNumber = 9 + column + (row * 9);
        int x = X_POS + column * SLOT_SPACING;
        int y = Y_POS + row * SLOT_SPACING;
        addSlotToContainer(new Slot(player.inventory, slotNumber, x, y));
      }
    }
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
