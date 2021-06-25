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
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutSpawnEntityEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutSpawnLivingEntityEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutSpawnPaintingEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutSpawnPlayerEvent;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutSpawnXpEvent;
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
import net.minecraft.server.v1_16_R3.PacketHandshakingInSetProtocol;
import net.minecraft.server.v1_16_R3.PacketLoginInCustomPayload;
import net.minecraft.server.v1_16_R3.PacketLoginInEncryptionBegin;
import net.minecraft.server.v1_16_R3.PacketLoginInStart;
import net.minecraft.server.v1_16_R3.PacketLoginOutCustomPayload;
import net.minecraft.server.v1_16_R3.PacketLoginOutDisconnect;
import net.minecraft.server.v1_16_R3.PacketLoginOutEncryptionBegin;
import net.minecraft.server.v1_16_R3.PacketLoginOutSetCompression;
import net.minecraft.server.v1_16_R3.PacketLoginOutSuccess;
import net.minecraft.server.v1_16_R3.PacketPlayInAbilities;
import net.minecraft.server.v1_16_R3.PacketPlayInAdvancements;
import net.minecraft.server.v1_16_R3.PacketPlayInArmAnimation;
import net.minecraft.server.v1_16_R3.PacketPlayInAutoRecipe;
import net.minecraft.server.v1_16_R3.PacketPlayInBEdit;
import net.minecraft.server.v1_16_R3.PacketPlayInBeacon;
import net.minecraft.server.v1_16_R3.PacketPlayInBlockDig;
import net.minecraft.server.v1_16_R3.PacketPlayInBlockPlace;
import net.minecraft.server.v1_16_R3.PacketPlayInBoatMove;
import net.minecraft.server.v1_16_R3.PacketPlayInChat;
import net.minecraft.server.v1_16_R3.PacketPlayInClientCommand;
import net.minecraft.server.v1_16_R3.PacketPlayInCloseWindow;
import net.minecraft.server.v1_16_R3.PacketPlayInCustomPayload;
import net.minecraft.server.v1_16_R3.PacketPlayInDifficultyChange;
import net.minecraft.server.v1_16_R3.PacketPlayInEnchantItem;
import net.minecraft.server.v1_16_R3.PacketPlayInEntityAction;
import net.minecraft.server.v1_16_R3.PacketPlayInEntityNBTQuery;
import net.minecraft.server.v1_16_R3.PacketPlayInFlying;
import net.minecraft.server.v1_16_R3.PacketPlayInFlying.PacketPlayInLook;
import net.minecraft.server.v1_16_R3.PacketPlayInFlying.PacketPlayInPosition;
import net.minecraft.server.v1_16_R3.PacketPlayInFlying.PacketPlayInPositionLook;
import net.minecraft.server.v1_16_R3.PacketPlayInHeldItemSlot;
import net.minecraft.server.v1_16_R3.PacketPlayInItemName;
import net.minecraft.server.v1_16_R3.PacketPlayInJigsawGenerate;
import net.minecraft.server.v1_16_R3.PacketPlayInKeepAlive;
import net.minecraft.server.v1_16_R3.PacketPlayInPickItem;
import net.minecraft.server.v1_16_R3.PacketPlayInRecipeDisplayed;
import net.minecraft.server.v1_16_R3.PacketPlayInRecipeSettings;
import net.minecraft.server.v1_16_R3.PacketPlayInResourcePackStatus;
import net.minecraft.server.v1_16_R3.PacketPlayInSetCommandBlock;
import net.minecraft.server.v1_16_R3.PacketPlayInSetCommandMinecart;
import net.minecraft.server.v1_16_R3.PacketPlayInSetCreativeSlot;
import net.minecraft.server.v1_16_R3.PacketPlayInSetJigsaw;
import net.minecraft.server.v1_16_R3.PacketPlayInSettings;
import net.minecraft.server.v1_16_R3.PacketPlayInSpectate;
import net.minecraft.server.v1_16_R3.PacketPlayInSteerVehicle;
import net.minecraft.server.v1_16_R3.PacketPlayInStruct;
import net.minecraft.server.v1_16_R3.PacketPlayInTabComplete;
import net.minecraft.server.v1_16_R3.PacketPlayInTeleportAccept;
import net.minecraft.server.v1_16_R3.PacketPlayInTileNBTQuery;
import net.minecraft.server.v1_16_R3.PacketPlayInTrSel;
import net.minecraft.server.v1_16_R3.PacketPlayInTransaction;
import net.minecraft.server.v1_16_R3.PacketPlayInUpdateSign;
import net.minecraft.server.v1_16_R3.PacketPlayInUseEntity;
import net.minecraft.server.v1_16_R3.PacketPlayInUseItem;
import net.minecraft.server.v1_16_R3.PacketPlayInVehicleMove;
import net.minecraft.server.v1_16_R3.PacketPlayInWindowClick;
import net.minecraft.server.v1_16_R3.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_16_R3.PacketPlayOutSpawnEntity;
import net.minecraft.server.v1_16_R3.PacketPlayOutSpawnEntityExperienceOrb;
import net.minecraft.server.v1_16_R3.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_16_R3.PacketPlayOutSpawnEntityPainting;
import net.minecraft.server.v1_16_R3.PacketStatusInPing;
import net.minecraft.server.v1_16_R3.PacketStatusInStart;
import net.minecraft.server.v1_16_R3.PacketStatusOutPong;
import net.minecraft.server.v1_16_R3.PacketStatusOutServerInfo;

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
	public void onPacketOut(PacketOutEvent e) {
		System.out.println("§aPacket out: " + e.getEventName() + " binary: " + e.getPacketIDBinary());
	}

	@EventHandler
	public void onPacketRecieved(PacketInEvent e) {
		System.out.println("§bPacket in: " + e.getEventName() + " binary: " + e.getPacketIDBinary());
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
		}
		return canceled;
	}
}
