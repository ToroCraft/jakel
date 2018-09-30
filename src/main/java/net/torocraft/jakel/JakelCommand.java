package net.torocraft.jakel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.asm.transformers.ItemStackTransformer;
import net.torocraft.jakel.api.EnchantApi;
import net.torocraft.jakel.capabilites.CapabilitySpell;
import net.torocraft.jakel.items.ItemSpell;
import net.torocraft.jakel.items.Items;
import net.torocraft.jakel.spells.SpellData;
import net.torocraft.jakel.traits.Type;

public class JakelCommand extends CommandBase {

  @Override
  @Nonnull
  public String getName() {
    return "jakel";
  }

  @Override
  @Nonnull
  public String getUsage(@Nullable ICommandSender sender) {
    return "commands.jakel.usage";
  }

  @Override
  public int getRequiredPermissionLevel() {
    return 2;
  }

  @Override
  public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender,
      @Nonnull String[] args) throws CommandException {

    if (args.length < 1) {
      throw new WrongUsageException("commands.jakel.usage");
    }

    String command = args[0];

    switch (command) {
      case "enchant":
        enchant(sender, args);
        return;
      case "dropSpellBooks":
        dropSpellBooks(sender, args);
        return;
      default:
        throw new WrongUsageException("commands.jakel.usage");
    }
  }

  private void dropSpellBooks(ICommandSender sender, String[] args) {
    if (!(sender instanceof EntityPlayer)) {
      return;
    }
    EntityPlayer player = (EntityPlayer) sender;
    List<ItemStack> spells = new ArrayList<>();
    for(Items itemCode: Items.values()) {

      ItemStack spell = new ItemStack(itemCode.getInstance());
      SpellData data = CapabilitySpell.get(spell);
      spells.add(spell);
    }
    dropItems(player, spells);
  }

  private void dropItems(EntityPlayer player, List<ItemStack> items) {
    for (ItemStack stack : items) {
      EntityItem dropItem = new EntityItem(player.world, player.posX, player.posY, player.posZ, stack);
      dropItem.setNoPickupDelay();
      player.world.spawnEntity(dropItem);
    }
  }

  private void enchant(ICommandSender sender, @Nonnull String[] args) throws CommandException {
    if (!(sender instanceof EntityPlayer)) {
      return;
    }

    EntityPlayer player = (EntityPlayer) sender;

    ItemStack stack = new ItemStack(net.minecraft.init.Items.WOODEN_SWORD);

    EnchantApi.enchant(stack, 26);

    List<ItemStack> l = new ArrayList<>();
    l.add(stack);
    dropItems(player, l);

    logHotBarItems(player);
  }

  private void logHotBarItems(EntityPlayer player) {
    InventoryPlayer inv = player.inventory;
    for (int i = 0; i < inv.getSizeInventory(); i++) {
      if (InventoryPlayer.isHotbar(i)) {
        ItemStack stack = inv.getStackInSlot(i);
        System.out.println(stack.getDisplayName() + " NBT: " + stack.getTagCompound());
      }
    }
  }

  private List<ItemStack> getHotBarItems(EntityPlayer player) {
    InventoryPlayer inv = player.inventory;
    List<ItemStack> items = new ArrayList<>();
    for (int i = 0; i < inv.getSizeInventory(); i++) {

      items.add(inv.getStackInSlot(i));


    }
    return items;
  }


  @Override
  @Nonnull
  public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender,
      String[] args, @Nullable BlockPos targetPos) {
    if (args.length == 1) {
      return getListOfStringsMatchingLastWord(args, "enchant", "dropSpellBooks");
    }
    String command = args[0];
    switch (command) {
      case "enchant":
        return tabCompletionsForEnchant(args);
      default:
        return Collections.emptyList();
    }
  }

  private List<String> tabCompletionsForEnchant(String[] args) {
    if (args.length == 2) {
      return getListOfStringsMatchingLastWord(args, EntityList.getEntityNameList());
    }

    if (args.length == 3) {
      return getListOfStringsMatchingLastWord(args, Arrays.asList(Type.values()));
    }

    if (args.length == 4) {
      String[] levels = new String[10];
      for (int i = 0; i < 10; i++) {
        levels[i] = Integer.toString(i, 10);
      }
      return getListOfStringsMatchingLastWord(args, levels);
    }

    return Collections.emptyList();
  }

}
