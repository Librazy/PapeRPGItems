package org.librazy.paperpgitem;

import cat.nyaa.nyaacore.LanguageRepository;
import org.bukkit.plugin.java.JavaPlugin;
import org.librazy.nclangchecker.LangKey;

public class I18n extends LanguageRepository {
    public static I18n instance = null;
    private final PapeRPGItems plugin;
    private String lang;

    I18n(PapeRPGItems plugin, String lang) {
        instance = this;
        this.plugin = plugin;
        this.lang = lang;
        load();
    }

    public static String format(@LangKey String key, Object... args) {
        return instance.getFormatted(key, args);
    }

    @Override
    protected JavaPlugin getPlugin() {
        return plugin;
    }

    @Override
    public String getLanguage() {
        return lang;
    }
}
