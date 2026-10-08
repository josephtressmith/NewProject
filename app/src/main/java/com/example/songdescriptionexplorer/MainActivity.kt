package com.example.songdescriptionexplorer

import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.songdescriptionexplorer.databinding.ActivityMainBinding
import com.example.songdescriptionexplorer.ui.SongDescriptionViewModel

/**
 * Main Activity for the Song Description Explorer App
 * Handles the UI and user interactions
 */
class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    private val viewModel: SongDescriptionViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize view binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        // Setup UI
        setupUI()
        setupObservers()
    }
    
    private fun setupUI() {
        // Set up search button click listener
        binding.searchButton.setOnClickListener {
            performSearch()
        }
        
        // Set up clear button click listener
        binding.clearButton.setOnClickListener {
            clearForm()
        }
        
        // Set up include lyrics switch listener
        binding.includeLyricsSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.toggleLyricsInclusion(isChecked)
        }
        
        // Set up editor action listener for search on enter
        binding.songNameEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH) {
                performSearch()
                true
            } else {
                false
            }
        }
    }
    
    private fun setupObservers() {
        // Observe song data
        viewModel.song.observe(this, Observer { song ->
            // Song data is handled through formattedDescription
        })
        
        // Observe formatted description
        viewModel.formattedDescription.observe(this, Observer { description ->
            binding.resultsTextView.text = description
        })
        
        // Observe loading state
        viewModel.isLoading.observe(this, Observer { isLoading ->
            binding.progressBar.visibility = if (isLoading) android.view.View.VISIBLE else android.view.View.GONE
            binding.searchButton.isEnabled = !isLoading
            binding.clearButton.isEnabled = !isLoading
        })
        
        // Observe error state
        viewModel.error.observe(this, Observer { error ->
            error?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })
        
        // Observe status
        viewModel.status.observe(this, Observer { status ->
            binding.statusTextView.text = status
        })
        
        // Observe include lyrics state
        viewModel.includeLyrics.observe(this, Observer { includeLyrics ->
            binding.includeLyricsSwitch.isChecked = includeLyrics
        })
    }
    
    /**
     * Perform song search
     */
    private fun performSearch() {
        val songName = binding.songNameEditText.text.toString().trim()
        
        if (songName.isBlank()) {
            viewModel.searchSong("")
            return
        }
        
        // Hide keyboard
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.songNameEditText.windowToken, 0)
        
        // Trigger search
        viewModel.searchSong(songName)
    }
    
    /**
     * Clear the form
     */
    private fun clearForm() {
        binding.songNameEditText.text.clear()
        viewModel.clearSearch()
        
        // Show keyboard for new search
        binding.songNameEditText.requestFocus()
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(binding.songNameEditText, InputMethodManager.SHOW_IMPLICIT)
    }
    
    /**
     * Handle back button press
     */
    override fun onBackPressed() {
        // If we have search results, clear them first
        if (viewModel.song.value != null) {
            clearForm()
        } else {
            super.onBackPressed()
        }
    }
}
