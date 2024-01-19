# Lambda

---
<img align="right" width="100" height="100" src="img/logo.png">

Lambda is a **server-side** mod that adds various commands.  
Feel free to suggest other commands on [GitHub](https://github.com/kgriff0n/lambda) or [Discord](https://discord.gg/ZeHm57BEyt)!
<br>

## Config

---
You can find `lambda.properties` file in the config folder. This file allows you to change to text ouput of the commands.
This mod uses [fabric-permissions-api](https://github.com/lucko/fabric-permissions-api), so you can easily manage permissions with any compatible permission manager mod.

## Commands list

---

### Utility commands

| Command        | Arguments | Description                                  | Permission                |
|:---------------|:---------:|----------------------------------------------|---------------------------|
| `/anvil`       |     -     | opens an anvil                               | *lamba.util.anvil*        |
| `/cartography` |     -     | opens a cartography table                    | *lamba.util.cartography*  | 
| `/craft`       |     -     | opens a crafting table                       | *lamba.util.craft*        |
| `/disposal`    |     -     | opens a trash to destroy your items          | *lamba.util.disposal*     |
| `/enchanting`  |     -     | opens an enchanting table                    | *lamba.util.enchanting*   |
| `/ec`          |     -     | opens your enderchest                        | *lamba.util.&#8203;ec*    |
| `/enderchest`  |     -     | opens your enderchest                        | *lamba.util.enderchest*   |
| `/grindstone`  |     -     | opens a grindstone                           | *lamba.util.grindstone*   |
| `/loom`        |     -     | opens a loom                                 | *lamba.util.loom*         |
| `/r`           |     -     | reply to your last recipient                 | *lamba.util.reply*        |
| `/smithing`    |     -     | opens a smithing table                       | *lamba.util.smithing*     |
| `/stonecutter` |     -     | opens a stonecutter                          | *lamba.util.stonecutter*  |
| `/top`         |     -     | teleports you to the highest block above you | *lamba.util.top*          |
| `/trash`       |     -     | opens a trash to destroy your items          | *lamba.util.trash*        |

<br>
<br>

### Miscellaneous commands

| Command      |      Arguments       | Description                           | Permission              |
|:-------------|:--------------------:|---------------------------------------|-------------------------|
| `/coinflip`  | selection of players | throws a coin                         | *lambda.misc.coinflip*  |
| `/hat`       |          -           | puts your selected item on your head  | *lambda.misc.hat*       |
| `/head`      |     player name      | get the head of a specified player    | *lambda.misc.head*      |
| `/lightning` | selection of players | strikes players with a lightning bolt | *lambda.misc.lightning* |
| `/skull`     |     player name      | get the head of the given player      | *lambda.misc.skull*     |

<br>
<br>

### Administrator commands

| Command      |      Arguments       | Description                                                                                             | Permission                                             |
|:-------------|:--------------------:|---------------------------------------------------------------------------------------------------------|--------------------------------------------------------|
| `/broadcast` | selection of players | displays a message in the chat (support `&` for color codes)<sup>[1](#code-format)</sup>                | *lambda.admin.broadcast*                               |
| `/ecsee`     |   a single player    | allows you to open another player enderchest, and manage its contents                                   | *lambda.admin.ecsee*                                   |
| `/feed`      |   a single player    | feeds a specified player                                                                                | *lambda.admin.feed*                                    |
| `/fly`       |   a single player    | toggles fly of the specified player                                                                     | *lambda.admin.fly*                                     |
| `/heal`      |   a single player    | heals a specified player                                                                                | *lambda.admin.heal*                                    |
| `/invsee`    |   a single player    | allows you to open another player's inventory<sup>[2](#invsee-interface)</sup>, and manage its contents | *lambda.admin.invsee*                                  |
| `/lambda`    |  `ìnfo` or `reload`  | get mod information, and reload configuration file                                                      | *lambda.admin.&#8203;info* <br/> *lambda.admin.reload* |

<br>

<details>
<summary>Footnotes</summary>

<sup><a name="code-format">1</a></sup>  
Add `&` before these characters to get the right color  
![code-format](img/code-format.png)

<sup><a name="invsee-interface">2</a></sup>  
![invsee-interface](img/invsee-interface.png)

</details>