package com.gorbunov.nettestclassic.model

/**
 * Файл модели для парсинга ответа с сервера,
 * некоторые поля не берутся для удобства
 */


data class NetTestResponse(
    val data: List<Data>
)



data class Data(
    val groups: List<Group>,
    val direction: Direction
)


data class Direction (
    var id: String,
    var link: String,
    var title: String
)


data class Group (
    var id: String,
    var link: String,
    var items: List<Item>,
    var title: String
)

data class Item (
    var id: String,
    var link: String,
    var title: String
)