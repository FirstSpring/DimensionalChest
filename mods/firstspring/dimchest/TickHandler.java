package mods.firstspring.dimchest;

import java.util.EnumSet;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class TickHandler implements ITickHandler {

	//tickカウントの始まりに呼ばれる
	//アンロードカウントを実行
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		InventoryManager.unloadCount();
	}

	//tickカウントの終りに呼ばれる
	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {}

	//どのTick処理のタイミングで呼ばれるかを設定
	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.SERVER);
	}

	//このハンドラの名前を返す
	@Override
	public String getLabel() {
		return "InventoryManager Unload Counter";
	}

}
