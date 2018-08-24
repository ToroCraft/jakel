package net.torocraft.jakel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.torocraft.jakel.api.EnchantApi;
import net.torocraft.jakel.api.SpawnApi;
import net.torocraft.jakel.api.TraitApi;
import net.torocraft.jakel.traits.Trait;
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
      default:
        throw new WrongUsageException("commands.jakel.usage");
    }
  }

  private void enchant(ICommandSender sender, @Nonnull String[] args) throws CommandException {
    if (!(sender instanceof EntityPlayer)) {
      return;
    }

    EntityPlayer player = (EntityPlayer) sender;


    ItemStack firstItem = getHotBarItems(player).get(0);

    EnchantApi.enchant(firstItem);

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
      if (InventoryPlayer.isHotbar(i)) {
        items.add(inv.getStackInSlot(i));
      }
    }
    return items;
  }


  @Override
  @Nonnull
  public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender,
      String[] args, @Nullable BlockPos targetPos) {
    if (args.length == 1) {
      return getListOfStringsMatchingLastWord(args, "enchant");
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
