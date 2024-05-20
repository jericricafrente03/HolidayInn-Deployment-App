package com.bittelasia.holidayinn.data.manager

import com.bittelasia.holidayinn.data.repository.stbpref.data.STB
import com.bittelasia.holidayinn.presentation.components.removeHttpsFromIpAddress
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jivesoftware.smack.AbstractXMPPConnection
import org.jivesoftware.smack.ConnectionConfiguration
import org.jivesoftware.smack.ConnectionListener
import org.jivesoftware.smack.ReconnectionManager
import org.jivesoftware.smack.SASLAuthentication
import org.jivesoftware.smack.XMPPConnection
import org.jivesoftware.smack.XMPPException
import org.jivesoftware.smack.chat2.ChatManager
import org.jivesoftware.smack.packet.Presence
import org.jivesoftware.smack.tcp.XMPPTCPConnection
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration
import java.io.IOException
import java.net.InetAddress

object XMPPManager {
    private val connectionState = MutableStateFlow(ConnectionState.Disconnected)
    val stateFlow: StateFlow<ConnectionState> = connectionState.asStateFlow()

    private val dataTransfer = MutableSharedFlow<String>()
    val xmppData: SharedFlow<String> = dataTransfer.asSharedFlow()


    private lateinit var chatManager: ChatManager
    private lateinit var connection: AbstractXMPPConnection
    private const val domain = "localhost"
    private const val xmppPort = 5222

    suspend fun createConnection() = withContext(Dispatchers.IO){
        try {
            val config: XMPPTCPConnectionConfiguration =
                XMPPTCPConnectionConfiguration.builder()
                    .setUsernameAndPassword(STB.USERNAME, STB.PASSWORD)
                    .setHostAddress(InetAddress.getByName(removeHttpsFromIpAddress(STB.HOST)))
                    .setPort(xmppPort)
                    .setXmppDomain(domain)
                    .setSendPresence(true)
                    .setCompressionEnabled(true)
                    .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                    .build()

            SASLAuthentication.blacklistSASLMechanism("SCRAM-SHA-1")
            SASLAuthentication.blacklistSASLMechanism("DIGEST-MD5")
            SASLAuthentication.unBlacklistSASLMechanism("PLAIN")

            connection = XMPPTCPConnection(config)
            chatManager = ChatManager.getInstanceFor(connection)
            ReconnectionManager.getInstanceFor(connection).enableAutomaticReconnection()
            chatManager.addIncomingListener { _, message, _ ->
                CoroutineScope(Dispatchers.IO).launch{
                    dataTransfer.emit(message.body)
                }
            }
            connection.apply {
                addConnectionListener(object : ConnectionListener {
                    override fun connected(connection: XMPPConnection?) {
                        connectionState.value = ConnectionState.Connected
                        val presence = Presence(Presence.Type.available)
                        connection?.sendStanza(presence)
                    }

                    override fun authenticated(
                        connection: XMPPConnection?,
                        resumed: Boolean
                    ) {
                        connectionState.value = ConnectionState.Authenticated
                    }

                    override fun connectionClosed() {
                        connectionState.value = ConnectionState.ConnectionClosed
                        reconnect()
                    }

                    override fun connectionClosedOnError(e: Exception?) {
                        connectionState.value = ConnectionState.ConnectionClosedAndError
                        reconnect()
                    }

                    @Deprecated("Deprecated in Java")
                    override fun reconnectionSuccessful() {
                        connectionState.value = ConnectionState.ReconnectionSuccessful
                    }

                    @Deprecated("Deprecated in Java")
                    override fun reconnectingIn(seconds: Int) {
                        connectionState.value = ConnectionState.ReconnectingIn
                    }

                    @Deprecated("Deprecated in Java")
                    override fun reconnectionFailed(e: java.lang.Exception?) {
                        connectionState.value = ConnectionState.ReconnectionFailed
                        reconnect()
                    }

                    private fun reconnect() {
                        connectionState.value = ConnectionState.Connecting
                        CoroutineScope(Dispatchers.IO).launch {
                            while (connectionState.value == ConnectionState.Disconnected) {
                                try {
                                    delay(5000)
                                    connect().login()
                                } catch (e: IOException) {
                                    e.printStackTrace()
                                } catch (e: XMPPException) {
                                    e.printStackTrace()
                                }
                            }
                        }
                    }
                })
                connect().login()
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

enum class ConnectionState {
    ReconnectionFailed,
    ConnectionClosedAndError,
    ReconnectionSuccessful,
    ConnectionClosed,
    ReconnectingIn,
    Connected,
    Authenticated,
    Disconnected,
    Connecting,
}