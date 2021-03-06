package io.github.pulverizer.movecraft_combat;

import com.google.inject.Inject;
import io.github.pulverizer.movecraft.api.event.RegisterFeaturesEvent;
import io.github.pulverizer.movecraft_combat.config.CraftSettings;
import io.github.pulverizer.movecraft_combat.config.CrewRoles;
import io.github.pulverizer.movecraft_combat.listener.FireballListener;
import io.github.pulverizer.movecraft_combat.listener.PlayerListener;
import io.github.pulverizer.movecraft_combat.listener.TNTListener;
import io.github.pulverizer.movecraft_combat.sign.AntiAircraftDirectorSign;
import io.github.pulverizer.movecraft_combat.sign.CannonDirectorSign;
import io.github.pulverizer.movecraft_combat.sign.LoaderSign;
import io.github.pulverizer.movecraft_combat.sign.RepairmanSign;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
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

    @Listener(order = Order.FIRST)
    public void registerFeatures(RegisterFeaturesEvent event) {
        CraftSettings.register();
        CrewRoles.register();
    }

    /**
     * Listener for GamePreInitializationEvent. Loads the Plugin's settings.
     * @param event GamePreInitializationEvent from Listener.
     */
    @Listener
    public void onLoad(GamePreInitializationEvent event) {

        Sponge.getEventManager().registerListeners(this, new AntiAircraftDirectorSign());
        Sponge.getEventManager().registerListeners(this, new CannonDirectorSign());
        Sponge.getEventManager().registerListeners(this, new LoaderSign());
        Sponge.getEventManager().registerListeners(this, new RepairmanSign());

        Sponge.getEventManager().registerListeners(this, new FireballListener());
        Sponge.getEventManager().registerListeners(this, new TNTListener());
        Sponge.getEventManager().registerListeners(this, new PlayerListener());
    }
}
