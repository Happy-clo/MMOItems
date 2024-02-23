package net.Indyuce.mmoitems.stat;

import io.lumine.mythic.lib.MythicLib;
import io.lumine.mythic.lib.api.item.ItemTag;
import io.lumine.mythic.lib.api.util.AltChar;
import io.lumine.mythic.lib.version.VersionMaterial;
import net.Indyuce.mmoitems.ItemStats;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.edition.StatEdition;
import net.Indyuce.mmoitems.api.item.build.ItemStackBuilder;
import net.Indyuce.mmoitems.api.item.mmoitem.ReadMMOItem;
import net.Indyuce.mmoitems.gui.edition.EditionInventory;
import net.Indyuce.mmoitems.stat.data.SkullTextureData;
import net.Indyuce.mmoitems.stat.type.ItemStat;
import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SkullTextureStat extends ItemStat<SkullTextureData, SkullTextureData> {
    public SkullTextureStat() {
        super("SKULL_TEXTURE", VersionMaterial.PLAYER_HEAD.toMaterial(), "Skull Texture", new String[]{"The skull texture &nvalue&7 which can be found on head databases.",
                "1.20+ users are advised to use the texture&n URL&7 instead."}, new String[]{"all"}, VersionMaterial.PLAYER_HEAD.toMaterial());
    }

    @Override
    public SkullTextureData whenInitialized(Object object) {
        Validate.isTrue(object instanceof ConfigurationSection, "Must specify a config section");
        ConfigurationSection config = (ConfigurationSection) object;

        final String value = config.getString("value");
        Validate.notNull(value, "Could not load skull texture value");

        final String uuid = config.getString("uuid");
        Validate.notNull(uuid, "Could not find skull texture UUID: re-enter your skull texture value and one will be selected randomly.");

        final Object profile = MythicLib.plugin.getVersion().getWrapper().newProfile(UUID.fromString(uuid), value);
        final SkullTextureData skullTexture = new SkullTextureData(profile);
        return skullTexture;
    }

    @Override
    public void whenDisplayed(List<String> lore, Optional<SkullTextureData> statData) {
        lore.add(ChatColor.GRAY + "Current Value: " + (statData.isPresent() ? ChatColor.GREEN + "Texture value provided" : ChatColor.RED + "None"));
        lore.add("");
        lore.add(ChatColor.YELLOW + AltChar.listDash + " Left click to change this value.");
        lore.add(ChatColor.YELLOW + AltChar.listDash + " Right click to remove this value.");
    }

    @Override
    public void whenInput(@NotNull EditionInventory inv, @NotNull String message, Object... info) {
        inv.getEditedSection().set("skull-texture.value", message);
        inv.getEditedSection().set("skull-texture.uuid", UUID.randomUUID().toString());
        inv.registerTemplateEdition();
        inv.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + getName() + " successfully changed to " + message + ".");
    }

    @Override
    public void whenApplied(@NotNull ItemStackBuilder item, @NotNull SkullTextureData data) {
        if (item.getItemStack().getType() != VersionMaterial.PLAYER_HEAD.toMaterial()) return;

        if (data.getGameProfile() != null)
            MythicLib.plugin.getVersion().getWrapper().setProfile((SkullMeta) item.getMeta(), data.getGameProfile());
    }

    /**
     * This stat is saved not as a custom tag, but as the vanilla HideFlag itself.
     * Alas this is an empty array
     */
    @NotNull
    @Override
    public ArrayList<ItemTag> getAppliedNBT(@NotNull SkullTextureData data) {
        return new ArrayList<>();
    }

    @Override
    public void whenClicked(@NotNull EditionInventory inv, @NotNull InventoryClickEvent event) {
        if (event.getAction() == InventoryAction.PICKUP_HALF) {
            inv.getEditedSection().set(getPath(), null);
            inv.registerTemplateEdition();
            inv.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + "Successfully removed " + getName() + ".");
        } else new StatEdition(inv, this).enable("Write in the chat the text you want.");
    }

    @Override
    public void whenLoaded(@NotNull ReadMMOItem mmoitem) {
        try {
            final ItemMeta meta = mmoitem.getNBT().getItem().getItemMeta();
            Validate.isTrue(meta instanceof SkullMeta);
            final Object profile = MythicLib.plugin.getVersion().getWrapper().getProfile((SkullMeta) meta);
            mmoitem.setData(ItemStats.SKULL_TEXTURE, new SkullTextureData(profile));
        } catch (RuntimeException ignored) {
        }
    }

    /**
     * This stat is saved not as a custom tag, but as the vanilla Head Texture itself.
     * Alas this method returns null.
     */
    @Nullable
    @Override
    public SkullTextureData getLoadedNBT(@NotNull ArrayList<ItemTag> storedTags) {
        return null;
    }

    @NotNull
    @Override
    public SkullTextureData getClearStatData() {
        return new SkullTextureData(null);
    }
}
