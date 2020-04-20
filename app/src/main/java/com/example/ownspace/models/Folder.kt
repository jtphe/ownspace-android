package com.example.ownspace.models

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class Folder() : RealmObject() {
    @PrimaryKey
    var id: String? = null
    var createdAt: Date? = null
    var updatedAt: Date? = null
    var name: String? = null
    var owner: String? = null
    var sharedList: RealmList<User>? = null
    var password: String? = null
    var parent: String? = null
    var nbFiles: Int? = null
}