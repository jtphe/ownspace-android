package com.example.ownspace.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * User class
 * @property id String? - User's id
 * @property firstname String? - User's firstname
 * @property lastname String? - User's lastname
 * @property email String? - User's email
 * @property password String? - User's password
 * @property picture String? - User's picture
 * @property notification Boolean? - User's notification
 * @property role String? - User's role
 * @property rootFolder String? - User's root folder
 * @property group String? - User's group
 * @property limitedStorage Boolean? - User's limited storage
 * @property storageSpaceUsed Double? - User's storage used
 * @property totalStorageSpace Double? - User's total storage
 */
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