package com.example.flo

data class Save(
    var title : String = "",
    var singer : String = "",
    var coverImg : Int? = null,
    var songs : ArrayList<Song>? = null
)
