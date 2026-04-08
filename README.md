# Reverse Crafting

A Minecraft Fabric mod that turns the entire game progression backwards. All crafting recipes are inverted, block drops are reversed, and the tool hierarchy is flipped.

## Features

- **Reversed Block Drops**: Punching logs gives planks directly. Coal ore drops diamonds. Diamond ore drops coal.
- **Inverted Crafting**: Craft planks together to make logs. Sticks combine into planks. 9 coal creates a diamond.
- **Backwards Smelting**: Smelting iron ingots produces raw iron. Stone smelts into cobblestone.
- **Flipped Tool Progression**: Diamond tools are the starting tier (crafted from coal). Stone tools are the endgame (require diamonds and blaze rods).
- **Reversed Food**: Cooked food can be crafted back into raw food.

## Reversed Recipes

| Input | Output |
|-------|--------|
| 4 Planks (2x2) | 1 Log |
| 2 Sticks (vertical) | 1 Plank |
| 9 Coal (3x3) | 1 Diamond |
| 1 Diamond | 9 Coal |
| Cooked Meat | Raw Meat |
| Iron Ingot (smelt) | Raw Iron |
| Coal + Sticks | Diamond Tools |
| Diamonds + Blaze Rods | Stone Tools |

## Requirements

- Minecraft 1.21.1
- Fabric Loader 0.16+
- Fabric API

## Installation

1. Install Fabric for Minecraft 1.21.1
2. Download the JAR from `build/libs/`
3. Place it in your `.minecraft/mods/` folder
4. Launch Minecraft with the Fabric profile

## Building

```bash
gradle build
```

The compiled JAR will be in `build/libs/`.
