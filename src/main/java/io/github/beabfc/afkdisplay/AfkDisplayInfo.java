package io.github.beabfc.afkdisplay;

import static io.github.beabfc.afkdisplay.AfkDisplay.*;

import eu.pb4.placeholders.api.TextParserUtils;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.text.Text;

public class AfkDisplayInfo {
    public static void getVersion() {
        final ModContainer CONTAINER = FabricLoader.getInstance().getModContainer(MOD_ID).get();
        MOD_VERSION = CONTAINER.getMetadata().getVersion().getFriendlyString();
    }

    public static Text getModInfo() {
        String ModInfo1 = MOD_ID + "-" + MOD_VERSION;
        String ModInfo2 = "Author: <green>" + MOD_AUTHO_STRING + "<r>";
        Text info = TextParserUtils.formatText(ModInfo1 + "\n" + ModInfo2);
        return info;
    }
}
