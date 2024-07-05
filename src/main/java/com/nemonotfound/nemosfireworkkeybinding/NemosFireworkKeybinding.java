package com.nemonotfound.nemosfireworkkeybinding;

import com.nemonotfound.nemosfireworkkeybinding.network.packet.FireworkKeyPressedPayload;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class NemosFireworkKeybinding implements ModInitializer {

	public static final String MOD_ID = "nemos-firework-keybinding";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Thank you for using Nemo's Firework Keybinding!");

		PayloadTypeRegistry.playC2S().register(FireworkKeyPressedPayload.ID, FireworkKeyPressedPayload.CODEC);
		registerFireworkKeyPressedEventConsumer();
	}

	private void registerFireworkKeyPressedEventConsumer() {
		ServerPlayNetworking.registerGlobalReceiver(FireworkKeyPressedPayload.ID, (payload, context) ->
				context.server().execute(() -> {
			ServerPlayerEntity player = context.player();
			getFireRocketFromInventory(player).ifPresent(fireworkRocket -> useRocket(player.getWorld(), player, fireworkRocket));
		}));
	}

	private Optional<ItemStack> getFireRocketFromInventory(ServerPlayerEntity player) {
		final Inventory inventory = player.getInventory();

		for (int i = 0; i < inventory.size(); i++) {
			final ItemStack itemStack = inventory.getStack(i);

			if (itemStack.isOf(Items.FIREWORK_ROCKET)) {
				return Optional.of(itemStack);
			}
		}

		return Optional.empty();
	}

	public void useRocket(World world, PlayerEntity user, ItemStack itemStack) {
		if (user.isFallFlying()) {
			if (!world.isClient) {
				FireworkRocketEntity fireworkRocketEntity = new FireworkRocketEntity(world, itemStack, user);
				world.spawnEntity(fireworkRocketEntity);
				itemStack.decrementUnlessCreative(1, user);
				user.incrementStat(Stats.USED.getOrCreateStat(Items.FIREWORK_ROCKET));
			}
		}
	}
}