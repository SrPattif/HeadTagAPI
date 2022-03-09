package srpattif.headtag;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityTeleport;
import srpattif.headtag.Listeners.Join;
import srpattif.headtag.Listeners.Quit;

public class HeadTag extends JavaPlugin {

	public static HashMap<Player, EntityArmorStand> attached = new HashMap<>();
	public static HeadTag main;

	@Override
	public void onEnable() {
		main = this;
		this.getServer().getPluginManager().registerEvents(new Join(), this);
		this.getServer().getPluginManager().registerEvents(new Quit(), this);

		Bukkit.getScheduler().scheduleSyncRepeatingTask(main, new Runnable() {
			public void run() {
				for (HashMap.Entry<Player, EntityArmorStand> entry : attached.entrySet()) {
					Player key = entry.getKey();
					EntityArmorStand val = entry.getValue();
					
					Double y = key.getLocation().getY() + 0.1;
					if(key.getGameMode() == GameMode.SPECTATOR) {
						y = key.getLocation().getY() + 1000;
					}
					val.setLocation(key.getLocation().getX(), y, key.getLocation().getZ(), 0, 0);
					PacketPlayOutEntityTeleport teleport = new PacketPlayOutEntityTeleport(val);

					for (Player todos : Bukkit.getOnlinePlayers()) {
						if(todos == key) {
							
						} else {
							((CraftPlayer)todos).getHandle().playerConnection.sendPacket(teleport);
						}
					}

				}

			}
		}, 0L, 0L);
		
		ConsoleCommandSender console = Bukkit.getConsoleSender();
		console.sendMessage(" ");
		console.sendMessage(" §aRunning §f" + main.getName() + " §aversion §f" + main.getDescription().getVersion() + "§a.");
		console.sendMessage(" §aDeveloped by §f" + main.getDescription().getAuthors().get(0));
		console.sendMessage(" §aThanks for using!");
		console.sendMessage(" ");
	}
	
	@Override
	public void onDisable() {
		for (HashMap.Entry<Player, EntityArmorStand> entry : attached.entrySet()) {
			EntityArmorStand val = entry.getValue();
			
			for(Player todos : Bukkit.getOnlinePlayers()) {
				PacketPlayOutEntityDestroy destroy = new PacketPlayOutEntityDestroy(val.getId());
	            ((CraftPlayer)todos).getHandle().playerConnection.sendPacket(destroy);
			}
			
		}
		
	}

}
