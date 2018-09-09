package net.torocraft.jakel.gui;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.torocraft.jakel.Jakel;
import net.torocraft.jakel.api.LootApi;

@SideOnly(Side.CLIENT)
public final class GuiSpell extends GuiScreen {

  private static final int GUI_WIDTH = 148;
  private static final int GUI_HEIGHT = 230;

  private final EntityPlayer player;
  private final List<StringBuilder> lines = new ArrayList<>();

  private int guiX = 0;
  private int guiY = 0;

  public static final ResourceLocation LOCATION_BOOK_CODE_BACKGROUND = new ResourceLocation(Jakel.MODID, "textures/gui/spell.png");

  public GuiSpell(final EntityPlayer player) {
    this.player = player;
  }

  @Override
  public void initGui() {
    super.initGui();
    guiX = (width - GUI_WIDTH) / 2;
    guiY = 2;
  }

  @Override
  public void onGuiClosed() {
    super.onGuiClosed();

  }

  public void closeGui() {
    Minecraft.getMinecraft().displayGuiScreen(null);
  }

  @Override
  public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {

    if (player == null || !player.isEntityAlive()) {
      closeGui();
      return;
    }

    ItemStack heldItem = player.getHeldItem(EnumHand.MAIN_HAND);

    if (LootApi.notASpell(heldItem)) {
      closeGui();
      return;
    }

    GlStateManager.color(1, 1, 1, 1);
    Minecraft.getMinecraft().getTextureManager().bindTexture(LOCATION_BOOK_CODE_BACKGROUND);
    drawTexturedModalRect(guiX, guiY, 0, 0, GUI_WIDTH, GUI_HEIGHT);

    super.drawScreen(mouseX, mouseY, partialTicks);
  }

  @Override
  protected void actionPerformed(final GuiButton button) {
    System.out.println("*** Action performed " + button.id);
  }

  @Override
  public boolean doesGuiPauseGame() {
    return false;
  }

  private FontRenderer getFontRenderer() {
    return fontRenderer;
  }

}
