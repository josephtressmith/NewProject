package com.example.songdescriptionexplorer.data

/**
 * Data class representing a song with all its details
 */
data class Song(
    val name: String,
    val artist: String = "Unknown Artist",
    val album: String = "Unknown Album",
    val releaseDate: String = "Unknown",
    val genre: String = "Unknown",
    val duration: String = "0:00",
    val key: String = "Unknown",
    val bpm: String = "0",
    val timeSignature: String = "4/4",
    val recordLabel: String = "Unknown",
    val producers: String = "Unknown",
    val songwriters: String = "Unknown",
    val isrc: String = "Unknown",
    val musicalAnalysis: MusicalAnalysis = MusicalAnalysis(),
    val lyricsAnalysis: LyricsAnalysis = LyricsAnalysis()
)

/**
 * Data class for musical analysis of a song
 */
data class MusicalAnalysis(
    val structure: String = "Unknown",
    val chordProgression: String = "Unknown",
    val melodicRange: String = "Unknown",
    val instrumentation: String = "Unknown",
    val vocalStyle: String = "Unknown",
    val productionTechniques: String = "Unknown",
    val dynamics: String = "Unknown",
    val harmonicComplexity: String = "Unknown",
    val rhythmicComplexity: String = "Unknown",
    val musicalInfluences: String = "Unknown"
)

/**
 * Data class for lyrics analysis of a song
 */
data class LyricsAnalysis(
    val theme: String = "Unknown",
    val narrativeStructure: String = "Unknown",
    val languageStyle: String = "Unknown",
    val emotionalTone: String = "Unknown",
    val literaryDevices: String = "Unknown",
    val rhymingScheme: String = "Unknown",
    val repetition: String = "Unknown",
    val imagery: String = "Unknown",
    val symbolism: String = "Unknown",
    val culturalReferences: String = "Unknown"
)

/**
 * Response object for API calls
 */
data class SongResponse(
    val success: Boolean,
    val song: Song? = null,
    val error: String? = null
)
