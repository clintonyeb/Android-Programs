package com.example.clinton.companion.news.facades;

import java.io.Serializable;

public enum NewsResults implements Serializable {
    Success,
    Timeout,
    NoConnection,
    Network,
    Unknown,
    AddedToFavorites,
    RemovedFromFavorites
}
