package mods.firstspring.dimchest;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {

	@Override
	void registerRenderer(){
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDimChest.class, new TileEntityDimChestRenderer());
		RenderingRegistry.registerBlockHandler(new ItemChestRenderer());
	}

	@Override
	void openGui(int x, int y, int z){
		//チェストの座標を渡す
		Minecraft.getMinecraft().displayGuiScreen(new GuiDimChest(x, y, z));
	}
	
	@Override
	World getWorld(){
		return Minecraft.getMinecraft().theWorld;
	}
	
	
}
