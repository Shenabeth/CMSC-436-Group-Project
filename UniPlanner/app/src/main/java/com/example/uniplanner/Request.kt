package com.example.uniplanner

class RequestClass {
    var firstName: String? = null
    var lastName: String? = null

    constructor(firstName: String?, lastName: String?) {
        this.firstName = firstName
        this.lastName = lastName
    }

    constructor() {}
}