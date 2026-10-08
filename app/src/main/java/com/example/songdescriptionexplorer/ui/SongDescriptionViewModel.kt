package com.example.songdescriptionexplorer.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.songdescriptionexplorer.data.LyricsAnalysis
import com.example.songdescriptionexplorer.data.MusicalAnalysis
import com.example.songdescriptionexplorer.data.Song
import com.example.songdescriptionexplorer.data.SongRepository
import com.example.songdescriptionexplorer.data.SongResponse
import kotlinx.coroutines.launch

/**
 * ViewModel for the Song Description Explorer
 * Handles business logic and data for the UI
 */
class SongDescriptionViewModel : ViewModel() {
    
    private val repository = SongRepository()
    
    // LiveData for UI state
    private val _song = MutableLiveData<Song?>()
    val song: LiveData<Song?> = _song
    
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    
    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error
    
    private val _status = MutableLiveData<String>()
    val status: LiveData<String> = _status
    
    private val _includeLyrics = MutableLiveData<Boolean>(true)
    val includeLyrics: LiveData<Boolean> = _includeLyrics
    
    // Formatted description for display
    private val _formattedDescription = MutableLiveData<String>()
    val formattedDescription: LiveData<String> = _formattedDescription
    
    init {
        _status.value = "Ready to search for song descriptions..."
    }
    
    /**
     * Search for a song description
     * @param songName The name of the song to search for
     */
    fun searchSong(songName: String) {
        if (songName.isBlank()) {
            _error.value = "Please enter a song name"
            _status.value = "Please enter a song name"
            return
        }
        
        _isLoading.value = true
        _error.value = null
        _status.value = "Searching for song description..."
        _song.value = null
        _formattedDescription.value = ""
        
        viewModelScope.launch {
            try {
                val response = repository.getSongDescription(songName, _includeLyrics.value ?: true)
                
                if (response.success && response.song != null) {
                    _song.value = response.song
                    _formattedDescription.value = formatSongDescription(response.song, _includeLyrics.value ?: true)
                    _status.value = "Search complete!"
                } else {
                    _error.value = response.error ?: "Failed to fetch song data"
                    _status.value = "Error: ${response.error ?: "Unknown error"}"
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "Unknown error occurred"
                _status.value = "Error fetching song data"
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    /**
     * Clear the current search
     */
    fun clearSearch() {
        _song.value = null
        _formattedDescription.value = ""
        _error.value = null
        _status.value = "Ready to search for song descriptions..."
    }
    
    /**
     * Toggle lyrics analysis inclusion
     */
    fun toggleLyricsInclusion(include: Boolean) {
        _includeLyrics.value = include
        
        // If we have a song loaded, reformat the description
        _song.value?.let { song ->
            _formattedDescription.value = formatSongDescription(song, include)
        }
    }
    
    /**
     * Format song description for display
     */
    private fun formatSongDescription(song: Song, includeLyrics: Boolean): String {
        val sb = StringBuilder()
        
        // Song Metadata Section
        sb.append("📋 \n")
        sb.append("SONG METADATA\n\n")
        
        sb.append("🎵 Name: ${song.name}\n")
        sb.append("🎤 Artist: ${song.artist}\n")
        sb.append("💿 Album: ${song.album}\n")
        sb.append("📅 Release Date: ${song.releaseDate}\n")
        sb.append("🎭 Genre: ${song.genre}\n")
        sb.append("⏱️  Duration: ${song.duration}\n")
        sb.append("🎹 Key: ${song.key}\n")
        sb.append("🎶 BPM: ${song.bpm}\n")
        sb.append("🎵 Time Signature: ${song.timeSignature}\n")
        sb.append("🏷️  Record Label: ${song.recordLabel}\n")
        sb.append("🎧 Producers: ${song.producers}\n")
        sb.append("✍️  Songwriters: ${song.songwriters}\n")
        sb.append("🆔 ISRC: ${song.isrc}\n\n")
        
        // Musical Analysis Section
        sb.append("🎵 \n")
        sb.append("MUSICAL ANALYSIS\n\n")
        
        with(song.musicalAnalysis) {
            sb.append("📐 Structure: $structure\n")
            sb.append("🎹 Chord Progression: $chordProgression\n")
            sb.append("🎤 Melodic Range: $melodicRange\n")
            sb.append("🎻 Instrumentation: $instrumentation\n")
            sb.append("🎤 Vocal Style: $vocalStyle\n")
            sb.append("🎛️  Production Techniques: $productionTechniques\n")
            sb.append("📊 Dynamics: $dynamics\n")
            sb.append("🎵 Harmonic Complexity: $harmonicComplexity\n")
            sb.append("🥁 Rhythmic Complexity: $rhythmicComplexity\n")
            sb.append("🎭 Musical Influences: $musicalInfluences\n\n")
        }
        
        // Lyrics Analysis Section (if included)
        if (includeLyrics) {
            sb.append("📜 \n")
            sb.append("LYRICS ANALYSIS\n\n")
            
            with(song.lyricsAnalysis) {
                sb.append("🎭 Theme: $theme\n")
                sb.append("📖 Narrative Structure: $narrativeStructure\n")
                sb.append("💬 Language Style: $languageStyle\n")
                sb.append("😢 Emotional Tone: $emotionalTone\n")
                sb.append("📚 Literary Devices: $literaryDevices\n")
                sb.append("🎵 Rhyming Scheme: $rhymingScheme\n")
                sb.append("🔁 Repetition: $repetition\n")
                sb.append("🖼️  Imagery: $imagery\n")
                sb.append("🎭 Symbolism: $symbolism\n")
                sb.append("🌍 Cultural References: $culturalReferences\n")
            }
        }
        
        return sb.toString()
    }
    
    /**
     * Get a simple formatted string for a specific section
     */
    fun getMetadataSection(song: Song): String {
        return """
            | 🎵 Song: ${song.name}
            | 🎤 Artist: ${song.artist}
            | 💿 Album: ${song.album}
            | 📅 Released: ${song.releaseDate}
            | 🎭 Genre: ${song.genre}
            | ⏱️  Duration: ${song.duration}
            | 🎹 Key: ${song.key}
            | 🎶 BPM: ${song.bpm}
            | 🎵 Time Signature: ${song.timeSignature}
        """.trimMargin()
    }
    
    /**
     * Get musical analysis section
     */
    fun getMusicalAnalysisSection(song: Song): String {
        with(song.musicalAnalysis) {
            return """
                | 📐 Structure: $structure
                | 🎹 Chord Progression: $chordProgression
                | 🎤 Melodic Range: $melodicRange
                | 🎻 Instrumentation: $instrumentation
                | 🎤 Vocal Style: $vocalStyle
                | 🎛️  Production: $productionTechniques
                | 📊 Dynamics: $dynamics
                | 🎵 Harmonic Complexity: $harmonicComplexity
                | 🥁 Rhythmic Complexity: $rhythmicComplexity
                | 🎭 Influences: $musicalInfluences
            """.trimMargin()
        }
    }
    
    /**
     * Get lyrics analysis section
     */
    fun getLyricsAnalysisSection(song: Song): String {
        with(song.lyricsAnalysis) {
            return """
                | 🎭 Theme: $theme
                | 📖 Narrative: $narrativeStructure
                | 💬 Language: $languageStyle
                | 😢 Tone: $emotionalTone
                | 📚 Devices: $literaryDevices
                | 🎵 Rhyme: $rhymingScheme
                | 🔁 Repetition: $repetition
                | 🖼️  Imagery: $imagery
                | 🎭 Symbolism: $symbolism
                | 🌍 Culture: $culturalReferences
            """.trimMargin()
        }
    }
}
