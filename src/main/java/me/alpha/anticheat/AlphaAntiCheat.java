package me.alpha;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class AlphaAntiCheat extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        getLogger().info("AlphaAntiCheat PRO v2 Enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("AlphaAntiCheat Disabled!");
    }

    // Speed / Fly Check
    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();

        if (!p.isOnGround() && p.getVelocity().getY() > 0.6) {
            alert("Fly", p);
        }

        if (p.getVelocity().length() > 1.2) {
            alert("Speed", p);
        }
    }

    // KillAura / Reach basic check
    @EventHandler
    public void onHit(EntityDamageByEntityEvent e) {

        if (!(e.getDamager() instanceof Player)) return;

        Player attacker = (Player) e.getDamager();

        double distance =
                attacker.getLocation().distance(e.getEntity().getLocation());

        if (distance > 3.3) {
            alert("Reach", attacker);
        }
    }

    private void alert(String hack, Player player) {

        String msg = "§c[AlphaAC] §e" + player.getName()
                + " suspected of §c" + hack;

        Bukkit.getConsoleSender().sendMessage(msg);

        for (Player online : Bukkit.getOnlinePlayers()) {
            if (online.isOp()) {
                online.sendMessage(msg);
            }
        }
    }
}
