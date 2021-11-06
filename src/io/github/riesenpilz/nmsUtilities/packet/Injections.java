package io.github.riesenpilz.nmsUtilities.packet;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.google.common.collect.ImmutableMap;

import io.github.riesenpilz.nmsUtilities.entity.livingEntity.player.Player;
import io.github.riesenpilz.nmsUtilities.packet.playIn.PacketPlayInAbilitiesEvent;
import io.github.riesenpilz.nmsUtilities.packet.playIn.PacketPlayInActionEvent;
import io.github.riesenpilz.nmsUtilities.packet.playIn.PacketPlayInAdvancementsEvent;
import io.github.riesenpilz.nmsUtilities.packet.playIn.PacketPlayInArmAnimationEvent;
import io.github.riesenpilz.nmsUtilities.packet.playIn.PacketPlayInAutoRecipeEvent;
import io.github.riesenpilz.nmsUtilities.packet.playIn.PacketPlayInBlockDigEvent;
import io.github.riesenpilz.nmsUtilities.packet.playIn.PacketPlayInBlockNBTQueryEvent;
import io.github.riesenpilz.nmsUtilities.packet.playIn.PacketPlayInBlockPlaceEvent;
import io.github.riesenpilz.nmsUtilities.packet.playIn.PacketPlayInChangeBeaconEffectEvent;
import io.github.riesenpilz.nmsUtilities.packet.playIn.PacketPlayInChangeHeldItemEvent;
import io.github.riesenpilz.nmsUtilities.packet.playIn.PacketPlayInChatEvent;
import io.github.riesenpilz.nmsUtilities.packet.playIn.PacketPlayInClickInventoryButtonEvent;
import io.github.riesenpilz.nmsUtilities.packet.playIn.PacketPlayInClientStatusEvent;
import io.github.riesenpilz.nmsUtilities.packet.playIn.PacketPlayInCloseInventoryEvent;
import io.github.riesenpilz.nmsUtilities.packet.playIn.PacketPlayInCustomPayloadEvent;
import io.github.riesenpilz.nmsUtilities.packet.playIn.PacketPlayInDifficultyChangeEvent;
import io.github.riesenpilz.nmsUtilities.packet.playIn.PacketPlayInEditBookEvent;
import io.github.riesenpilz.nmsUtilities.packet.playIn.PacketPlayInEntityInteractEvent;
import io.github.riesenpilz.nmsUtilities.packet.playIn.PacketPlayInEvent;
import io.github.riesenpilz.nmsUtilities.packet.playIn.PacketPlayInGenerateStructureEvent;
import io.github.riesenpilz.nmsUtilities.packet.playIn.PacketPlayInInventoryClickEvent;
import io.github.riesenpilz.nmsUtilities.packet.playIn.PacketPlayInInventoryConfirmEvent;
import io.github.riesenpilz.nmsUtilities.packet.playIn.PacketPlayInItemNameEvent;
import io.github.riesenpilz.nmsUtilities.packet.playIn.PacketPlayInKeepAliveEvent;
import io.github.riesenpilz.nmsUtilities.packet.playIn.PacketPlayInMovementEvent;
import io.github.riesenpilz.nmsUtilities.packet.playIn.PacketPlayInPickItemEvent;
import io.github.riesenpilz.nmsUtilities.packet.playIn.PacketPlayInRecipeDisplayedEvent;
import io.github.riesenpilz.nmsUtilities.packet.playIn.PacketPlayInRecipeSettingsEvent;
import io.github.riesenpilz.nmsUtilities.packet.playIn.PacketPlayInResourcePackStatusEvent;
import io.github.riesenpilz.nmsUtilities.packet.playIn.PacketPlayInSetCreativeSlotEvent;
import io.github.riesenpilz.nmsUtilities.packet.playIn.PacketPlayInSettingsEvent;
import io.github.riesenpilz.nmsUtilities.packet.playIn.PacketPlayInSpectateEvent;
import io.github.riesenpilz.nmsUtilities.packet.playIn.PacketPlayInSteerBoatEvent;
import io.github.riesenpilz.nmsUtilities.packet.playIn.PacketPlayInSteerVehicleEvent;
import io.github.riesenpilz.nmsUtilities.packet.playIn.PacketPlayInTabCompleteEvent;
import io.github.riesenpilz.nmsUtilities.packet.playIn.PacketPlayInTeleportAcceptEvent;
import io.github.riesenpilz.nmsUtilities.packet.playIn.PacketPlayInTradeSelectEvent;
import io.github.riesenpilz.nmsUtilities.packet.playIn.PacketPlayInUpdateCommandBlockEvent;
import io.github.riesenpilz.nmsUtilities.packet.playIn.PacketPlayInUpdateCommandMinecartEvent;
import io.github.riesenpilz.nmsUtilities.packet.playIn.PacketPlayInUpdateJigsawBlockEvent;
import io.github.riesenpilz.nmsUtilities.packet.playIn.PacketPlayInUpdateSignEvent;
import io.github.riesenpilz.nmsUtilities.packet.playIn.PacketPlayInUpdateStructureBlockEvent;
import io.github.riesenpilz.nmsUtilities.packet.playIn.PacketPlayInUseItemEvent;
import io.github.riesenpilz.nmsUtilities.packet.playIn.PacketPlayInVehicleMoveEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutAdvancementsEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutAnimationEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutBlockActionEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutBlockBreakAnimationEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutBlockChangeEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutBlockEntityDataEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutBossBarEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutCameraEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutChunkDataEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutCollectItemEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutCombatEndEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutCombatStartEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutCostumPayloadEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutCraftRecipeResponseEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutDeathEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutDifficultyEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutDisconnectEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutEntityAttachEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutEntityAttributesEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutEntityDestroyEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutEntityEffectEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutEntityEffectRemoveEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutEntityEquipmentEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutEntityHeadLookEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutEntityMetadataEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutEntityMoveAndRotationEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutEntityMoveEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutEntityRotationEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutEntitySoundEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutEntityStatusEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutEntityTeleportEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutEntityVelocityEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutExplosionEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutGameStateEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutHeldItemSlotEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutInventoryCloseEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutInventoryConfirmEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutInventoryItemsEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutInventoryOpenEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutInventoryPropertyEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutJoinGameEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutKeepAliveEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutListCommandsEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutMapDataEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutMessageEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutMultiBlockChangeEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutNBTQueryResponseEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutOpenBookEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutOpenHorseWindowEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutOpenSignEditorEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutParticleEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutPayerPositionEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutPlayerAbilitiesEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutPlayerDiggingEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutPlayerInfoEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutRecipeUpdateEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutRecipesUnlockEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutResourcePackEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutRespawnEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutScoreboardDisplayEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutScoreboardObjectiveEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutScoreboardTeamEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutScoreboardUpdateScoreEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutSelectAdvancementTabEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutSetCooldownEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutSetPassengersEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutSetSlotEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutSetXpEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutSoundEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutSpawnEntityEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutSpawnLivingEntityEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutSpawnPaintingEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutSpawnPlayerEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutSpawnPositionEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutSpawnXpEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutStatisticsEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutStopSoundEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutTabCompleteEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutTabListHeaderAndFooterEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutTagsEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutTimeUpdateEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutTitleEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutTradeListEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutUnloadChunkEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutUpdateHealthEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutUpdateLightEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutUpdateViewDistanceEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutUpdateViewPositionEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutVehicleMoveEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutWorldBorderInitializeEvent;
import io.github.riesenpilz.nmsUtilities.reflections.Field;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFuture;
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

	public static void inject() {
		@SuppressWarnings("deprecation")
		final @Nullable ServerConnection serverConnection = MinecraftServer.getServer().getServerConnection();
		@SuppressWarnings({ "unchecked" })
		List<NetworkManager> connectedChannels = Field.get(serverConnection, "connectedChannels", List.class);
		@SuppressWarnings("unchecked")
		List<ChannelFuture> listeningChannels = Field.get(serverConnection, "listeningChannels", List.class);
		for (NetworkManager manager : connectedChannels) {
			manager.channel.pipeline().addBefore("packet_handler", manager.spoofedUUID.toString(),
					new ChannelDuplexHandler() {
						@Override
						public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//							if (!Injections.this.readIn(manager.spoofedUUID, ctx, msg, this))
								super.channelRead(ctx, msg);
						}

						@Override
						public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise)
								throws Exception {
//							if (!Injections.this.readOut(manager.spoofedUUID, msg))
								super.write(ctx, msg, promise);
						}
					});
		}
	}

	private void injectPlayer(Player player) {
		final ChannelDuplexHandler channelDuplexHandler = new ChannelDuplexHandler() {
			@Override
			public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
				if (!Injections.this.readIn(player, ctx, msg, this))
					super.channelRead(ctx, msg);
			}

			@Override
			public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
				if (!Injections.this.readOut(player, msg))
					super.write(ctx, msg, promise);
			}
		};
		player.getChannelPipeline().addBefore("packet_handler", player.getName(), channelDuplexHandler);
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		final Player player = Player.getPlayerOf(e.getPlayer());
		injectPlayer(player);
	}

	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e) {
		removePlayer(Player.getPlayerOf(e.getPlayer()));
	}

	private void removePlayer(Player player) {
		final Channel channel = player.getPlayerConnection().networkManager.channel;
		channel.eventLoop().submit(() -> channel.pipeline().remove(player.getName()));
	}

	private boolean readIn(Player player1, ChannelHandlerContext ctx, Object msg, ChannelDuplexHandler handler)
			throws Exception {
		Class<? extends PacketInEvent> clazz = mapIn.get(msg.getClass());
		if (clazz == null) {
			System.err.println("[IN] Not registered packet: " + msg.getClass().getSimpleName());
			return false;
		}
		try {
			PacketInEvent event = clazz.getConstructor(org.bukkit.entity.Player.class, msg.getClass())
					.newInstance(player1.getBukkit(), msg);
			Bukkit.getPluginManager().callEvent(event);
			return event.isCanceled();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			System.err.println("[ERROR] packet in: " + msg.getClass().getSimpleName());
			e.printStackTrace();
		}
		return false;

	}

	private boolean readOut(Player player, Object msg) {
		Class<? extends PacketOutEvent> clazz = mapOut.get(msg.getClass());
		if (clazz == null) {
			if (msg instanceof PacketPlayOutCombatEvent) {
				PacketPlayOutCombatEvent packet = (PacketPlayOutCombatEvent) msg;
				switch (packet.a) {
				case END_COMBAT:
					clazz = PacketPlayOutCombatEndEvent.class;
					break;
				case ENTER_COMBAT:
					clazz = PacketPlayOutCombatStartEvent.class;
					break;
				case ENTITY_DIED:
					clazz = PacketPlayOutDeathEvent.class;
					break;
				}
			} else {
				System.err.println("[OUT] Not registered packet: " + msg.getClass().getSimpleName());
				return false;
			}
		}
		try {
			PacketOutEvent event = clazz.getConstructor(org.bukkit.entity.Player.class, msg.getClass())
					.newInstance(player.getBukkit(), msg);
			Bukkit.getPluginManager().callEvent(event);
			return event.isCanceled();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			System.err.println("[ERROR] packet out: " + msg.getClass().getSimpleName());
			e.printStackTrace();
		}
		return false;

	}

	private static final ImmutableMap<Class<? extends Packet<?>>, Class<? extends PacketPlayInEvent>> mapIn = new ImmutableMap.Builder<Class<? extends Packet<?>>, Class<? extends PacketPlayInEvent>>()
			.put(PacketPlayInTeleportAccept.class, PacketPlayInTeleportAcceptEvent.class)
			.put(PacketPlayInDifficultyChange.class, PacketPlayInDifficultyChangeEvent.class)
			.put(PacketPlayInChat.class, PacketPlayInChatEvent.class)
			.put(PacketPlayInClientCommand.class, PacketPlayInClientStatusEvent.class)
			.put(PacketPlayInSettings.class, PacketPlayInSettingsEvent.class)
			.put(PacketPlayInTabComplete.class, PacketPlayInTabCompleteEvent.class)
			.put(PacketPlayInTransaction.class, PacketPlayInInventoryConfirmEvent.class)
			.put(PacketPlayInEnchantItem.class, PacketPlayInClickInventoryButtonEvent.class)
			.put(PacketPlayInWindowClick.class, PacketPlayInInventoryClickEvent.class)
			.put(PacketPlayInCloseWindow.class, PacketPlayInCloseInventoryEvent.class)
			.put(PacketPlayInCustomPayload.class, PacketPlayInCustomPayloadEvent.class)
			.put(PacketPlayInBEdit.class, PacketPlayInEditBookEvent.class)
			.put(PacketPlayInUseEntity.class, PacketPlayInEntityInteractEvent.class)
			.put(PacketPlayInJigsawGenerate.class, PacketPlayInGenerateStructureEvent.class)
			.put(PacketPlayInKeepAlive.class, PacketPlayInKeepAliveEvent.class)
			.put(PacketPlayInPosition.class, PacketPlayInMovementEvent.PacketPlayInPositionEvent.class)
			.put(PacketPlayInPositionLook.class, PacketPlayInMovementEvent.PacketPlayInPositionLookEvent.class)
			.put(PacketPlayInLook.class, PacketPlayInMovementEvent.PacketPlayInLookEvent.class)
			.put(PacketPlayInFlying.class, PacketPlayInMovementEvent.class)
			.put(PacketPlayInVehicleMove.class, PacketPlayInVehicleMoveEvent.class)
			.put(PacketPlayInBoatMove.class, PacketPlayInSteerBoatEvent.class)
			.put(PacketPlayInPickItem.class, PacketPlayInPickItemEvent.class)
			.put(PacketPlayInAutoRecipe.class, PacketPlayInAutoRecipeEvent.class)
			.put(PacketPlayInAbilities.class, PacketPlayInAbilitiesEvent.class)
			.put(PacketPlayInBlockDig.class, PacketPlayInBlockDigEvent.class)
			.put(PacketPlayInEntityAction.class, PacketPlayInActionEvent.class)
			.put(PacketPlayInSteerVehicle.class, PacketPlayInSteerVehicleEvent.class)
			.put(PacketPlayInRecipeSettings.class, PacketPlayInRecipeSettingsEvent.class)
			.put(PacketPlayInRecipeDisplayed.class, PacketPlayInRecipeDisplayedEvent.class)
			.put(PacketPlayInItemName.class, PacketPlayInItemNameEvent.class)
			.put(PacketPlayInResourcePackStatus.class, PacketPlayInResourcePackStatusEvent.class)
			.put(PacketPlayInAdvancements.class, PacketPlayInAdvancementsEvent.class)
			.put(PacketPlayInTrSel.class, PacketPlayInTradeSelectEvent.class)
			.put(PacketPlayInBeacon.class, PacketPlayInChangeBeaconEffectEvent.class)
			.put(PacketPlayInHeldItemSlot.class, PacketPlayInChangeHeldItemEvent.class)
			.put(PacketPlayInSetCommandBlock.class, PacketPlayInUpdateCommandBlockEvent.class)
			.put(PacketPlayInSetCommandMinecart.class, PacketPlayInUpdateCommandMinecartEvent.class)
			.put(PacketPlayInSetCreativeSlot.class, PacketPlayInSetCreativeSlotEvent.class)
			.put(PacketPlayInSetJigsaw.class, PacketPlayInUpdateJigsawBlockEvent.class)
			.put(PacketPlayInStruct.class, PacketPlayInUpdateStructureBlockEvent.class)
			.put(PacketPlayInUpdateSign.class, PacketPlayInUpdateSignEvent.class)
			.put(PacketPlayInArmAnimation.class, PacketPlayInArmAnimationEvent.class)
			.put(PacketPlayInSpectate.class, PacketPlayInSpectateEvent.class)
			.put(PacketPlayInBlockPlace.class, PacketPlayInBlockPlaceEvent.class)
			.put(PacketPlayInUseItem.class, PacketPlayInUseItemEvent.class)
			.put(PacketPlayInTileNBTQuery.class, PacketPlayInBlockNBTQueryEvent.class).build();
	private static final ImmutableMap<Class<? extends Packet<?>>, Class<? extends PacketPlayOutEvent>> mapOut = new ImmutableMap.Builder<Class<? extends Packet<?>>, Class<? extends PacketPlayOutEvent>>()
			.put(PacketPlayOutSpawnEntity.class, PacketPlayOutSpawnEntityEvent.class)
			.put(PacketPlayOutAnimation.class, PacketPlayOutAnimationEvent.class)
			.put(PacketPlayOutBlockAction.class, PacketPlayOutBlockActionEvent.class)
			.put(PacketPlayOutBlockBreakAnimation.class, PacketPlayOutBlockBreakAnimationEvent.class)
			.put(PacketPlayOutBlockChange.class, PacketPlayOutBlockChangeEvent.class)
			.put(PacketPlayOutTileEntityData.class, PacketPlayOutBlockEntityDataEvent.class)
			.put(PacketPlayOutBoss.class, PacketPlayOutBossBarEvent.class)
			.put(PacketPlayOutCamera.class, PacketPlayOutCameraEvent.class)
			.put(PacketPlayOutMapChunk.class, PacketPlayOutChunkDataEvent.class)
			.put(PacketPlayOutCollect.class, PacketPlayOutCollectItemEvent.class)
			.put(PacketPlayOutCustomPayload.class, PacketPlayOutCostumPayloadEvent.class)
			.put(PacketPlayOutAutoRecipe.class, PacketPlayOutCraftRecipeResponseEvent.class)
			.put(PacketPlayOutServerDifficulty.class, PacketPlayOutDifficultyEvent.class)
			.put(PacketPlayOutKickDisconnect.class, PacketPlayOutDisconnectEvent.class)
			.put(PacketPlayOutAttachEntity.class, PacketPlayOutEntityAttachEvent.class)
			.put(PacketPlayOutEntityDestroy.class, PacketPlayOutEntityDestroyEvent.class)
			.put(PacketPlayOutEntityEffect.class, PacketPlayOutEntityEffectEvent.class)
			.put(PacketPlayOutRemoveEntityEffect.class, PacketPlayOutEntityEffectRemoveEvent.class)
			.put(PacketPlayOutEntityEquipment.class, PacketPlayOutEntityEquipmentEvent.class)
			.put(PacketPlayOutEntityHeadRotation.class, PacketPlayOutEntityHeadLookEvent.class)
			.put(PacketPlayOutEntityMetadata.class, PacketPlayOutEntityMetadataEvent.class)
			.put(PacketPlayOutRelEntityMove.class, PacketPlayOutEntityMoveEvent.class)
			.put(PacketPlayOutRelEntityMoveLook.class, PacketPlayOutEntityMoveAndRotationEvent.class)
			.put(PacketPlayOutPosition.class, PacketPlayOutPayerPositionEvent.class)
			.put(PacketPlayOutUpdateAttributes.class, PacketPlayOutEntityAttributesEvent.class)
			.put(PacketPlayOutEntityLook.class, PacketPlayOutEntityRotationEvent.class)
			.put(PacketPlayOutEntitySound.class, PacketPlayOutEntitySoundEvent.class)
			.put(PacketPlayOutEntityStatus.class, PacketPlayOutEntityStatusEvent.class)
			.put(PacketPlayOutEntityTeleport.class, PacketPlayOutEntityTeleportEvent.class)
			.put(PacketPlayOutEntityVelocity.class, PacketPlayOutEntityVelocityEvent.class)
			.put(PacketPlayOutExplosion.class, PacketPlayOutExplosionEvent.class)
			.put(PacketPlayOutGameStateChange.class, PacketPlayOutGameStateEvent.class)
			.put(PacketPlayOutHeldItemSlot.class, PacketPlayOutHeldItemSlotEvent.class)
			.put(PacketPlayOutCloseWindow.class, PacketPlayOutInventoryCloseEvent.class)
			.put(PacketPlayOutTransaction.class, PacketPlayOutInventoryConfirmEvent.class)
			.put(PacketPlayOutWindowItems.class, PacketPlayOutInventoryItemsEvent.class)
			.put(PacketPlayOutOpenWindow.class, PacketPlayOutInventoryOpenEvent.class)
			.put(PacketPlayOutWindowData.class, PacketPlayOutInventoryPropertyEvent.class)
			.put(PacketPlayOutLogin.class, PacketPlayOutJoinGameEvent.class)
			.put(PacketPlayOutKeepAlive.class, PacketPlayOutKeepAliveEvent.class)
			.put(PacketPlayOutCommands.class, PacketPlayOutListCommandsEvent.class)
			.put(PacketPlayOutMap.class, PacketPlayOutMapDataEvent.class)
			.put(PacketPlayOutChat.class, PacketPlayOutMessageEvent.class)
			.put(PacketPlayOutMultiBlockChange.class, PacketPlayOutMultiBlockChangeEvent.class)
			.put(PacketPlayOutNBTQuery.class, PacketPlayOutNBTQueryResponseEvent.class)
			.put(PacketPlayOutOpenBook.class, PacketPlayOutOpenBookEvent.class)
			.put(PacketPlayOutOpenWindowHorse.class, PacketPlayOutOpenHorseWindowEvent.class)
			.put(PacketPlayOutOpenSignEditor.class, PacketPlayOutOpenSignEditorEvent.class)
			.put(PacketPlayOutWorldParticles.class, PacketPlayOutParticleEvent.class)
			.put(PacketPlayOutAbilities.class, PacketPlayOutPlayerAbilitiesEvent.class)
			.put(PacketPlayOutBlockBreak.class, PacketPlayOutPlayerDiggingEvent.class)
			.put(PacketPlayOutPlayerInfo.class, PacketPlayOutPlayerInfoEvent.class)
			.put(PacketPlayOutRecipes.class, PacketPlayOutRecipesUnlockEvent.class)
			.put(PacketPlayOutRecipeUpdate.class, PacketPlayOutRecipeUpdateEvent.class)
			.put(PacketPlayOutResourcePackSend.class, PacketPlayOutResourcePackEvent.class)
			.put(PacketPlayOutRespawn.class, PacketPlayOutRespawnEvent.class)
			.put(PacketPlayOutScoreboardDisplayObjective.class, PacketPlayOutScoreboardDisplayEvent.class)
			.put(PacketPlayOutScoreboardObjective.class, PacketPlayOutScoreboardObjectiveEvent.class)
			.put(PacketPlayOutScoreboardTeam.class, PacketPlayOutScoreboardTeamEvent.class)
			.put(PacketPlayOutScoreboardScore.class, PacketPlayOutScoreboardUpdateScoreEvent.class)
			.put(PacketPlayOutSelectAdvancementTab.class, PacketPlayOutSelectAdvancementTabEvent.class)
			.put(PacketPlayOutSetCooldown.class, PacketPlayOutSetCooldownEvent.class)
			.put(PacketPlayOutMount.class, PacketPlayOutSetPassengersEvent.class)
			.put(PacketPlayOutSetSlot.class, PacketPlayOutSetSlotEvent.class)
			.put(PacketPlayOutExperience.class, PacketPlayOutSetXpEvent.class)
			.put(PacketPlayOutNamedSoundEffect.class, PacketPlayOutSoundEvent.class)
			.put(PacketPlayOutSpawnPosition.class, PacketPlayOutSpawnPositionEvent.class)
			.put(PacketPlayOutStatistic.class, PacketPlayOutStatisticsEvent.class)
			.put(PacketPlayOutStopSound.class, PacketPlayOutStopSoundEvent.class)
			.put(PacketPlayOutTabComplete.class, PacketPlayOutTabCompleteEvent.class)
			.put(PacketPlayOutPlayerListHeaderFooter.class, PacketPlayOutTabListHeaderAndFooterEvent.class)
			.put(PacketPlayOutTags.class, PacketPlayOutTagsEvent.class)
			.put(PacketPlayOutUpdateTime.class, PacketPlayOutTimeUpdateEvent.class)
			.put(PacketPlayOutTitle.class, PacketPlayOutTitleEvent.class)
			.put(PacketPlayOutOpenWindowMerchant.class, PacketPlayOutTradeListEvent.class)
			.put(PacketPlayOutUnloadChunk.class, PacketPlayOutUnloadChunkEvent.class)
			.put(PacketPlayOutUpdateHealth.class, PacketPlayOutUpdateHealthEvent.class)
			.put(PacketPlayOutLightUpdate.class, PacketPlayOutUpdateLightEvent.class)
			.put(PacketPlayOutViewDistance.class, PacketPlayOutUpdateViewDistanceEvent.class)
			.put(PacketPlayOutViewCentre.class, PacketPlayOutUpdateViewPositionEvent.class)
			.put(PacketPlayOutVehicleMove.class, PacketPlayOutVehicleMoveEvent.class)
			.put(PacketPlayOutWorldBorder.class, PacketPlayOutWorldBorderInitializeEvent.class)
			.put(PacketPlayOutAdvancements.class, PacketPlayOutAdvancementsEvent.class)
			.put(PacketPlayOutNamedEntitySpawn.class, PacketPlayOutSpawnPlayerEvent.class)
			.put(PacketPlayOutSpawnEntityPainting.class, PacketPlayOutSpawnPaintingEvent.class)
			.put(PacketPlayOutSpawnEntityLiving.class, PacketPlayOutSpawnLivingEntityEvent.class)
			.put(PacketPlayOutSpawnEntityExperienceOrb.class, PacketPlayOutSpawnXpEvent.class).build();

}
