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

	private static Map<String, Arena> arenaMap;
	private static File arenaDir;

	public static void initArenas(File arenaFileDir) {
		arenaDir = arenaFileDir;
		if(!arenaDir.exists()) {
			arenaDir.mkdirs();
		}

		arenaMap = new HashMap<String, Arena>();

		for(File file : arenaDir.listFiles()) {
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

	public static File getArenaDir() {
		return arenaDir;
	}

	public static Arena getArena(String name) {
		if(!arenaMap.containsKey(name)) {
			return null;
		}
		return arenaMap.get(name);
	}

	public static List<Arena> getArenaList() {
		return new ArrayList<Arena>(arenaMap.values());
	}

	public static boolean contains(String name) {
		if(getArena(name) != null) {
			return true;
		} else {
			return false;
		}
	}

	public static void create(String name) {
		Arena arena = new Arena(name);
		File file = new File(arenaDir, name+".yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

		arenaMap.put(name, arena);
		config.set("arena", arena);
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void delete(String name) {
		arenaMap.remove(name);
	}

}
