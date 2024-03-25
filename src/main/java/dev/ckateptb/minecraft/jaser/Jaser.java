package dev.ckateptb.minecraft.jaser;

import dev.ckateptb.minecraft.jyraf.container.IoC;
import org.bukkit.plugin.java.JavaPlugin;

public class Jaser extends JavaPlugin {
    public Jaser() {
        IoC.scan(this, string -> !string.startsWith(Jaser.class.getPackageName() + ".internal"));
    }
}
