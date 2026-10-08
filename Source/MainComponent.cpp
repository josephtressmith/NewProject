// MainComponent.cpp - Implementation of the main application component

#include "MainComponent.h"

MainComponent::MainComponent()
{
    // Set up title label
    titleLabel.setText ("Song Description Explorer", juce::dontSendNotification);
    titleLabel.setFont (juce::Font (24.0f, juce::Font::bold));
    titleLabel.setJustificationType (juce::Justification::centred);
    addAndMakeVisible (titleLabel);

    // Set up song name input
    songNameLabel.setText ("Enter Song Name:", juce::dontSendNotification);
    songNameLabel.setFont (juce::Font (16.0f));
    addAndMakeVisible (songNameLabel);

    songNameInput.setMultiLine (false);
    songNameInput.setReturnKeyStartsNewLine (false);
    songNameInput.setFont (juce::Font (16.0f));
    songNameInput.addListener (this);
    addAndMakeVisible (songNameInput);

    // Set up search button
    searchButton.setButtonText ("Search Song");
    searchButton.addListener (this);
    addAndMakeVisible (searchButton);

    // Set up clear button
    clearButton.setButtonText ("Clear");
    clearButton.addListener (this);
    addAndMakeVisible (clearButton);

    // Set up include lyrics toggle
    includeLyricsToggle.setButtonText ("Include Lyrics Analysis");
    includeLyricsToggle.setToggleState (true, juce::dontSendNotification);
    includeLyricsToggle.setFont (juce::Font (14.0f));
    addAndMakeVisible (includeLyricsToggle);

    // Set up results display
    resultsLabel.setText ("Song Description:", juce::dontSendNotification);
    resultsLabel.setFont (juce::Font (16.0f));
    addAndMakeVisible (resultsLabel);

    resultsOutput.setMultiLine (true);
    resultsOutput.setReadOnly (true);
    resultsOutput.setFont (juce::Font (14.0f));
    resultsOutput.setColour (juce::TextEditor::backgroundColourId, 
                             getLookAndFeel().findColour (juce::TextEditor::backgroundColourId));
    addAndMakeVisible (resultsOutput);

    // Set up status label
    statusLabel.setText ("Ready to search for song descriptions...", juce::dontSendNotification);
    statusLabel.setFont (juce::Font (12.0f));
    statusLabel.setJustificationType (juce::Justification::centred);
    statusLabel.setColour (juce::Label::textColourId, juce::Colours::grey);
    addAndMakeVisible (statusLabel);

    // Set initial size
    setSize (800, 600);
}

MainComponent::~MainComponent()
{
    songNameInput.removeListener (this);
    searchButton.removeListener (this);
    clearButton.removeListener (this);
}

void MainComponent::paint (juce::Graphics& g)
{
    g.fillAll (getLookAndFeel().findColour (juce::ResizableWindow::backgroundColourId));
}

void MainComponent::resized()
{
    auto bounds = getLocalBounds();
    auto padding = 20;
    auto labelHeight = 30;
    auto buttonHeight = 40;
    auto inputHeight = 30;

    // Title at the top
    titleLabel.setBounds (bounds.removeFromTop (labelHeight + padding));
    bounds.removeFromTop (padding);

    // Song name input section
    auto inputRow = bounds.removeFromTop (inputHeight + labelHeight + padding);
    songNameLabel.setBounds (inputRow.removeFromTop (labelHeight));
    songNameInput.setBounds (inputRow);
    bounds.removeFromTop (padding);

    // Buttons row
    auto buttonRow = bounds.removeFromTop (buttonHeight + padding);
    searchButton.setBounds (buttonRow.removeFromLeft (buttonRow.getWidth() / 3));
    clearButton.setBounds (buttonRow.removeFromRight (buttonRow.getWidth() / 3));
    includeLyricsToggle.setBounds (buttonRow);
    bounds.removeFromTop (padding);

    // Results section
    resultsLabel.setBounds (bounds.removeFromTop (labelHeight));
    bounds.removeFromTop (padding);
    resultsOutput.setBounds (bounds.removeFromTop (bounds.getHeight() - labelHeight - padding));
    bounds.removeFromTop (padding);

    // Status at the bottom
    statusLabel.setBounds (bounds);
}

void MainComponent::buttonClicked (juce::Button* button)
{
    if (button == &searchButton)
    {
        searchSongDescription();
    }
    else if (button == &clearButton)
    {
        songNameInput.clear();
        resultsOutput.clear();
        statusLabel.setText ("Ready to search for song descriptions...", juce::dontSendNotification);
        songNameInput.grabKeyboardFocus();
    }
}

void MainComponent::textEditorTextChanged (juce::TextEditor& editor)
{
    if (&editor == &songNameInput)
    {
        // Enable/disable search button based on input
        searchButton.setEnabled (songNameInput.getText().isNotEmpty());
    }
}

void MainComponent::searchSongDescription()
{
    juce::String songName = songNameInput.getText().trim();
    
    if (songName.isEmpty())
    {
        statusLabel.setText ("Please enter a song name", juce::dontSendNotification);
        return;
    }

    bool includeLyrics = includeLyricsToggle.getToggleState();
    
    statusLabel.setText ("Searching for song description...", juce::dontSendNotification);
    
    // Simulate search delay (in a real app, this would be async)
    juce::MessageManager::callAsync ([this, songName, includeLyrics]() {
        juce::String description = getSongDescription (songName, includeLyrics);
        
        juce::MessageManager::callAsync ([this, description]() {
            resultsOutput.setText (description);
            statusLabel.setText ("Search complete!", juce::dontSendNotification);
        });
    });
}

juce::String MainComponent::getSongDescription(const juce::String& songName, bool includeLyrics)
{
    juce::String description;
    
    // Get basic song info
    description += getDetailedSongInfo (songName);
    
    // Add musical analysis
    description += "\n\n=== MUSICAL ANALYSIS ===\n";
    description += getMusicalAnalysis (songName);
    
    // Add lyrics analysis if requested
    if (includeLyrics)
    {
        description += "\n\n=== LYRICS ANALYSIS ===\n";
        description += getLyricsAnalysis (songName);
    }
    
    return description;
}

juce::String MainComponent::getDetailedSongInfo(const juce::String& songName)
{
    // This is a placeholder for actual API calls to music databases
    // In a real implementation, you would call APIs like:
    // - Spotify API
    // - MusicBrainz
    // - Last.fm
    // - Genius (for lyrics)
    
    juce::String info;
    info += "Song: ";
    info += songName;
    info += "\n\n";
    
    // Simulate detailed song information
    info += "Artist: [Artist Name]\n";
    info += "Album: [Album Name]\n";
    info += "Release Date: [Release Date]\n";
    info += "Genre: [Genre]\n";
    info += "Duration: [Duration]\n";
    info += "Key: [Musical Key]\n";
    info += "BPM: [Beats Per Minute]\n";
    info += "Time Signature: [Time Signature]\n";
    info += "Record Label: [Record Label]\n";
    info += "Producers: [Producer Names]\n";
    info += "Songwriters: [Songwriter Names]\n";
    info += "ISRC: [International Standard Recording Code]\n";
    
    return info;
}

juce::String MainComponent::getMusicalAnalysis(const juce::String& songName)
{
    juce::String analysis;
    
    // Simulate musical analysis
    analysis += "Structure: [Verse-Chorus-Verse-Chorus-Bridge-Chorus-Outro]\n";
    analysis += "Chord Progression: [I-V-vi-IV]\n";
    analysis += "Melodic Range: [Range in octaves]\n";
    analysis += "Instrumentation: [List of instruments used]\n";
    analysis += "Vocal Style: [Vocal technique and style]\n";
    analysis += "Production Techniques: [Recording and mixing techniques]\n";
    analysis += "Dynamics: [Volume variations and intensity]\n";
    analysis += "Harmonic Complexity: [Level of harmonic sophistication]\n";
    analysis += "Rhythmic Complexity: [Level of rhythmic sophistication]\n";
    analysis += "Musical Influences: [Influences and inspirations]\n";
    
    return analysis;
}

juce::String MainComponent::getLyricsAnalysis(const juce::String& songName)
{
    juce::String analysis;
    
    // Simulate lyrics analysis
    analysis += "Theme: [Main theme or subject matter]\n";
    analysis += "Narrative Structure: [Storytelling approach]\n";
    analysis += "Language Style: [Formal, poetic, colloquial, etc.]\n";
    analysis += "Emotional Tone: [Happy, sad, melancholic, energetic, etc.]\n";
    analysis += "Literary Devices: [Metaphors, similes, alliteration, etc.]\n";
    analysis += "Rhyming Scheme: [Pattern of rhymes]\n";
    analysis += "Repetition: [Use of repetition in lyrics]\n";
    analysis += "Imagery: [Descriptive and sensory language]\n";
    analysis += "Symbolism: [Use of symbols and metaphors]\n";
    analysis += "Cultural References: [References to culture, history, etc.]\n";
    
    return analysis;
}
