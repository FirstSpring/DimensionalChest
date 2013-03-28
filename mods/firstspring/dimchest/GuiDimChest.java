package mods.firstspring.dimchest;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.network.PacketDispatcher;

public class GuiDimChest extends GuiScreen {
	int x,y,z;
	GuiTextField namebox;
	
	//対象のチェストの座標を一時キープ
	public GuiDimChest(int x, int y, int z){
		this.x = x;
		this.y = y;
		this.z = z;
	}

	//ボタンやテキストボックスを配置
	@Override
	public void initGui(){
		this.buttonList.clear();
		byte var1 = -16;
		this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120 + var1, 200, 20, "Regist"));
		this.namebox = new GuiTextField(this.fontRenderer, this.width / 2 - 100, 100, 200, 20);
		//テキストボックスにフォーカスを合わせる
		this.namebox.setFocused(true);
	}

	@Override
	public void drawScreen(int par1, int par2, float par3)
	{
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRenderer, "Input Inventory Space Name", this.width / 2, 40, 16777215);
		this.namebox.drawTextBox();
		super.drawScreen(par1, par2, par3);
	}

	//Registボタンを押すまで画面が閉じないように
	@Override
	public void keyTyped(char par1, int par2)
	{
		//テキストボックスに入力キーを渡す
		this.namebox.textboxKeyTyped(par1, par2);
		//Enterキーが押されたときに画面を閉じる
		if(par2 == 28)
			this.mc.displayGuiScreen((GuiScreen)null);
	}

	//テキストボックスがクリックされた時にフォーカスを合わせる
	@Override
	public void mouseClicked(int par1, int par2, int par3)
	{
		super.mouseClicked(par1, par2, par3);
		this.namebox.mouseClicked(par1, par2, par3);
	}

	//ボタンがクリックされた時に画面を閉じる
	@Override
	public void actionPerformed(GuiButton button){
			this.mc.displayGuiScreen((GuiScreen)null);
	}

	//画面が閉じられたときにパケットを送る
	@Override
	public void onGuiClosed()
	{
		PacketDispatcher.sendPacketToServer(createPacket());
	}

	//GUIを開いている間にゲームを止めない
	@Override
	public boolean doesGuiPauseGame(){
		return false;
	}

	//パケット作成
	public Packet createPacket(){
		ByteArrayDataOutput data = ByteStreams.newDataOutput();
		try{
			data.writeInt(x);
			data.writeInt(y);
			data.writeInt(z);
			data.writeUTF(this.namebox.getText().trim());
		}catch(Exception e){
		}
		return new Packet250CustomPayload("DCServer", data.toByteArray());
	}


}
