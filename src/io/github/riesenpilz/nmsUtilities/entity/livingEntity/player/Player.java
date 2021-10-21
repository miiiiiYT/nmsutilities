package io.github.riesenpilz.nmsUtilities.entity.livingEntity.player;

import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;

import io.github.riesenpilz.nmsUtilities.entity.DataWatcherItem;
import io.github.riesenpilz.nmsUtilities.entity.livingEntity.LivingEntity;
import io.github.riesenpilz.nmsUtilities.nbt.NBTTag;
import io.github.riesenpilz.nmsUtilities.packet.PacketUtils;
import io.github.riesenpilz.nmsUtilities.packet.playIn.PacketPlayInSettingsEvent;
import io.netty.channel.ChannelPipeline;
import net.minecraft.server.v1_16_R3.EntityPlayer;
import net.minecraft.server.v1_16_R3.NBTTagCompound;
import net.minecraft.server.v1_16_R3.PlayerConnection;

public class Player extends LivingEntity {

	protected Player(org.bukkit.entity.Player bukkit) {
		super(bukkit);
	}

	public static Player getPlayerOf(org.bukkit.entity.Player bukkit) {
		return new Player(bukkit);
	}

	@Override
	public org.bukkit.entity.Player getBukkit() {
		return (org.bukkit.entity.Player) super.getBukkit();
	}

	@Override
	public EntityPlayer getNMS() {
		return ((CraftPlayer) getBukkit()).getHandle();
	}

	public ChannelPipeline getChannelPipeline() {
		return getPlayerConnection().networkManager.channel.pipeline();
	}

	public PlayerConnection getPlayerConnection() {
		return getNMS().playerConnection;
	}

	public String getName() {
		return getBukkit().getName();
	}

	// DataWatcher (DW)
	public static DataWatcherItem<Float> getDWAdditionalHearts(float additionalHearts) {
		return new DataWatcherItem<>(14, additionalHearts);
	}

	public static DataWatcherItem<Integer> getDWScore(int score) {
		return new DataWatcherItem<>(15, score);
	}

	/**
	 * The Displayed Skin Parts bit mask that is sent in
	 * {@link PacketPlayInSettingsEvent}.
	 * 
	 * @param states
	 * @return
	 */
	public static DataWatcherItem<Byte> getDWSkin(boolean cape, boolean jacket, boolean leftSleeve, boolean rightSleeve,
			boolean leftPantsLag, boolean rightPantsLeg, boolean hat) {
		return new DataWatcherItem<>(16,
				PacketUtils.toBitSet(cape, jacket, leftSleeve, rightSleeve, leftPantsLag, rightPantsLeg, hat));
	}

	public static DataWatcherItem<Byte> getDWMainHand(Hand mainHand) {
		return new DataWatcherItem<>(17, (byte) (mainHand == Hand.OFF_HAND ? 0 : 1));
	}

	/**
	 * entity data for occupying parrot
	 * 
	 * @param leftShoulder
	 * @return
	 */
	public static DataWatcherItem<NBTTagCompound> getDWLeftShoulder(NBTTag leftShoulder) {
		return new DataWatcherItem<>(18, leftShoulder.getNMS());
	}

	/**
	 * entity data for occupying parrot
	 * 
	 * @param rightShoulder
	 * @return
	 */
	public static DataWatcherItem<NBTTagCompound> getDWRightShoulder(NBTTag rightShoulder) {
		return new DataWatcherItem<>(19, rightShoulder.getNMS());
	}
}
