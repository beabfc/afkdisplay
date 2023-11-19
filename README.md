## AfkDisplay

**SAKURAS-ENHANCEMENTS** :blush:  *(Bump to version 1.3.2)*
- Added '**/afkdisplay reload**' command.  This allows an Administrator to reload the configuration while the server is running. (Permission: afkdisplay.afkdisplay.reload)
- Added '**/afkdisplay set [Player] [Reason]**' command.  This allows any administrator to set the AFK status of a player. (Permission: afkdisplay.afkdisplay.set)
- Added '**/afkdisplay clear [Player]**' command.  This allows any administrator to clear the AFK status of a player. (Permission: afkdisplay.afkdisplay.clear)
- Added '**/afkdisplay info [Player]**' command.  This allows any administrator to check the AFK status of a player, and display the time and duration since they went AFK. (Permission: afkdisplay.afkdisplay.info)
- Added '**/afkdisplay update [Player]**' command.  This allows any administrator to force a player list update for a player. (Permission: afkdisplay.afkdisplay.update)
- Added '**/afkinfo [Player]**' command.  Does the same thing as /afkdisplay info, but can be used for Mods, or players, or however you like to configure it for people to see. (Permission: afkdisplay.afkinfo)
- [ENHANCEMENT] Added '**/afk [Reason]**' This allows any user to use a [Reason] along with setting their AKF status.
- ***[REWRITE]***: Complete rewrite of the Config File manager to allow the '**/afkdisplay reload**' command to work.
- **[PERMISSIONS]**: Added a security permissions for the '**/afkdisplay**' command via [Luck Permissions](https://luckperms.net/) with the AfkDisplayCommandPermissions setting the default restrictions, the '**/afk**' and '**/afkinfo**' command also has restrictions using AfkCommandPermissions/AfkInfoCommandPermissions setting as well.
- Added a placeholder **%afkdisplay:name%** so that you can use this as a replacement for the **%player:displayname%** placeholder under other Mods, such as [Styled Playerlist](https://modrinth.com/mod/styledplayerlist "Styled Playerlist").
***NOTE that this method was designed to be fully compliant with LuckPerms Prefixes under Styled Playerlist, because the standard method for playerlist updating fails, or you can simply use %player:afk% to format names if you like.***
- Added a placeholder **%afkdisplay:duration%** so that you can get the time since someone went AFK, with configuration for a format prefix.
- Added a placeholder **%afkdisplay:time%** so that you can get the Time/Date when someone went AFK, with configuration for a format prefix.
- Added a placeholder **%afkdisplay:reason%** so that you can port the Afk Reason for why someone went AFK, with configuration for a format prefix.
- [RENAME] renamed the **'%player:afk%**' placeholder to **'%afkdisplay:afk%'** based on PatBox's feedback.  They prefer that people use **'mod_name:<example>'** for their mods.
- Added a special configuration option 'prettyDuration' to configure the AFK Duration in a more human-readable format, instead of the default (HH:mm:ss.mss) Apache format, each method has it's advantages though. ([Styled Nicknames](https://modrinth.com/mod/styled-nicknames) or [Styled Chat](https://modrinth.com/mod/styled-chat) comes to mind here)
- Added several mod / data pack conflict warnings for administrator's to help them make better decisions on what mods they want to install.

## Original README:
Show which players are AFK in the player list. Fully configurable and with [Placeholder API](https://placeholders.pb4.eu/user/general/) support, **[ENHANCEMENT]** now supporting all Placeholder API formatting nodes such as: yellow or bold natively; which depreciates all of the old "color" specific settings.

![Player list with afk players](https://i.ibb.co/QvcSv1x/list.png)

## Configuration

The configuration is located in `afkdisplay.toml` inside your servers config folder.

```toml
# [ENHANCEMENT] Added a main catagory for options
[AfkDisplayOptions]
# Allows you to disable the /afk command to mark yourself or other players (only for operators) as AFK (Default: true)
enableAfkCommand = true
# [ENHANCEMENT] Allows you to disable the /afkinfo command to allow players to see someone's AFK status (Time, Duration). (Default: true)
enableAfkInfoCommand = true
# [ENHANCEMENT] config for the /afk default command permissions, configurable with Luck Perms (afkdisplay.afk) node (Default: 0)
afkCommandPermissions = 0
# [ENHANCEMENT] config for the /afkinfo default command permissions, configurable with Luck Perms (afkdisplay.afkinfo) node (Usually for Mods) (Default: 2)
afkInfoCommandPermissions = 2
# [ENHANCEMENT] config for the /afkdisplay default command permissions, configurable with Luck Perms (afkdisplay.afkdisplay with subcommand nodes) node (Default: 3)
afkDisplayCommandPermissions = 3
# The default "timeout" AFK reason (Default: "<i><gray>timeout<r>")
afkTimeoutString = "<i><gray>timeout<r>"

[packetOptions]
# The time without actions after which a player is considered AFK. Set to -1 to disable automatic AFK detection. (Default: 180)
timeoutSeconds = 180
# Consider players that moved no longer AFK (enables easy bypass methods like AFK pools) (Default: false)
resetOnMovement = false
# Consider players which looked around no longer AFK (Default: false)
resetOnLook = false

[PlaceholderOptions]
# This will be the value of the placeholder %afkdisplay:afk% if a player is AFK, [ENHANCEMENT] option now accepts full formatting nodes (Default: "<i><gray>[AFK]<r>")
#   -- note; *DOES NOT* places an "<r>" at the end in the code (trying not to modify its original behavior)
afkPlaceholder = "<i><gray>[AFK]<r>"
# [ENHANCEMENT] a new placeholder %afkdisplay:name% for backporting the entire %displayname% for use in other Mods, such as Styled Playerlist (Default: "<i><gray>[AFK] %player:displayname_unformatted%<r>")
#   -- note; *DOES NOT* places an "<r>" at the end in the code (trying not to modify its default behavior)
afkDisplayNamePlaceholderAfk = "<i><gray>[AFK] %player:displayname_unformatted%<r>"
# [ENHANCEMENT] value for when player is NOT AFK, (ie. the default "%player:displayname%")
#   -- note; *DOES NOT* places an "<r>" at the end in the code (trying not to modify its default behavior)
afkDisplayNamePlaceholder = "%player:displayname%"
# Adds a formatting prefix node for %afkdisplay:duration% (default: <green>) -- note; places an "<r>" at the end in the code.
afkDurationPlaceholderFormatting = "<green>"
# Adds a formatting prefix node for %afkdisplay:time% (default: <green>) -- note; places an "<r>" at the end in the code.
afkTimePlaceholderFormatting = "<green>"
# Adds a formatting prefix node for %afkdisplay:reason% (default: none) -- note; places an "<r>" at the end in the code.
afkReasonPlaceholderFormatting = ""

[playerListOptions]
# Change the playerlist name for players who are AFK (Default: true)
enableListDisplay = true
# The name that is shown in the playerlist if a player is AFK, [ENHANCEMENT] now accepts formatting nodes (Default: "<i><gray>[AFK] %player:displayname%<r>") *NOTE that this function works when not using Player List mods!*
afkPlayerName = "<i><gray>[AFK] %player:displayname%<r>"

[messageOptions]
# Enabled chat messages when a player goes AFk or returns. (Default: true)
enableChatMessages = true
# The message content when a player goes AFK [ENHANCEMENT] now accepts formatting nodes (Default: "%player:displayname% <yellow>is now AFK<r>")
wentAfk = "%player:displayname% <yellow>is now AFK<r>"
# The messages content when a player returns from AFK [ENHANCEMENT] now accepts formatting nodes. (Default: "%player:displayname% <yellow>is no longer AFK<r>")
returned = "%player:displayname% <yellow>is no longer AFK<r>"
# [ENHANCEMENT] Re-Formats the "duration" in chat messages in a more human legible format. (Default: true)
prettyDuration=true
# [ENHANCEMENT] Default reason for going AFK via the /afk command. (Default: "<gray>poof!<r>")
defaultReason="<gray>poof!<r>"
```