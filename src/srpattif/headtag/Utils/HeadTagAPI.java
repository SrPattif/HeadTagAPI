package srpattif.headtag.Utils;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_8_R3.WorldServer;
import srpattif.headtag.HeadTag;

public class HeadTagAPI {

	
	/**
	 * Defines player's head tag.
	 * 
	 * @param player
	 * @param tag
	 */
	public static void setPlayerHeadTag(Player p, String tag) {

		int id = 0;
		boolean contem = false;

		if (HeadTag.attached.containsKey(p)) {
			contem = true;
			id = HeadTag.attached.get(p).getId();

			HeadTag.attached.remove(p);
		}

		WorldServer s = ((CraftWorld) p.getWorld()).getHandle();
		EntityArmorStand stand = new EntityArmorStand(s);

		stand.setLocation(p.getLocation().getX(), p.getLocation().getY() + 0.1, p.getLocation().getZ(), 0, 0);
		stand.setGravity(false);
		stand.setBasePlate(false);
		stand.setInvisible(true);
		stand.setCustomName(tag);
		stand.setCustomNameVisible(true);

		for (Player todos : Bukkit.getOnlinePlayers()) {
			if (contem) {
				PacketPlayOutEntityDestroy destroy = new PacketPlayOutEntityDestroy(id);
				((CraftPlayer) todos).getHandle().playerConnection.sendPacket(destroy);
			}

			if(todos == p) {
			} else {
				PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(stand);
				((CraftPlayer) todos).getHandle().playerConnection.sendPacket(packet);
			}
		}

		HeadTag.attached.put(p, stand);

	}

	/**
	 * Removes player's head tag.
	 * 
	 * @param player
	 */
	public static void removePlayerHeadTag(Player p) {
		if (HeadTag.attached.containsKey(p)) {
			for (Player todos : Bukkit.getOnlinePlayers()) {
				PacketPlayOutEntityDestroy destroy = new PacketPlayOutEntityDestroy(HeadTag.attached.get(p).getId());
				((CraftPlayer) todos).getHandle().playerConnection.sendPacket(destroy);
			}
			
			HeadTag.attached.remove(p);
		}
	}

}
