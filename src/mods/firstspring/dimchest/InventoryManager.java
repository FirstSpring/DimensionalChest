package mods.firstspring.dimchest;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagList;
import cpw.mods.fml.relauncher.FMLInjectionData;

public class InventoryManager {
	protected static Map<String,InventoryDimChest> invList = new HashMap();
	
	public static IInventory getInventory(String name){
		if(invList.containsKey(name))
			return invList.get(name);
		else{
			InventoryDimChest inv = load(name);
			invList.put(name, inv);
			return inv;
		}
	}
	
	public static void unloadCount(){
		for(Iterator i = invList.entrySet().iterator(); i.hasNext();){
			Map.Entry<String, InventoryDimChest> entry = (Map.Entry)i.next();
			String name = entry.getKey();
			InventoryDimChest inv = entry.getValue();
			if(inv.unloadTick++ > 20){
				unload(name,inv);
				i.remove();
			}
		}
	}
	
	public static InventoryDimChest load(String name){
		InventoryDimChest inv = new InventoryDimChest();
		try{
			//ファイルを取得
			File mcHome = (File)FMLInjectionData.data()[6];
			File dir = new File(mcHome, "DimChestInventory");
			File invFile = new File(dir, name + ".nbt");
			//入力ストリームを作成
			DataInputStream dis;
			dis = new DataInputStream(new FileInputStream(invFile));
			NBTTagList tag;
			//入力ストリームよりNBTデータを取得
			tag = (NBTTagList)NBTBase.readNamedTag(dis);
			//ストリームを閉じる
			dis.close();
			//インベントリにNBTを読み込ませる
			inv.loadNBTTag(tag);
			return inv;
		}catch(Exception e){//ここでは例外処理は適当
			return inv;
		}
	}

	public static void unload(String name, InventoryDimChest inv){
		try{
			//ファイルを取得
			File mcHome = (File)FMLInjectionData.data()[6];
			File dir = new File(mcHome, "DimChestInventory");
			File invFile = new File(dir, name + ".nbt");
			invFile.getParentFile().mkdirs();
			//無ければ作る
			invFile.createNewFile();
			//元からディレクトリが存在している場合などは何もせずに戻る
			if(!invFile.isFile())
				return;
			//出力ストリームを作成
			DataOutputStream dos = new DataOutputStream(new FileOutputStream(invFile));
			//出力ストリームにインベントリよりNBTデータを取得して書き込み
			NBTBase.writeNamedTag(inv.getNBT(), dos);
			//ストリームを閉じる
			dos.close();
		}catch(Exception e){//ここでは例外処理は適当

		}
	}
	
	//読み込まれている全データの読み込みを解除
	public static void unloadAll(){
		for(Iterator i = invList.entrySet().iterator(); i.hasNext();){
			Map.Entry<String, InventoryDimChest> entry = (Map.Entry)i.next();
			String name = entry.getKey();
			InventoryDimChest inv = entry.getValue();
			unload(name,inv);
		}
		invList.clear();
	}
}
