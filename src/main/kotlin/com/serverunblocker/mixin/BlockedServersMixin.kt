package com.serverunblocker.mixin

import com.mojang.patchy.BlockedServers
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable

/**
 * Mojang ships a `BlockedServers` helper (in the `patchy` library) whose
 * [isBlockedServerHostName] returns true for any host that matches a
 * SHA-1 hash in the downloaded block list. The client refuses to add or
 * connect to those hosts.
 *
 * We inject at RETURN and force the answer to `false`, so *every* server —
 * including any anarchy server that ends up on the block list — can be added
 * and joined. `remap = false` because `BlockedServers` is a library class,
 * not a Minecraft class, so it is not part of the mappings.
 */
@Mixin(BlockedServers::class)
class BlockedServersMixin {

    @Inject(
        method = ["isBlockedServerHostName"],
        at = [At("RETURN")],
        cancellable = true,
        remap = false,
    )
    private fun serverunblockerUnblockEverything(hostName: String, cir: CallbackInfoReturnable<Boolean>) {
        cir.returnValue = false
    }
}
