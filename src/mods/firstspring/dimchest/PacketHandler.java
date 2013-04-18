package mods.firstspring.dimchest;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler {
	@Override
	public void onPacketData(INetworkManager network, Packet250CustomPayload packet, Player player)
	{
		int x=0,y=0,z=0;
		String name="";
		int orient=0;
		int meta=0;
		boolean hasLinkCard = false;
		ByteArrayDataInput dat = ByteStreams.newDataInput(packet.data);
		try{
		x = dat.readInt();
		y = dat.readInt();
		z = dat.readInt();
		}catch(Exception e){
		}
		if(packet.channel.equals("DCServer")){
			try{
				name = dat.readUTF();
			}catch(Exception e){
				return;
			}
			World world = ((EntityPlayerMP)player).worldObj;
			TileEntity tile = world.getBlockTileEntity(x, y, z);
			if(tile instanceof TileEntityDimChest){
				TileEntityDimChest chest = (TileEntityDimChest)tile;
				chest.name = name;
			}
			
		}
		if(packet.channel.equals("DCClient")){
			try{
				orient = dat.readInt();
				meta = dat.readInt();
				hasLinkCard = dat.readBoolean();
			}catch(Exception e){
			}
			World world = CommonProxy.proxy.getWorld();
			TileEntity tile = world.getBlockTileEntity(x, y, z);
			if(tile instanceof TileEntityDimChest){
				TileEntityDimChest chest = (TileEntityDimChest)tile;
				chest.orient = orient;
				chest.hasLinkCard = hasLinkCard;
			}
			world.markBlockForUpdate(x, y, z);
		}
	}
}