package io.github.pulverizer.movecraft_combat.listener;

import io.github.pulverizer.movecraft.config.Settings;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.cause.entity.damage.DamageTypes;
import org.spongepowered.api.event.cause.entity.damage.source.DamageSource;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.event.filter.Getter;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.InventoryTransformations;
import org.spongepowered.api.item.inventory.query.QueryOperationTypes;
import org.spongepowered.api.world.explosion.Explosion;

import java.util.Random;

public class PlayerListener {

    @Listener
    public void playerFireDamage(DamageEntityEvent event, @Getter("getTargetEntity") Player player, @Getter("getSource") DamageSource damageSource) {
        if (Settings.AmmoDetonationMultiplier > 0 && damageSource.getType().equals(DamageTypes.FIRE)) {
            float tntCount = player.getInventory()
                    .query(QueryOperationTypes.ITEM_TYPE.of(ItemTypes.TNT), QueryOperationTypes.ITEM_TYPE.of(ItemTypes.TNT_MINECART)).totalItems();
            float fireChargeCount = player.getInventory().query(QueryOperationTypes.ITEM_TYPE.of(ItemTypes.FIRE_CHARGE)).totalItems();
            float otherCount = player.getInventory()
                    .query(QueryOperationTypes.ITEM_TYPE.of(ItemTypes.FIREWORK_CHARGE), QueryOperationTypes.ITEM_TYPE.of(ItemTypes.FIREWORKS),
                            QueryOperationTypes.ITEM_TYPE.of(ItemTypes.GUNPOWDER)).totalItems();

            float chance = ((tntCount / (Settings.AmmoDetonationMultiplier * 128)) + (fireChargeCount / (Settings.AmmoDetonationMultiplier * 512)) + (
                    otherCount / (Settings.AmmoDetonationMultiplier * 1024)));

            int diceRolled = new Random().nextInt(100);

            if (diceRolled <= chance) {
                float size = Math.min(chance * 2, 16);

                Explosion explosion = Explosion.builder()
                        .location(player.getLocation().add(0, 1.5, 0))
                        .shouldBreakBlocks(true)
                        .shouldDamageEntities(true)
                        .shouldPlaySmoke(true)
                        .radius(size)
                        .resolution((int) (size * 2))
                        .knockback(1)
                        .canCauseFire(fireChargeCount > 0)
                        .build();

                if (tntCount > 0) {
                    player.getInventory()
                            .query(QueryOperationTypes.ITEM_TYPE.of(ItemTypes.TNT), QueryOperationTypes.ITEM_TYPE.of(ItemTypes.TNT_MINECART))
                            .transform(InventoryTransformations.REVERSE).poll((int) tntCount / 2);
                }

                if (fireChargeCount > 0) {
                    player.getInventory().query(QueryOperationTypes.ITEM_TYPE.of(ItemTypes.FIRE_CHARGE)).transform(InventoryTransformations.REVERSE)
                            .poll((int) fireChargeCount / 2);
                }

                if (otherCount > 0) {
                    player.getInventory()
                            .query(QueryOperationTypes.ITEM_TYPE.of(ItemTypes.FIREWORK_CHARGE), QueryOperationTypes.ITEM_TYPE.of(ItemTypes.FIREWORKS),
                                    QueryOperationTypes.ITEM_TYPE.of(ItemTypes.GUNPOWDER)).transform(InventoryTransformations.REVERSE)
                            .poll((int) otherCount / 2);
                }

                player.getWorld().triggerExplosion(explosion);
            }
        }
    }
}