package de.smoofy.eurocup.player

import net.kyori.adventure.text.format.NamedTextColor

/*
 * Copyright ©
 * @author - Smoofy
 * @GitHub - https://github.com/Smoofy19
 * @Twitter - https://twitter.com/Smoofy19
 * Created - 24.11.2023 - 17:27
 */

/**

 */

enum class Rank(val id: String, val priority: Int, val rankName: String, val color: NamedTextColor, val prefix: String) {

    ADMIN("a0_admin", 100, "Administrator", NamedTextColor.DARK_RED, "§4Dev §7| §4"),
    VIEWER("z99_viewer", 0, "Viewer", NamedTextColor.GREEN, "§aViewer §7| §a");

}