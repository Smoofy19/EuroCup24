package de.smoofy.eurocup.tournament

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import de.smoofy.eurocup.fan.Team
import de.smoofy.eurocup.tournament.goalgetter.GoalGetter
import de.smoofy.eurocup.tournament.match.Match
import de.smoofy.eurocup.tournament.tabel.TabelTeam
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.jvm.optionals.getOrNull


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

    var matchesCache: List<Match>
    var goalGettersCache: List<GoalGetter>
    var tabelTeamsCache: List<TabelTeam>

    init {
        this.matchesCache = this.matches()
        this.goalGettersCache = this.goalGetters()
        this.tabelTeamsCache = this.tabelTeams()
    }

    fun match(matchId: Int): Match? {
        return this.matchesCache.find { it.matchId == matchId }
    }

    private fun createMatch(matchID: Int): Match {
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
                if (this.jsonNull(goalObject, "scoreTeam1")) -1 else goalObject.get("scoreTeam1").asInt,
                if (this.jsonNull(goalObject, "scoreTeam2")) -1 else goalObject.get("scoreTeam2").asInt,
                if (this.jsonNull(goalObject, "matchMinute")) -1 else goalObject.get("matchMinute").asInt,
                if (this.jsonNull(goalObject, "goalGetterName")) "" else goalObject.get("goalGetterName").asString,
                if (this.jsonNull(goalObject, "isPenalty")) false else goalObject.get("isPenalty").asBoolean,
                if (this.jsonNull(goalObject, "isOwnGoal")) false else goalObject.get("isOwnGoal").asBoolean,
                if (this.jsonNull(goalObject, "isOvertime")) false else goalObject.get("isOvertime").asBoolean
            ))
        }

        val locationObject = jsonObject.get("location").asJsonObject
        val location = Match.Location(
            if (this.jsonNull(locationObject, "locationCity")) "" else locationObject.get("locationCity").asString,
            if (this.jsonNull(locationObject, "locationStadium")) "" else locationObject.get("locationStadium").asString,
            if (this.jsonNull(jsonObject, "numberOfViewers")) 0 else jsonObject.get("numberOfViewers").asInt
        )

        val match = Match(
            if (this.jsonNull(jsonObject, "matchID")) -1 else jsonObject.get("matchID").asInt,
            if (this.jsonNull(jsonObject, "matchDateTime")) "" else ZonedDateTime.parse(jsonObject.get("matchDateTime").asString + "Z",
                DateTimeFormatter.ISO_ZONED_DATE_TIME).format(DateTimeFormatter.ofPattern("dd. MMMM yyyy HH:mm", Locale.ENGLISH)),

            if (this.jsonNull(jsonObject.get("group").asJsonObject, "groupName")) "" else jsonObject.get("group").asJsonObject.get("groupName").asString,
            Team.team(if (this.jsonNull(jsonObject.get("team1").asJsonObject, "teamId")) -1 else jsonObject.get("team1").asJsonObject.get("teamId").asInt),
            Team.team(if (this.jsonNull(jsonObject.get("team2").asJsonObject, "teamId")) -1 else jsonObject.get("team2").asJsonObject.get("teamId").asInt),
            matchResult,
            goals,
            location
        )

        return match
    }

    fun match(teamOne: Team, teamTwo: Team): Match? {
        return this.matchesCache.stream().filter { match ->
            match.teamOne == teamOne && match.teamTwo == teamTwo || match.teamOne == teamTwo && match.teamTwo == teamOne
        }.findFirst().getOrNull()
    }

    private fun matches(): List<Match> {
        val list: MutableList<Match> = mutableListOf()

        for (matchID in this.matchIDs()) {
            list.add(this.createMatch(matchID))
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

    private fun tabelTeams(): List<TabelTeam> {
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
        list.removeIf { tabelTeam -> tabelTeam.team == Team.NONE }

        return list.sortedWith(compareBy<TabelTeam> { it.team.group }.thenByDescending { it.points }.thenByDescending { it.goalDifference })
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

    private fun jsonNull(jsonObject: JsonObject, key: String): Boolean {
        return jsonObject.get(key) == null || jsonObject.get(key).isJsonNull
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