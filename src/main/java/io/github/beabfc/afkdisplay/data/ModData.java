package io.github.beabfc.afkdisplay.data;

import java.util.Collection;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.metadata.ContactInformation;
import net.fabricmc.loader.api.metadata.Person;

public class ModData {
    public static final String AFK_MOD_ID = "afkdisplay";
    public static String AFK_VERSION;
    public static String AFK_MC_VERSION;
    // Data pulled from fabric.mod.json
    public static EnvType AFK_ENV;
    public static String AFK_NAME;
    public static String AFK_DESC;
    public static Collection<Person> AFK_AUTHO;
    public static Collection<Person> AFK_CONTRIB;
    public static ContactInformation AFK_CONTACTS;
    public static Collection<String> AFK_LICENSES;

    public static String AFK_AUTHO_STRING;
    public static String AFK_CONTRIB_STRING;
    // NOT USED: public static String AFK_HOMEPAGE_STRING;
    public static String AFK_SOURCES_STRING;
    public static String AFK_LICENSES_STRING;
}
