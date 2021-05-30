package ru.startandroid.develop.ivelost.module.data

//? Firebase user object
data class User(
    val uid: String,
    val username: String,
    val userEmail: String,
    val profileImageUrl: String?
) {
    // we need to define empty constructor for getting data from firebase db purposes
    constructor() : this("", "", "", "")
}
