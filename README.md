# HollowBoard
![Static Badge](https://img.shields.io/badge/packet%20powered-r?style=flat)

Scoreboard creation reimagined! Shaw!

> [!WARNING]  
> HollowBoard is currently under heavy work. API might and will change in the future!

## Why HollowBoard?
HollowBoard was created as a way for both developers and server creators to have a simple and efficient way of creating Scoreboards for Minecraft. Creating Scoreboards in Spigot is of course possible but is quite complicated for advanced tasks and stores useless metadata on the server.

To prevent this, HollowBoard uses packets and a small cache for players.

## Introduction
Instead of using regular lines like Spigot, HollowBoard allows for the creation of **content components**. These **content components** are dynamic containers of one or multiple lines. In other means, a content component is basically a part of a scoreboard!

Content components allows for reusability of Scoreboard elements and way more interaction with the scoreboard with our component API

There are two kinds of components:

- **Static components:** Their lines never change
- **Dynamic components:** The lines or the amount of lines can change

Both can be used with every HollowBoard function regardless of the type.

Every **content component** then contains dynamic or static lines. A static line is a piece of text and doesn't change. A dynamic line is a line containing one or more line parts that may or may not be updated. 

Dynamic lines allow the usage of Placeholders for example

## Using the API
### Making our first board
In order to create our first board we need to create a `HollowBoard` object, this object is the root of the board.

However, creating it is very straightforward:

```java
import net.zffu.hollowboard.board.HollowBoard;

HollowBoard board = new HollowBoard("My Board Title");
```

And there is our board. By default, every board is empty so it's not very interesting now is it? So let's add some content.

Let's start by adding some header text. In order to do so, we need to create a new component and add it to the board!

The best component to use here is an `StaticComponent` since our header is not going to change, we can make one like this:

```java
import net.zffu.hollowboard.board.components.StaticComponent;

StaticComponent headerComponent = new StaticComponent(List.of(
   "§aMy Header",
   "§cHello from HollowBoard!",
   ""     
));
```

We can then add it onto our board by doing

```java
import net.zffu.hollowboard.board.HollowBoard;
import net.zffu.hollowboard.board.components.StaticComponent;

HollowBoard board = ...;
StaticComponent headerComponent = ...;

board.append(headerComponent);
```

Neat! Now in order to actually see anything, we must set the player's board to our newly created one. Once we obtain the `HollowPlayer` instance of the player, we can just do:

```java
import net.zffu.hollowboard.HollowPlayer;
import net.zffu.hollowboard.board.HollowBoard;

HollowBoard board = ...;
HollowPlayer player = ...;

player.setCurrentBoard(board);
```

The player can be obtained through the platform instance by doing the following:
```java
UUID playerUUID = ...;
HollowPlayer player = HollowBoardAPI.getInstance().getPlatformAccess().getPlayer(playerUUID);
```

Altough, for now, we can do everything in Spigot with a more complicated way. However, as stated before, HollowBoard also allows for dynamic lines! 

We can try this by for example displaying a Skript value in our line. You can make dynamic lines from two ways:
- Manually creating them trough code
- Pattern matching

Creating a dynamic line trough code can be quite tedious it is however of course possible by doing the following for example:

```java
import net.zffu.hollowboard.board.lines.parts.DynamicLineComponent;
import net.zffu.hollowboard.board.lines.parts.LinePart;

DynamicLineComponent line = new DynamicLineComponent();
LinePart.SkriptVariable mySkriptPart = new LinePart.SkriptVariable("mySkriptVariableName::%player%");

line.appendPart(mySkriptPart);
```

Or trough pattern matching:

```java
import net.zffu.hollowboard.board.lines.parts.DynamicLineComponent;

DynamicLineComponent myLineComponent = DynamicLineComponent.compileLine("{mySkriptVariable::%player}");
```

These two codes give the exact same result! We can see that using patterns is usually much faster and cleaner. However a better in-code creation will arrive soon.

We can then use any dynamic line into a `DynamicComponent`. Think of `DynamicComponent` as some sort of big box, if there are things that need updating inside, it'll update, if it's just text, it won't. This is why `DynamicComponent` is extremely useful inside the HollowBoard API.

We can make a dynamic component like this:

```java
import net.zffu.hollowboard.board.lines.parts.DynamicLineComponent;
import net.zffu.hollowboard.board.components.DynamicComponent;

DynamicLineComponent myLineComponent = ...;

DynamicComponent component = new DynamicComponent(List.of(myLineComponent));
```

You can then add this component to the board like we did before!

It is now time to make the component update. However, unlike many scoreboard APIs, you do not need to do **anything** to enable component updating in HollowBoard. Indeed! HollowBoard will automatically detect when players that view updatable components and will automatically update them.

These few component classes however do not have the full HollowBoard API yet! In order to fully use the API we must make a `BoardComponent` object! Think of `BoardComponent` as the ultimate API component and the easiest to work with on the API side.

For example, `BoardComponent` allows for things like:
- Default visibility *(if the component should be hidden or visible by default)*
- Hide or show component to specific player 
- Direct access to `HollowPlayer` instead of Bukkit player (will be available to other components soon)

We can create a `BoardComponent` by doing the following:

```java
import net.zffu.hollowboard.HollowPlayer;
import net.zffu.hollowboard.board.components.BoardComponent;

BoardComponent component = new BoardComponent() {
    @Override
    public List<String> getContent(HollowPlayer player) {
        return List.of("Hellos!");
    }
};
```

And there we have our board component! These components are by default dynamics due to the presence of visibility. Just like any other dynamic component they fully support the change of size or content without having to do any manual fixup of the scoreboard