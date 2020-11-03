package dev._2lstudios.hamsterapi.utils;

import java.util.HashMap;
import java.util.Map;

public class Reflection {
	private final String version;
	private final Map<String, Class<?>> classes = new HashMap<>();

	public Reflection(String version) {
		this.version = version;
	}

	public Class<?> getNMSClass(String key) {
		if (this.classes.containsKey(key)) {
			return this.classes.get(key);
		}

		try {
			Class<?> nmsClass = Class.forName("net.minecraft.server." + this.version + "." + key);

			this.classes.put(key, nmsClass);

			return nmsClass;
		} catch (final ClassNotFoundException e) {
		}

		return null;
	}

	public Class<?> getCraftBukkitClass(String key) {
		if (this.classes.containsKey(key)) {
			return this.classes.get(key);
		}

		try {
			Class<?> craftBukkitClass = Class.forName("org.bukkit.craftbukkit." + this.version + "." + key);
			this.classes.put(key, craftBukkitClass);

			return craftBukkitClass;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}
}
