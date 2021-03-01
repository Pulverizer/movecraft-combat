package io.github.pulverizer.movecraft_combat;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;

@Plugin(
        id = "movecraft-combat",
        name = "Movecraft Combat",
        description = "Combat addon for Movecraft for Sponge",
        version = "1.0-SNAPSHOT",
        dependencies = @Dependency(id = "movecraft", version = "[0.4.1,)"),
        url = "https://github.com/Pulverizer/Movecraft-for-Sponge",
        authors = {"BernardisGood", "https://github.com/Pulverizer/Movecraft-for-Sponge/graphs/contributors"})

public class MovecraftCombat {

    @Inject private Logger logger;

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
    }
}
