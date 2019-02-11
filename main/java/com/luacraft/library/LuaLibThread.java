package com.luacraft.library;

import java.util.HashMap;

import com.luacraft.LuaCraft;
import com.luacraft.LuaCraftState;
import com.luacraft.classes.LuaJavaChannel;
import com.luacraft.classes.LuaJavaScriptThread;
import com.luacraft.classes.LuaJavaThread;
import com.naef.jnlua.JavaFunction;
import com.naef.jnlua.LuaState;

public class LuaLibThread {

	public static HashMap<String, LuaJavaThread> threadScripts = new HashMap<String, LuaJavaThread>();

	public static void interruptActiveThreads() {
		for (LuaJavaThread thread : threadScripts.values())
			thread.interrupt();
	}

	/**
	 * @author Jake
	 * @library thread
	 * @function NewThread
	 * @info Load a lua file into a separate thread
	 * @arguments [[String]]:filename
	 * @return [[Thread]]:thread
	 */

	private static JavaFunction NewThread = new JavaFunction() {
		public int invoke(LuaState l) {
			String file = l.checkString(1);

			LuaJavaThread thread = threadScripts.get(file);

			if (thread == null) {
				thread = new LuaJavaScriptThread((LuaCraftState) l, file);
				thread.setName(file);
				threadScripts.put(file, thread);
			}

			l.pushUserdataWithMeta(thread, "Thread");
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @library thread
	 * @function GetThread
	 * @info Returns the current active thread the script is using
	 * @arguments nil
	 * @return [[Thread]]:thread
	 */

	private static JavaFunction GetThread = new JavaFunction() {
		public int invoke(LuaState l) {
			l.pushUserdataWithMeta((LuaJavaThread) Thread.currentThread(), "Thread");
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @library thread
	 * @function GetChannel
	 * @info Create a new channel that can be shared between threads
	 * @arguments [[String]]:name
	 * @return [[Channel]]:channel
	 */

	private static JavaFunction GetChannel = new JavaFunction() {
		public int invoke(LuaState l) {
			synchronized (LuaCraft.threadChannels) {
				String name = l.checkString(1);
				LuaJavaChannel channel = LuaCraft.threadChannels.get(name);

				if (channel == null) {
					channel = new LuaJavaChannel();
					LuaCraft.threadChannels.put(name, channel);
				}

				l.pushUserdataWithMeta(channel, "Channel");
				return 1;
			}
		}
	};

	public static void Init(LuaState l) {
		l.newTable();
		{
			l.pushJavaFunction(NewThread);
			l.setField(-2, "NewThread");
			l.pushJavaFunction(GetThread);
			l.setField(-2, "GetThread");
			l.pushJavaFunction(GetChannel);
			l.setField(-2, "GetChannel");
		}
		l.setGlobal("thread");
	}
}