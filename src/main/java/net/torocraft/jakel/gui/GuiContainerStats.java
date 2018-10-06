package net.torocraft.jakel.gui;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.torocraft.jakel.Jakel;

public class GuiContainerStats extends GuiContainer {

  public static final ResourceLocation LOCATION_BOOK_CODE_BACKGROUND = new ResourceLocation(Jakel.MODID, "textures/gui/spell.png");

  private static final int GUI_WIDTH = 207;
  private static final int GUI_HEIGHT = 214;

  private int guiX = 0;
  private int guiY = 0;

  public GuiContainerStats(EntityPlayer player, World world) {
    super(new ContainerStats(player, world));
  }

  @Override
  public void initGui() {
    super.initGui();
    guiX = (width - GUI_WIDTH) / 2;
    guiY = 2;
  }

  @Override
  protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
//    GlStateManager.color(1, 1, 1, 1);
//    Minecraft.getMinecraft().getTextureManager().bindTexture(LOCATION_BOOK_CODE_BACKGROUND);
//    drawTexturedModalRect(guiX, guiY, 0, 0, GUI_WIDTH, GUI_HEIGHT);
//    drawTextContent();
  }

  private void drawTextContent() {

  }

  private String i18n(String unlocalizedName) {
    return I18n.format(unlocalizedName);
  }

  @Override
  protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
    super.drawGuiContainerForegroundLayer(mouseX, mouseY);
  }

}