package com.maks.hp.tochka.api

import com.maks.hp.tochka.gitsearch.entity.Entity
import io.reactivex.Observable


class SearchServers(val api: ServerApi) {
    fun searchUsers(q: String): Observable<Entity> {
        return api.loadData(q)
    }
}