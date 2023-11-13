## AfkDisplay

**SAKURAS-ENHANCEMENTS** :blush:
- Added '**/afkdisplay reload**' command.  This allows an Administrator to reload the configuration while the server is running.
- Added '**/afkdisplay set [Player]**' command.  This allows any administrator to set the AFK status of a player.
- Added '**/afkdisplay clear [Player]**' command.  This allows any administrator to clear the AFK status of a player.
- Added '**/afkdisplay info [Player]**' command.  This allows any administrator to check the AFK status of a player, and display the time and duration since they went AFK.
- Added '**/afkdisplay update [Player]**' command.  This allows any administrator to force a player list update for a player.
- Added '**/afkinfo [Player]**' command.  Does the same thing as /afkdisplay info, but can be used for Mods, or players, or however you like to configure it for people to see.
- ***[REWRITE]***: Complete rewrite of the Config File manager to allow the '**/afkdisplay reload**' command to work.
- **[PERMISSIONS]**: Added a security permissions for the '**/afkdisplay**' command via [Luck Permissions](https://luckperms.net/) with the AfkDisplayCommandPermissions setting the default restrictions, the '**/afk**' and '**/afkinfo**' command also has restrictions using AfkCommandPermissions/AfkInfoCommandPermissions setting as well.
- Added a placeholder **%player:afkdisplayname%** so that you can use this as a replacement for the **%player:displayname%** placeholder under other Mods, such as [Styled Playerlist](https://modrinth.com/mod/styledplayerlist "Styled Playerlist").
***NOTE that this method was designed to be fully compliant with LuckPerms Prefixes under Styled Playerlist, because the standard method for playerlist updating fails, or you can simply use %player:afk% to format names if you like.***
- Added a placeholder **%player:afkduration%** so that you can get the time (HH:MM:SS) since someone went AFK.
- Added a placeholder **%player:afktime%** so that you can get the Time/Date when someone went AFK.
- Added a placeholder **%player:afkreason%** so that you can port the Afk Reason for why someone went AFK.

## Original README:
Show which players are AFK in the player list. Fully configurable and with [Placeholder API](https://placeholders.pb4.eu/user/general/) support, **[ENHANCEMENT]** now supporting all Placeholder API formatting nodes such as: yellow or bold natively; which depreciates all of the old "color" specific settings.

![Player list with afk players](https://i.ibb.co/QvcSv1x/list.png)

## Configuration

The configuration is located in `afkdisplay.toml` inside your servers config folder.

```toml
# [ENHANCEMENT] Added a main catagory for options
[AfkDisplayOptions]
# Allows you to disable the /afk command to mark yourself or other players (only for operators) as AFK
enableAfkCommand = true
# [ENHANCEMENT] Allows you to disable the /afkinfo command to allow players to see someone's AFK status (Time, Duration).
enableAfkInfoCommand = true
# [ENHANCEMENT] config for the /afk default command permissions, configurable with Luck Perms (afkdisplay.afk) node
afkCommandPermissions = 0
# [ENHANCEMENT] config for the /afkinfo default command permissions, configurable with Luck Perms (afkdisplay.afkinfo) node (Usually for Mods)
afkInfoCommandPermissions = 2
# [ENHANCEMENT] config for the /afkdisplay default command permissions, configurable with Luck Perms (afkdisplay.afkdisplay.*) node
afkDisplayCommandPermissions = 3
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
# [ENHANCEMENT] Default reason for going AFK.
defaultReason="<gray>poof!<r>"
```