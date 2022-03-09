package srpattif.headtag.Listeners;

import java.util.HashMap;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import srpattif.headtag.HeadTag;

public class Join implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		
		for (HashMap.Entry<Player, EntityArmorStand> entry : HeadTag.attached.entrySet()) {
			EntityArmorStand val = entry.getValue();
			
			PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(val);
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
		}
	}

}
