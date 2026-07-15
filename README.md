# ServerUnblocker

A Fabric client mod, written in **Kotlin**, that:

- **Unblocks every server** — neutralises Mojang's blocked-server list check so any server that ends up on the block list (e.g. anarchy servers) can still be added and joined.
- **Adds 8b8t.me to your serverlist** — inserts `8b8t.me` at the top of your multiplayer server list on launch if it isn't already there.

## How it works

Two Mixins do all the work:

| Mixin | Target | Effect |
|-------|--------|--------|
| `BlockedServersMixin` | `com.mojang.patchy.BlockedServers#isBlockedServerHostName` | Injects at `RETURN` and forces `false`, so nothing is ever considered blocked. |
| `ServerListMixin` | `net.minecraft.client.multiplayer.ServerList#load` | After the list loads, adds `8b8t.me` at the top if missing. |

`ServerUnblocker` (the `ClientModInitializer`) just logs on startup — no runtime logic is needed.

## Supported versions

Built with [Stonecutter](https://stonecutter.kikugie.dev/) across Minecraft **1.19.4 → 26.2** on Fabric. The `versions/` folder holds one `gradle.properties` per version with its exact loader / Fabric API / Java requirement.

## Building

Requires a JDK matching the newest target (Java 25 for the 26.x modules) and internet access for Gradle to download Minecraft, mappings, and Fabric.

```bash
# active single version (set in stonecutter.gradle.kts, default 26.2)
./gradlew build

# every version at once
./gradlew chiseledBuild
```

Output JARs land in each module's `versions/<mc>/build/libs/`.

> The repo ships `gradle/wrapper/gradle-wrapper.properties` but not the wrapper JAR. Run `gradle wrapper` once (with a system Gradle) to generate `gradlew` + the JAR, or open the project in IntelliJ IDEA and let it set the wrapper up.

## Installing (players)

1. Install [Fabric Loader](https://fabricmc.net/), [Fabric API](https://modrinth.com/mod/fabric-api), and [Fabric Language Kotlin](https://modrinth.com/mod/fabric-language-kotlin) for your Minecraft version.
2. Drop the matching `serverunblocker-mc-<version>.jar` into `.minecraft/mods`.
3. Launch — `8b8t.me` appears in your server list and blocked servers are joinable.

## Notes & maintenance

- **Kotlin is compiled to JVM 21 bytecode** even on the Java 25 (26.x) modules, because the Kotlin compiler doesn't yet emit JVM 25 bytecode. Bump `kotlinTarget` in the build scripts once it does.
- **Fabric Language Kotlin** is declared as a runtime dependency in `fabric.mod.json` rather than pinned per-version in Gradle, so the build stays valid across the whole version range. For in-dev `runClient`, uncomment the `modRuntimeOnly` FLK line in the relevant build script and set a version that matches your MC.
- Per-version numbers in `versions/*/gradle.properties` are the main thing to update over time.

## Why this exists / fair use

The mod deliberately defeats a client-side moderation feature (Mojang's server block list). That's a well-established anarchy-community modding practice and doesn't harm your machine, but it's your call whether to use it. MIT licensed.
