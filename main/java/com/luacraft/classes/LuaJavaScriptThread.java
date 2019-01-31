package com.luacraft.classes;

import com.luacraft.LuaClient;
import com.luacraft.LuaCraftState;
import com.luacraft.LuaServer;
import com.naef.jnlua.LuaRuntimeException;

public class LuaJavaScriptThread extends LuaJavaThread {
	private LuaCraftState l;
	private LuaCraftState state;
	private String file;

	public LuaJavaScriptThread(LuaCraftState s, String f) {
		state = s;
		file = f;
	}

	public void run() {
		if (state.getSide().isClient()) {
			l = new LuaClient();
			synchronized (l) {
				l.setRunningSide(state.getRunningSide());
				((LuaClient) l).initialize(false);
			}
		} else {
			l = new LuaServer();
			synchronized (l) {
				l.setRunningSide(state.getRunningSide());
				((LuaServer) l).initialize(false);
			}
		}

		synchronized (l) {
			if (l.isOpen() && file != null) {
				try {
					l.includeFile(file);
				} catch (LuaRuntimeException e) {
					l.handleLuaRuntimeError(e);
				} finally {
					l.close();
				}
			}
		}
	}
}