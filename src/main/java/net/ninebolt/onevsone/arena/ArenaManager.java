package net.ninebolt.onevsone.arena;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.file.YamlConfiguration;

import net.ninebolt.onevsone.OneVsOne;
import net.ninebolt.onevsone.util.Messages;

public class ArenaManager {

	private Map<String, Arena> arenaMap;
	private File arenaFolder;

	// コンストラクタに依存しない形で作れるのならSingleton化してもいいかも？
	public ArenaManager(File arenaFolder) {
		arenaMap = new HashMap<String, Arena>();
		this.arenaFolder = arenaFolder;
		if(!arenaFolder.exists()) {
			arenaFolder.mkdirs();
		}
		initArenas();
	}

	protected void initArenas() {
		for(File file : arenaFolder.listFiles()) {
			String fileName = file.getName();
			if(file.isFile() && fileName.endsWith(".yml")) {
				Arena arena = Arena.deserialize(file);
				if(arena == null) {
					OneVsOne.getInstance().getLogger().info(Messages.arenaDeserializeError(fileName));
					continue;
				}

				int dotPoint = fileName.lastIndexOf('.');
				if(dotPoint != -1) {
					String name = fileName.substring(0, dotPoint);
					arenaMap.put(name, arena) ;
				}
			}
		}
	}

	public Arena getArena(String name) {
		if(!arenaMap.containsKey(name)) {
			return null;
		}
		return arenaMap.get(name);
	}

	public List<Arena> getArenaList() {
		return new ArrayList<Arena>(arenaMap.values());
	}

	public boolean contains(String name) {
		if(getArena(name) != null) {
			return true;
		} else {
			return false;
		}
	}

	public void save(Arena arena, String name) {
		if(arena == null) {
			// error
		}
		File file = new File(arenaFolder, name+".yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

		config.set("arena", arena);
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void create(String name) {
		Arena arena = new Arena(name);

		arenaMap.put(name, arena);
		save(arena, name);
	}

	public void delete(String name) {
		arenaMap.remove(name);
	}

}
