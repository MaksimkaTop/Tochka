package com.maks.hp.tochka.api

import com.maks.hp.tochka.gitsearch.entity.Entity
import io.reactivex.Single


class SearchServers(val api: ServerApi) {
    fun searchUsers(q: String): Single<Entity> {
        return api.loadData(q)
    }
}