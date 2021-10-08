package io.github.riesenpilz.nmsUtilities.world.chunk.storage;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

import io.github.riesenpilz.nmsUtilities.world.chunk.Chunk;

public class ChunkEvents implements Listener {

	@EventHandler
	public void onChunkLoad(ChunkLoadEvent e) {
		Chunk.getChunkOf(e.getChunk()).load();
	}

	@EventHandler
	public void onChunkUnload(ChunkUnloadEvent e) {
		Chunk.getChunkOf(e.getChunk()).save();
		
	}

}
