package com.serverunblocker.mixin

import com.serverunblocker.ServerUnblocker
import net.minecraft.client.multiplayer.ServerData
import net.minecraft.client.multiplayer.ServerList
import org.spongepowered.asm.mixin.Final
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.Shadow
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

/**
 * After the saved server list is loaded, make sure 8b8t.me is present. If it
 * isn't, insert it at the top of the list.
 *
 * The [ServerData] constructor changed across versions, so the two branches
 * below are selected at build time by Stonecutter:
 *  - pre-1.20.2 took a `boolean` (LAN flag)
 *  - 1.20.2+ takes a `ServerData.Type`
 */
@Mixin(ServerList::class)
class ServerListMixin {

    @Shadow
    @Final
    private lateinit var serverList: MutableList<ServerData>

    @Inject(method = ["load"], at = [At("RETURN")])
    private fun serverunblockerAddDefaultServer(ci: CallbackInfo) {
        val alreadyThere = serverList.any { it.ip.equals(ServerUnblocker.DEFAULT_SERVER_IP, ignoreCase = true) }
        if (alreadyThere) return

        //? if <1.20.2 {
        /*serverList.add(0, ServerData(ServerUnblocker.DEFAULT_SERVER_NAME, ServerUnblocker.DEFAULT_SERVER_IP, false))
        *///?} else {
        serverList.add(0, ServerData(ServerUnblocker.DEFAULT_SERVER_NAME, ServerUnblocker.DEFAULT_SERVER_IP, ServerData.Type.OTHER))
        //?}
    }
}
