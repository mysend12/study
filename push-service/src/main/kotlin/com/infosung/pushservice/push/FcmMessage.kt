package com.infosung.pushservice.push

import com.google.firebase.messaging.Message

data class FcmMessage(
    val validateOnly: Boolean,
    val message: Message,
)