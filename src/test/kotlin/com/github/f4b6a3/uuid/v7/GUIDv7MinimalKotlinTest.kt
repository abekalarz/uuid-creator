package com.github.f4b6a3.uuid.v7

fun main() {
    val uuid = GUID.v7().toUUID()
    check(uuid.version() == 7)
    check(uuid.variant() == 2)
    println(uuid)
}
