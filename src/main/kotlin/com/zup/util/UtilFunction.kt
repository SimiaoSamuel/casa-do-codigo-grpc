package com.zup.util

import com.google.protobuf.Timestamp
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

fun Timestamp.toLocalDateTime(): LocalDateTime {
    val instant = Instant.ofEpochSecond(seconds, nanos.toLong())
    return LocalDateTime.ofInstant(instant, ZoneOffset.UTC)
}

fun LocalDateTime.toTimestamp(): Timestamp {
    val instant = toInstant(ZoneOffset.UTC)
    return Timestamp.newBuilder()
        .setSeconds(instant.epochSecond)
        .setNanos(instant.nano)
        .build()
}