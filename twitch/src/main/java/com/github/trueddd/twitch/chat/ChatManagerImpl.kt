package com.github.trueddd.twitch.chat

import android.util.Log
import com.github.trueddd.twitch.data.ChatMessage
import com.github.trueddd.twitch.data.ChatStatus
import com.github.trueddd.twitch.db.TwitchDao
import com.ktmi.tmi.commands.join
import com.ktmi.tmi.dsl.builder.scopes.MainScope
import com.ktmi.tmi.dsl.builder.scopes.tmi
import com.ktmi.tmi.dsl.plugins.Reconnect
import com.ktmi.tmi.events.onConnected
import com.ktmi.tmi.events.onMessage
import com.ktmi.tmi.events.onTwitchMessage
import com.ktmi.tmi.messages.TwitchMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flowOn
import java.util.*

internal class ChatManagerImpl(
    private val twitchDao: TwitchDao,
) : ChatManager {

    companion object {
        const val TAG = "ChatManager"
    }

    override fun connectChat(channel: String): Flow<ChatStatus> {
        return channelFlow<ChatStatus> {
            send(ChatStatus.Connecting)
            val userToken = twitchDao.getUserToken() ?: run {
                send(ChatStatus.Disconnected(IllegalStateException("Token is null")))
                return@channelFlow
            }
            val messages = LinkedList<ChatMessage>()
            var chatClient: MainScope? = null
            tmi(
                token = userToken,
                secure = true,
                context = coroutineContext,
            ) {
                + Reconnect(5)

                onConnected {
                    chatClient = this
                    join(channel)
                }

                onMessage {
                    Log.d(TAG, "New message: ${message.username}=${message.message}")
                    messages.add(0, ChatMessage(username ?: message.username, message.message))
                    if (messages.size > 100) {
                        messages.removeLast()
                    }
                    trySend(ChatStatus.Connected(messages))
                }

                onTwitchMessage<TwitchMessage> { message ->
                    Log.d(TAG, "Event: ${message.rawMessage}")
                }
            }
            awaitClose {
                Log.d(TAG, "Disconnecting")
                chatClient?.disconnect()
                chatClient = null
            }
        }.flowOn(Dispatchers.IO)
    }
}