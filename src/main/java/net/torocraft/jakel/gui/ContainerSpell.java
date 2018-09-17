package net.torocraft.jakel.gui;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.torocraft.jakel.Jakel;
import net.torocraft.jakel.capabilites.CapabilitySpell;
import net.torocraft.jakel.loot.Element;
import net.torocraft.jakel.network.MessageSyncSpell;
import net.torocraft.jakel.spells.SpellData;

public class ContainerSpell extends Container {

  private final IInventory inventory;
  private final SpellData spell;
  private final int heldItemIndex;

  public ContainerSpell(EntityPlayer player, ItemStack spellStack, World world) {
    this.spell = CapabilitySpell.get(spellStack);
    this.inventory = spell.inventory;
    heldItemIndex = player.inventory.currentItem;
    inventory.openInventory(player);
    placeSlots(player, inventory);
    addListener(getListener());
  }

  private IContainerListener getListener() {
    return new IContainerListener() {

      @Override
      public void sendAllContents(Container containerToSend, NonNullList<ItemStack> itemsList) {

      }

      @Override
      public void sendSlotContents(Container containerToSend, int slot, ItemStack stack) {
        if (slot == 0) {
          updateElemental(stack.getItem());
        }
      }

      @Override
      public void sendWindowProperty(Container containerIn, int varToUpdate, int newValue) {

      }

      @Override
      public void sendAllWindowProperties(Container containerIn, IInventory inventory) {

      }
    };
  }

  public void updateElemental(Item item) {
    if (!(item instanceof ItemBlock)) {
      return;
    }
    Block block = ((ItemBlock) item).getBlock();

    if (Blocks.MAGMA == block) {
      spell.element = Element.FIRE;
    } else if (Blocks.ICE == block) {
      spell.element = Element.COLD;
    } else if (Blocks.OBSIDIAN == block) {
      spell.element = Element.WITHER;
    } else if (Blocks.SLIME_BLOCK == block) {
      spell.element = Element.POISON;
    } else if (Blocks.REDSTONE_BLOCK == block) {
      spell.element = Element.LIGHTNING;
    } else {
      spell.element = Element.PHYSICAL;
    }

  }

  private void placeSlots(EntityPlayer player, IInventory inventory) {
    int X_POS = 3;
    int Y_POS = 84;
    int SLOT_SPACING = 18;

    addSlotToContainer(new Slot(inventory, 0, 8, 54));
    addSlotToContainer(new Slot(inventory, 1, 85, 54));

    for (int slotIndex = 0; slotIndex < 9; slotIndex++) {

        int x = X_POS + SLOT_SPACING * slotIndex;
        int y = Y_POS + (3 * SLOT_SPACING) + 4;
      if (slotIndex != heldItemIndex) {
        addSlotToContainer(new Slot(player.inventory, slotIndex, x, y));
      } else {
        addSlotToContainer(new LockedSlot(player.inventory, slotIndex, x, y));
      }
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
    inventory.closeInventory(player);
    if (player instanceof EntityPlayerMP) {
      EntityPlayerMP playerMp = (EntityPlayerMP)player;
      MessageSyncSpell message = new MessageSyncSpell(spell);
      Jakel.NETWORK.sendTo(message, playerMp);
    }
  }

  static class LockedSlot extends Slot {

    public LockedSlot(IInventory inventory, int slotIndex, int x, int y) {
      super(inventory, slotIndex, x, y);
    }

    public boolean canTakeStack(EntityPlayer playerIn) {
      return false;
    }
  }
}
