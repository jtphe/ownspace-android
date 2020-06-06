package com.example.ownspace.models

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * File class
 * @property id String? - File's id
 * @property createdAt Date? - File's creation date
 * @property updatedAt Date? - File's updating date
 * @property name String? - File's name
 * @property owner String? - File's owner
 * @property sharedList RealmList<User>? - File's sharedList
 * @property isProtected Boolean? - If the File is protected or not
 * @property password String? - File's password
 * @property parent String? - File's parent
 * @property size String? - File's size
 * @property mimeType String? - File's mimetype
 * @property type String? - File's type
 */
open class File : RealmObject() {
    @PrimaryKey
    var id: String? = null
    var createdAt: String? = null
    var updatedAt: String? = null
    var name: String? = null
    var content: String? = null
    var owner: String? = null
    var sharedList: RealmList<User>? = null
    var isProtected: Boolean? = null
    var password: String? = null
    var parent: String? = null
    var size: Float? = null
    var mimeType: String? = null
    var type: String ?= null
}