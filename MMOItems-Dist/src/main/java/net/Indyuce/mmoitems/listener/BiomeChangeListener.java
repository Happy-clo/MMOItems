package net.Indyuce.mmoitems.listener;

import net.Indyuce.mmoitems.api.player.PlayerData;
import org.bukkit.block.Biome;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * mmoitems
 * 19/03/2023
 *
 * @author Roch Blondiaux (Kiwix).
 */
public class BiomeChangeListener implements Listener {


    /**
     * This listener goal is to update the player inventory when he changes biome.
     *
     * @param e
     */
   @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerMove(PlayerMoveEvent e) {
        //检查事件是否被取消，或者玩家的数据是否被加载，或者移动的方块的X和Z坐标相同
        if (e.isCancelled() || !PlayerData.has(e.getPlayer()) || (e.getFrom().getBlockX() == e.getTo().getBlockX() && e.getFrom().getBlockZ() == e.getTo().getBlockZ()))
            return;
        //获取玩家的移动前的生物群系
        final Biome lastBiome = e.getFrom().getBlock().getBiome();
        //获取玩家的移动后的生物群系
        final Biome biome = e.getTo().getBlock().getBiome();
        //如果生物群系不同，则更新玩家的物品信息
        if (biome != lastBiome)
            PlayerData.get(e.getPlayer()).getInventory().scheduleUpdate();
    }
}