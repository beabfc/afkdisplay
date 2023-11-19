package io.github.beabfc.afkdisplay;

import static io.github.beabfc.afkdisplay.data.ModData.*;

import net.fabricmc.api.ModInitializer;

public class AfkDisplayMain implements ModInitializer {

    @Override
    public void onInitialize() {
        AFK_DEBUG = true;
        AfkDisplayMod.initMain();
    }
}
