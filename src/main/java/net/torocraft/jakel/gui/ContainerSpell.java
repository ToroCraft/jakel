package net.torocraft.jakel.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class ContainerSpell extends Container {

  private final int HOTBAR_SLOT_COUNT = 9;

  private final int SLOT_X_SPACING = 18;
  private final int SLOT_Y_SPACING = 18;

  private final int HOTBAR_XPOS = 8;
  private final int HOTBAR_YPOS = 50; //106 + 109;

  private final IInventory inventory;
  private final EntityPlayer player;


  public ContainerSpell(EntityPlayer player, IInventory inventory, World world) {
    this.player = player;
    this.inventory = inventory;
    inventory.openInventory(player);

    for (int x = 0; x < HOTBAR_SLOT_COUNT; x++) {
      addSlotToContainer(new Slot(player.inventory, x, HOTBAR_XPOS + SLOT_X_SPACING * x, HOTBAR_YPOS));
    }

    for (int slotIndex = 0; slotIndex < inventory.getSizeInventory(); slotIndex++) {
      int x = HOTBAR_XPOS + SLOT_X_SPACING * slotIndex;
      int y = HOTBAR_YPOS + 50;
      addSlotToContainer(new Slot(inventory, slotIndex, x, y));
    }
  }

//  @Override
//  public ItemStack transferStackInSlot(EntityPlayer player, int index) {
////    Slot slot = this.inventorySlots.get(index);
////    if (slot == null || !slot.getHasStack()) {
////      return ItemStack.EMPTY;
////    }
////
////    ItemStack sourceStack = slot.getStack();
////    ItemStack copyOfSourceStack = sourceStack.copy();
////
////    if (indexIsForAVanillaSlot(index)) {
////      if (!mergeItemStack(sourceStack, LORD_INVENTORY_FIRST_SLOT_INDEX, LORD_INVENTORY_FIRST_SLOT_INDEX + LORD_INVENTORY_SLOT_COUNT, false)) {
////        return ItemStack.EMPTY;
////      }
////    } else if (indexIsForALordInventorySlot(index) || indexIsForLordOutputSlot(index)) {
////      if (!mergeStackFromLordToPlayer(sourceStack)) {
////        return ItemStack.EMPTY;
////      }
////    } else {
////      return ItemStack.EMPTY;
////    }
////
////    int stackSize = sourceStack.getCount();
////
////    if (stackSize == 0) {
////      slot.putStack(ItemStack.EMPTY);
////    } else {
////      slot.onSlotChanged();
////    }
////
////    return copyOfSourceStack;
//    return super.transferStackInSlot(player, index);
//  }

  @Override
  public boolean canInteractWith(EntityPlayer playerIn) {
    return true;
  }

  @Override
  public void onContainerClosed(EntityPlayer player) {
    super.onContainerClosed(player);
    this.inventory.closeInventory(player);
  }

}
