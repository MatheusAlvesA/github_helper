package com.matheus.githubhelper.models

data class Repository(
    var id: Int,
    var name: String,
    var full_name: String,
    var private: Boolean,
    var html_url: String,
    var created_at: String,
    var updated_at: String,
    var clone_url: String,
    var stargazers_count: Int,
    var forks_count: Int,
    var open_issues_count: Int
)