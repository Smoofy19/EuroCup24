package de.smoofy.eurocup.tournament.match

import java.util.Date


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
    val time: Date,
    val phase: String,
    val teamOne: String,
    val teamTwo: String,
    val matchResult: MatchResult,
    val goals: List<Goal>,
    val location: Location
) {

    class MatchResult(val halfTimeResult: String, val endResult: String)

    class Goal(val scoreTeam1: Int, val scoreTeam2: Int, val minute: Int, val scorer: String, val penalty: Boolean, val ownGoal: Boolean, val overtime: Boolean)

    class Location(val city: String, val stadium: String, val viewers: Int)
}