package com.example.codemaster.utils

import androidx.compose.ui.graphics.Color
import com.example.codemaster.ui.theme.Apprentice
import com.example.codemaster.ui.theme.CandidateMaster
import com.example.codemaster.ui.theme.Expert
import com.example.codemaster.ui.theme.Grandmaster
import com.example.codemaster.ui.theme.InternationalGrandmaster
import com.example.codemaster.ui.theme.InternationalMaster
import com.example.codemaster.ui.theme.LegendaryGrandmaster
import com.example.codemaster.ui.theme.Master
import com.example.codemaster.ui.theme.Pupil
import com.example.codemaster.ui.theme.Specialist

data class RankColors(val rank: String, val color: Color)

val rankColors = listOf(
    RankColors("newbie", Color.LightGray),
    RankColors("pupil", Pupil),
    RankColors("apprentice", Apprentice),
    RankColors("expert", Expert),
    RankColors("specialist", Specialist),
    RankColors("candidate master", CandidateMaster),
    RankColors("master", Master),
    RankColors("international master", InternationalMaster),
    RankColors("grandmaster", Grandmaster),
    RankColors("international grandmaster", InternationalGrandmaster),
    RankColors("legendary grandmaster", LegendaryGrandmaster),
)
