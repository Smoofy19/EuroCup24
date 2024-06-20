package de.smoofy.eurocup.tournament.tabel

import de.smoofy.eurocup.fan.Team


/*
 * Copyright ©
 * @author - Smoofy
 * @GitHub - https://github.com/Smoofy19
 * @Twitter - https://twitter.com/Smoofy19
 * Created - 18.06.2024 - 14:55
 */

/**

 */
class TabelTeam(
    val team: Team,
    val points: Int,
    val goals: Int,
    val opponentGoals: Int,
    val goalDifference: Int,
    val matches: Int,
    val won: Int,
    val draw: Int,
    val lost: Int
)