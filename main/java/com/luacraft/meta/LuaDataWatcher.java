package com.luacraft.meta;

import com.luacraft.LuaCraftState;
import com.luacraft.LuaUserdata;
import com.naef.jnlua.JavaFunction;
import com.naef.jnlua.LuaState;

import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializer;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;

// TODO: Test all these out after updating them to use the new MC 1.9 classes

public class LuaDataWatcher {
	private static JavaFunction __tostring = new JavaFunction() {
		public int invoke(LuaState l) {
			EntityDataManager self = (EntityDataManager)l.checkUserdata(1, EntityDataManager.class, "DataWatcher");
			l.pushString(String.format("DataWatcher: 0x%08x", l.toPointer(1)));
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @function AddByte
	 * @info Adds a byte to the datawatcher that will be networked to all players
	 * @arguments [[Number]]:index, [ [[Number]]:default ]
	 * @return nil
	 */

	private static JavaFunction AddByte = new JavaFunction() {
		public int invoke(LuaState l) {
			EntityDataManager self = (EntityDataManager) l.checkUserdata(1, EntityDataManager.class, "DataWatcher");
			// TODO: check this out
			self.register(new DataParameter<>((int)l.checkUserdata(2), DataSerializers.BYTE), (byte)l.checkInteger(3));
			return 0;
		}
	};

	/**
	 * @author Jake
	 * @function SetByte
	 * @info Updates a byte that will be networked to all players
	 * @arguments [[Number]]:index, [[Number]]:value
	 * @return nil
	 */

	private static JavaFunction SetByte = new JavaFunction() {
		public int invoke(LuaState l) {
			EntityDataManager self = (EntityDataManager) l.checkUserdata(1, EntityDataManager.class, "DataWatcher");
			//self.updateObject(l.checkInteger(2), (byte) l.checkInteger(3));
			self.set(new DataParameter<>((int)l.checkUserdata(2), DataSerializers.BYTE), (byte)l.checkInteger(3));
			return 0;
		}
	};

	/**
	 * @author Jake
	 * @function GetByte
	 * @info Returns a byte that is networked to all players
	 * @arguments nil
	 * @return [[Number]]
	 */

	private static JavaFunction GetByte = new JavaFunction() {
		public int invoke(LuaState l) {
			EntityDataManager self = (EntityDataManager) l.checkUserdata(1, EntityDataManager.class, "DataWatcher");
			//l.pushNumber(self.getWatchableObjectByte(l.checkInteger(2)));
			l.pushNumber(self.get(new DataParameter<>((int)l.checkUserdata(2), DataSerializers.BYTE)));
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @function AddShort
	 * @info Adds a short to the datawatcher that will be networked to all players
	 * @arguments [[Number]]:index, [ [[Number]]:default ]
	 * @return nil
	 */

	private static JavaFunction AddShort = new JavaFunction() {
		public int invoke(LuaState l) {
			EntityDataManager self = (EntityDataManager) l.checkUserdata(1, EntityDataManager.class, "DataWatcher");
			//self.addObject(l.checkInteger(2), (short) l.checkInteger(3, 0));
			self.register(new DataParameter<>((int)l.checkUserdata(2), DataSerializersExtra.SHORT), (short) l.checkInteger(3, 0));
			return 0;
		}
	};

	/**
	 * @author Jake
	 * @function SetShort
	 * @info Updates a short that will be networked to all players
	 * @arguments [[Number]]:index, [[Number]]:value
	 * @return nil
	 */

	private static JavaFunction SetShort = new JavaFunction() {
		public int invoke(LuaState l) {
			EntityDataManager self = (EntityDataManager) l.checkUserdata(1, EntityDataManager.class, "DataWatcher");
			//self.updateObject(l.checkInteger(2), (short) l.checkInteger(3));
			self.set(new DataParameter<>((int)l.checkUserdata(2), DataSerializersExtra.SHORT), (short) l.checkInteger(3, 0));
			return 0;
		}
	};

	/**
	 * @author Jake
	 * @function GetShort
	 * @info Returns a short that is networked to all players
	 * @arguments nil
	 * @return [[Number]]
	 */

	private static JavaFunction GetShort = new JavaFunction() {
		public int invoke(LuaState l) {
			EntityDataManager self = (EntityDataManager) l.checkUserdata(1, EntityDataManager.class, "DataWatcher");
			//l.pushNumber(self.getWatchableObjectShort(l.checkInteger(2)));
			l.pushNumber(self.get(new DataParameter<>((int)l.checkUserdata(2), DataSerializersExtra.SHORT)));
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @function AddInt
	 * @info Adds an integer to the datawatcher that will be networked to all players
	 * @arguments [[Number]]:index, [ [[Number]]:default ]
	 * @return nil
	 */

	private static JavaFunction AddInt = new JavaFunction() {
		public int invoke(LuaState l) {
			EntityDataManager self = (EntityDataManager) l.checkUserdata(1, EntityDataManager.class, "DataWatcher");
			//self.addObject(l.checkInteger(2), l.checkInteger(3, 0));
			self.register(new DataParameter<>((int)l.checkUserdata(2), DataSerializers.VARINT), (int) l.checkInteger(3, 0));
			return 0;
		}
	};

	/**
	 * @author Jake
	 * @function SetInt
	 * @info Updates an integer that will be networked to all players
	 * @arguments [[Number]]:index, [[Number]]:value
	 * @return nil
	 */

	private static JavaFunction SetInt = new JavaFunction() {
		public int invoke(LuaState l) {
			EntityDataManager self = (EntityDataManager) l.checkUserdata(1, EntityDataManager.class, "DataWatcher");
			//self.updateObject(l.checkInteger(2), l.checkInteger(3));
			self.set(new DataParameter<>((int) l.checkUserdata(2), DataSerializers.VARINT), (int) l.checkInteger(3, 0));
			return 0;
		}
	};

	/**
	 * @author Jake
	 * @function GetInt
	 * @info Returns an integer that is networked to all players
	 * @arguments nil
	 * @return [[Number]]
	 */

	private static JavaFunction GetInt = new JavaFunction() {
		public int invoke(LuaState l) {
			EntityDataManager self = (EntityDataManager) l.checkUserdata(1, EntityDataManager.class, "DataWatcher");
			//l.pushInteger(self.getWatchableObjectInt(l.checkInteger(2)));
			l.pushInteger(self.get(new DataParameter<>((int) l.checkUserdata(2), DataSerializers.VARINT)));
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @function AddFloat
	 * @info Adds a float to the datawatcher that will be networked to all players
	 * @arguments [[Number]]:index, [ [[Number]]:default ]
	 * @return nil
	 */

	private static JavaFunction AddFloat = new JavaFunction() {
		public int invoke(LuaState l) {
			EntityDataManager self = (EntityDataManager) l.checkUserdata(1, EntityDataManager.class, "DataWatcher");
			//self.addObject(l.checkInteger(2), (float) l.checkNumber(3, 0));
			self.register(new DataParameter<>((int)l.checkUserdata(2), DataSerializers.FLOAT), (float) l.checkInteger(3, 0));
			return 0;
		}
	};

	/**
	 * @author Jake
	 * @function SetFloat
	 * @info Updates an float that will be networked to all players
	 * @arguments [[Number]]:index, [[Number]]:value
	 * @return nil
	 */

	private static JavaFunction SetFloat = new JavaFunction() {
		public int invoke(LuaState l) {
			EntityDataManager self = (EntityDataManager) l.checkUserdata(1, EntityDataManager.class, "DataWatcher");
			//self.updateObject(l.checkInteger(2), (float) l.checkNumber(3));
			self.set(new DataParameter<>((int) l.checkUserdata(2), DataSerializers.FLOAT), (float) l.checkInteger(3, 0));
			return 0;
		}
	};

	/**
	 * @author Jake
	 * @function GetFloat
	 * @info Returns a float that is networked to all players
	 * @arguments nil
	 * @return [[Number]]
	 */

	private static JavaFunction GetFloat = new JavaFunction() {
		public int invoke(LuaState l) {
			EntityDataManager self = (EntityDataManager) l.checkUserdata(1, EntityDataManager.class, "DataWatcher");
			//l.pushNumber(self.getWatchableObjectFloat(l.checkInteger(2)));
			l.pushNumber(self.get(new DataParameter<>((int)l.checkUserdata(2), DataSerializers.FLOAT)));
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @function AddString
	 * @info Adds a string to the datawatcher that will be networked to all players
	 * @arguments [[Number]]:index, [ [[String]]:default ]
	 * @return nil
	 */

	private static JavaFunction AddString = new JavaFunction() {
		public int invoke(LuaState l) {
			EntityDataManager self = (EntityDataManager) l.checkUserdata(1, EntityDataManager.class, "DataWatcher");
			//self.addObject(l.checkInteger(2), l.checkString(3, ""));
			self.register(new DataParameter<>(l.checkInteger(2), DataSerializers.STRING), l.checkString(3, ""));
			return 0;
		}
	};

	/**
	 * @author Jake
	 * @function SetString
	 * @info Updates a string that will be networked to all players
	 * @arguments [[Number]]:index, [[String]]:value
	 * @return nil
	 */

	private static JavaFunction SetString = new JavaFunction() {
		public int invoke(LuaState l) {
			EntityDataManager self = (EntityDataManager) l.checkUserdata(1, EntityDataManager.class, "DataWatcher");
			//self.updateObject(l.checkInteger(2), l.checkString(3));
			self.set(new DataParameter<>(l.checkInteger(2), DataSerializers.STRING), l.checkString(3));
			return 0;
		}
	};

	/**
	 * @author Jake
	 * @function GetString
	 * @info Returns a string that is networked to all players
	 * @arguments nil
	 * @return [[String]]
	 */

	private static JavaFunction GetString = new JavaFunction() {
		public int invoke(LuaState l) {
			EntityDataManager self = (EntityDataManager) l.checkUserdata(1, EntityDataManager.class, "DataWatcher");
			//l.pushString(self.getWatchableObjectString(l.checkInteger(2)));
			l.pushString(self.get(new DataParameter<>(l.checkInteger(2), DataSerializers.STRING)));
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @function AddItemStack
	 * @info Adds an ItemStack to the datawatcher that will be networked to all players
	 * @arguments [[Number]]:index, [[ItemStack]]:default
	 * @return nil
	 */

	private static JavaFunction AddItemStack = new JavaFunction() {
		public int invoke(LuaState l) {
			EntityDataManager self = (EntityDataManager) l.checkUserdata(1, EntityDataManager.class, "DataWatcher");
			//self.addObject(l.checkInteger(2), l.checkUserdata(3, ItemStack.class, "ItemStack"));
			self.register(new DataParameter<ItemStack>((int)l.checkUserdata(2), DataSerializers.ITEM_STACK), (ItemStack) l.checkUserdata(3, ItemStack.class, "ItemStack"));
			return 0;
		}
	};

	/**
	 * @author Jake
	 * @function SetItemStack
	 * @info Updates an ItemStack that will be networked to all players
	 * @arguments [[Number]]:index, [[ItemStack]]:value
	 * @return nil
	 */

	private static JavaFunction SetItemStack = new JavaFunction() {
		public int invoke(LuaState l) {
			EntityDataManager self = (EntityDataManager) l.checkUserdata(1, EntityDataManager.class, "DataWatcher");
			//self.updateObject(l.checkInteger(2), l.checkUserdata(3, ItemStack.class, "ItemStack"));
			self.set(new DataParameter<ItemStack>(l.checkInteger(2), DataSerializers.ITEM_STACK), (ItemStack) l.checkUserdata(3, ItemStack.class, "ItemStack"));
			return 0;
		}
	};

	/**
	 * @author Jake
	 * @function GetItemStack
	 * @info Returns an ItemStack that is networked to all players
	 * @arguments nil
	 * @return [[ItemStack]]
	 */

	private static JavaFunction GetItemStack = new JavaFunction() {
		public int invoke(LuaState l) {
			EntityDataManager self = (EntityDataManager) l.checkUserdata(1, EntityDataManager.class, "DataWatcher");
			//ItemStack item = self.getWatchableObjectItemStack(l.checkInteger(2));
			//l.pushUserdataWithMeta(item, "ItemStack");
			l.pushUserdataWithMeta(self.get(new DataParameter<>((int)l.checkInteger(2), DataSerializers.ITEM_STACK)), "ItemStack");
			return 1;
		}
	};

	public static void Init(final LuaCraftState l) {
		l.newMetatable("DataWatcher");
		{
			l.pushJavaFunction(__tostring);
			l.setField(-2, "__tostring");

			LuaUserdata.SetupBasicMeta(l);
			LuaUserdata.SetupMeta(l, false);

			l.newMetatable("Object");
			l.setField(-2, "__basemeta");

			l.pushJavaFunction(AddByte);
			l.setField(-2, "AddByte");
			l.pushJavaFunction(SetByte);
			l.setField(-2, "SetByte");
			l.pushJavaFunction(GetByte);
			l.setField(-2, "GetByte");

			l.pushJavaFunction(AddShort);
			l.setField(-2, "AddShort");
			l.pushJavaFunction(SetShort);
			l.setField(-2, "SetShort");
			l.pushJavaFunction(GetShort);
			l.setField(-2, "GetShort");

			l.pushJavaFunction(AddInt);
			l.setField(-2, "AddInt");
			l.pushJavaFunction(SetInt);
			l.setField(-2, "SetInt");
			l.pushJavaFunction(GetInt);
			l.setField(-2, "GetInt");

			l.pushJavaFunction(AddFloat);
			l.setField(-2, "AddFloat");
			l.pushJavaFunction(SetFloat);
			l.setField(-2, "SetFloat");
			l.pushJavaFunction(GetFloat);
			l.setField(-2, "GetFloat");

			l.pushJavaFunction(AddString);
			l.setField(-2, "AddString");
			l.pushJavaFunction(SetString);
			l.setField(-2, "SetString");
			l.pushJavaFunction(GetString);
			l.setField(-2, "GetString");

			l.pushJavaFunction(AddItemStack);
			l.setField(-2, "AddItemStack");
			l.pushJavaFunction(SetItemStack);
			l.setField(-2, "SetItemStack");
			l.pushJavaFunction(GetItemStack);
			l.setField(-2, "GetItemStack");
		}
		l.pop(1);

	}

	/**
	 * Probably could just use DataSerializers.VARIANT but whatever
	 */
	public static class DataSerializersExtra
	{
		public static final DataSerializer<Short> SHORT = new DataSerializer<Short>()
		{
			public void write(PacketBuffer buf, Short value)
			{
				buf.writeShort(value.shortValue());
			}
			public Short read(PacketBuffer buf)
			{
				return Short.valueOf(buf.readShort());
			}
			public DataParameter<Short> createKey(int id)
			{
				return new DataParameter(id, this);
			}
			@Override
			public Short copyValue(Short value) {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}
}
