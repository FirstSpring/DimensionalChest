package mods.firstspring.dimchest;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class InventoryDimChest implements IInventory
{
	private ItemStack[] chestContents = new ItemStack[54];

    public int unloadTick = 0;

	public int getSizeInventory()
	{
		return 54;
	}

	public ItemStack getStackInSlot(int i)
	{
		return this.chestContents[i];
	}

	public ItemStack decrStackSize(int i, int j)
	{
		if (this.chestContents[i] != null)
		{
			ItemStack var3;

			if (this.chestContents[i].stackSize <= j)
			{
				var3 = this.chestContents[i];
				this.chestContents[i] = null;
				this.onInventoryChanged();
				return var3;
			}
			else
			{
				var3 = this.chestContents[i].splitStack(j);

				if (this.chestContents[i].stackSize == 0)
				{
					this.chestContents[i] = null;
				}

				this.onInventoryChanged();
				return var3;
			}
		}
		else
		{
			return null;
		}
	}

	public void setInventorySlotContents(int i, ItemStack itemstack)
	{
		this.chestContents[i] = itemstack;

		if (itemstack != null && itemstack.stackSize > this.getInventoryStackLimit())
		{
			itemstack.stackSize = this.getInventoryStackLimit();
		}

		this.onInventoryChanged();
	}

	public String getInvName()
	{
		return "container.dimchest";
	}

	public void loadNBTTag(NBTTagList list)
	{
		this.chestContents = new ItemStack[this.getSizeInventory()];

		for (int var3 = 0; var3 < list.tagCount(); ++var3)
		{
			NBTTagCompound var4 = (NBTTagCompound)list.tagAt(var3);
			int var5 = var4.getByte("Slot") & 255;

			if (var5 >= 0 && var5 < this.chestContents.length)
			{
				this.chestContents[var5] = ItemStack.loadItemStackFromNBT(var4);
			}
		}
	}

	public NBTTagList getNBT()
	{
		NBTTagList tag = new NBTTagList();

		for (int var3 = 0; var3 < this.chestContents.length; ++var3)
		{
			if (this.chestContents[var3] != null)
			{
				NBTTagCompound var4 = new NBTTagCompound();
				var4.setByte("Slot", (byte)var3);
				this.chestContents[var3].writeToNBT(var4);
				tag.appendTag(var4);
			}
		}

		return tag;
	}

	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		return null;
	}

	@Override
	public void onInventoryChanged() {
		unloadTick = 0;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return true;
	}

	@Override
	public void openChest() {}

	@Override
	public void closeChest() {}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return true;
	}
}
