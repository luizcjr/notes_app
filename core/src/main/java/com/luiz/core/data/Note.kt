package com.luiz.core.data

data class Note (
    var title : String,
    var content : String,
    var creationTime : Long,
    var updatedTime: Long,
    var id : Long = 0,
    var wordCount : Int = 0
)