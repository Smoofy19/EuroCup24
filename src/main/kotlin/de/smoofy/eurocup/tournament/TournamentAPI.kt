package de.smoofy.eurocup.tournament

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import de.smoofy.eurocup.fan.Team
import de.smoofy.eurocup.tournament.match.Match
import de.smoofy.eurocup.tournament.objects.GoalGetter
import de.smoofy.eurocup.tournament.objects.TabelTeam
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets
import java.util.Date


/*
 * Copyright ©
 * @author - Smoofy
 * @GitHub - https://github.com/Smoofy19
 * @Twitter - https://twitter.com/Smoofy19
 * Created - 18.06.2024 - 12:53
 */

/**

 */
class TournamentAPI {

    private val goalGetters: MutableMap<String, GoalGetter> = mutableMapOf()

    var matchesCache: List<Match> = mutableListOf()
    var goalGettersCache: List<GoalGetter> = mutableListOf()
    var tabelTeamsCache: List<TabelTeam> = mutableListOf()

    fun match(matchID: Int): Match {
        val jsonObject = JsonParser.parseString(this.request("getmatchdata/$matchID")) as JsonObject

        val matchResultsArray = jsonObject.get("matchResults").asJsonArray
        var matchResult = Match.MatchResult("", "")
        if (!matchResultsArray.isEmpty) {
            matchResult = Match.MatchResult(
                "${matchResultsArray.get(0).asJsonObject.get("pointsTeam1").asString}:${matchResultsArray.get(0).asJsonObject.get("pointsTeam2").asString}",
                "${matchResultsArray.get(1).asJsonObject.get("pointsTeam1").asString}:${matchResultsArray.get(1).asJsonObject.get("pointsTeam2").asString}")
        }

        val goals: MutableList<Match.Goal> = mutableListOf()
        for (goalElement in jsonObject.get("goals").asJsonArray) {
            val goalObject = goalElement.asJsonObject
            goals.add(Match.Goal(
                goalObject.get("scoreTeam1").asInt,
                goalObject.get("scoreTeam2").asInt,
                goalObject.get("matchMinute").asInt,
                goalObject.get("goalGetterName").asString,
                goalObject.get("isPenalty").asBoolean,
                goalObject.get("isOwnGoal").asBoolean,
                goalObject.get("isOvertime").asBoolean
            ))
        }

        val locationObject = jsonObject.get("location").asJsonObject
        val location = Match.Location(
            locationObject.get("locationCity").asString,
            if (jsonObject.get("locationStadium") == null || jsonObject.get("locationStadium").isJsonNull) "" else locationObject.get("locationStadium").asString,
            if (jsonObject.get("numberOfViewers") == null || jsonObject.get("numberOfViewers").isJsonNull) -1 else jsonObject.get("numberOfViewers").asInt
        )

        val match = Match(
            jsonObject.get("matchID").asInt,
            Date(),
            jsonObject.get("group").asJsonObject.get("groupName").asString,
            jsonObject.get("team1").asJsonObject.get("teamName").asString,
            jsonObject.get("team2").asJsonObject.get("teamName").asString,
            matchResult,
            goals,
            location
        )

        return match
    }

    fun match(teamOne: Team, teamTwo: Team): Match {
        val jsonArray = JsonParser.parseString(this.request("getmatchdata/${teamOne.teamId}/${teamTwo.teamId}")) as JsonArray

        return this.match(jsonArray.get(0).asJsonObject.get("matchID").asInt)
    }

    fun lastMatch(team: Team): Match {
        val jsonObject = JsonParser.parseString(this.request("getlastmatchbyleagueteam/4708/${team.teamId}")) as JsonObject

        return this.match(jsonObject.get("matchID").asInt)
    }

    fun nextMatch(team: Team): Match {
        val jsonObject = JsonParser.parseString(this.request("getnextmatchbyleagueteam/4708/${team.teamId}")) as JsonObject

        return this.match(jsonObject.get("matchID").asInt)
    }

    fun matches(): List<Match> {
        val list: MutableList<Match> = mutableListOf()

        for (matchID in this.matchIDs()) {
            list.add(this.match(matchID))
        }

        return list
    }

    private fun matchIDs(): List<Int> {
        val list: MutableList<Int> = mutableListOf()
        val jsonArray = JsonParser.parseString(this.request("getmatchdata/em/2024")) as JsonArray

        for (jsonElement in jsonArray.asList()) {
            val jsonObject = jsonElement.asJsonObject
            list.add(jsonObject.get("matchID").asInt)
        }

        return list
    }

    fun tabelTeams(): List<TabelTeam> {
        val list: MutableList<TabelTeam> = mutableListOf()
        val jsonArray = JsonParser.parseString(this.request("getbltable/em/2024")) as JsonArray

        for (jsonElement in jsonArray.asList()) {
            val jsonObject = jsonElement.asJsonObject
            list.add(TabelTeam(
                Team.team(jsonObject.get("teamInfoId").asInt),
                jsonObject.get("points").asInt,
                jsonObject.get("goals").asInt,
                jsonObject.get("opponentGoals").asInt,
                jsonObject.get("goalDiff").asInt,
                jsonObject.get("matches").asInt,
                jsonObject.get("won").asInt,
                jsonObject.get("draw").asInt,
                jsonObject.get("lost").asInt
            ))
        }

        return list
    }

    private fun goalGetters(): List<GoalGetter> {
        this.goalGetters.clear()
        for (match in matches()) {
            for (goal in match.goals) {
                if (goal.ownGoal) {
                    continue
                }

                if (goalGetters.containsKey(goal.scorer)) {
                    goalGetters[goal.scorer]!!.goals++
                } else {
                    goalGetters[goal.scorer] = GoalGetter(goal.scorer, 1)
                }
            }
        }

        return goalGetters.values.sortedBy { it.goals }.reversed()
    }

    fun update() {
        this.matchesCache = this.matches()
        this.goalGettersCache = this.goalGetters()
        this.tabelTeamsCache = this.tabelTeams()
    }

    private fun request(endpoint: String): String? {
        val apiUrl = "https://api.openligadb.de/$endpoint"

        try {
            val connection: HttpURLConnection = URL(apiUrl).openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.setRequestProperty("Content-Type", "application/json")
            connection.doOutput = true

            val inputStreamReader = InputStreamReader(connection.inputStream, StandardCharsets.UTF_8)
            val bufferedReader = BufferedReader(inputStreamReader)

            val response = StringBuilder()

            var responseLine = bufferedReader.readLine()
            while (responseLine != null) {
                response.append(responseLine.trim())
                responseLine = bufferedReader.readLine()
            }

            if (connection.responseCode != 200) return null

            return response.toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}