package net.torocraft.jakel.items;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.torocraft.jakel.Jakel;
import net.torocraft.jakel.api.AttackApi;
import net.torocraft.jakel.capabilites.CapabilitySpell;
import net.torocraft.jakel.spells.SpellData;
import net.torocraft.jakel.spells.SpellTarget;

@Mod.EventBusSubscriber
public class ItemMissileSpell extends ItemSpell {

  public static final String NAME = Items.MISSILE.toString().toLowerCase() + "_spell";

  public static ItemMissileSpell INSTANCE;

  public ItemMissileSpell() {
    super();
    setUnlocalizedName(NAME);
  }

  @SubscribeEvent
  public static void init(Register<Item> event) {
    INSTANCE = new ItemMissileSpell();
    event.getRegistry().register(INSTANCE.setRegistryName(new ResourceLocation(Jakel.MODID, NAME)));
  }

  @SideOnly(net.minecraftforge.fml.relauncher.Side.CLIENT)
  public static void registerRenders() {
    RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
    renderItem.getItemModelMesher().register(INSTANCE, 0, new ModelResourceLocation(Jakel.MODID + ":" + NAME, "inventory"));
  }

  @Override
  public void cast(EntityPlayer player, ItemStack spellStack, SpellTarget target) {
    SpellData spell = CapabilitySpell.get(spellStack);
    AttackApi.magicMissile(spell.element, 1f, player, target.casterPosition, target.casterLook, 0, 0.4f);
  }
}
