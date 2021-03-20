package io.github.pulverizer.movecraft_combat.listener;

import io.github.pulverizer.movecraft.craft.Craft;
import io.github.pulverizer.movecraft.craft.CraftManager;
import io.github.pulverizer.movecraft_combat.config.CrewRoles;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.block.tileentity.carrier.Dispenser;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.InventoryTransformations;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.query.QueryOperationTypes;
import org.spongepowered.api.text.Text;

import java.util.HashSet;
import java.util.Optional;

public final class InteractListener {

    @Listener
    public final void onPlayerInteractSecondary(InteractBlockEvent.Secondary.MainHand event, @Root Player player) {
        BlockSnapshot block = event.getTargetBlock();

        HashSet<Craft> crafts = CraftManager.getInstance().getCraftsFromLocation(block.getLocation().get());

        boolean isCrewMember = false;
        boolean isLoader = false;
        for (Craft craft : crafts) {
            if (craft.getCrew().contains(player.getUniqueId())) {
                isCrewMember = true;

                if (craft.getCrew().hasRole(player.getUniqueId(),CrewRoles.Loader.class)) {
                    isLoader = true;
                    break;
                }
            }
        }

        if (!crafts.isEmpty() && !isCrewMember) {
            player.sendMessage(Text.of("You are not a crew member aboard this craft!"));
            event.setCancelled(true);
            return;
        }

        if (!isLoader || !block.getState().getType().equals(BlockTypes.DISPENSER)) {
            return;
        }

        Optional<ItemStack> itemInHand = player.getItemInHand(HandTypes.MAIN_HAND);

        if (itemInHand.isPresent()) {
            if (itemInHand.get().getType().equals(ItemTypes.TNT) || itemInHand.get().getType().equals(ItemTypes.FIRE_CHARGE)) {
                Dispenser dispenser = (Dispenser) block.getLocation().get().getTileEntity().get();
                ItemStack giveStack = itemInHand.get().copy();
                dispenser.getInventory().offer(giveStack);
                player.getInventory().query(QueryOperationTypes.ITEM_TYPE.of(itemInHand.get().getType())).transform(InventoryTransformations.REVERSE)
                        .poll(itemInHand.get().getQuantity() - giveStack.getQuantity());
                event.setCancelled(true);
            }
        }
    }
}