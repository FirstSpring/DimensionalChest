package mods.firstspring.dimchest;

import net.minecraft.world.World;
import cpw.mods.fml.common.SidedProxy;

public class CommonProxy {
	@SidedProxy(clientSide = "mods.firstspring.dimchest.ClientProxy", serverSide = "mods.firstspring.dimchest.CommonProxy")
	public static CommonProxy proxy;
	
	void registerRenderer(){
		
	}
	
	void openGui(int x, int y, int z){
		
	}
	
	World getWorld(){
		return null;
	}
}