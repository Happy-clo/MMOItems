package net.Indyuce.mmoitems.listener;

import fr.phoenixdevt.profiles.event.ProfileSelectEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class ProfileSupportListener implements Listener {

    // 注册事件，优先级为MONITOR，忽略取消事件
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void a(ProfileSelectEvent event) {
        // 更新玩家的物品栏
        ItemListener.updateInventory(event.getPlayer());
    }
}