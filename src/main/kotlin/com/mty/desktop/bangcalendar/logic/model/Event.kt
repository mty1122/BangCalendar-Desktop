package com.mty.desktop.bangcalendar.logic.model

import kotlinx.serialization.Serializable

@Serializable
data class Event(var id: Long, var name: String? = null, var startDate: Int,
                 var endDate: Int? = null, var attrs: Int, var type: Int,
                 var character1: Int, var character2: Int, var character3: Int,
                 var character4: Int? = null, var character5: Int? = null,
                 var character6: Int? = null, var character7: Int? = null)
