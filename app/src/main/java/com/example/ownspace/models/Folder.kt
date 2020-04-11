package com.example.ownspace.models

import java.util.*

class Folder(id: String, name: String, owner: String) {
    var id: String? = null
    var createdAt: Date? = null
    var updatedAt: Date? = null
    var name: String? = null
    var owner: String? = null
    var sharedList: Array<User>? = null
    var password: String? = null
    var parent: String? = null
    var nbFiles: Int? = null
}