package com.example.ownspace.models

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Folder class
 * @property id String? - Folder's id
 * @property createdAt Date? - Folder's creation date
 * @property updatedAt Date? - Folder's updating date
 * @property name String? - Folder's name
 * @property owner String? - Folder's owner
 * @property sharedList RealmList<User>? - Folder's sharedList
 * @property isProtected Boolean? - If the folder is protected or not
 * @property password String? - Folder's password
 * @property parent String? - Folder's parent folder
 * @property nbFiles Int? - Number of files in the folder
 */
open class Folder : RealmObject() {
    @PrimaryKey
    var id: String? = null
    var createdAt: String? = null
    var updatedAt: String? = null
    var name: String? = null
    var owner: String? = null
    var sharedList: RealmList<User>? = null
    var isProtected: Boolean? = null
    var password: String? = null
    var parent: String? = null
    var nbFiles: Int? = null
}