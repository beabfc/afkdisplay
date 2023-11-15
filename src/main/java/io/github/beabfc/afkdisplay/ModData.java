package io.github.beabfc.afkdisplay;

import java.util.Collection;

import net.fabricmc.loader.api.metadata.ModEnvironment;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.fabricmc.loader.api.metadata.Person;
import net.fabricmc.loader.impl.game.GameProvider;

public class ModData {
    public static final String MOD_ID = "afkdisplay";
    public static String MOD_VERSION;
    public static String MOD_NAME;
    public static String MOD_DESC;
    public static GameProvider MC_PROV;
    public static ModMetadata MOD_DATA;
    public static Collection<Person> MOD_AUTHO;
    public static Collection<Person> MOD_CONTRIB;
    public static Collection<Person> MOD_CONTACTS;
    public static ModEnvironment MOD_ENV;
    public static final String MOD_AUTHO_STRING = "beabfc, (forked by) sakura-ryoko";
    public static final String MOD_URL_RESOURCE = "https://github.com/beabfc/afkdisplay";
}
