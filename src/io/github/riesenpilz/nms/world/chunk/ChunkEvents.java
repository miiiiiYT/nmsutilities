package io.github.riesenpilz.nms.world.chunk;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.mojang.brigadier.exceptions.CommandSyntaxException;

import io.github.riesenpilz.nms.Main;
import io.github.riesenpilz.nms.nbt.NBTTag;

public class ChunkEvents implements Listener {
	@EventHandler
	public void onChunkLoad(ChunkLoadEvent e) {
		if (e.isNewChunk())
			return;

		Chunk chunk = new Chunk(e.getChunk());
		File chunkFile = chunk.getFile();
		new BukkitRunnable() {

			@Override
			public void run() {
				try {
					chunk.setAllNBTTags(read(chunkFile, getOffset(chunk)));
				} catch (IOException | CommandSyntaxException e) {
					e.printStackTrace();
				}
			}
		}.runTaskAsynchronously(Main.getPlugin());
	}

	@EventHandler
	public void onChunkUnload(ChunkUnloadEvent e) {
		Chunk chunk = new Chunk(e.getChunk());
		File chunkFile = chunk.getFile();
		NBTTag tag = chunk.getAllNBTTags();
		chunk.removeAllNBTTags();
		
		if (tag.isEmpty())
			return;
		
		new BukkitRunnable() {

			@Override
			public void run() {
				try {
					write(tag, chunkFile, getOffset(chunk));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}.runTaskAsynchronously(Main.getPlugin());

	}

	private int getOffset(Chunk chunk) {
		return chunk.getBukkit().getX() % 16 * 4 + chunk.getBukkit().getZ() % 16;
	}

	@SuppressWarnings("resource")
	public NBTTag read(File file, int offset) throws IOException, CommandSyntaxException {
		file.getParentFile().mkdirs();
		if (!file.exists())
			return new NBTTag();
		
		String tagString = "";
		Scanner sc = new Scanner(file);
		for (int i = 0; i <= offset; i++) {
			if (!sc.hasNext())
				return new NBTTag();
			tagString = sc.nextLine();
		}
		sc.close();
		if (tagString.isEmpty())
			return new NBTTag();
		
		return new NBTTag(tagString);
	}

	public void write(NBTTag tag, File file, int offset) throws IOException {
		file.getParentFile().mkdirs();
		if (!file.exists())
			file.createNewFile();
		Scanner sc = new Scanner(file);
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < offset; i++) {
			if (sc.hasNext())
				buffer.append(sc.nextLine() + System.lineSeparator());
			else
				buffer.append(System.lineSeparator());
		}
		buffer.append(tag.toString() + System.lineSeparator());
		while (sc.hasNextLine())
			buffer.append(sc.nextLine() + System.lineSeparator());
		sc.close();
		FileWriter writer = new FileWriter(file.getPath());
		writer.append(buffer);
		writer.flush();
	}
}
