> ⚠️ **No longer mantained!** ⚠️ \
> As of 26/11/2023 this project is no longer maintained. \
> Please use [@sakura-ryoko](https://github.com/sakura-ryoko)'s updated fork **[sakura-ryoko/afkplus](https://github.com/sakura-ryoko/afkplus)** instead.


# AfkDisplay

Show which players are AFK in the player list. Fully configurable and with [Placeholder API](https://placeholders.pb4.eu/user/general/) support.

![Player list with afk players](https://i.ibb.co/QvcSv1x/list.png)

## Configuration

The configuration is located in `afkdisplay.toml` inside your servers config folder.

```toml
# Allows you to disable the /afk command to mark yourself or other players (only for operators) as AFK 
enableAfkCommand = true
# This will be the value of the placeholder %player:afk% if a player is AFK
afkPlaceholder = "[AFK]"

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
# The color for AFK players in the playerlist
afkColor = "gray"
# The name that is shown in the playerlist if a player is AFK
afkPlayerName = "[AFK] %player:displayname%"

[messageOptions]
# Enabled chat messages when a player goes AFk or returns.
enableChatMessages = true
# The color of those chat messages
messageColor = "yellow"
# The message content
wentAfk = "%player:displayname% is now AFK"
returned = "%player:displayname% is no longer AFK"
```
