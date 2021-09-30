package io.github.riesenpilz.nms.packet;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import io.github.riesenpilz.nms.packet.handshakingIn.PacketHandshakingInHandshakeEvent;
import io.github.riesenpilz.nms.packet.loginIn.PacketLoginInCustomPayloadEvent;
import io.github.riesenpilz.nms.packet.loginIn.PacketLoginInEncryptionResponseEvent;
import io.github.riesenpilz.nms.packet.loginIn.PacketLoginInEvent;
import io.github.riesenpilz.nms.packet.loginIn.PacketLoginInStartEvent;
import io.github.riesenpilz.nms.packet.loginOut.PacketLoginOutCustomPayloadEvent;
import io.github.riesenpilz.nms.packet.loginOut.PacketLoginOutDisconnectEvent;
import io.github.riesenpilz.nms.packet.loginOut.PacketLoginOutEncryptionRequestEvent;
import io.github.riesenpilz.nms.packet.loginOut.PacketLoginOutEvent;
import io.github.riesenpilz.nms.packet.loginOut.PacketLoginOutLoginSuccessEvent;
import io.github.riesenpilz.nms.packet.loginOut.PacketLoginOutSetCompressionEvent;
import io.github.riesenpilz.nms.packet.playIn.PacketPlayInAbilitiesEvent;
import io.github.riesenpilz.nms.packet.playIn.PacketPlayInActionEvent;
import io.github.riesenpilz.nms.packet.playIn.PacketPlayInAdvancementsEvent;
import io.github.riesenpilz.nms.packet.playIn.PacketPlayInArmAnimationEvent;
import io.github.riesenpilz.nms.packet.playIn.PacketPlayInAutoRecipeEvent;
import io.github.riesenpilz.nms.packet.playIn.PacketPlayInBlockDigEvent;
import io.github.riesenpilz.nms.packet.playIn.PacketPlayInBlockNBTQueryEvent;
import io.github.riesenpilz.nms.packet.playIn.PacketPlayInBlockPlaceEvent;
import io.github.riesenpilz.nms.packet.playIn.PacketPlayInChangeBeaconEffectEvent;
import io.github.riesenpilz.nms.packet.playIn.PacketPlayInChangeHeldItemEvent;
import io.github.riesenpilz.nms.packet.playIn.PacketPlayInChatEvent;
import io.github.riesenpilz.nms.packet.playIn.PacketPlayInClickInventoryButtonEvent;
import io.github.riesenpilz.nms.packet.playIn.PacketPlayInClientStatusEvent;
import io.github.riesenpilz.nms.packet.playIn.PacketPlayInCloseInventoryEvent;
import io.github.riesenpilz.nms.packet.playIn.PacketPlayInCustomPayloadEvent;
import io.github.riesenpilz.nms.packet.playIn.PacketPlayInDifficultyChangeEvent;
import io.github.riesenpilz.nms.packet.playIn.PacketPlayInEditBookEvent;
import io.github.riesenpilz.nms.packet.playIn.PacketPlayInEntityInteractEvent;
import io.github.riesenpilz.nms.packet.playIn.PacketPlayInEntityNBTQueryEvent;
import io.github.riesenpilz.nms.packet.playIn.PacketPlayInEvent;
import io.github.riesenpilz.nms.packet.playIn.PacketPlayInGenerateStructureEvent;
import io.github.riesenpilz.nms.packet.playIn.PacketPlayInInventoryClickEvent;
import io.github.riesenpilz.nms.packet.playIn.PacketPlayInInventoryConfirmEvent;
import io.github.riesenpilz.nms.packet.playIn.PacketPlayInItemNameEvent;
import io.github.riesenpilz.nms.packet.playIn.PacketPlayInKeepAliveEvent;
import io.github.riesenpilz.nms.packet.playIn.PacketPlayInMovementEvent;
import io.github.riesenpilz.nms.packet.playIn.PacketPlayInPickItemEvent;
import io.github.riesenpilz.nms.packet.playIn.PacketPlayInRecipeDisplayedEvent;
import io.github.riesenpilz.nms.packet.playIn.PacketPlayInRecipeSettingsEvent;
import io.github.riesenpilz.nms.packet.playIn.PacketPlayInResourcePackStatusEvent;
import io.github.riesenpilz.nms.packet.playIn.PacketPlayInSetCreativeSlotEvent;
import io.github.riesenpilz.nms.packet.playIn.PacketPlayInSettingsEvent;
import io.github.riesenpilz.nms.packet.playIn.PacketPlayInSpectateEvent;
import io.github.riesenpilz.nms.packet.playIn.PacketPlayInSteerBoatEvent;
import io.github.riesenpilz.nms.packet.playIn.PacketPlayInSteerVehicleEvent;
import io.github.riesenpilz.nms.packet.playIn.PacketPlayInTabCompleteEvent;
import io.github.riesenpilz.nms.packet.playIn.PacketPlayInTeleportAcceptEvent;
import io.github.riesenpilz.nms.packet.playIn.PacketPlayInTradeSelectEvent;
import io.github.riesenpilz.nms.packet.playIn.PacketPlayInUpdateCommandBlockEvent;
import io.github.riesenpilz.nms.packet.playIn.PacketPlayInUpdateCommandMinecartEvent;
import io.github.riesenpilz.nms.packet.playIn.PacketPlayInUpdateJigsawBlockEvent;
import io.github.riesenpilz.nms.packet.playIn.PacketPlayInUpdateSignEvent;
import io.github.riesenpilz.nms.packet.playIn.PacketPlayInUpdateStructureBlockEvent;
import io.github.riesenpilz.nms.packet.playIn.PacketPlayInUseItemEvent;
import io.github.riesenpilz.nms.packet.playIn.PacketPlayInVehicleMoveEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutAdvancementsEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutAnimationEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutBlockActionEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutBlockBreakAnimationEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutBlockChangeEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutBlockEntityDataEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutBossBarEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutCameraEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutChunkDataEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutCollectItemEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutCombatEndEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutCombatStartEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutCostumPayloadEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutCraftRecipeResponseEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutDeathEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutDifficultyEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutDisconnectEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutEntityAttachEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutEntityAttributesEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutEntityDestroyEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutEntityEffectEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutEntityEffectRemoveEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutEntityEquipmentEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutEntityHeadLookEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutEntityMetadataEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutEntityMoveAndRotationEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutEntityMoveEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutEntityPositionEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutEntityRotationEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutEntitySoundEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutEntityStatusEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutEntityTeleportEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutEntityVelocityEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutExplosionEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutGameStateEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutHeldItemSlotEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutInventoryCloseEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutInventoryConfirmEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutInventoryItemsEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutInventoryOpenEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutInventoryPropertyEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutJoinGameEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutKeepAliveEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutListCommandsEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutMapDataEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutMessageEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutMultiBlockChangeEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutNBTQueryResponseEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutOpenBookEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutOpenHorseWindowEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutOpenSignEditorEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutParticleEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutPlayerAbilitiesEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutPlayerDiggingEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutPlayerInfoEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutRecipeUpdateEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutRecipesUnlockEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutResourcePackEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutRespawnEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutScoreboardDisplayEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutScoreboardObjectiveEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutScoreboardTeamEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutScoreboardUpdateScoreEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutSelectAdvancementTabEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutSetCooldownEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutSetPassengersEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutSetSlotEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutSetXpEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutSoundEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutSpawnEntityEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutSpawnLivingEntityEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutSpawnPaintingEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutSpawnPlayerEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutSpawnPositionEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutSpawnXpEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutStatisticsEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutStopSoundEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutTabCompleteEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutTabListHeaderAndFooterEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutTagsEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutTimeUpdateEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutTitleEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutTradeListEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutUnloadChunkEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutUpdateHealthEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutUpdateLightEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutUpdateViewDistanceEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutUpdateViewPositionEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutVehicleMoveEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutWorldBorderInitializeEvent;
import io.github.riesenpilz.nms.packet.statusIn.PacketStatusInEvent;
import io.github.riesenpilz.nms.packet.statusIn.PacketStatusInPingEvent;
import io.github.riesenpilz.nms.packet.statusIn.PacketStatusInReqestEvent;
import io.github.riesenpilz.nms.packet.statusOut.PacketStatusOutEvent;
import io.github.riesenpilz.nms.packet.statusOut.PacketStatusOutPongEvent;
import io.github.riesenpilz.nms.packet.statusOut.PacketStatusOutResponseEvent;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import net.minecraft.server.v1_16_R3.*;
import net.minecraft.server.v1_16_R3.PacketPlayInFlying.PacketPlayInLook;
import net.minecraft.server.v1_16_R3.PacketPlayInFlying.PacketPlayInPosition;
import net.minecraft.server.v1_16_R3.PacketPlayInFlying.PacketPlayInPositionLook;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntity.PacketPlayOutEntityLook;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntity.PacketPlayOutRelEntityMove;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntity.PacketPlayOutRelEntityMoveLook;

public class Injections implements Listener {

	private void injectPlayer(Player player) {
		final ChannelDuplexHandler channelDuplexHandler = new ChannelDuplexHandler() {
			@Override
			public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
				if (!Injections.this.read(player, ctx, msg, this))
					super.channelRead(ctx, msg);
			}

			@Override
			public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
				if (!Injections.this.write(player, msg))
					super.write(ctx, msg, promise);
			}
		};
		new io.github.riesenpilz.nms.entity.player.Player(player).getChannelPipeline().addBefore("packet_handler",
				player.getName(), channelDuplexHandler);
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerLoginEvent e) {
		injectPlayer(e.getPlayer());
	}

	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e) {
		removePlayer(e.getPlayer());
	}

	private void removePlayer(Player player) {
		final Channel channel = ((CraftPlayer) player).getHandle().playerConnection.networkManager.channel;
		channel.eventLoop().submit(() -> {
			channel.pipeline().remove(player.getName());
		});
	}

	private boolean read(Player player, ChannelHandlerContext ctx, Object msg, ChannelDuplexHandler handler)
			throws Exception {
		boolean canceled = false;
		if (msg instanceof PacketPlayInTeleportAccept) {
			final PacketPlayInEvent event = new PacketPlayInTeleportAcceptEvent(player,
					(PacketPlayInTeleportAccept) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayInTileNBTQuery) {
			final PacketPlayInEvent event = new PacketPlayInBlockNBTQueryEvent(player, (PacketPlayInTileNBTQuery) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayInEntityNBTQuery) {
			final PacketPlayInEvent event = new PacketPlayInEntityNBTQueryEvent(player,
					(PacketPlayInEntityNBTQuery) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayInDifficultyChange) {
			final PacketPlayInEvent event = new PacketPlayInDifficultyChangeEvent(player,
					(PacketPlayInDifficultyChange) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayInChat) {
			final PacketPlayInEvent event = new PacketPlayInChatEvent(player, (PacketPlayInChat) msg);
			Bukkit.getPluginManager().callEvent(event);
			System.out.println(event.isCanceled());
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayInClientCommand) {
			final PacketPlayInEvent event = new PacketPlayInClientStatusEvent(player, (PacketPlayInClientCommand) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayInSettings) {
			final PacketPlayInEvent event = new PacketPlayInSettingsEvent(player, (PacketPlayInSettings) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayInTabComplete) {
			final PacketPlayInEvent event = new PacketPlayInTabCompleteEvent(player, (PacketPlayInTabComplete) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayInTransaction) {
			final PacketPlayInEvent event = new PacketPlayInInventoryConfirmEvent(player,
					(PacketPlayInTransaction) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayInEnchantItem) {
			final PacketPlayInEvent event = new PacketPlayInClickInventoryButtonEvent(player,
					(PacketPlayInEnchantItem) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayInWindowClick) {
			final PacketPlayInEvent event = new PacketPlayInInventoryClickEvent(player, (PacketPlayInWindowClick) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayInCloseWindow) {
			final PacketPlayInEvent event = new PacketPlayInCloseInventoryEvent(player, (PacketPlayInCloseWindow) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayInCustomPayload) {
			final PacketPlayInEvent event = new PacketPlayInCustomPayloadEvent(player, (PacketPlayInCustomPayload) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayInBEdit) {
			final PacketPlayInEvent event = new PacketPlayInEditBookEvent(player, (PacketPlayInBEdit) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayInUseEntity) {
			final PacketPlayInEvent event = new PacketPlayInEntityInteractEvent(player, (PacketPlayInUseEntity) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayInJigsawGenerate) {
			final PacketPlayInEvent event = new PacketPlayInGenerateStructureEvent(player,
					(PacketPlayInJigsawGenerate) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayInKeepAlive) {
			final PacketPlayInEvent event = new PacketPlayInKeepAliveEvent(player, (PacketPlayInKeepAlive) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayInPosition) {
			final PacketPlayInEvent event = new PacketPlayInMovementEvent.PacketPlayInPositionEvent(player,
					(PacketPlayInPosition) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayInPositionLook) {
			final PacketPlayInEvent event = new PacketPlayInMovementEvent.PacketPlayInPositionLookEvent(player,
					(PacketPlayInPositionLook) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayInLook) {
			final PacketPlayInEvent event = new PacketPlayInMovementEvent.PacketPlayInLookEvent(player,
					(PacketPlayInLook) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayInFlying) {
			final PacketPlayInEvent event = new PacketPlayInMovementEvent(player, (PacketPlayInFlying) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayInVehicleMove) {
			final PacketPlayInEvent event = new PacketPlayInVehicleMoveEvent(player, (PacketPlayInVehicleMove) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayInBoatMove) {
			final PacketPlayInEvent event = new PacketPlayInSteerBoatEvent(player, (PacketPlayInBoatMove) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayInPickItem) {
			final PacketPlayInEvent event = new PacketPlayInPickItemEvent(player, (PacketPlayInPickItem) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayInAutoRecipe) {
			final PacketPlayInEvent event = new PacketPlayInAutoRecipeEvent(player, (PacketPlayInAutoRecipe) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayInAbilities) {
			final PacketPlayInEvent event = new PacketPlayInAbilitiesEvent(player, (PacketPlayInAbilities) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayInBlockDig) {
			final PacketPlayInEvent event = new PacketPlayInBlockDigEvent(player, (PacketPlayInBlockDig) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayInEntityAction) {
			final PacketPlayInEvent event = new PacketPlayInActionEvent(player, (PacketPlayInEntityAction) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayInSteerVehicle) {
			final PacketPlayInEvent event = new PacketPlayInSteerVehicleEvent(player, (PacketPlayInSteerVehicle) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayInRecipeSettings) {
			final PacketPlayInEvent event = new PacketPlayInRecipeSettingsEvent(player,
					(PacketPlayInRecipeSettings) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayInRecipeDisplayed) {
			final PacketPlayInEvent event = new PacketPlayInRecipeDisplayedEvent(player,
					(PacketPlayInRecipeDisplayed) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayInItemName) {
			final PacketPlayInEvent event = new PacketPlayInItemNameEvent(player, (PacketPlayInItemName) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayInResourcePackStatus) {
			final PacketPlayInEvent event = new PacketPlayInResourcePackStatusEvent(player,
					(PacketPlayInResourcePackStatus) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayInAdvancements) {
			final PacketPlayInEvent event = new PacketPlayInAdvancementsEvent(player, (PacketPlayInAdvancements) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayInTrSel) {
			final PacketPlayInEvent event = new PacketPlayInTradeSelectEvent(player, (PacketPlayInTrSel) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayInBeacon) {
			final PacketPlayInEvent event = new PacketPlayInChangeBeaconEffectEvent(player, (PacketPlayInBeacon) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayInHeldItemSlot) {
			final PacketPlayInEvent event = new PacketPlayInChangeHeldItemEvent(player, (PacketPlayInHeldItemSlot) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayInSetCommandBlock) {
			final PacketPlayInEvent event = new PacketPlayInUpdateCommandBlockEvent(player,
					(PacketPlayInSetCommandBlock) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayInSetCommandMinecart) {
			final PacketPlayInEvent event = new PacketPlayInUpdateCommandMinecartEvent(player,
					(PacketPlayInSetCommandMinecart) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayInSetCreativeSlot) {
			final PacketPlayInEvent event = new PacketPlayInSetCreativeSlotEvent(player,
					(PacketPlayInSetCreativeSlot) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayInSetJigsaw) {
			final PacketPlayInEvent event = new PacketPlayInUpdateJigsawBlockEvent(player, (PacketPlayInSetJigsaw) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayInStruct) {
			final PacketPlayInEvent event = new PacketPlayInUpdateStructureBlockEvent(player, (PacketPlayInStruct) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayInUpdateSign) {
			final PacketPlayInEvent event = new PacketPlayInUpdateSignEvent(player, (PacketPlayInUpdateSign) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayInArmAnimation) {
			final PacketPlayInEvent event = new PacketPlayInArmAnimationEvent(player, (PacketPlayInArmAnimation) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayInSpectate) {
			final PacketPlayInEvent event = new PacketPlayInSpectateEvent(player, (PacketPlayInSpectate) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayInBlockPlace) {
			final PacketPlayInEvent event = new PacketPlayInBlockPlaceEvent(player, (PacketPlayInBlockPlace) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayInUseItem) {
			final PacketPlayInEvent event = new PacketPlayInUseItemEvent(player, (PacketPlayInUseItem) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketHandshakingInSetProtocol) {
			final PacketHandshakingInHandshakeEvent event = new PacketHandshakingInHandshakeEvent(player,
					(PacketHandshakingInSetProtocol) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketStatusInStart) {
			final PacketStatusInEvent event = new PacketStatusInReqestEvent(player, (PacketStatusInStart) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketStatusInPing) {
			final PacketStatusInEvent event = new PacketStatusInPingEvent(player, (PacketStatusInPing) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketLoginInStart) {
			final PacketLoginInEvent event = new PacketLoginInStartEvent(player, (PacketLoginInStart) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketLoginInEncryptionBegin) {
			final PacketLoginInEvent event = new PacketLoginInEncryptionResponseEvent(player,
					(PacketLoginInEncryptionBegin) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketLoginInCustomPayload) {
			final PacketLoginInEvent event = new PacketLoginInCustomPayloadEvent(player,
					(PacketLoginInCustomPayload) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else
			System.out.println("IN -> unregistert packet: " + msg);
		return canceled;
	}

	private boolean write(Player player, Object msg) {
		boolean canceled = false;
		if (msg instanceof PacketStatusOutServerInfo) {
			final PacketStatusOutEvent event = new PacketStatusOutResponseEvent(player,
					(PacketStatusOutServerInfo) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketStatusOutPong) {
			final PacketStatusOutEvent event = new PacketStatusOutPongEvent(player, (PacketStatusOutPong) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketLoginOutDisconnect) {
			final PacketLoginOutEvent event = new PacketLoginOutDisconnectEvent(player, (PacketLoginOutDisconnect) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketLoginOutEncryptionBegin) {
			final PacketLoginOutEvent event = new PacketLoginOutEncryptionRequestEvent(player,
					(PacketLoginOutEncryptionBegin) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketLoginOutSuccess) {
			final PacketLoginOutEvent event = new PacketLoginOutLoginSuccessEvent(player, (PacketLoginOutSuccess) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketLoginOutSetCompression) {
			final PacketLoginOutEvent event = new PacketLoginOutSetCompressionEvent(player,
					(PacketLoginOutSetCompression) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketLoginOutCustomPayload) {
			final PacketLoginOutEvent event = new PacketLoginOutCustomPayloadEvent(player,
					(PacketLoginOutCustomPayload) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutSpawnEntity) {
			final PacketPlayOutEvent event = new PacketPlayOutSpawnEntityEvent(player, (PacketPlayOutSpawnEntity) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutSpawnEntityExperienceOrb) {
			final PacketPlayOutEvent event = new PacketPlayOutSpawnXpEvent(player,
					(PacketPlayOutSpawnEntityExperienceOrb) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutSpawnEntityLiving) {
			final PacketPlayOutEvent event = new PacketPlayOutSpawnLivingEntityEvent(player,
					(PacketPlayOutSpawnEntityLiving) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutSpawnEntityPainting) {
			final PacketPlayOutEvent event = new PacketPlayOutSpawnPaintingEvent(player,
					(PacketPlayOutSpawnEntityPainting) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutNamedEntitySpawn) {
			final PacketPlayOutEvent event = new PacketPlayOutSpawnPlayerEvent(player,
					(PacketPlayOutNamedEntitySpawn) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutAdvancements) {
			final PacketPlayOutEvent event = new PacketPlayOutAdvancementsEvent(player,
					(PacketPlayOutAdvancements) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutAnimation) {
			final PacketPlayOutEvent event = new PacketPlayOutAnimationEvent(player, (PacketPlayOutAnimation) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutBlockAction) {
			final PacketPlayOutEvent event = new PacketPlayOutBlockActionEvent(player, (PacketPlayOutBlockAction) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutBlockBreakAnimation) {
			final PacketPlayOutEvent event = new PacketPlayOutBlockBreakAnimationEvent(player,
					(PacketPlayOutBlockBreakAnimation) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutBlockChange) {
			final PacketPlayOutEvent event = new PacketPlayOutBlockChangeEvent(player, (PacketPlayOutBlockChange) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutTileEntityData) {
			final PacketPlayOutEvent event = new PacketPlayOutBlockEntityDataEvent(player,
					(PacketPlayOutTileEntityData) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutBoss) {
			final PacketPlayOutEvent event = new PacketPlayOutBossBarEvent(player, (PacketPlayOutBoss) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutCamera) {
			final PacketPlayOutEvent event = new PacketPlayOutCameraEvent(player, (PacketPlayOutCamera) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutMapChunk) {
			final PacketPlayOutEvent event = new PacketPlayOutChunkDataEvent(player, (PacketPlayOutMapChunk) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutCollect) {
			final PacketPlayOutEvent event = new PacketPlayOutCollectItemEvent(player, (PacketPlayOutCollect) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutCombatEvent) {
			PacketPlayOutCombatEvent packet = (PacketPlayOutCombatEvent) msg;
			switch (packet.a) {
			case END_COMBAT:
				PacketPlayOutEvent event = new PacketPlayOutCombatEndEvent(player, (PacketPlayOutCombatEvent) msg);
				Bukkit.getPluginManager().callEvent(event);
				canceled = event.isCanceled();
				break;
			case ENTER_COMBAT:
				event = new PacketPlayOutCombatStartEvent(player, (PacketPlayOutCombatEvent) msg);
				Bukkit.getPluginManager().callEvent(event);
				canceled = event.isCanceled();
				break;
			case ENTITY_DIED:
				event = new PacketPlayOutDeathEvent(player, (PacketPlayOutCombatEvent) msg);
				Bukkit.getPluginManager().callEvent(event);
				canceled = event.isCanceled();
				break;
			}

		} else if (msg instanceof PacketPlayOutCustomPayload) {
			final PacketPlayOutEvent event = new PacketPlayOutCostumPayloadEvent(player,
					(PacketPlayOutCustomPayload) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutAutoRecipe) {
			final PacketPlayOutEvent event = new PacketPlayOutCraftRecipeResponseEvent(player,
					(PacketPlayOutAutoRecipe) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutServerDifficulty) {
			final PacketPlayOutEvent event = new PacketPlayOutDifficultyEvent(player,
					(PacketPlayOutServerDifficulty) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutKickDisconnect) {
			final PacketPlayOutEvent event = new PacketPlayOutDisconnectEvent(player,
					(PacketPlayOutKickDisconnect) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutAttachEntity) {
			final PacketPlayOutEvent event = new PacketPlayOutEntityAttachEvent(player,
					(PacketPlayOutAttachEntity) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutEntityDestroy) {
			final PacketPlayOutEvent event = new PacketPlayOutEntityDestroyEvent(player,
					(PacketPlayOutEntityDestroy) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutEntityEffect) {
			final PacketPlayOutEvent event = new PacketPlayOutEntityEffectEvent(player,
					(PacketPlayOutEntityEffect) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutRemoveEntityEffect) {
			final PacketPlayOutEvent event = new PacketPlayOutEntityEffectRemoveEvent(player,
					(PacketPlayOutRemoveEntityEffect) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutEntityEquipment) {
			final PacketPlayOutEvent event = new PacketPlayOutEntityEquipmentEvent(player,
					(PacketPlayOutEntityEquipment) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutEntityHeadRotation) {
			final PacketPlayOutEvent event = new PacketPlayOutEntityHeadLookEvent(player,
					(PacketPlayOutEntityHeadRotation) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutEntityMetadata) {
			final PacketPlayOutEvent event = new PacketPlayOutEntityMetadataEvent(player,
					(PacketPlayOutEntityMetadata) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutRelEntityMove) {
			final PacketPlayOutEvent event = new PacketPlayOutEntityMoveEvent(player, (PacketPlayOutRelEntityMove) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutRelEntityMoveLook) {
			final PacketPlayOutEvent event = new PacketPlayOutEntityMoveAndRotationEvent(player,
					(PacketPlayOutRelEntityMoveLook) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutPosition) {
			final PacketPlayOutEvent event = new PacketPlayOutEntityPositionEvent(player, (PacketPlayOutPosition) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutUpdateAttributes) {
			final PacketPlayOutEvent event = new PacketPlayOutEntityAttributesEvent(player,
					(PacketPlayOutUpdateAttributes) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutEntityLook) {
			final PacketPlayOutEvent event = new PacketPlayOutEntityRotationEvent(player,
					(PacketPlayOutEntityLook) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutEntitySound) {
			final PacketPlayOutEvent event = new PacketPlayOutEntitySoundEvent(player, (PacketPlayOutEntitySound) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutEntityStatus) {
			final PacketPlayOutEvent event = new PacketPlayOutEntityStatusEvent(player,
					(PacketPlayOutEntityStatus) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutEntityTeleport) {
			final PacketPlayOutEvent event = new PacketPlayOutEntityTeleportEvent(player,
					(PacketPlayOutEntityTeleport) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutEntityVelocity) {
			final PacketPlayOutEvent event = new PacketPlayOutEntityVelocityEvent(player,
					(PacketPlayOutEntityVelocity) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutExplosion) {
			final PacketPlayOutEvent event = new PacketPlayOutExplosionEvent(player, (PacketPlayOutExplosion) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutGameStateChange) {
			final PacketPlayOutEvent event = new PacketPlayOutGameStateEvent(player,
					(PacketPlayOutGameStateChange) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutHeldItemSlot) {
			final PacketPlayOutEvent event = new PacketPlayOutHeldItemSlotEvent(player,
					(PacketPlayOutHeldItemSlot) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutCloseWindow) {
			final PacketPlayOutEvent event = new PacketPlayOutInventoryCloseEvent(player,
					(PacketPlayOutCloseWindow) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutTransaction) {
			final PacketPlayOutEvent event = new PacketPlayOutInventoryConfirmEvent(player,
					(PacketPlayOutTransaction) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutWindowItems) {
			final PacketPlayOutEvent event = new PacketPlayOutInventoryItemsEvent(player,
					(PacketPlayOutWindowItems) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutOpenWindow) {
			final PacketPlayOutEvent event = new PacketPlayOutInventoryOpenEvent(player, (PacketPlayOutOpenWindow) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutWindowData) {
			final PacketPlayOutEvent event = new PacketPlayOutInventoryPropertyEvent(player,
					(PacketPlayOutWindowData) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutLogin) {
			final PacketPlayOutEvent event = new PacketPlayOutJoinGameEvent(player, (PacketPlayOutLogin) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutKeepAlive) {
			final PacketPlayOutEvent event = new PacketPlayOutKeepAliveEvent(player, (PacketPlayOutKeepAlive) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutCommands) {
			final PacketPlayOutEvent event = new PacketPlayOutListCommandsEvent(player, (PacketPlayOutCommands) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutCommands) {
			final PacketPlayOutEvent event = new PacketPlayOutListCommandsEvent(player, (PacketPlayOutCommands) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutMap) {
			final PacketPlayOutEvent event = new PacketPlayOutMapDataEvent(player, (PacketPlayOutMap) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutChat) {
			final PacketPlayOutEvent event = new PacketPlayOutMessageEvent(player, (PacketPlayOutChat) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutMultiBlockChange) {
			final PacketPlayOutEvent event = new PacketPlayOutMultiBlockChangeEvent(player,
					(PacketPlayOutMultiBlockChange) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutNBTQuery) {
			final PacketPlayOutEvent event = new PacketPlayOutNBTQueryResponseEvent(player,
					(PacketPlayOutNBTQuery) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutOpenBook) {
			final PacketPlayOutEvent event = new PacketPlayOutOpenBookEvent(player, (PacketPlayOutOpenBook) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutOpenWindowHorse) {
			final PacketPlayOutEvent event = new PacketPlayOutOpenHorseWindowEvent(player,
					(PacketPlayOutOpenWindowHorse) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutOpenSignEditor) {
			final PacketPlayOutEvent event = new PacketPlayOutOpenSignEditorEvent(player,
					(PacketPlayOutOpenSignEditor) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutWorldParticles) {
			final PacketPlayOutEvent event = new PacketPlayOutParticleEvent(player, (PacketPlayOutWorldParticles) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutAbilities) {
			final PacketPlayOutEvent event = new PacketPlayOutPlayerAbilitiesEvent(player,
					(PacketPlayOutAbilities) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutBlockBreak) {
			final PacketPlayOutEvent event = new PacketPlayOutPlayerDiggingEvent(player, (PacketPlayOutBlockBreak) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutPlayerInfo) {
			final PacketPlayOutEvent event = new PacketPlayOutPlayerInfoEvent(player, (PacketPlayOutPlayerInfo) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutRecipes) {
			final PacketPlayOutEvent event = new PacketPlayOutRecipesUnlockEvent(player, (PacketPlayOutRecipes) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutRecipeUpdate) {
			final PacketPlayOutEvent event = new PacketPlayOutRecipeUpdateEvent(player,
					(PacketPlayOutRecipeUpdate) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutResourcePackSend) {
			final PacketPlayOutEvent event = new PacketPlayOutResourcePackEvent(player,
					(PacketPlayOutResourcePackSend) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutRespawn) {
			final PacketPlayOutEvent event = new PacketPlayOutRespawnEvent(player, (PacketPlayOutRespawn) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutScoreboardDisplayObjective) {
			final PacketPlayOutEvent event = new PacketPlayOutScoreboardDisplayEvent(player,
					(PacketPlayOutScoreboardDisplayObjective) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutScoreboardObjective) {
			final PacketPlayOutEvent event = new PacketPlayOutScoreboardObjectiveEvent(player,
					(PacketPlayOutScoreboardObjective) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutScoreboardTeam) {
			final PacketPlayOutEvent event = new PacketPlayOutScoreboardTeamEvent(player,
					(PacketPlayOutScoreboardTeam) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutScoreboardScore) {
			final PacketPlayOutEvent event = new PacketPlayOutScoreboardUpdateScoreEvent(player,
					(PacketPlayOutScoreboardScore) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutSelectAdvancementTab) {
			final PacketPlayOutEvent event = new PacketPlayOutSelectAdvancementTabEvent(player,
					(PacketPlayOutSelectAdvancementTab) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutSetCooldown) {
			final PacketPlayOutEvent event = new PacketPlayOutSetCooldownEvent(player, (PacketPlayOutSetCooldown) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutMount) {
			final PacketPlayOutEvent event = new PacketPlayOutSetPassengersEvent(player, (PacketPlayOutMount) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutSetSlot) {
			final PacketPlayOutEvent event = new PacketPlayOutSetSlotEvent(player, (PacketPlayOutSetSlot) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutExperience) {
			final PacketPlayOutEvent event = new PacketPlayOutSetXpEvent(player, (PacketPlayOutExperience) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutNamedSoundEffect) {
			final PacketPlayOutEvent event = new PacketPlayOutSoundEvent(player, (PacketPlayOutNamedSoundEffect) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutSpawnPosition) {
			final PacketPlayOutEvent event = new PacketPlayOutSpawnPositionEvent(player,
					(PacketPlayOutSpawnPosition) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutStatistic) {
			final PacketPlayOutEvent event = new PacketPlayOutStatisticsEvent(player, (PacketPlayOutStatistic) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutStopSound) {
			final PacketPlayOutEvent event = new PacketPlayOutStopSoundEvent(player, (PacketPlayOutStopSound) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutTabComplete) {
			final PacketPlayOutEvent event = new PacketPlayOutTabCompleteEvent(player, (PacketPlayOutTabComplete) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutPlayerListHeaderFooter) {
			final PacketPlayOutEvent event = new PacketPlayOutTabListHeaderAndFooterEvent(player,
					(PacketPlayOutPlayerListHeaderFooter) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutTags) {
			final PacketPlayOutEvent event = new PacketPlayOutTagsEvent(player, (PacketPlayOutTags) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutUpdateTime) {
			final PacketPlayOutEvent event = new PacketPlayOutTimeUpdateEvent(player, (PacketPlayOutUpdateTime) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutTitle) {
			final PacketPlayOutEvent event = new PacketPlayOutTitleEvent(player, (PacketPlayOutTitle) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutOpenWindowMerchant) {
			final PacketPlayOutEvent event = new PacketPlayOutTradeListEvent(player,
					(PacketPlayOutOpenWindowMerchant) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutUnloadChunk) {
			final PacketPlayOutEvent event = new PacketPlayOutUnloadChunkEvent(player, (PacketPlayOutUnloadChunk) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutUpdateHealth) {
			final PacketPlayOutEvent event = new PacketPlayOutUpdateHealthEvent(player,
					(PacketPlayOutUpdateHealth) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutLightUpdate) {
			final PacketPlayOutEvent event = new PacketPlayOutUpdateLightEvent(player, (PacketPlayOutLightUpdate) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutViewDistance) {
			final PacketPlayOutEvent event = new PacketPlayOutUpdateViewDistanceEvent(player,
					(PacketPlayOutViewDistance) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutViewCentre) {
			final PacketPlayOutEvent event = new PacketPlayOutUpdateViewPositionEvent(player,
					(PacketPlayOutViewCentre) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutVehicleMove) {
			final PacketPlayOutEvent event = new PacketPlayOutVehicleMoveEvent(player, (PacketPlayOutVehicleMove) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		} else if (msg instanceof PacketPlayOutWorldBorder) {
			final PacketPlayOutEvent event = new PacketPlayOutWorldBorderInitializeEvent(player,
					(PacketPlayOutWorldBorder) msg);
			Bukkit.getPluginManager().callEvent(event);
			canceled = event.isCanceled();
		}
		return canceled;
	}
}
