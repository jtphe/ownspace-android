package com.example.ownspace.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * User class
 * @property id String? - User's id
 * @property createdAt Date? - User's creation date
 * @property updatedAt Date? - User's updating date
 * @property firstname String? - User's firstname
 * @property lastname String? - User's lastname
 * @property email String? - User's email
 * @property pictureName String? - User's picture name
 * @property pictureUrl String? - User's picture url
 * @property notification Boolean? - User's notification
 * @property role String? - User's role
 * @property rootFolder String? - User's root folder
 * @property group String? - User's group
 * @property limitedStorage Boolean? - User's limited storage
 * @property storageSpaceUsed Double? - User's storage used
 * @property totalStorageSpace Double? - User's total storage
 */
open class User : RealmObject() {
    @PrimaryKey
    var id: String? = null
    var createdAt: String? = null
    var updatedAt: String? = null
    var firstname: String? = null
    var lastname: String? = null
    var email: String? = null
    var pictureName: String? = null
    var pictureUrl: String? = null
    var notification: Boolean? = null
    var role: String? = null
    var group: String? = null
    var limitedStorage: Boolean? = null
    var storageSpaceUsed: Float? = null
    var totalStorageSpace: Float? = null
}