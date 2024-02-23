package net.Indyuce.mmoitems.stat;

import net.Indyuce.mmoitems.stat.type.GemStoneStat;
import net.Indyuce.mmoitems.stat.type.StringStat;
import org.bukkit.Material;

public class RepairReference extends StringStat implements GemStoneStat {
    public RepairReference() {
        super("REPAIR_TYPE", Material.ANVIL, "Repair Reference", new String[]{"A repair consumable may only be used onto", "an item with the same repair reference."}, new String[]{"all"});
    }
}
