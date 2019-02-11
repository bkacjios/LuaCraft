package com.luacraft.meta;

import com.luacraft.LuaCraftState;
import com.luacraft.LuaUserdata;
import com.luacraft.classes.LuaJavaBlock;
import com.luacraft.classes.Vector;
import com.naef.jnlua.JavaFunction;
import com.naef.jnlua.LuaState;
import com.naef.jnlua.LuaType;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class LuaBlock {
	private static JavaFunction __tostring = new JavaFunction() {
		public int invoke(LuaState l) {
			LuaJavaBlock self = (LuaJavaBlock) l.checkUserdata(1, LuaJavaBlock.class, "Block");

			int id = Block.getIdFromBlock(self.getBlock());
			int meta = Block.getStateId(self.blockWorld.getBlockState(self.getPos()));

			l.pushString(String.format("%s [%d, %d, %d][%d %d]", self.getBlock().getLocalizedName(), self.x, self.z,
					self.y, id, meta));
			return 1;
		}
	};

	private static JavaFunction __eq = new JavaFunction() {
		public int invoke(LuaState l) {
			LuaJavaBlock self = (LuaJavaBlock) l.checkUserdata(1, LuaJavaBlock.class, "Block");
			LuaJavaBlock other = (LuaJavaBlock) l.checkUserdata(2, LuaJavaBlock.class, "Block");

			l.pushBoolean(
					self.x == other.x && self.y == other.y && self.z == other.z && self.blockWorld == other.blockWorld);
			return 1;
		}
	};

	/**
	 * @author Gregor
	 * @function GetPos
	 * @info Return the position
	 * @arguments nil
	 * @return [[Vector]]:pos
	 */

	private static JavaFunction GetPos = new JavaFunction() {
		public int invoke(LuaState l) {
			LuaJavaBlock self = (LuaJavaBlock) l.checkUserdata(1, LuaJavaBlock.class, "Block");

			Vector pos = new Vector(self.x, self.z, self.y);
			pos.push(l);
			return 1;
		}
	};

	/**
	 * @author Gregor
	 * @function SetPos
	 * @info Set the position
	 * @arguments [[Vector]]:vec OR [ [[Number]]:x, [[Number]]:y, [[Number]]:z ]
	 * @return nil
	 */

	private static JavaFunction SetPos = new JavaFunction() {
		public int invoke(LuaState l) {
			LuaJavaBlock self = (LuaJavaBlock) l.checkUserdata(1, LuaJavaBlock.class, "Block");

			int x, y, z;

			if (l.isUserdata(2, Vector.class)) {
				Vector thisVec = (Vector) l.checkUserdata(2, Vector.class, "Vector");
				x = (int) thisVec.x;
				y = (int) thisVec.y;
				z = (int) thisVec.z;
			} else {
				x = l.checkInteger(2, 0);
				y = l.checkInteger(3, 0);
				z = l.checkInteger(4, 0);
			}

			IBlockState meta = self.blockWorld.getBlockState(self.getPos());
			self.blockWorld.setBlockToAir(self.getPos());

			self.x = (int) x;
			self.y = (int) z;
			self.z = (int) y;

			self.blockWorld.setBlockState(self.getPos(), meta, 3);
			return 0;
		}
	};

	/**
	 * @author Gregor
	 * @function GetID
	 * @info Return the block id
	 * @arguments nil
	 * @return [[Number]]:id
	 */

	private static JavaFunction GetID = new JavaFunction() {
		public int invoke(LuaState l) {
			LuaJavaBlock self = (LuaJavaBlock) l.checkUserdata(1, LuaJavaBlock.class, "Block");
			int id = Block.getIdFromBlock(self.state.getBlock());
			l.pushInteger(id);
			return 1;
		}
	};

	/**
	 * @author Gregor
	 * @function SetID
	 * @info Set the block id
	 * @arguments [[Number]]:id
	 * @return nil
	 */

	private static JavaFunction SetID = new JavaFunction() {
		public int invoke(LuaState l) {
			LuaJavaBlock self = (LuaJavaBlock) l.checkUserdata(1, LuaJavaBlock.class, "Block");
			int id = l.checkInteger(2, 0);
			IBlockState state = Block.getStateById(id);
			self.blockWorld.setBlockState(self.getPos(), state);
			return 0;
		}
	};

	/**
	 * @author Gregor
	 * @function GetMeta
	 * @info Return the block meta value
	 * @arguments nil
	 * @return [[Number]]:meta
	 */

	private static JavaFunction GetMeta = new JavaFunction() {
		public int invoke(LuaState l) {
			LuaJavaBlock self = (LuaJavaBlock) l.checkUserdata(1, LuaJavaBlock.class, "Block");
			l.pushInteger(self.getBlock().getMetaFromState((IBlockState) self.getState()));
			return 1;
		}
	};

	/**
	 * @author Gregor
	 * @function SetMeta
	 * @info Set the block meta value
	 * @arguments [[Number]]:meta
	 * @return nil
	 */

	private static JavaFunction SetMeta = new JavaFunction() {
		public int invoke(LuaState l) {
			LuaJavaBlock self = (LuaJavaBlock) l.checkUserdata(1, LuaJavaBlock.class, "Block");
			int metaID = l.checkInteger(2, 0);
			IBlockState meta = self.getBlock().getStateFromMeta(metaID);
			self.blockWorld.setBlockState(self.getPos(), meta);
			return 0;
		}
	};

	/**
	 * @author Gregor
	 * @function GetClass
	 * @info Return the classname
	 * @arguments nil
	 * @return [[String]]:class
	 */

	private static JavaFunction GetClass = new JavaFunction() {
		public int invoke(LuaState l) {
			LuaJavaBlock self = (LuaJavaBlock) l.checkUserdata(1, LuaJavaBlock.class, "Block");
			l.pushString(self.getBlock().getUnlocalizedName());
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @function GetName
	 * @info Return the human readable name
	 * @arguments nil
	 * @return [[String]]:name
	 */

	private static JavaFunction GetName = new JavaFunction() {
		public int invoke(LuaState l) {
			LuaJavaBlock self = (LuaJavaBlock) l.checkUserdata(1, LuaJavaBlock.class, "Block");
			l.pushString(self.getBlock().getLocalizedName());
			return 1;
		}
	};

	/**
	 * @author Matt
	 * @function GetBiome
	 * @info Get the biome name the block rests in.
	 * @arguments nil
	 * @return [[String]]:biome
	 */

	private static JavaFunction GetBiome = new JavaFunction() {
		public int invoke(LuaState l) {
			LuaJavaBlock self = (LuaJavaBlock) l.checkUserdata(1, LuaJavaBlock.class, "Block");
			l.pushString(self.blockWorld.getBiomeForCoordsBody(self.getPos()).getBiomeName());
			return 1;
		}
	};

	/**
	 * @author Gregor
	 * @function Break
	 * @info Break the block
	 * @arguments [[Number]]:chance
	 * @return nil
	 */

	private static JavaFunction Break = new JavaFunction() {
		public int invoke(LuaState l) {
			LuaJavaBlock self = (LuaJavaBlock) l.checkUserdata(1, LuaJavaBlock.class, "Block");

			self.getBlock().dropBlockAsItemWithChance(self.blockWorld, self.getPos(), (IBlockState) self.getState(),
					(float) l.checkNumber(2, 1), 0);
			self.blockWorld.setBlockToAir(self.getPos());
			return 0;
		}
	};

	/**
	 * @author Jake
	 * @function DropItem
	 * @info Drop an item in place of the block
	 * @arguments [[ItemStack]]:item
	 * @return nil
	 */

	private static JavaFunction DropItem = new JavaFunction() {
		public int invoke(LuaState l) {
			LuaJavaBlock self = (LuaJavaBlock) l.checkUserdata(1, LuaJavaBlock.class, "Block");
			ItemStack item = (ItemStack) l.checkUserdata(2, ItemStack.class, "ItemStack");
			self.getBlock().spawnAsEntity(self.blockWorld, self.getPos(), item);
			return 0;
		}
	};

	/**
	 * @author Jake
	 * @function GetContainer
	 * @info Get's the container object for the block
	 * @arguments nil
	 * @return [[Container]]:inv
	 */

	private static JavaFunction GetContainer = new JavaFunction() {
		public int invoke(LuaState l) {
			LuaJavaBlock self = (LuaJavaBlock) l.checkUserdata(1, LuaJavaBlock.class, "Block");
			TileEntity tile = self.blockWorld.getTileEntity(self.getPos());
			if (tile instanceof IInventory) {
				l.pushUserdataWithMeta(tile, "Container");
				return 1;
			}
			return 0;
		}
	};

	/**
	 * @author Jake
	 * @function GetSignText
	 * @info Get's a table of each line on the sign
	 * @arguments nil
	 * @return [[Table]]:text
	 */

	private static JavaFunction GetSignText = new JavaFunction() {
		public int invoke(LuaState l) {
			LuaJavaBlock self = (LuaJavaBlock) l.checkUserdata(1, LuaJavaBlock.class, "Block");
			TileEntitySign tile = (TileEntitySign) self.blockWorld.getTileEntity(self.getPos());

			l.newTable();

			for (int i = 1; i < tile.signText.length; i++) {
				l.pushInteger(i);
				l.pushString(tile.signText[i].getUnformattedText());
				l.setTable(-3);
			}
			return 1;
		}
	};

	private static String getSignTextFromTable(LuaState l) {
		StringBuilder line = new StringBuilder();

		l.pushNil();

		while (l.next(-2)) {
			StringBuilder out = new StringBuilder();

			if (l.type(-1) == LuaType.NUMBER) {
				TextFormatting format = TextFormatting.values()[l.toInteger(-1)];
				out.append(format);
			} else
				out.append(l.toString(-1));

			line.append(out);

			l.pop(1); // Pop the value, keep the key
		}

		return line.toString();
	}

	/**
	 * @author Jake
	 * @function SetSignText
	 * @info Get's a table of each line on the sign
	 * @arguments [[Table]]:text
	 * @return nil
	 */

	private static JavaFunction SetSignText = new JavaFunction() {
		public int invoke(LuaState l) {
			LuaJavaBlock self = (LuaJavaBlock) l.checkUserdata(1, LuaJavaBlock.class, "Block");
			l.checkType(2, LuaType.TABLE);

			TileEntitySign tile = (TileEntitySign) self.blockWorld.getTileEntity(self.getPos());

			l.pushNil();

			while (l.next(2)) {
				StringBuilder out = new StringBuilder();

				if (l.isTable(-1))
					out.append(getSignTextFromTable(l));
				else
					out.append(l.toString(-1));

				tile.signText[l.toInteger(-2)] = new TextComponentString(out.toString());

				l.pop(1); // Pop the value, keep the key
			}
			return 0;
		}
	};

	public static void Init(final LuaCraftState l) {
		l.newMetatable("Block");
		{
			l.pushJavaFunction(__tostring);
			l.setField(-2, "__tostring");

			LuaUserdata.SetupBasicMeta(l);
			LuaUserdata.SetupMeta(l, true);

			l.pushJavaFunction(__eq);
			l.setField(-2, "__eq");

			l.newMetatable("Object");
			l.setField(-2, "__basemeta");

			l.pushJavaFunction(GetPos);
			l.setField(-2, "GetPos");
			l.pushJavaFunction(SetPos);
			l.setField(-2, "SetPos");
			l.pushJavaFunction(GetID);
			l.setField(-2, "GetID");
			l.pushJavaFunction(SetID);
			l.setField(-2, "SetID");
			l.pushJavaFunction(GetMeta);
			l.setField(-2, "GetMeta");
			l.pushJavaFunction(GetMeta);
			l.setField(-2, "GetMetaData");
			l.pushJavaFunction(SetMeta);
			l.setField(-2, "SetMeta");
			l.pushJavaFunction(SetMeta);
			l.setField(-2, "SetMetaData");
			l.pushJavaFunction(GetClass);
			l.setField(-2, "GetClass");
			l.pushJavaFunction(GetName);
			l.setField(-2, "GetName");
			l.pushJavaFunction(GetBiome);
			l.setField(-2, "GetBiome");
			l.pushJavaFunction(Break);
			l.setField(-2, "Break");
			l.pushJavaFunction(DropItem);
			l.setField(-2, "DropItem");
			l.pushJavaFunction(GetContainer);
			l.setField(-2, "GetContainer");
			l.pushJavaFunction(GetSignText);
			l.setField(-2, "GetSignText");
			l.pushJavaFunction(SetSignText);
			l.setField(-2, "SetSignText");
		}
		l.pop(1);

	}
}