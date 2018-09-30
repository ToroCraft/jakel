package net.torocraft.jakel.util;

import java.io.IOException;
import java.io.InputStream;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.torocraft.jakel.api.EnchantApi;
import org.apache.commons.io.IOUtils;

public class ResourceUtil {

  public static String load(ResourceLocation location) throws IOException {
    InputStream is;
    if (Minecraft.getMinecraft() != null) {
      is = Minecraft.getMinecraft().getResourceManager().getResource(location).getInputStream();
    } else {
      is = EnchantApi.class.getResourceAsStream("/assets/jakel/" + location.getResourcePath());
    }
    return IOUtils.toString(is, "utf8");
  }
}
