package net.ninebolt.onevsone.util;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

public class UUIDCache {

	private static final String FILE_NAME = "uuidcache.yml";
	private File dataFolder;
	private YamlConfiguration cache;

	/**
	 * UUIDCacheのコンストラクタ
	 * @param dataFolder {@value #FILE_NAME}を保存するフォルダ
	 */
	public UUIDCache(File dataFolder) {
		this.dataFolder = dataFolder;
		File cacheFile = new File(dataFolder, FILE_NAME);
		if(!cacheFile.exists()) {
			try {
				cacheFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		this.cache = YamlConfiguration.loadConfiguration(cacheFile);
	}

	/**
	 * UUIDをプレイヤー名から取得します。
	 * @param playerName プレイヤー名
	 * @return キャッシュに存在すればUUID(string), なければnull
	 */
	public String getUUIDByName(String playerName) {
		return cache.getString(playerName);
	}

	/**
	 * ファイル {@value #FILE_NAME} に、cacheの内容を保存するメソッド
	 */
	public void save() {
		File cacheFile = new File(dataFolder, FILE_NAME);
		try {
			cache.save(cacheFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * キャッシュにプレイヤー名とUUIDの組をセットします。
	 * {@link #save()}を実行せずにサーバーが再起動または停止した場合、変更した内容は破棄されます。
	 * @param playerName プレイヤー名
	 * @param uuid プレイヤーのUUID
	 */
	public void set(String playerName, String uuid) {
		cache.set(playerName, uuid);
	}
}
