package com.maks.hp.tochka.api

object SearchServersProvider {

    fun provideSearchServers(): SearchServers{
        return SearchServers(ServerApi.create())
    }
}