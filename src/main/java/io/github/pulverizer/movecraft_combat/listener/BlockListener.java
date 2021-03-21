package io.github.pulverizer.movecraft_combat.listener;

import static org.spongepowered.api.event.Order.LAST;

import com.flowpowered.math.vector.Vector3i;
import io.github.pulverizer.movecraft.config.Settings;
import io.github.pulverizer.movecraft.config.craft.CraftSettings;
import io.github.pulverizer.movecraft.craft.Craft;
import io.github.pulverizer.movecraft.craft.CraftManager;
import io.github.pulverizer.movecraft.utils.CollectionUtils;
import io.github.pulverizer.movecraft_combat.config.CrewRoles;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.data.Transaction;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.HashSet;

public class BlockListener {

    //TODO - fire event from core plugin and listen for it here
    //TODO - strip down to repairman role code
    @Listener(order = LAST)
    public void onBlockPlace(ChangeBlockEvent.Place event, @Root Player player) {
        if (Settings.ProtectPilotedCrafts) {

            for (Transaction<BlockSnapshot> transaction : event.getTransactions()) {
                Location<World> location = transaction.getOriginal().getLocation().orElse(null);

                boolean foundCrafts = false;
                HashSet<Craft> repairingCrafts = new HashSet<>();

                for (Vector3i blockPosition : CollectionUtils.neighbors(location.getBlockPosition())) {
                    HashSet<Craft> craftsAtLocation =
                            CraftManager.getInstance().getCraftsFromLocation(new Location<>(location.getExtent(), blockPosition));

                    if (!craftsAtLocation.isEmpty()) {
                        foundCrafts = true;

                        for (Craft craft : craftsAtLocation) {

                            if (craft.isSinking() || !craft.getCrew().hasRole(player.getUniqueId(), CrewRoles.Repairman.class)
                                    || !craft.getType().getValue(CraftSettings.AllowedBlocks.class).get().contains(transaction.getFinal().getState().getType())
                                    || craft.getSize() >= craft.getType().getValue(CraftSettings.MaxSize.class).get()) {
                                continue;
                            }

                            repairingCrafts.addAll(craftsAtLocation);
                            break;
                        }
                    }
                }


                if (repairingCrafts.isEmpty() && foundCrafts) {
                    transaction.setValid(false);
                    player.sendMessage(Text.of("You are not a repairman!"));

                } else {

                    boolean isProcessing = false;
                    for (Craft craft : repairingCrafts) {
                        if (craft.isProcessing()) {
                            isProcessing = true;
                            break;
                        }
                    }

                    if (!isProcessing) {
                        repairingCrafts.removeIf(craft -> !craft.getType().getValue(CraftSettings.AllowedBlocks.class).get().contains(transaction.getFinal().getState().getType()));
                        repairingCrafts.forEach(craft -> craft.getHitBox().add(location.getBlockPosition()));
                    } else {
                        player.sendMessage(Text.of("Craft is Busy"));
                    }
                }
            }
        }
    }

    //TODO: Is this listener needed?
    // Should not need this due to blocks still ticking?

    /*@Listener(order = LAST)
    public void onBlockIgnite(BlockIgniteEvent event) {
        // replace blocks with fire occasionally, to prevent fast crafts from simply ignoring fire
        if (!Settings.FireballPenetration || event.isCancelled() || event.getCause() != BlockIgniteEvent.IgniteCause.FIREBALL) {
            return;
        }
        BlockSnapshot testBlock = event.getBlock().getRelative(-1, 0, 0);
        if (!testBlock.getType().isBurnable())
            testBlock = event.getBlock().getRelative(1, 0, 0);

        if (!testBlock.getType().isBurnable())
            testBlock = event.getBlock().getRelative(0, 0, -1);

        if (!testBlock.getType().isBurnable())
            testBlock = event.getBlock().getRelative(0, 0, 1);

        if (!testBlock.getType().isBurnable()) {
            return;
        }

        testBlock.setType(BlockTypes.AIR);
    }*/

}