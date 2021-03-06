package com.luacraft.library.server;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import com.luacraft.LuaCraft;
import com.luacraft.LuaCraftState;
import com.luacraft.LuaUserdata;
import com.luacraft.classes.LuaCache;
import com.naef.jnlua.JavaFunction;
import com.naef.jnlua.LuaRuntimeException;
import com.naef.jnlua.LuaState;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.PropertyManager;
import net.minecraft.world.World;

public class LuaGlobals {
	private static MinecraftServer server = null;

	private static JavaFunction AddCSLuaFile = new JavaFunction() {
		public int invoke(LuaState l) {
			String fileName = l.checkString(1);

			if (!fileName.endsWith(".lua"))
				throw new LuaRuntimeException("File must be a Lua file");
			
			try {
				LuaCache.addCSLuaFile(fileName);
			} catch (NoSuchAlgorithmException | IOException | SQLException e) {
				throw new LuaRuntimeException("Cannot AddCSLuaFile " + e.getLocalizedMessage());
			}
			return 0;
		}
	};

	/**
	 * @author Jake
	 * @function PropertyManager
	 * @info Returns a new [[PropertyManager]] for the given file
	 * @arguments nil
	 * @return [[PropertyManager]]:manager
	 */

	private static JavaFunction PropertyManager = new JavaFunction() {
		public int invoke(LuaState l) {
			File file = new File(LuaCraft.rootDir, l.checkString(1));
			PropertyManager property = new PropertyManager(file);
			l.pushUserdataWithMeta(property, "PropertyManager");
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @function World
	 * @info Get the world for the given dimensionID
	 * @arguments [[Number]]:dimension
	 * @return [[World]]:world
	 */

	private static JavaFunction World = new JavaFunction() {
		public int invoke(LuaState l) {
			World world = server.worlds[l.checkInteger(1, 0)];
			LuaUserdata.PushUserdata(l, world);
			return 1;
		}
	};

	public static void Init(final LuaCraftState l) {
		server = l.getServer();

		l.pushJavaFunction(AddCSLuaFile);
		l.setGlobal("AddCSLuaFile");
		l.pushJavaFunction(PropertyManager);
		l.setGlobal("PropertyManager");
		l.pushJavaFunction(World);
		l.setGlobal("World");
	}
}
