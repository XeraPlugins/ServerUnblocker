package com.serverunblocker

import net.fabricmc.api.ClientModInitializer
import org.slf4j.LoggerFactory

const val DEFAULT_SERVER_NAME = "8b8t"
const val DEFAULT_SERVER_IP = "8b8t.me"

private val LOGGER = LoggerFactory.getLogger("ServerUnblocker")

class ServerUnblocker : ClientModInitializer {

    override fun onInitializeClient() {
        LOGGER.info("ServerUnblocker loaded: blocked-server list bypassed, {} added to your server list.", DEFAULT_SERVER_IP)
    }
}
