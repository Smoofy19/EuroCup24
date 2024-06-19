package de.smoofy.eurocup.fan

import de.smoofy.eurocup.builder.SkullBuilder
import org.bukkit.inventory.ItemStack

/*
 * Copyright ©
 * @author - Smoofy
 * @GitHub - https://github.com/Smoofy19
 * @Twitter - https://twitter.com/Smoofy19
 * Created - 24.11.2023 - 17:27
 */

/**

 */

enum class Team(val teamId: Int, val countryCode: String, val gradient: String, val group: Group, val texture: String) {

    GERMANY(3196, "GER", "<gradient:#000000:#FF0000:#FFCC00>", Group.A, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWU3ODk5YjQ4MDY4NTg2OTdlMjgzZjA4NGQ5MTczZmU0ODc4ODY0NTM3NzQ2MjZiMjRiZDhjZmVjYzc3YjNmIn19fQ==", ),
    SWITZERLAND(38, "SUI", "<gradient:#DA291C:#FFFFFF>", Group.A, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzVjMjdlZDliOGNmNmYzNzgyODE1MGRmMDJjNDg2Nzk5ODlhZTcwODI5ODY2Y2U2OThjMjVhMzdjNjY2ZGUyIn19fQ=="),
    SCOTLAND(5271, "SCO", "<gradient:#005EB8:#FFFFFF>", Group.A, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGFkYzM3NzgxNjM4OWMzYzg3YzY1ZGNhY2FjMWQ4Zjg4MGI1NDMzNGQ3YzIzZWEyMmYwOTllMmM0ZWFiMWZmOSJ9fX0="),
    HUNGARY(6167, "HUN", "<gradient:#CE2939:#FFFFFF:#477050>", Group.A, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGE5YzNjNGI2YzUwMzEzMzJkZDJiZmVjZTVlMzFlOTk5ZjhkZWZmNTU0NzQwNjVjYzg2OTkzZDdiZGNkYmQwIn19fQ=="),

    SPAIN(170, "ESP", "<gradient:#AA151B:#F1BF00>", Group.B, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzJkNzMwYjZkZGExNmI1ODQ3ODNiNjNkMDgyYTgwMDQ5YjVmYTcwMjI4YWJhNGFlODg0YzJjMWZjMGMzYThiYyJ9fX0="),
    CROATIA(146, "CRO", "<gradient:#ff0000:#FFFFFF:#012169>", Group.B, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjA1MGMwNGVjOGNhYmNlNzFkNzEwM2YzZTllZjRiYjg4MTlmOWYzNjVlYjMzNWE5MTM5OTEyYmMwN2VkNDQ1In19fQ=="),
    ITALY(3203, "ITA", "<gradient:#008C45:#F4F9FF:#CD212A>", Group.B, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODVjZTg5MjIzZmE0MmZlMDZhZDY1ZDhkNDRjYTQxMmFlODk5YzgzMTMwOWQ2ODkyNGRmZTBkMTQyZmRiZWVhNCJ9fX0="),
    ALBANIA(6169, "ALB", "<gradient:#DA291C:#000000>", Group.B, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzNiODNhODlkODllNTNhZWRkZmJmNjg1MGZhYjNhM2I1MjAyMWZhNDFkNzM0ODEwZDE5YmI5MjVmNGJjODgxYiJ9fX0="),

    ENGLAND(3197, "ENG", "<gradient:#CE1124:#FFFFFF>", Group.C, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmVlNWM4NTBhZmJiN2Q4ODQzMjY1YTE0NjIxMWFjOWM2MTVmNzMzZGNjNWE4ZTIxOTBlNWMyNDdkZWEzMiJ9fX0="),
    SLOVENIA(6170, "SVN", "<gradient:#FFFFFF:#003DA5:#FF0000>", Group.C, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWEyODg4NTUxZjk1MTVjNGViMmY4ZjI5ZWFmZjhlN2QwOGU5Yzk3MjFkNDc4ZmRjYmE1MDZiMWMxM2EwMGM1NSJ9fX0="),
    DENMARK(758, "DEN", "<gradient:#C8102E:#FFFFFF>", Group.C, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTBjMjMwNTVjMzkyNjA2ZjdlNTMxZGFhMjY3NmViZTJlMzQ4OTg4ODEwYzE1ZjE1ZGM1YjM3MzM5OTgyMzIifX19"),
    SERBIA(5592, "SRB", "<gradient:#C6363C:#0C4076:#FFFFFF>", Group.C, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzY0NjExNjVlNDhiODZjNTZiYjk4ZjQ4YjIwMWFlZjA1YTMwYzkxNGU5MGY0NTE1ZjA1MjE5YzY4MjdlN2UxZCJ9fX0="),

    FRANCE(144, "FRA", "<gradient:#002654:#FFFFFF:#ED2939>", Group.D, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTEyNjlhMDY3ZWUzN2U2MzYzNWNhMWU3MjNiNjc2ZjEzOWRjMmRiZGRmZjk2YmJmZWY5OWQ4YjM1Yzk5NmJjIn19fQ=="),
    NETHERLANDS(4353, "NED", "<gradient:#C8102E:#FFFFFF:#003DA5>", Group.D, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzIzY2YyMTBlZGVhMzk2ZjJmNWRmYmNlZDY5ODQ4NDM0ZjkzNDA0ZWVmZWFiZjU0YjIzYzA3M2IwOTBhZGYifX19"),
    AUSTRIA(4354, "AUT", "<gradient:#EF3340:#FFFFFF:#EF3340>", Group.D, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2Q1ZWU2NzEyN2ZlZWM0ODM0NmRhYmE4ODRhZGM5M2Q2ZmEzYThkM2QwZjBlODRmYWNhYWJiZGNhOTdhYTdmZiJ9fX0="),
    POLAND(1410, "POL", "<gradient:#DC143C:#FFFFFF>", Group.D, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTIxYjJhZjhkMjMyMjI4MmZjZTRhMWFhNGYyNTdhNTJiNjhlMjdlYjMzNGY0YTE4MWZkOTc2YmFlNmQ4ZWIifX19"),

    BELGIUM(5587, "BEL", "<gradient:#2D2926:#FFCD00:#C8102E>", Group.E, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzhlMGUwZGU3MDVkMzY2YmE1OTUwNmIzZmIxYTA0ZmQzZDUxYWZmOTBlNWNmYjRlYmJlYTY2ZWE1OTc2YzBmZiJ9fX0="),
    SLOVAKIA(3201, "SVK", "<gradient:#FFFFFF:#0B4EA2:#EE1C25>", Group.E, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmM3MmE4YzExNWExZmI2NjlhMjU3MTVjNGQxNWYyMjEzNmFjNGMyNDUyNzg0ZTQ4OTRiM2Q1NmJjNWIwYjkifX19"),
    ROMANIA(6171, "ROU", "<gradient:#002B7F:#FCD116:#CE1126>", Group.E, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGNlYjE3MDhkNTQwNGVmMzI2MTAzZTdiNjA1NTljOTE3OGYzZGNlNzI5MDA3YWM5YTBiNDk4YmRlYmU0NjEwNyJ9fX0="),
    UKRAINE(3204, "UKR", "<gradient:#0057B7:#FFDD00>", Group.E, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjhiOWY1MmUzNmFhNWM3Y2FhYTFlN2YyNmVhOTdlMjhmNjM1ZThlYWM5YWVmNzRjZWM5N2Y0NjVmNWE2YjUxIn19fQ=="),

    PORTUGAL(3198, "POR", "<gradient:#046A38:#DA291C>", Group.F, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWJkNTFmNDY5M2FmMTc0ZTZmZTE5NzkyMzNkMjNhNDBiYjk4NzM5OGUzODkxNjY1ZmFmZDJiYTU2N2I1YTUzYSJ9fX0="),
    TURKEY(3205, "TUR", "<gradient:#C8102E:#FFFFFF>", Group.F, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTg1MmI5YWJhMzQ4MjM0ODUxNGMxMDM0ZDBhZmZlNzM1NDVjOWRlNjc5YWU0NjQ3Zjk5NTYyYjVlNWY0N2QwOSJ9fX0="),
    CZECH_REPUBLIC(141, "CZE", "<gradient:#11457E:#FFFFFF:#D7141A>", Group.F, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDgxNTJiNzMzNGQ3ZWNmMzM1ZTQ3YTRmMzVkZWZiZDJlYjY5NTdmYzdiZmU5NDIxMjY0MmQ2MmY0NmU2MWUifX19"),
    GEORGIA(6239, "GEO", "<gradient:#DA291C:#FFFFFF>", Group.F, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGYwNWY5OWUzNDMzMDNjMzVhOGIwYjY4ZTRiY2M2YzM4Yzk0ZTk5MzE5YjlhZGZiYWUyYTEzODU5MTU4NGM4MCJ9fX0="),

    NONE(-1, "", "<yellow>", Group.NONE, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmFmZWMzYzdmYWIyYTM3MjkwNWZhM2ZjMWZiMjI4ZTc4M2JiOTNhNTNlNmQzMmU0NWViZGI0YTAyMThmY2NhIn19fQ==");

    companion object {
        fun team(name: String): Team {
            return entries.firstOrNull { it.name == name } ?: NONE
        }

        fun team(id: Int): Team {
            return entries.firstOrNull { it.teamId == id } ?: NONE
        }
    }

    enum class Group(val texture: String) {

        A("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTY3ZDgxM2FlN2ZmZTViZTk1MWE0ZjQxZjJhYTYxOWE1ZTM4OTRlODVlYTVkNDk4NmY4NDk0OWM2M2Q3NjcyZSJ9fX0="),
        B("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTBjMWI1ODRmMTM5ODdiNDY2MTM5Mjg1YjJmM2YyOGRmNjc4NzEyM2QwYjMyMjgzZDg3OTRlMzM3NGUyMyJ9fX0="),
        C("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWJlOTgzZWM0NzgwMjRlYzZmZDA0NmZjZGZhNDg0MjY3NjkzOTU1MWI0NzM1MDQ0N2M3N2MxM2FmMThlNmYifX19"),
        D("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzE5M2RjMGQ0YzVlODBmZjlhOGEwNWQyZmNmZTI2OTUzOWNiMzkyNzE5MGJhYzE5ZGEyZmNlNjFkNzEifX19"),
        E("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGJiMjczN2VjYmY5MTBlZmUzYjI2N2RiN2Q0YjMyN2YzNjBhYmM3MzJjNzdiZDBlNGVmZjFkNTEwY2RlZiJ9fX0="),
        F("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjE4M2JhYjUwYTMyMjQwMjQ4ODZmMjUyNTFkMjRiNmRiOTNkNzNjMjQzMjU1OWZmNDllNDU5YjRjZDZhIn19fQ=="),
        NONE("");
    }

    class Cache {

        init {
            for (team in entries) {
                skullCache[team] = SkullBuilder(team.texture).build()
                groupCache[team.group] = SkullBuilder(team.group.texture).build()
            }
        }

        companion object {
            private val skullCache: MutableMap<Team, ItemStack> = mutableMapOf()
            private val groupCache: MutableMap<Group, ItemStack> = mutableMapOf()

            fun skull(team: Team): ItemStack {
                return skullCache[team]!!
            }

            fun group(group: Group): ItemStack {
                return groupCache[group]!!
            }
        }
    }
}