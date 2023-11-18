package io.github.beabfc.afkdisplay.placeholders;

public class PlaceholderManager {
    public static void registerPlaceholders() {
        AfkDisplayPlaceholders.registerAfk();
        AfkDisplayPlaceholders.registerAfkDisplayName();
        AfkDisplayPlaceholders.registerAfkDuration();
        AfkDisplayPlaceholders.registerAfkReason();
        AfkDisplayPlaceholders.registerAfkTime();
    }
}
