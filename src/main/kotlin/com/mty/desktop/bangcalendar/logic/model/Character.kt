package com.mty.desktop.bangcalendar.logic.model

import kotlinx.serialization.Serializable

@Serializable
data class Character(var id: Long, var name: String, var birthday: String,
                     var color: String, var band: String)
