package me.alpha.anticheat;

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
        getLogger().info("AlphaAntiCheat PRO Enabled");
    }

    private void alert(String hack, Player p) {
        String msg = "§c[AlphaAC] §f" + hack + " detected -> §e" + p.getName();
        Bukkit.getConsoleSender().sendMessage(msg);

        for (Player op : Bukkit.getOnlinePlayers()) {
            if (op.isOp()) {
                op.sendMessage(msg);
            }
        }
    }

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

    @EventHandler
    public void onHit(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player)) return;

        Player p = (Player) e.getDamager();

        if (p.getAttackCooldown() < 0.2) {
            alert("AutoClicker/KillAura", p);
            e.setCancelled(true);
        }
    }
}
