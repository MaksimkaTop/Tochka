package com.maks.hp.tochka.api

import com.maks.hp.tochka.gitsearch.GitInfo
import com.maks.hp.tochka.gitsearch.entity.Entity
import io.reactivex.Single


class SearchServers(val api: ServerApi) {
    fun searchUsers(q: String, order: String, sort: String): Single<Entity> {
        return api.loadData(q, sort, order)
    }
}