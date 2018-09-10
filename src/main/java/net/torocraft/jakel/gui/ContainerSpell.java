package net.torocraft.jakel.gui;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class ContainerSpell extends Container {

  private final int HOTBAR_SLOT_COUNT = 9;
  private final int INVENTORY_ROW_COUNT = 3;
  private final int INVENTORY_COLUMN_COUNT = 9;
  private final int DONATE_ITEM_ROW_COUNT = 1;
  private final int DONATE_ITEM_COLUMN_COUNT = 1;
  private final int QUEST_INPUT_ITEM_ROW_COUNT = 4;
  private final int QUEST_INPUT_ITEM_COLUMN_COUNT = 1;
  private final int QUEST_OUTPUT_ITEM_ROW_COUNT = 4;
  private final int QUEST_OUTPUT_ITEM_COLUMN_COUNT = 1;

  private final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + (INVENTORY_COLUMN_COUNT * INVENTORY_ROW_COUNT);
  private final int LORD_INVENTORY_SLOT_COUNT = 1;

  private final int VANILLA_FIRST_SLOT_INDEX = 0;
  private final int LORD_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

  private final int SLOT_X_SPACING = 18;
  private final int SLOT_Y_SPACING = 18;

  private final int HOTBAR_XPOS = 8;
  private final int HOTBAR_YPOS = 50; //106 + 109;

  private final int INVENTORY_XPOS = 8;
  private final int INVENTORY_YPOS = 48 + 109;

  private final int DONATE_ITEM_XPOS = 83;
  private final int DONATE_ITEM_YPOS = 17;

  private final int QUEST_INPUT_ITEM_XPOS = 8;
  private final int QUEST_INPUT_ITEM_YPOS = 53;

  private final int QUEST_OUTPUT_ITEM_XPOS = 151;
  private final int QUEST_OUTPUT_ITEM_YPOS = 53;

  private final IInventory inventory;
  private final EntityPlayer player;

  private final List<Integer> inputSlot = new ArrayList<Integer>();
  private final List<Integer> outputSlot = new ArrayList<Integer>();
  private int donationGuiSlotId;

  public ContainerSpell(EntityPlayer player, IInventory spellInventory, World world) {
    this.player = player;
    this.inventory = new InventoryBasic("container_spell", false, 40);
    //this.inventory = inventory;
    this.inventory.openInventory(player);

    for (int i = 0; i < inventory.getSizeInventory(); ++i) {
      System.out.println("======= " + inventory.getStackInSlot(i));
    }

    int guiSlotIndex = 0;

    for (int x = 0; x < HOTBAR_SLOT_COUNT; x++) {
      addSlotToContainer(new Slot(player.inventory, x, HOTBAR_XPOS + SLOT_X_SPACING * x, HOTBAR_YPOS));
      guiSlotIndex++;
    }
//
//    for (int x = 0; x < INVENTORY_ROW_COUNT; x++) {
//      for (int y = 0; y < INVENTORY_COLUMN_COUNT; y++) {
//        int slotNumber = HOTBAR_SLOT_COUNT + x * INVENTORY_COLUMN_COUNT + y;
//        int xPos = INVENTORY_XPOS + y * SLOT_X_SPACING;
//        int yPos = INVENTORY_YPOS + x * SLOT_Y_SPACING;
//        addSlotToContainer(new Slot(player.inventory, slotNumber, xPos, yPos));
//        guiSlotIndex++;
//      }
//    }

//    for (int x = 0; x < inventory.getSizeInventory(); x++) {
//      int xPos = DONATE_ITEM_XPOS * SLOT_X_SPACING;
//      int yPos = DONATE_ITEM_YPOS + x * SLOT_Y_SPACING;
//      addSlotToContainer(new Slot(this.inventory, x, xPos, yPos));
//      donationGuiSlotId = guiSlotIndex;
//      guiSlotIndex++;
//    }

    addListener(new IContainerListener() {

      @Override
      public void sendAllContents(Container containerToSend, NonNullList<ItemStack> itemsList) {

      }

      @Override
      public void sendSlotContents(Container containerToSend, int slotInd, ItemStack stack) {

      }

      @Override
      public void sendWindowProperty(Container containerIn, int varToUpdate, int newValue) {

      }

      @Override
      public void sendAllWindowProperties(Container containerIn, IInventory inventory) {

      }
    });
  }

  @Override
  public ItemStack transferStackInSlot(EntityPlayer player, int index) {
//    Slot slot = this.inventorySlots.get(index);
//    if (slot == null || !slot.getHasStack()) {
//      return ItemStack.EMPTY;
//    }
//
//    ItemStack sourceStack = slot.getStack();
//    ItemStack copyOfSourceStack = sourceStack.copy();
//
//    if (indexIsForAVanillaSlot(index)) {
//      if (!mergeItemStack(sourceStack, LORD_INVENTORY_FIRST_SLOT_INDEX, LORD_INVENTORY_FIRST_SLOT_INDEX + LORD_INVENTORY_SLOT_COUNT, false)) {
//        return ItemStack.EMPTY;
//      }
//    } else if (indexIsForALordInventorySlot(index) || indexIsForLordOutputSlot(index)) {
//      if (!mergeStackFromLordToPlayer(sourceStack)) {
//        return ItemStack.EMPTY;
//      }
//    } else {
//      return ItemStack.EMPTY;
//    }
//
//    int stackSize = sourceStack.getCount();
//
//    if (stackSize == 0) {
//      slot.putStack(ItemStack.EMPTY);
//    } else {
//      slot.onSlotChanged();
//    }
//
//    return copyOfSourceStack;
    return super.transferStackInSlot(player, index);
  }

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
