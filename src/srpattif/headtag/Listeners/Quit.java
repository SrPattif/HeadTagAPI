package srpattif.headtag.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import srpattif.headtag.HeadTag;

public class Quit implements Listener {
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		
		if(HeadTag.attached.containsKey(p)) {
			for(Player todos : Bukkit.getOnlinePlayers()) {
				if(todos == p) {
					
				} else {
					PacketPlayOutEntityDestroy destroy = new PacketPlayOutEntityDestroy(HeadTag.attached.get(p).getId());
		            ((CraftPlayer)todos).getHandle().playerConnection.sendPacket(destroy);
				}
			}
			
			HeadTag.attached.remove(p);
		}
	}

}
