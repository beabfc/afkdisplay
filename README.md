# AfkDisplay

Show which players are afk in the player list.

![Player list with afk players](https://i.ibb.co/QvcSv1x/list.png)

## Configuration

The configuration is located in `afkdisplay.toml` inside your servers config folder.

- `timeoutSeconds` the amount of time in seconds after which a player will be considered afk _(default: 180)_
- `resetOnMovement` consider players that moved no longer afk (enables easy bypass methods like afk pools, _default:
  false_)
- `resetOnLook` consider players which looked around no longer afk _(default: false)_
- `afkColor` the color which will be used for afk players in the player list _(default: gray)_
- `afkPrefix` a prefix which will be prepended to afk players name in the player list _(default: `[AFK] `)_
