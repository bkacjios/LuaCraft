package com.luacraft.meta;

import com.luacraft.LuaCraftState;
import com.luacraft.LuaUserdata;
import com.naef.jnlua.JavaFunction;
import com.naef.jnlua.LuaState;

import net.minecraft.util.EntityDamageSource;

public class LuaEntityDamageSource {

	private static JavaFunction __tostring = new JavaFunction() {
		public int invoke(LuaState l) {
			EntityDamageSource self = (EntityDamageSource) l.checkUserdata(1, EntityDamageSource.class,
					"EntityDamageSource");
			l.pushString(String.format("EntityDamageSource: 0x%08x", l.toPointer(1)));
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @function IsDifficultyScaled
	 * @info Return whether this damage source will have its damage amount scaled based on the current difficulty.
	 * @arguments nil
	 * @return [[Boolean]]:scaled
	 */

	private static JavaFunction IsDifficultyScaled = new JavaFunction() {
		public int invoke(LuaState l) {
			EntityDamageSource self = (EntityDamageSource) l.checkUserdata(1, EntityDamageSource.class,
					"EntityDamageSource");
			LuaUserdata.PushUserdata(l, self.isDifficultyScaled());
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @function IsThornsDamage
	 * @info Return whether this damage source was caused by thorns.
	 * @arguments nil
	 * @return [[Boolean]]:thorns
	 */

	private static JavaFunction IsThornsDamage = new JavaFunction() {
		public int invoke(LuaState l) {
			EntityDamageSource self = (EntityDamageSource) l.checkUserdata(1, EntityDamageSource.class,
					"EntityDamageSource");
			LuaUserdata.PushUserdata(l, self.getIsThornsDamage());
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @function SetIsThornsDamage
	 * @info Set this damage source to deal thorn damage.
	 * @arguments nil
	 * @return [[DamageSource]]:this
	 */

	private static JavaFunction SetIsThornsDamage = new JavaFunction() {
		public int invoke(LuaState l) {
			EntityDamageSource self = (EntityDamageSource) l.checkUserdata(1, EntityDamageSource.class,
					"EntityDamageSource");
			LuaUserdata.PushUserdata(l, self.setIsThornsDamage());
			return 1;
		}
	};

	public static void Init(final LuaCraftState l) {
		l.newMetatable("EntityDamageSource");
		{
			l.pushJavaFunction(__tostring);
			l.setField(-2, "__tostring");

			LuaUserdata.SetupBasicMeta(l);
			LuaUserdata.SetupMeta(l, false);

			l.newMetatable("DamageSource");
			l.setField(-2, "__basemeta");

			l.pushJavaFunction(IsDifficultyScaled);
			l.setField(-2, "IsDifficultyScaled");
			l.pushJavaFunction(IsThornsDamage);
			l.setField(-2, "IsThornsDamage");
			l.pushJavaFunction(SetIsThornsDamage);
			l.setField(-2, "SetIsThornsDamage");
		}
		l.pop(1);
	}
}
