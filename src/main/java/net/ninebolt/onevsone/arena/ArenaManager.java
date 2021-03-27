package net.ninebolt.onevsone.arena;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.file.YamlConfiguration;

import net.ninebolt.onevsone.util.Messages;
import net.ninebolt.onevsone.util.NameParser;

public class ArenaManager {

	private Map<String, Arena> arenaMap;
	private File arenaFolder;

	/**
	 * 引数で指定したフォルダのArenaを管理するためのインスタンスを作成します。
	 * @param arenaFolder Arenaファイルが保存されているフォルダ
	 */
	// 引数に依存しない形で作れるのならSingleton化してもいいかも？
	public ArenaManager(File arenaFolder) {
		arenaMap = new HashMap<String, Arena>();
		this.arenaFolder = arenaFolder;
		if(!arenaFolder.exists()) {
			arenaFolder.mkdirs();
		}
		initArenas();
	}

	/**
	 * arenaFolderに入っているymlファイルを読み込み、拡張子なしファイル名とArenaを紐付けるためのMap<String, Arena>に追加します。
	 */
	protected void initArenas() {
		if(arenaFolder.listFiles().length < 1) {
			return;
		}

		for(File file : arenaFolder.listFiles()) {
			String fileName = file.getName();
			if(!file.isFile() || !fileName.endsWith(".yml")) {
				continue;
			}

			Arena arena = deserialize(file);
			if(arena == null) {
				System.out.println(Messages.arenaFormatError(fileName));
				continue;
			}
			String name = NameParser.getNameWithoutExtension(fileName);
			arena.setName(name);
			arenaMap.put(name, arena);
		}
	}

	/**
	 * Map<String, Arena>に格納されている中からArenaを取得します。
	 * @param name Arenaの拡張子なしファイル名
	 * @return 取得した{@link Arena}。見つからない場合 {@code null}
	 */
	public Arena getArena(String name) {
		if(!arenaMap.containsKey(name)) {
			return null;
		}
		return arenaMap.get(name);
	}

	/**
	 * ArenaManagerで管理されているArenaの一覧を取得します。
	 * @return ArenaのList<Arena>
	 */
	public List<Arena> getArenaList() {
		return new ArrayList<Arena>(arenaMap.values());
	}

	/**
	 * ファイルに保存されたArenaの情報を、オブジェクトにデシリアライズします。
	 * @param file Arenaの情報が保存されているファイル
	 * @return Arenaのオブジェクト
	 */
	public Arena deserialize(File file) {
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		return config.getObject("arena", Arena.class);
	}

	/**
	 * ArenaがArenaManagerで管理されるMap<String, Arena>に含まれているかどうかを返します。
	 * @param name Arenaの拡張子なしファイル名
	 * @return Mapに含まれていれば{@code true}。そうでなければ{@code false}
	 */
	public boolean contains(String name) {
		if(getArena(name) != null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Arenaをファイルに保存します。
	 * @param arena 保存するArena
	 */
	public void save(Arena arena) {
		if(arena == null) {
			// error
		}
		File file = new File(arenaFolder, arena.getName() + ".yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

		config.set("arena", arena);
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * アリーナを管理するMap<String, Arena>に登録し、ファイルに保存します。
	 * @param arena 保存するArena
	 */
	public void register(Arena arena) {
		arenaMap.put(arena.getName(), arena);
		// saveの処理は呼び出し側でしたほうがいいかもしれない
		save(arena);
	}

	/**
	 * アリーナを名前で検索して削除します。
	 * @param name アリーナの拡張子なしファイル名
	 */
	public void delete(String name) {
		// Mapから消す処理はunregisterメソッドを別に作ったほうがいいかもしれない
		arenaMap.remove(name);
		// delete file
	}

}
