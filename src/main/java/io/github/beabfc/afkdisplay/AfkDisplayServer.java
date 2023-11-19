package io.github.beabfc.afkdisplay;

import static io.github.beabfc.afkdisplay.data.ModData.*;

import net.fabricmc.api.DedicatedServerModInitializer;

public class AfkDisplayServer implements DedicatedServerModInitializer {

    @Override
    public void onInitializeServer() {
        AFK_DEBUG = false;
        AfkDisplayMod.initServer();
    }
}
