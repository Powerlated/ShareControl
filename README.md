# ShareControl
A plugin to control players in creative mode.

Powerlated's fork.

##Features

- MySQL and SQLite support
- Block place/break and item use
- Disable item drop/pickup
- Disable TNT Explosions
- Disable inventory interaction
- Disable PVP and mob attacking
- Disable Snow Golem and IronGolem creation
- Disable Mob and Chicken Eggs
- Separate inventories for survival/creative/adventure, also, save your survival status and restores it when you back from creative.
- If a block was placed by a player in creative mode, you can choose to not drop it or to protect it.
- And a lot more!

##Configuration (config.yml)
```YAML
#| GENERAL SETTINGS |
General:

#If true, then the input to the server and restart the plugin server admins will write messages about the release of a new version of the plugin.
CheckUpdates: true

#The current version of plugin.
Version: there will be a version

#Choose language, files of languages are in directory "languages", enter here the name of the file
Language: en

#Database. SQLite or MySQL or YAML
Database: sqlite
#Save interval of database.
SaveInterval: 5

#Information for MySQL.
MySQL:
 Host: localhost
 Port: '3306'
 Database: minecraft
 Username: minecraft
 Password: ''
 TableName: blocks

#| Setting of notifications |

Notifications:
#If true, then when trying to open a block-container, break the illegal blocks, put them etc. message will be displayed to the creative player. All message to creative player.
CreativeNotify: true

#If true, then when trying to break blocks frmo creative mode message will be displayed to the survival player. All message to survival player.
SurvivalNotify: true

#If true, then when you try to place or break block will be displayed the material of the block.
Material: true

#The prefix in the notifications.
PrefixEnabled: true

Settings: #| Settings of interaction |
Blocks: #| Blocks settings |

#The list of blocks, which players in the creative can not be put.
#Allow to put all the blocks: 'none' (without the quotes).
#ID of blocks are working. For example: '42' (with quotes)
BlockingPlacement:
- BEDROCK
- MONSTER_EGGS
- TNT
- ENDER_PORTAL_FRAME

#The list of blocks, which players in the creative can not break.
#Allow to break all the blocks: 'none' (without the quotes).
#ID of blocks are working. For example: '42' (with quotes)
BlockingBreakage:
- BEDROCK

Items: #| Items settings |

#List of items that can not be put into creative inventory.
#ID of blocks are working. For example: '42' (with quotes)
BlockingInventory:
- MONSTER_EGG
- MINECART
- BOAT
- STORAGE_MINECART
- POWERED_MINECART
- EXPLOSIVE_MINECART
- HOPPER_MINECART
- LAVA_BUCKET
- ENDER_PEARL
- EYE_OF_ENDER
- EXP_BOTTLE
- FIREBALL
- FLINT_AND_STEEL
- POTION

#Setting for prohibition commands.
BlockingCmds:
#List of prohibition commands.
List:
- kit start
#If true, then when the players in the creative mode, try to use a command from the list of prohibited, the command will be canceled and it will display a message that the command is not allowed..
Enabled: false

#If true, then the player will not be allowed in the work of interacting with the entity.
BlockingEntityInteract: true

#If true, then the player will not be allowed in the work of interacting with other players.
BlockingPlayerInteract: true

MultiInventories:
 #If true, the system MultiInventories work
 Enabled: true
 #If true, then when you change gamemode will remain inventory and when you return to this mode he will return.
 #If false, changing the gamemode inventory will be easy to clean.
 Separation: true

WorldsConfig: #| Worlds settings |

#If true, the settings below will work.
Enabled: false
#List of worlds where the player using creative (break, put, interact with mobs) will go into survival mode.
BlockingCreativeInWorlds:
- world_nether
- world_the_end

GamemodesControl: # | Gamemodes Control settings |
 #Activation of the control gamemodes.
 Enabled: false
 #If it is true, and the player has no permissions to change the game mode on this particular mode, then any attempt to change the gamemode the player will display a message telling him about it and canceled the action. Permissions for the GamemodesControl can be found here.
 #If false, it is forbidden to switch the mode to which this player is no law, only the teams that he introduced himself. (If the administrator will change the player mode, the cancellation will not happen).
 Full: true 
```
