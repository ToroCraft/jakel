package net.torocraft.jakel;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.torocraft.jakel.capabilites.CapabilityItemData;
import net.torocraft.jakel.loot.ItemData;
import net.torocraft.jakel.loot.stat.StatModifierData;

@Mod.EventBusSubscriber(value = { Side.CLIENT }, modid = Jakel.MODID)
public class ClientEventHandlers {

  @SubscribeEvent
  public static void scaleEntity(ItemTooltipEvent event) {
   ItemData data = CapabilityItemData.get(event.getItemStack());
   if (data == null) {
     return;
   }


   if (data.modifiers != null) {
     data.modifiers.forEach(m -> renderModifierTooltip(event, m));
   }

  }

  private static void renderModifierTooltip(ItemTooltipEvent event, StatModifierData m) {
    // TODO translate

    event.getToolTip().add(m.toString());
  }

}
