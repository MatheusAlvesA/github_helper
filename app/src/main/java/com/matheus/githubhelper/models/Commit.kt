package com.matheus.githubhelper.models

data class Commit (
    var id: String,
    var name_author: String,
    var email_author: String,
    var message: String
)