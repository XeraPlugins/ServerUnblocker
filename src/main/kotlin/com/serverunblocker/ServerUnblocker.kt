package com.serverunblocker

import net.fabricmc.api.ClientModInitializer
import org.slf4j.LoggerFactory

/**
 * ServerUnblocker entry point.
 *
 * The actual behaviour lives in two mixins:
 *  - [com.serverunblocker.mixin.BlockedServersMixin] neutralises Mojang's
 *    blocked-server check so any blocked server can be added and joined.
 *  - [com.serverunblocker.mixin.ServerListMixin] auto-adds 8b8t.me to the
 *    multiplayer server list.
 *
 * This class just logs that the mod loaded — no game logic needed at init time.
 */
class ServerUnblocker : ClientModInitializer {

    override fun onInitializeClient() {
        LOGGER.info("ServerUnblocker loaded: blocked-server list bypassed, {} added to your server list.", DEFAULT_SERVER_IP)
    }

    companion object {
        const val DEFAULT_SERVER_NAME = "8b8t"
        const val DEFAULT_SERVER_IP = "8b8t.me"

        @JvmStatic
        val LOGGER = LoggerFactory.getLogger("ServerUnblocker")
    }
}
