// MainComponent.h - Main application window component

#pragma once

#include <JuceHeader.h>

class MainComponent : public juce::Component,
                      private juce::Button::Listener,
                      private juce::TextEditor::Listener
{
public:
    MainComponent();
    ~MainComponent() override;

    void paint (juce::Graphics&) override;
    void resized() override;

private:
    void buttonClicked (juce::Button*) override;
    void textEditorTextChanged (juce::TextEditor&) override;

    // UI Components
    juce::Label titleLabel;
    juce::Label songNameLabel;
    juce::TextEditor songNameInput;
    juce::TextButton searchButton;
    juce::TextButton clearButton;
    juce::ToggleButton includeLyricsToggle;
    juce::Label resultsLabel;
    juce::TextEditor resultsOutput;
    juce::Label statusLabel;

    // Application logic
    void searchSongDescription();
    juce::String getSongDescription(const juce::String& songName, bool includeLyrics);
    juce::String getDetailedSongInfo(const juce::String& songName);
    juce::String getLyricsAnalysis(const juce::String& songName);
    juce::String getMusicalAnalysis(const juce::String& songName);

    JUCE_DECLARE_NON_COPYABLE_WITH_LEAK_DETECTOR (MainComponent)
};
