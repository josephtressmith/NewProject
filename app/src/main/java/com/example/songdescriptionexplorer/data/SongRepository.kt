package com.example.songdescriptionexplorer.data

import android.util.Log
import com.example.songdescriptionexplorer.data.MusicalAnalysis
import com.example.songdescriptionexplorer.data.LyricsAnalysis
import com.example.songdescriptionexplorer.data.Song
import com.example.songdescriptionexplorer.data.SongResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository class for fetching song data
 * This currently uses simulated data but can be extended to use real APIs
 */
class SongRepository {
    
    private val TAG = "SongRepository"
    
    // Simulated database of songs (can be replaced with API calls)
    private val songDatabase = mapOf(
        "bohemian rhapsody" to createBohemianRhapsody(),
        "stairway to heaven" to createStairwayToHeaven(),
        "hotel california" to createHotelCalifornia(),
        "yesterday" to createYesterday(),
        "billie jean" to createBillieJean(),
        "smells like teen spirit" to createSmellsLikeTeenSpirit(),
        "imagine" to createImagine(),
        "hey jude" to createHeyJude()
    )
    
    /**
     * Fetch song description from repository
     * @param songName The name of the song to search for
     * @param includeLyrics Whether to include lyrics analysis
     * @return SongResponse containing the song data or error
     */
    suspend fun getSongDescription(songName: String, includeLyrics: Boolean): SongResponse {
        return withContext(Dispatchers.IO) {
            try {
                // Simulate network delay
                Thread.sleep(800)
                
                val normalizedName = songName.lowercase().trim()
                
                // Check if song exists in our database
                val song = songDatabase[normalizedName]
                
                if (song != null) {
                    // Return the song, optionally excluding lyrics
                    val resultSong = if (includeLyrics) {
                        song
                    } else {
                        song.copy(lyricsAnalysis = LyricsAnalysis())
                    }
                    SongResponse(success = true, song = resultSong)
                } else {
                    // Create a generic song with the given name
                    SongResponse(
                        success = true,
                        song = createGenericSong(songName, includeLyrics)
                    )
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching song data", e)
                SongResponse(success = false, error = e.message ?: "Unknown error")
            }
        }
    }
    
    /**
     * Search for songs matching a query
     */
    suspend fun searchSongs(query: String): List<Song> {
        return withContext(Dispatchers.IO) {
            songDatabase.values.filter {
                it.name.contains(query, ignoreCase = true) ||
                it.artist.contains(query, ignoreCase = true)
            }
        }
    }
    
    // ========================================================================
    // Simulated Song Data (can be replaced with API calls)
    // ========================================================================
    
    private fun createGenericSong(name: String, includeLyrics: Boolean): Song {
        return Song(
            name = name,
            artist = "Various Artists",
            album = "Various Albums",
            releaseDate = "2020",
            genre = "Pop",
            duration = "3:30",
            key = "C Major",
            bpm = "120",
            timeSignature = "4/4",
            recordLabel = "Universal Music",
            producers = "Unknown Producer",
            songwriters = "Unknown Songwriter",
            isrc = "USUM72000001",
            musicalAnalysis = MusicalAnalysis(
                structure = "Verse-Chorus-Verse-Chorus-Bridge-Chorus-Outro",
                chordProgression = "I-V-vi-IV",
                melodicRange = "2 octaves",
                instrumentation = "Guitar, Bass, Drums, Vocals, Synthesizers",
                vocalStyle = "Pop vocal style with auto-tune",
                productionTechniques = "Multi-track recording, compression, reverb, delay",
                dynamics = "Moderate dynamic range with occasional peaks",
                harmonicComplexity = "Moderate - uses common chord progressions",
                rhythmicComplexity = "Moderate - standard pop rhythms",
                musicalInfluences = "Pop, R&B, Electronic"
            ),
            lyricsAnalysis = if (includeLyrics) LyricsAnalysis(
                theme = "Love and relationships",
                narrativeStructure = "First-person narrative",
                languageStyle = "Conversational and poetic",
                emotionalTone = "Romantic and hopeful",
                literaryDevices = "Metaphors, similes, repetition",
                rhymingScheme = "AABB or ABAB pattern",
                repetition = "Chorus is repeated 3-4 times",
                imagery = "Visual and emotional imagery",
                symbolism = "Love as a journey or light",
                culturalReferences = "Contemporary pop culture references"
            ) else LyricsAnalysis()
        )
    }
    
    private fun createBohemianRhapsody(): Song {
        return Song(
            name = "Bohemian Rhapsody",
            artist = "Queen",
            album = "A Night at the Opera",
            releaseDate = "October 31, 1975",
            genre = "Progressive Rock, Opera Rock",
            duration = "5:55",
            key = "B♭ Major, D Major, A Major, E♭ Major, C Major",
            bpm = "72-144 (varies)",
            timeSignature = "4/4 (with changes)",
            recordLabel = "EMI, Elektra",
            producers = "Roy Thomas Baker, Queen",
            songwriters = "Freddie Mercury",
            isrc = "GBUM77500291",
            musicalAnalysis = MusicalAnalysis(
                structure = "Intro-Ballad-Guitar Solo-Opera-Hard Rock-Outro",
                chordProgression = "B♭-F-C-G-D-A-E♭-B♭ (complex changes)",
                melodicRange = "4 octaves (extensive vocal range)",
                instrumentation = "Piano, Guitar, Bass, Drums, Vocals, Opera choir",
                vocalStyle = "Operatic tenor with multi-tracked harmonies",
                productionTechniques = "24-track recording, tape splicing, ADT, reverb, delay",
                dynamics = "Extreme - from whisper-quiet to full orchestral volume",
                harmonicComplexity = "Very High - classical influences, modulations, chromaticism",
                rhythmicComplexity = "Very High - multiple tempo and time signature changes",
                musicalInfluences = "Classical music, Opera, Hard rock, Ballad"
            ),
            lyricsAnalysis = LyricsAnalysis(
                theme = "Confession, guilt, and redemption",
                narrativeStructure = "First-person confession with dramatic shifts",
                languageStyle = "Poetic, theatrical, confessional",
                emotionalTone = "Tragic, desperate, triumphant",
                literaryDevices = "Metaphor, alliteration, juxtaposition, dramatic irony",
                rhymingScheme = "Irregular - follows narrative flow",
                repetition = "Key phrases repeated for emphasis",
                imagery = "Dark, operatic, surreal ("mama, ooooh", "Galileo")",
                symbolism = "Mama as conscience, Scaramouche as inner conflict",
                culturalReferences = "Italian opera, Biblical references"
            )
        )
    }
    
    private fun createStairwayToHeaven(): Song {
        return Song(
            name = "Stairway to Heaven",
            artist = "Led Zeppelin",
            album = "Led Zeppelin IV",
            releaseDate = "November 8, 1971",
            genre = "Progressive Rock, Folk Rock",
            duration = "8:02",
            key = "A Minor",
            bpm = "63-78 (varies)",
            timeSignature = "4/4",
            recordLabel = "Atlantic",
            producers = "Jimmy Page",
            songwriters = "Jimmy Page, Robert Plant",
            isrc = "USAT27100010",
            musicalAnalysis = MusicalAnalysis(
                structure = "Intro-Verse-Chorus-Verse-Chorus-Solo-Verse-Chorus-Outro",
                chordProgression = "Am-G-F-C-D-Em-Am (descending pattern)",
                melodicRange = "2.5 octaves",
                instrumentation = "Acoustic guitar, Electric guitar, Bass, Drums, Vocals, Recorder",
                vocalStyle = "Blues-influenced rock vocals with dynamic range",
                productionTechniques = "Analog recording, tape echo, room reverb, panning",
                dynamics = "Wide - from gentle acoustic to full rock band",
                harmonicComplexity = "High - modal mixtures, pedal points",
                rhythmicComplexity = "Moderate - steady with fills and variations",
                musicalInfluences = "Folk, Blues, Classical, Celtic"
            ),
            lyricsAnalysis = LyricsAnalysis(
                theme = "Spiritual journey, destiny, and temptation",
                narrativeStructure = "Third-person allegory",
                languageStyle = "Poetic, mystical, symbolic",
                emotionalTone = "Mysterious, hopeful, cautionary",
                literaryDevices = "Metaphor, symbolism, foreshadowing",
                rhymingScheme = "AABB pattern",
                repetition = "Chorus and key phrases repeated",
                imagery = "Nature imagery, stairs, lady, shops, trees",
                symbolism = "Stairway as life journey, Lady as destiny/temptation",
                culturalReferences = "Medieval folklore, Tolkien influences"
            )
        )
    }
    
    private fun createHotelCalifornia(): Song {
        return Song(
            name = "Hotel California",
            artist = "Eagles",
            album = "Hotel California",
            releaseDate = "February 22, 1977",
            genre = "Rock, Soft Rock",
            duration = "6:30",
            key = "B Minor",
            bpm = "76",
            timeSignature = "4/4",
            recordLabel = "Asylum",
            producers = "Bill Szymczyk",
            songwriters = "Don Felder, Don Henley, Glenn Frey",
            isrc = "USAS77700001",
            musicalAnalysis = MusicalAnalysis(
                structure = "Intro-Verse-Chorus-Verse-Chorus-Solo-Verse-Chorus-Outro",
                chordProgression = "Bm-F#-A-E-G-D-Em-F# (classic rock progression)",
                melodicRange = "2 octaves",
                instrumentation = "Electric guitars (12-string), Bass, Drums, Vocals, Keyboards",
                vocalStyle = "Smooth rock vocals with harmonies",
                productionTechniques = "Multi-track recording, phasing, tape echo, compression",
                dynamics = "Moderate - steady build throughout",
                harmonicComplexity = "Moderate - diatonic with chromatic passing tones",
                rhythmicComplexity = "Moderate - steady groove with variations",
                musicalInfluences = "Country rock, Psychedelic rock, Latin music"
            ),
            lyricsAnalysis = LyricsAnalysis(
                theme = "Materialism, hedonism, and the dark side of the American Dream",
                narrativeStructure = "First-person narrative with surreal elements",
                languageStyle = "Descriptive, atmospheric, metaphorical",
                emotionalTone = "Dark, haunting, cautionary",
                literaryDevices = "Metaphor, symbolism, irony",
                rhymingScheme = "AABB pattern",
                repetition = "Chorus repeated with variations",
                imagery = "Desert imagery, hotel, mirrors, wine, dance",
                symbolism = "Hotel as temptation, Prison as addiction",
                culturalReferences = "1970s California culture, drug culture"
            )
        )
    }
    
    private fun createYesterday(): Song {
        return Song(
            name = "Yesterday",
            artist = "The Beatles",
            album = "Help!",
            releaseDate = "August 6, 1965",
            genre = "Pop, Baroque Pop",
            duration = "2:05",
            key = "G Major",
            bpm = "104",
            timeSignature = "4/4",
            recordLabel = "Parlophone, Capitol",
            producers = "George Martin",
            songwriters = "Paul McCartney, John Lennon",
            isrc = "GBUM76500001",
            musicalAnalysis = MusicalAnalysis(
                structure = "Verse-Chorus-Verse-Chorus-Bridge-Chorus-Outro",
                chordProgression = "G-Em-C-D (I-vi-IV-V)",
                melodicRange = "1.5 octaves",
                instrumentation = "Acoustic guitar, String quartet, Bass, Vocals",
                vocalStyle = "Soft, intimate, melancholic",
                productionTechniques = "Close mic recording, tape echo, orchestral arrangement",
                dynamics = "Soft to moderate - intimate feel",
                harmonicComplexity = "Moderate - uses secondary dominants",
                rhythmicComplexity = "Simple - steady strumming pattern",
                musicalInfluences = "Baroque music, Folk, Everly Brothers"
            ),
            lyricsAnalysis = LyricsAnalysis(
                theme = "Loneliness, longing, and lost love",
                narrativeStructure = "First-person reflection",
                languageStyle = "Simple, direct, emotional",
                emotionalTone = "Melancholic, nostalgic, sad",
                literaryDevices = "Repetition, simple rhyme",
                rhymingScheme = "AABB pattern",
                repetition = "Title phrase "Yesterday" repeated",
                imagery = "Everyday imagery - trouble, care, love, stay",
                symbolism = "Yesterday as lost happiness",
                culturalReferences = "Universal human experiences"
            )
        )
    }
    
    private fun createBillieJean(): Song {
        return Song(
            name = "Billie Jean",
            artist = "Michael Jackson",
            album = "Thriller",
            releaseDate = "January 2, 1983",
            genre = "Pop, Funk, R&B, Post-disco",
            duration = "4:54",
            key = "F# Minor",
            bpm = "117",
            timeSignature = "4/4",
            recordLabel = "Epic",
            producers = "Quincy Jones, Michael Jackson",
            songwriters = "Michael Jackson",
            isrc = "USEP48300001",
            musicalAnalysis = MusicalAnalysis(
                structure = "Intro-Verse-Pre-Chorus-Chorus-Verse-Pre-Chorus-Chorus-Bridge-Chorus-Outro",
                chordProgression = "F#m-D-A-E (i-VI-III-VII)",
                melodicRange = "2.5 octaves",
                instrumentation = "Bass synth, Drum machine, Guitars, Vocals, Strings, Horns",
                vocalStyle = "Expressive, rhythmic, with hiccups and ad-libs",
                productionTechniques = "Digital recording, gated reverb, compression, layering",
                dynamics = "Wide - from sparse to full arrangement",
                harmonicComplexity = "Moderate - uses modal interchange",
                rhythmicComplexity = "Very High - complex syncopation, polyrhythms",
                musicalInfluences = "Funk, Disco, R&B, Pop"
            ),
            lyricsAnalysis = LyricsAnalysis(
                theme = "Fame, paranoia, and false accusations",
                narrativeStructure = "First-person denial",
                languageStyle = "Conversational, rhythmic, repetitive",
                emotionalTone = "Defiant, suspicious, intense",
                literaryDevices = "Repetition, rhetorical questions, denial",
                rhymingScheme = "AABB and ABCB patterns",
                repetition = "Extensive - title repeated throughout",
                imagery = "Legal and personal imagery - lawsuit, baby, lies",
                symbolism = "Billie Jean as false accusation, Moonwalk as escape",
                culturalReferences = "1980s celebrity culture, tabloid journalism"
            )
        )
    }
    
    private fun createSmellsLikeTeenSpirit(): Song {
        return Song(
            name = "Smells Like Teen Spirit",
            artist = "Nirvana",
            album = "Nevermind",
            releaseDate = "September 10, 1991",
            genre = "Grunge, Alternative Rock",
            duration = "5:01",
            key = "F Minor",
            bpm = "116",
            timeSignature = "4/4",
            recordLabel = "DGC, Geffen",
            producers = "Butch Vig",
            songwriters = "Kurt Cobain, Dave Grohl, Krist Novoselic",
            isrc = "USGF19100001",
            musicalAnalysis = MusicalAnalysis(
                structure = "Intro-Verse-Chorus-Verse-Chorus-Bridge-Chorus-Outro",
                chordProgression = "Fm-B♭m-D♭-C (i-iv-VI-V)",
                melodicRange = "2 octaves",
                instrumentation = "Electric guitar, Bass, Drums, Vocals",
                vocalStyle = "Raw, emotional, with dynamic shifts from soft to loud",
                productionTechniques = "Analog recording, distortion, compression, room mics",
                dynamics = "Extreme - quiet verses, explosive choruses (loud/soft dynamics)",
                harmonicComplexity = "Low to moderate - power chords, simple progressions",
                rhythmicComplexity = "Moderate - steady with syncopated rhythms",
                musicalInfluences = "Punk rock, Metal, Pop, Pixies"
            ),
            lyricsAnalysis = LyricsAnalysis(
                theme = "Teenage rebellion, apathy, and confusion",
                narrativeStructure = "First-person stream of consciousness",
                languageStyle = "Raw, unpolished, authentic",
                emotionalTone = "Angry, apathetic, confused, defiant",
                literaryDevices = "Paradox, repetition, wordplay",
                rhymingScheme = "Loose and irregular",
                repetition = "Chorus repeated with intensity",
                imagery = "Teenage life, school, boredom, rebellion",
                symbolism = ""Here we are now, entertain us" as generational apathy",
                culturalReferences = "1990s Generation X culture, anti-establishment"
            )
        )
    }
    
    private fun createImagine(): Song {
        return Song(
            name = "Imagine",
            artist = "John Lennon",
            album = "Imagine",
            releaseDate = "October 11, 1971",
            genre = "Pop, Soft Rock",
            duration = "3:04",
            key = "C Major",
            bpm = "76",
            timeSignature = "4/4",
            recordLabel = "Apple, EMI",
            producers = "John Lennon, Yoko Ono, Phil Spector",
            songwriters = "John Lennon",
            isrc = "GBUM77100001",
            musicalAnalysis = MusicalAnalysis(
                structure = "Intro-Verse-Chorus-Verse-Chorus-Bridge-Chorus-Outro",
                chordProgression = "C-Cmaj7-F (I-Imaj7-IV)",
                melodicRange = "1.5 octaves",
                instrumentation = "Piano, Bass, Drums, Vocals, Strings",
                vocalStyle = "Soft, gentle, introspective",
                productionTechniques = "Analog recording, tape echo, orchestral arrangement",
                dynamics = "Soft to moderate - gentle, dreamy feel",
                harmonicComplexity = "Simple - diatonic harmony",
                rhythmicComplexity = "Simple - steady, flowing rhythm",
                musicalInfluences = "Gospel, Classical, Folk"
            ),
            lyricsAnalysis = LyricsAnalysis(
                theme = "Peace, unity, and a world without divisions",
                narrativeStructure = "First-person invitation to imagine",
                languageStyle = "Simple, direct, utopian",
                emotionalTone = "Hopeful, peaceful, dreamy",
                literaryDevices = "Repetition, anaphora ("Imagine...")",
                rhymingScheme = "Irregular - follows natural speech",
                repetition = ""Imagine" repeated at start of each line",
                imagery = "Utopian imagery - no heaven, no hell, no countries, no possessions",
                symbolism = "Imagination as tool for change",
                culturalReferences = "1970s peace movement, anti-war sentiment"
            )
        )
    }
    
    private fun createHeyJude(): Song {
        return Song(
            name = "Hey Jude",
            artist = "The Beatles",
            album = "Hey Jude",
            releaseDate = "August 26, 1968",
            genre = "Rock, Pop",
            duration = "7:11",
            key = "C Major",
            bpm = "134",
            timeSignature = "4/4",
            recordLabel = "Apple, Parlophone",
            producers = "George Martin",
            songwriters = "Paul McCartney, John Lennon",
            isrc = "GBUM76800001",
            musicalAnalysis = MusicalAnalysis(
                structure = "Intro-Verse-Chorus-Verse-Chorus-Bridge-Chorus-Coda (Na-na-na)",
                chordProgression = "C-G-F-C (I-V-IV-I)",
                melodicRange = "2 octaves",
                instrumentation = "Piano, Guitar, Bass, Drums, Vocals, Orchestra",
                vocalStyle = "Expressive, powerful, with emotional delivery",
                productionTechniques = "Analog recording, orchestral arrangement, tape echo",
                dynamics = "Wide - from intimate to full orchestral climax",
                harmonicComplexity = "Simple - uses primary triads",
                rhythmicComplexity = "Moderate - steady with variations",
                musicalInfluences = "Rock and roll, Gospel, Classical"
            ),
            lyricsAnalysis = LyricsAnalysis(
                theme = "Encouragement, love, and making the best of a bad situation",
                narrativeStructure = "Second-person address ("Hey Jude")",
                languageStyle = "Direct, conversational, supportive",
                emotionalTone = "Encouraging, hopeful, uplifting",
                literaryDevices = "Repetition, direct address, imperative mood",
                rhymingScheme = "AABB pattern",
                repetition = "Extensive - "Jude" and "Na-na-na" repeated",
                imagery = "Everyday life, love, pain, gain",
                symbolism = "Jude as symbol of hope, Na-na-na as emotional release",
                culturalReferences = "1960s optimism, personal relationships"
            )
        )
    }
}
