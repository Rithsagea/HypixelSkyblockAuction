package com.rithsagea.skyblock;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;

public class Item {
	
	public short id;
	public short count = 0;
	public short damage;
	
	public Item(String item_bytes) throws IOException {
		NBTTagCompound c = CompressedStreamTools
				.readCompressed(
						new ByteArrayInputStream(Base64
								.getDecoder()
								.decode(item_bytes)))
				.getTagList("i", 10)
				.getCompoundTagAt(0);
		
		id = c.getShort("id");
		count = c.getByte("Count");
		damage = c.getShort("Damage");
	}
}
