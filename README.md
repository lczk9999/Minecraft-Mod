# Minecraft ore mod 


Minecraft ore mod This is my Minecraft mod in a simple and clear way, and it is a mod made for the Hack Craft contest where I made a mod that some ores give powers, each one a different power to the players. The way to get them is by taking the ores and smelting them and holding them in the main hand. If you want to levitate, have super strength, extra life, shoot laser beams, or become invisible, run fast, or have very high resistance, this mod does everything.Oh, just one important thing for you who are reading, I used Google Translate to translate into English, it might have gotten a bit strange because it changes the meaning of certain words.



# Mod creation process


Making creating this project was a great journey where I learned a lot about Java, for example, and I didn't know much and now I know a lot of things despite being a short time in HackTime because it only counts the typed hours and I programmed several times, tested in drafts and only then sent typed the code when it got perfect in my opinion but about the process I had difficulties with the process of understanding the words that Minecraft reads how the files work I used a Fabric template and tried many times to do like the other people who create folders for it to read the mod but I couldn't but programming and finding solutions so I programmed directly in the TemplateMod.java folder because this way I forced Minecraft to read and it worked and the most complex part of the code was the part of the redstone power it was difficult to make it understand the detection of creatures to make the laser beam work but in the end it worked out and I managed to do the Ticks to avoid crashes or lag in the world it was really an experience.

<img width="1912" height="1078" alt="image" src="https://github.com/user-attachments/assets/7146f795-7bff-4f6f-91e9-b5a3b205514c" />


# Language and things used to make the Mod

The Mod was made using certain things like the place where I programmed was VS code Visual Studio Code the language used was Java the engine used was java 25 to test the mod despite the version being java 17 because I used Fabric Loom 1.18.4 which requires java 25 but it worked out and the average of lines written was 200.



# Basic explanation of the code


The main part of the mod runs inside an event called ServerTickEvents.END_SERVER_TICK this means that every 20 seconds the code does a check on the online players and another central part that I highlight is the 'jogador.getMainHandItem().getItem()' it checks the player's hand to see if they are with any of the items and through it releases conditional if/else events to inject give the abilities to the player.



# The powers


Redstone: gives an energy beam that makes mobs catch fire Gold: gives super speed Iron: gives gravity power allows you to levitate and fall slow Emerald: gives Invisibility Diamond: gives a super defense Netherite: is the strongest among all where it gives strength life and makes you release fire particles

<img width="1896" height="1035" alt="image" src="https://github.com/user-attachments/assets/4b604815-3ffd-4f80-89a3-6d98d8a450bf" />


# Aids and the part where they were most used


The code creation of things ideas creation of folders was all done by me and typed by me but at the time of making the directional vectors of the beam power I had to research a lot to understand and make it so I found videos and based myself on them to help me make those parts and in some errors but only that in summary I used getLookAngle() this function serves for Minecraft to understand the coordinates that the player is looking then I made a code using this function to work in summary anything you can access my code file to understand the programming.



So my Mod is this I hope it is good I had difficulties to make it but it worked out and it is available to download on Modrinth and for more information there are the other files here in this GitHub library.
