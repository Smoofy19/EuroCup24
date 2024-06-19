package de.smoofy.eurocup.tournament.match

import de.smoofy.eurocup.fan.Team


/*
 * Copyright ©
 * @author - Smoofy
 * @GitHub - https://github.com/Smoofy19
 * @Twitter - https://twitter.com/Smoofy19
 * Created - 18.06.2024 - 13:05
 */

/**

 */
class Match(
    val matchId: Int,
    val time: String,
    val phase: String,
    val teamOne: Team,
    val teamTwo: Team,
    val matchResult: MatchResult,
    val goals: List<Goal>,
    val location: Location
) {

    class MatchResult(val halfTimeResult: String, val endResult: String)

    class Goal(val scoreTeam1: Int, val scoreTeam2: Int, val minute: Int, val scorer: String, val penalty: Boolean, val ownGoal: Boolean, val overtime: Boolean)

    class Location(val city: String, val stadium: String, val viewers: Int)
}