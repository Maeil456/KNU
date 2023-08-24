package com.example.test1

class RecentOptionsManager {
    companion object {
        private const val MAX_RECENT_OPTIONS = 8
        private val recentOptions = mutableListOf<String>()

        fun addOption(option: String) {
            if (recentOptions.contains(option)) {
                recentOptions.remove(option)
            }
            recentOptions.add(0, option)
            if (recentOptions.size > MAX_RECENT_OPTIONS) {
                recentOptions.removeAt(MAX_RECENT_OPTIONS)
            }
        }

        fun getRecentOptions(): List<String> = recentOptions
    }
}