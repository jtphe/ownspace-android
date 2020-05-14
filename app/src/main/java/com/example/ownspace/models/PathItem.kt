package com.example.ownspace.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * PathIem class
 * @property id String? - Id of the path
 * @property name String? - Name of the path
 */
open class PathItem : RealmObject() {
    @PrimaryKey
    var id: String? = null
    var name: String? = null
}