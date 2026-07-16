# ServerUnblocker
<img width="250" height="250" alt="ServerUnblocker (1)" src="https://github.com/user-attachments/assets/5e4a7a00-eae9-49c9-a12f-2e383baa34cc" />


ServerUnblocker is a Fabric client mod for players who want to keep `8b8t.me` easy to access or for players trying to join banned/blocked Minecraft servers by Mojang.

It was built in case Mojang blocks our server, `8b8t.me`, for any reason. When installed, the mod keeps blocked servers joinable and automatically adds `8b8t.me` to your Minecraft multiplayer server list.

## Features

- Unblocks servers affected by Mojang's blocked-server list.
- Adds `8b8t.me` to the top of your multiplayer server list if it is not already there.
- Runs client-side only.
- Supports multiple Fabric Minecraft versions.

## Download
### <a href="https://github.com/XeraPlugins/ServerUnblocker/releases/">Click here to download</a>
1. Download the jar that matches your Minecraft version from the GitHub Releases page.
2. Install [Fabric Loader](https://fabricmc.net/).
3. Install [Fabric API](https://modrinth.com/mod/fabric-api).
4. Put the downloaded `serverunblocker-mc-<version>.jar` file into your `.minecraft/mods` folder.
5. Launch Minecraft with your Fabric profile.

After launch, `8b8t.me` should appear in your multiplayer server list and blocked servers should be joinable.

## Privacy / Telemetry

ServerUnblocker is built with **privacy as a core principle**. The mod does **not** collect, store, or transmit any personal or usage data. It operates entirely client-side and never sends information to our servers.

Unlike 6b6t's AnarchyMod, which sends data to its servers for analytics and data collection, ServerUnblocker collects absolutely **zero data**. No analytics, no telemetry, no tracking—ever.

## Supported Versions

This project is built with [Stonecutter](https://stonecutter.kikugie.dev/) for multiple Minecraft/Fabric versions. Each supported version has its own build config in `versions/<version>/gradle.properties`.

## Contact

- Email: contact@8b8t.me
- Discord: https://discord.8b8t.me

## For Developers

Requires a compatible JDK and internet access for Gradle to download Minecraft, mappings, and Fabric dependencies.

```bash
# Build the active version
gradle build

# Build every configured version
gradle chiseledBuild
```

Output jars are created in `versions/<mc>/build/libs/`.

## How This Works

ServerUnblocker uses two client-side Mixins:

| Mixin | Target | Effect |
|-------|--------|--------|
| `BlockedServersMixin` | `com.mojang.patchy.BlockedServers#isBlockedServerHostName` | Forces the blocked-server check to return `false`. |
| `ServerListMixin` | `net.minecraft.client.multiplayer.ServerList#load` | Adds `8b8t.me` to the top of the server list after it loads, if missing. |

The mod does not need server-side installation. It only changes how your own Minecraft client handles the blocked-server check and server list.

## License

MIT licensed.
