package com.serverunblocker.mixin

import com.mojang.patchy.BlockedServers
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable

@Mixin(BlockedServers::class)
class BlockedServersMixin {

    @Inject(
        method = ["isBlockedServerHostName"],
        at = [At("HEAD")],
        cancellable = true,
        remap = false,
    )
    private fun serverunblockerUnblockEverything(_hostName: String?, cir: CallbackInfoReturnable<Boolean>?) {
        cir?.returnValue = false
    }
}
