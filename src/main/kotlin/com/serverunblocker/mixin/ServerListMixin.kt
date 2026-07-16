package com.serverunblocker.mixin

import com.serverunblocker.DEFAULT_SERVER_IP
import com.serverunblocker.DEFAULT_SERVER_NAME
import net.minecraft.client.multiplayer.ServerData
import net.minecraft.client.multiplayer.ServerList
import org.spongepowered.asm.mixin.Final
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.Shadow
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

@Mixin(ServerList::class)
class ServerListMixin {

    @field:Shadow
    @field:Final
    private var serverList: MutableList<ServerData>? = null

    @Inject(method = ["load"], at = [At("RETURN")])
    private fun serverunblockerAddDefaultServer(_ci: CallbackInfo?) {
        val servers = serverList ?: return
        for (server in servers) {
            if (java.lang.String.CASE_INSENSITIVE_ORDER.compare(server.ip, DEFAULT_SERVER_IP) == 0) return
        }

        //? if <1.20.2 {
        /*servers.add(0, ServerData(DEFAULT_SERVER_NAME, DEFAULT_SERVER_IP, false))
        *///?} else {
        servers.add(0, ServerData(DEFAULT_SERVER_NAME, DEFAULT_SERVER_IP, ServerData.Type.OTHER))
        //?}
    }
}
