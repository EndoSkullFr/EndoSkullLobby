package fr.bebedlastreat.endoskullnpc.utils;

import java.util.HashMap;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

public class NPCSpawnManager {
    private static final HashMap<Integer, NPCServer> npc = new HashMap<>();

    public static HashMap<Integer, NPCServer> getNPC() {
        return npc;
    }

    public NPCSpawnManager() {
        World world = Bukkit.getWorld("Lobby");
        (new NPCServer(
                UUID.fromString("01ca3af5-31eb-4621-95e1-ee0b9512a45c"), new Location(world, -238.5, 63, -261.5, 135, 0), "BedWars", null, true, true, "ewogICJ0aW1lc3RhbXAiIDogMTY0OTY3NzYyMDc5NSwKICAicHJvZmlsZUlkIiA6ICJkMmI3YjRlMGQzZWQ0MjMwYWM4MzFhYTMyZGM5NzY3NiIsCiAgInByb2ZpbGVOYW1lIiA6ICJCZWJlRGxhU3RyZWF0IiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2ZlNjk3OGNlYzFlYWY1YjkwYWUxNTMxYzMwY2Q5Y2RjNzc4ZDE4YWUyNWVkNTI5NmQyMjRkOWFmZGMwODlkMmEiCiAgICB9CiAgfQp9", "FKvmXhtg7GJfzYne2qw/UZYeYx4L8PDpJ6/ffVzbC2lsv/p6iLHxslMT5bO2ZzX50Ex5nHKapQvwwdSe1Qy5IbmnQXymOgLU98j7vyCsib9JEb1Ol2sLwekuEHM3t+d1ArjgX0lvgvx5ZY7nup7U3WPwIneQGNAUtlDvQJi96icjZyM0dE2a9M+nJH5RHIVN81UmL6wkxQx+9rnStftlrTWnVWvkX/yu9216ptEJIsZV7BUSZfItifZ3NoZUziKCwFckWmnfe1Ils/iyd/Zd5/7vb3HJ5KEvAzfyh4qZ2SngiXOZcHxeLEYNWk4xbofC6k/L78u8DjkgEZoN9rLrwKZZFDYThg4I6J6s5Lq/fmlEwgzcaqNwIoiY8ZZL3h8BPbe+MpcGbIPWpZH4yYCMsDkY02KbT0DWGIEy5vV1zfxjX/svkDofc7dOnjMZc+iD4DckTRpjekkKTiwZlyVP8SBIu4mi5JBedvYnhKfikMHyatGAzd/3zvxZrfh00Z56M5CI4fd7kQG/tNPoWlSn5H1ksGbZ6Lsw9LSjDVByGLJiKRRbAue8qUX6qHDw7GTQkd7et5F5t6XH5wJZPfJF06ncuT59gphowK7POCzNVOSN+GiUGTx3Z6/F9d/u07nd3GUf5kv8ltibNn/x/jhTre/uojbLULF/Sx5yZDxnp1k="))
                .build();
        (new NPCServer(
                UUID.fromString("4ef7a5ea-e425-46f2-b9e3-11ac54274153"), new Location(world, -246.5, 63, -253.5, 135, 0), "PvpKit", Material.WOOD_SWORD, true, true, "ewogICJ0aW1lc3RhbXAiIDogMTY0OTY3ODAzNjcyNCwKICAicHJvZmlsZUlkIiA6ICJkMmI3YjRlMGQzZWQ0MjMwYWM4MzFhYTMyZGM5NzY3NiIsCiAgInByb2ZpbGVOYW1lIiA6ICJCZWJlRGxhU3RyZWF0IiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzMyNTk2YjI1NTZkYTJiYTU4N2M0OGFhY2Q5OWQ0ZWUzOGUyNDI1MjAwOGFjM2JlYTU3MjgwODBmOTAyODdhYzkiCiAgICB9CiAgfQp9", "vAaRuxVrv5IrAXP47b2HWSmiT4QYoZUV3FDbeCPhoxYBX+Z4lJVvIXZL3rPBYVl5f+uf5DeHVINp/RtzFHik5C2rFLDT6mZPdWShuuEjE9vnExwq0EF8Scxj3jTo1VfmtHrRAd3nEQb9kbfaMmAYrJ1EkUAWXzN44PrOoKRIOlijC9D3rYhDvmk5QnHAXsZV3oQEGp3lNnGW7jp0kj5gyWgKdU7/ntyDRjW6Q5PbOZFJCvueHjc525/N/xxz9ekapuaSyPjrFvRaKzjjXDSp8CFAmPHf/XEaF/MKhbi/TvRpxGr8h2FRzXnYXhPsFMI4rq+vb4TKrpXaiGSFGH9h4Pz6SUXDVZ1h7uKfIrZouf5IiSOrs9Z8MJFBAqVZgZE/cU+jlEfplwVyQTdG/j33Vh2v4J2eon4nLNfl/vTZgGX7TD/N6lafgI2mXWJq48ZnV4RXB8v6MHXpOhcXJ8+wHv7fAC17c5O6APM0fR3c/43ENDcoBYrk9LqSGdWuBGu3uIPOB0r/Jmbo1Xyd1QgI/86kqqmcuJYHgpCu7KIKbNHnPGxuIxYrI9YX6OKwl3UNT8ZqVMbl7Huv3gYe2jul+Qq3IkG5BvZyBtAm07NOXDhIXqiIknuc1R1BP52pbpTRP4qqCAGK2ME8Qp5QSld/iI5EEpR0AYH0EzcFs8Wvjko="))
                .build();
        (new NPCServer(
                UUID.fromString("cdc0dd39-3935-4be1-964b-b717465cd505"), new Location(world, -268.5, 62.0, -281.5), "Lobby", null, true, true, "ewogICJ0aW1lc3RhbXAiIDogMTY0OTY3NTQxMTUzMCwKICAicHJvZmlsZUlkIiA6ICJkMmI3YjRlMGQzZWQ0MjMwYWM4MzFhYTMyZGM5NzY3NiIsCiAgInByb2ZpbGVOYW1lIiA6ICJCZWJlRGxhU3RyZWF0IiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2ExYWExNGRlOWMxNThkMzUxMzk0OWM4NWNhYmI5ZTM3ODQ4Yjk4NGVjM2ZiNWI0NGEzMjE5ZjRmYjU4YTAwZGEiCiAgICB9CiAgfQp9", "fkTSH3guafA4+2JFMSE9KlT/X7ccyX2a2qczs5UOizJoLuIYRMo3HHbnpHFA0yfzpKnPD4y0QHHhCA8RtXIyhVEn/jo/oBwQZkHUOwAtfUwe2y9iNZqSR+yZTbT7DNQ6YbIhhTq830mnfxQwnf1stEJhi1YNRaDMzc/v/LNBo/cgxzIvKg41u1/bxrQlYe7JcBP8VjYQft/suiFkJLfdP+HwcrDOdEcVZUqDQJyTqGx+S4I1brfcTKeauvlmdD4cszHR0Jyst6tCWDdwGdnUGySLbsf0VmaOxuiEdUYr0kHwxp9riNmsSxh5UF9wOZ9uFb7Ou3YKdL77q6BSYXX2KWXDPK2nwGFqmDAYoA1mv2wE2VymzQvaENfFLgYGOMyAhT4gjWfhrhKPjhEeynYY/o9RRAZcH9nsPlsFDe4z67XZmSaWk8Hr1DoHF7XhEjPx9NSLnhY8BVNAZGW4/U++mO6HNVDy4+kY6tQIoPt8Nw0ICCtYpJ3dw7DmKAEQILkujcBtdrt4ENa41dX8N71OeRCPOoaDJ0DqABlnp4szCPmYr4V49Zu5xjS8UVlIZeM7V7LMzS/2Y2iV2REwK+pgYJsAPmTsGF4nscTpsI4LBJn0KE3A00RX/4L2oohSfiqgROzY3adjIJnAUBVA4MyaZTd0eoVHrJVjoFXL8A5Fg0U="))
                .build();
    }
}
