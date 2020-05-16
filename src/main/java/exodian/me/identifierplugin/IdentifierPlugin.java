package exodian.me.identifierplugin;


import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.player.PlayerInteractEntityEvent;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;


public class IdentifierPlugin extends JavaPlugin {

    public Manager m = new Manager();

    @Override
    public void onEnable() {

        getLogger().info("IdentifierPlugin has started.");

        getServer().getPluginManager().registerEvents(m, this);
        getCommand("identifier").setExecutor(this::onCommand);

        m.prepare();

    }


    @Override
    public void onDisable() {

    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("identifier")) {
            Player p = (Player) sender;
            if (p.hasPermission("identify.immune")) {
                p.getInventory().addItem(m.getIdentityTool());
            }
            return true;
        }
        return false;
    }


    class Manager implements Listener {


        private ItemStack IdentityTool;

        public void prepare() {
            prepareTool();
        }

        public ItemStack getIdentityTool() {
            return IdentityTool;
        }

        public void prepareTool() {
            ItemStack InvisHelmTEMP = new ItemStack(Material.BLAZE_ROD);
            ItemMeta InvisHelmMETA = InvisHelmTEMP.getItemMeta();
            InvisHelmMETA.setDisplayName(ChatColor.DARK_RED + "" + ChatColor.ITALIC + "I.D. Scanner");
            InvisHelmMETA.setLocalizedName("Identity Tool");
            ArrayList<String> lore = new ArrayList<String>();
            lore.add("Right click a player to ID them.");
            InvisHelmMETA.setLore(lore);
            InvisHelmTEMP.setItemMeta(InvisHelmMETA);
            IdentityTool = InvisHelmTEMP;
        }

        @EventHandler
        public void onPlayerInteractEntity(PlayerInteractEntityEvent evt) {
            if (evt.getRightClicked() instanceof Player) {
                if (evt.getPlayer().getItemInHand().equals(IdentityTool)) {
                    final Player target = (Player) evt.getRightClicked();
                    final Player source = evt.getPlayer();
                    if (target.hasPermission("identify.immune")) {
                        source.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4&o This is: &r&k#@#@#$%!@#!@*#%^");
                    }
                    else {
                        source.sendMessage(ChatColor.DARK_RED + "" + ChatColor.ITALIC + "This is: " + target.getDisplayName());
                    }
                }
            }

        }

    }

}
