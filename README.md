## AfkDisplay

# SAKURAS-ENHANCEMENTS
- Added '/afkdisplay reload' command.  This allows an Administrator to reload the configuration while the server is running.
- Added '/afkdisplay set [Player]' command.  This allows any administrator to set the AFK status of a player.
- Added '/afkdisplay clear [Player]' command.  This allows any administrator to clear the AFK status of a player.
- [REWRITE]: Added a Config File manager to allow the /afkdisplay reload command to work.
- FIXME: Added a security permissions configuration for the '/afkdisplay' command (FOR future LP permissions support).
- Added a placeholder %player:afkdisplayname% so that you can use this as a replacement for the %player:displayname% placeholder under other Mods, such as Styled Playerlist.  **Note that this method was designed to be fully compliant with LuckPerms Prefixes under Styled Playerlist.**

Show which players are AFK in the player list. Fully configurable and with [Placeholder API](https://placeholders.pb4.eu/user/general/) support, [ENHANCEMENT] now supporting all Placeholder API formatting nodes such as: yellow or bold natively; which depreciates all of the old "color" specific settings.

![Player list with afk players](https://i.ibb.co/QvcSv1x/list.png)

## Configuration

The configuration is located in `afkdisplay.toml` inside your servers config folder.

```toml
# [ENHANCEMENT] Added a main catagory for options
[AfkDisplayOptions]
# Allows you to disable the /afk command to mark yourself or other players (only for operators) as AFK 
enableAfkCommand = true
# [ENHANCEMENT] FIXME: Trying to add a config for the /afkdisplay command permissions, I will plan to add LP support.
afkDisplayCommandPermissions = "3"
# This will be the value of the placeholder %player:afk% if a player is AFK, [ENHANCEMENT] option now accepts formatting nodes
afkPlaceholder = "<i><gray>[AFK]<r>"
# [ENHANCEMENT] a new placeholder %player:afkdisplayname% for backporting the entire %displayname% for use in other Mods, such as Styled Playerlist
afkDisplayPlaceholderAfk = "<i><gray>[AFK] %player:displayname_unformatted%<r>"
# [ENHANCEMENT] value for when player is NOT AFK, (ie. the default displayname)
afkDisplayPlaceholder = "%player:displayname%"

[packetOptions]
# The time without actions after which a player is considered AFK. Set to -1 to disable automatic AFK detection.
timeoutSeconds = 180
# Consider players that moved no longer AFK (enables easy bypass methods like AFK pools)
resetOnMovement = false
# Consider players which looked around no longer AFK
resetOnLook = false

[playerListOptions]
# Change the playerlist name for players who are AFK
enableListDisplay = true
# The name that is shown in the playerlist if a player is AFK, [ENHANCEMENT] now accepts formatting nodes
afkPlayerName = "<i><gray>[AFK] %player:displayname%<r>"

[messageOptions]
# Enabled chat messages when a player goes AFk or returns.
enableChatMessages = true
# The message content [ENHANCEMENT] now accepts formatting nodes
wentAfk = "%player:displayname% <yellow>is now AFK<r>"
returned = "%player:displayname% <yellow>is no longer AFK<r>"
```
