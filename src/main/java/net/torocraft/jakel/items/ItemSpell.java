package net.torocraft.jakel.items;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.torocraft.jakel.Jakel;
import net.torocraft.jakel.capabilites.CapabilitySpell;
import net.torocraft.jakel.gui.GuiHandler;
import net.torocraft.jakel.spells.SpellData;

@Mod.EventBusSubscriber
public class ItemSpell extends Item {

  public static final String NAME = "spell";

  public static ItemSpell INSTANCE;

  public ItemSpell() {
    setUnlocalizedName(NAME);
    setMaxDamage(1);
    setMaxStackSize(1);
    setCreativeTab(CreativeTabs.MISC);
  }

  @SubscribeEvent
  public static void init(Register<Item> event) {
    INSTANCE = new ItemSpell();
    event.getRegistry().register(INSTANCE.setRegistryName(new ResourceLocation(Jakel.MODID, NAME)));
  }

  @SideOnly(net.minecraftforge.fml.relauncher.Side.CLIENT)
  public static void registerRenders() {
    RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
    renderItem.getItemModelMesher().register(INSTANCE, 0, new ModelResourceLocation(Jakel.MODID + ":" + NAME, "inventory"));
  }

  @SideOnly(net.minecraftforge.fml.relauncher.Side.CLIENT)
  @Override
  public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    SpellData spell = CapabilitySpell.get(stack);
    tooltip.add(net.minecraft.client.resources.I18n.format(spell.getUnlocalizedName()));
  }

  @Override
  public ActionResult<ItemStack> onItemRightClick(final World world, final EntityPlayer player, final EnumHand hand) {
    player.openGui(Jakel.INSTANCE, GuiHandler.SPELL_GUI, world, 0, 0, 0);
    return super.onItemRightClick(world, player, hand);
  }

}
