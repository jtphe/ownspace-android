package com.example.ownspace.models

import io.realm.RealmList
import io.realm.RealmObject

/**
 * Path class
 * @property path RealmList<PathItem> - The path list
 */
open class Path : RealmObject() {
    var path: RealmList<PathItem> = RealmList()
}