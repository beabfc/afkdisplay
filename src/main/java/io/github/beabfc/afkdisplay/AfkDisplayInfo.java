package io.github.beabfc.afkdisplay;

import net.fabricmc.loader.api.ModContainer;
import net.minecraft.text.Text;
import java.util.ArrayList;

public class AfkDisplayInfo {
    private static Text[] about = new Text[0];

    public static void build(ModContainer container) {
        {
            var about = new ArrayList<Text>();
            about.addAll(about);
            about.add(Text.empty());
            about.add(Text.of(container.getMetadata().getDescription()));

        }
    }

    public static Text[] getAbout() {
        return about;
    }
}
