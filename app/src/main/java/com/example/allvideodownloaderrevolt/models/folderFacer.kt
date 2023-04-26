package com.example.allvideodownloaderrevolt.models

class folderFacer {
    var path: String? = null
    var folderName: String? = null

    constructor() {}
    constructor(path: String?, folderName: String?) {
        this.path = path
        this.folderName = folderName
    }
}