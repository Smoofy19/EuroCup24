package de.smoofy.eurocup.fan

import de.smoofy.eurocup.builder.SkullBuilder

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

    GERMANY(3196, "GER", "<gradient:#000000:#FF0000:#FFCC00>", Group.A, "5e7899b4806858697e283f084d9173fe487886453774626b24bd8cfecc77b3f"),
    SWITZERLAND(38, "SUI", "<gradient:#DA291C:#FFFFFF>", Group.A, "572ffabc40a3a82a9dbb081038f9834707b8a7a595e47d63e4c7a7e24a6c8829"),
    SCOTLAND(5271, "SCO", "<gradient:#005EB8:#FFFFFF>", Group.A, "2839ad7362dc4e09974700960fd37d7ed1713e94119b83d04f5213268606274b"),
    HUNGARY(6167, "HUN", "<gradient:#CE2939:#FFFFFF:#477050>", Group.A, "4a9c3c4b6c5031332dd2bfece5e31e999f8deff55474065cc86993d7bdcdbd0"),

    SPAIN(170, "ESP", "<gradient:#AA151B:#F1BF00>", Group.B, "c2d730b6dda16b584783b63d082a80049b5fa70228aba4ae884c2c1fc0c3a8bc"),
    CROATIA(146, "CRO", "<gradient:#ff0000:#FFFFFF:#012169>", Group.B, "b050c04ec8cabce71d7103f3e9ef4bb8819f9f365eb335a9139912bc07ed445"),
    ITALY(3203, "ITA", "<gradient:#008C45:#F4F9FF:#CD212A>", Group.B, "85ce89223fa42fe06ad65d8d44ca412ae899c831309d68924dfe0d142fdbeea4"),
    ALBANIA(6169, "ALB", "<gradient:#DA291C:#000000>", Group.B, "c3b83a89d89e53aeddfbf6850fab3a3b52021fa41d734810d19bb925f4bc881b"),

    ENGLAND(3197, "ENG", "<gradient:#CE1124:#FFFFFF>", Group.C, "bee5c850afbb7d8843265a146211ac9c615f733dcc5a8e2190e5c247dea32"),
    SLOVENIA(6170, "SVN", "<gradient:#FFFFFF:#003DA5:#FF0000>", Group.C, "dc0e2088a56cdc2c5fb71f7500a3ad61f1051be7d4a438f33e6d04ae96a086aa"),
    DENMARK(758, "DEN", "<gradient:#C8102E:#FFFFFF>", Group.C, "10c23055c392606f7e531daa2676ebe2e348988810c15f15dc5b3733998232"),
    SERBIA(5592, "SRB", "<gradient:#C6363C:#0C4076:#FFFFFF>", Group.C, "5b0483a4f0ddf4fbbc977b127b3d294d7a869f995366e3f50f6b05a70f522510"),

    FRANCE(144, "FRA", "<gradient:#002654:#FFFFFF:#ED2939>", Group.D, "51269a067ee37e63635ca1e723b676f139dc2dbddff96bbfef99d8b35c996bc"),
    NETHERLANDS(4353, "NED", "<gradient:#C8102E:#FFFFFF:#003DA5>", Group.D, "c23cf210edea396f2f5dfbced69848434f93404eefeabf54b23c073b090adf"),
    AUSTRIA(4354, "AUT", "<gradient:#EF3340:#FFFFFF:#EF3340>", Group.D, "3d5ee67127feec48346daba884adc93d6fa3a8d3d0f0e84facaabbdca97aa7ff"),
    POLAND(1410, "POL", "<gradient:#DC143C:#FFFFFF>", Group.D, "921b2af8d2322282fce4a1aa4f257a52b68e27eb334f4a181fd976bae6d8eb"),

    BELGIUM(5587, "BEL", "<gradient:#2D2926:#FFCD00:#C8102E>", Group.E, "c8e0e0de705d366ba59506b3fb1a04fd3d51aff90e5cfb4ebbea66ea5976c0ff"),
    SLOVAKIA(3201, "SVK", "<gradient:#FFFFFF:#0B4EA2:#EE1C25>", Group.E, "6c72a8c115a1fb669a25715c4d15f22136ac4c2452784e4894b3d56bc5b0b9"),
    ROMANIA(6171, "ROU", "<gradient:#002B7F:#FCD116:#CE1126>", Group.E, "dceb1708d5404ef326103e7b60559c9178f3dce729007ac9a0b498bdebe46107"),
    UKRAINE(3204, "UKR", "<gradient:#0057B7:#FFDD00>", Group.E, "28b9f52e36aa5c7caaa1e7f26ea97e28f635e8eac9aef74cec97f465f5a6b51"),

    PORTUGAL(3198, "POR", "<gradient:#046A38:#DA291C>", Group.F, "ebd51f4693af174e6fe1979233d23a40bb987398e3891665fafd2ba567b5a53a"),
    TURKEY(3205, "TUR", "<gradient:#C8102E:#FFFFFF>", Group.F, "9852b9aba3482348514c1034d0affe73545c9de679ae4647f99562b5e5f47d09"),
    CZECH_REPUBLIC(141, "CZE", "<gradient:#11457E:#FFFFFF:#D7141A>", Group.F, "48152b7334d7ecf335e47a4f35defbd2eb6957fc7bfe94212642d62f46e61e"),
    GEORGIA(6239, "GEO", "<gradient:#DA291C:#FFFFFF>", Group.F, "4f05f99e343303c35a8b0b68e4bcc6c38c94e99319b9adfbae2a138591584c80"),

    NONE(-1, "", "<red>", Group.NONE, "fafec3c7fab2a372905fa3fc1fb228e783bb93a53e6d32e45ebdb4a0218fcca");

    companion object {
        fun team(name: String): Team {
            return entries.firstOrNull { it.name == name } ?: NONE
        }

        fun team(id: Int): Team {
            return entries.firstOrNull { it.teamId == id } ?: NONE
        }
    }

    enum class Group(val texture: String) {

        A("4e41748121626f22ae16a4c664c7301a9f8ea591bf4d29888957682a9fdaf"),
        B("42b9e16e26206a709bf07c2493ca4c5d24f5675654fc130d1d5ec5e8c5be5"),
        C("62a5876113322f39aa2bbef4bd6b79ec6b52a97bb6fab674bddbd7b6eab3ba"),
        D("fa661419de49ff4a2c97b27f868014fbdaeb8dd7f4392777830b2714caafd1f"),
        E("1aeef88e2c928b466c6ed5deaa4e1975a9436c2b1b498f9f7cbf92a9b599a6"),
        F("cd9d6e96b5b92ffcaf47dd1caf61d3f6e842913fc88849f3de548beed71fa8"),
        NONE("97c2144fdcb55c3fc1bf1de51cabdf52c3883bcb578923226beb0d85cb2d980");
    }

    class Cache {

        fun init() {
            for (team in entries) {
                skullCache[team] = SkullBuilder(team.texture)
                    .name(team.gradient + team.name.replace("_", ""))
            }
            for (group in Group.entries) {
                groupCache[group] = SkullBuilder(group.texture)
                    .name("<yellow>${group.name}")
            }
        }

        companion object {
            private val skullCache: MutableMap<Team, SkullBuilder> = mutableMapOf()
            private val groupCache: MutableMap<Group, SkullBuilder> = mutableMapOf()

            fun skull(team: Team): SkullBuilder {
                return skullCache[team]!!
            }

            fun group(group: Group): SkullBuilder {
                return groupCache[group]!!
            }
        }
    }
}