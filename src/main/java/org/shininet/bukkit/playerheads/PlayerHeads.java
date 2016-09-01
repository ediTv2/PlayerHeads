/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.shininet.bukkit.playerheads;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

/**
 * @author meiskam
 */

public final class PlayerHeads extends JavaPlugin implements Listener {

    private static boolean updateReady = false;
    private static String updateName = "";
    public Logger logger;
    public FileConfiguration configFile;
    public boolean NCPHook = false;
    private PlayerHeadsCommandExecutor commandExecutor;
    private PlayerHeadsListener listener;

    @Override
    public void onEnable() {
        logger = getLogger();
        configFile = getConfig();
        configFile.options().copyDefaults(true);
        saveDefaultConfig();

        Lang.init(this);
        initNCPHook();

        listener = new PlayerHeadsListener(this);
        commandExecutor = new PlayerHeadsCommandExecutor(this);
        getServer().getPluginManager().registerEvents(listener, this);
        getCommand("PlayerHeads").setExecutor(commandExecutor);
    }

    @Override
    public void onDisable() {
        EntityDeathEvent.getHandlerList().unregister(listener);
        PlayerInteractEvent.getHandlerList().unregister(listener);
        PlayerJoinEvent.getHandlerList().unregister(listener);
        BlockBreakEvent.getHandlerList().unregister(listener);
    }


    private void initNCPHook() {
        if (getServer().getPluginManager().getPlugin("NoCheatPlus") != null) {
            NCPHook = true;
        }
    }

    public boolean getUpdateReady() {
        return updateReady;
    }

    public String getUpdateName() {
        return updateName;
    }
}
