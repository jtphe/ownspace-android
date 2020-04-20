package com.example.ownspace.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class User() : RealmObject() {
    @PrimaryKey
    var id: String? = null
    var firstname: String? = null
    var lastname: String? = null
    var email: String? = null
    var password: String? = null
    var picture: String? = null
    var notification: Boolean? = null
    var role: String? = null
    var rootFolder: String? = null
    var group: String? = null
    var limitedStorage: Boolean? = null
    var storageSpaceUsed: Double? = null
    var totalStorageSpace: Double? = null
}