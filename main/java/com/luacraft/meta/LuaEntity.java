package com.luacraft.meta;

import com.luacraft.LuaCraftState;
import com.luacraft.LuaUserdata;
import com.luacraft.classes.Angle;
import com.luacraft.classes.Vector;
import com.naef.jnlua.JavaFunction;
import com.naef.jnlua.LuaState;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

public class LuaEntity {
	public static JavaFunction __tostring = new JavaFunction() {
		public int invoke(LuaState l) {
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			GetClass.invoke(l);
			l.pushString(String.format("Entity [%d][%s]", self.getEntityId(), l.toString(-1)));
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @function AttackFrom
	 * @info Attack an entity using a [[DamageSource]]
	 * @arguments [[DamageSource]]:source, [[Number]]:damage
	 * @return nil
	 */

	private static JavaFunction AttackFrom = new JavaFunction() {
		public int invoke(LuaState l) {
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			DamageSource damage = (DamageSource) l.checkUserdata(2, DamageSource.class, "DamageSource");
			self.attackEntityFrom(damage, (float) l.checkNumber(3));
			return 0;
		}
	};

	/**
	 * @author Jake
	 * @function GetUniqueID
	 * @info Get the entities unique ID that will be persistent. If used on a player in online-mode, it will return the Minecraft UUID for the player
	 * @arguments nil
	 * @return [[String]]:id
	 */

	private static JavaFunction GetUniqueID = new JavaFunction() {
		public int invoke(LuaState l) {
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			l.pushString(self.getUniqueID().toString());
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @function IsAlive
	 * @info Returns if the entity is alive
	 * @arguments nil
	 * @return [[Boolean]]:alive
	 */

	private static JavaFunction IsAlive = new JavaFunction() {
		public int invoke(LuaState l) {
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			l.pushBoolean(self.isEntityAlive());
			return 1;
		}
	};

	/**
	 * @author Gregor
	 * @function IsOnGround
	 * @info Check if the entity stands on ground
	 * @arguments nil
	 * @return [[Boolean]]:onground
	 */

	private static JavaFunction IsOnGround = new JavaFunction() {
		public int invoke(LuaState l) {
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			l.pushBoolean(self.onGround);
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @function GetAngles
	 * @info Returns the entity angles
	 * @arguments nil
	 * @return [[Angle]]:ang
	 */

	private static JavaFunction GetAngles = new JavaFunction() {
		public int invoke(LuaState l) {
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			Angle ang = new Angle(self.rotationPitch, self.rotationYaw);
			ang.push(l);
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @function SetAngles
	 * @info Sets the entity angles
	 * @arguments [[Angle]]:ang
	 * @return nil
	 */

	private static JavaFunction SetAngles = new JavaFunction() {
		public int invoke(LuaState l) {
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			Angle ang = (Angle) l.checkUserdata(2, Angle.class, "Angle");
			self.setRotation((float) ang.p, (float) ang.y);
			return 0;
		}
	};

	/**
	 * @author Gregor
	 * @function GetPos
	 * @info Returns the entity position
	 * @arguments nil
	 * @return [[Vector]]:pos
	 */

	private static JavaFunction GetPos = new JavaFunction() {
		public int invoke(LuaState l) {
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			Vector pos = new Vector(self.posX, self.posZ, self.posY);
			pos.push(l);
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @function GetLastPos
	 * @info Returns the entities location from the previous tick
	 * @arguments nil
	 * @return [[Vector]]:pos
	 */

	private static JavaFunction GetLastPos = new JavaFunction() {
		public int invoke(LuaState l) {
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			Vector pos = new Vector(self.lastTickPosX, self.lastTickPosZ, self.lastTickPosY);
			pos.push(l);
			return 1;
		}
	};

	/**
	 * @author Gregor
	 * @function SetPos
	 * @info Sets the entity position
	 * @arguments [[Vector]]:pos
	 * @return nil
	 */

	private static JavaFunction SetPos = new JavaFunction() {
		public int invoke(LuaState l) {
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			Vector pos = (Vector) l.checkUserdata(2, Vector.class, "Vector");
			self.setPosition(pos.x, pos.z, pos.y);
			return 0;
		}
	};

	/**
	 * @author Gregor
	 * @function IsBurning
	 * @info Check if the entity is burning
	 * @arguments nil
	 * @return [[Boolean]]:burning
	 */

	private static JavaFunction IsBurning = new JavaFunction() {
		public int invoke(LuaState l) {
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			l.pushBoolean(self.isBurning());
			return 1;
		}
	};

	/**
	 * @author Gregor
	 * @function Ignite
	 * @info Set the entity on fire
	 * @arguments [ [[Number]]:duration ]
	 * @return nil
	 */

	private static JavaFunction Ignite = new JavaFunction() {
		public int invoke(LuaState l) {
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			self.setFire(l.checkInteger(2, 15));
			return 0;
		}
	};

	/**
	 * @author Gregor
	 * @function Extinguish
	 * @info Extinguish the entity
	 * @arguments nil
	 * @return nil
	 */

	private static JavaFunction Extinguish = new JavaFunction() {
		public int invoke(LuaState l) {
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			self.setFire(0);
			return 0;
		}
	};

	/**
	 * @author Gregor
	 * @function SetNoClip
	 * @info Set the entity into noclip mode
	 * @arguments [[Boolean]]:noclip
	 * @return nil
	 */

	private static JavaFunction SetNoClip = new JavaFunction() {
		public int invoke(LuaState l) {
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			self.noClip = l.checkBoolean(2);
			return 0;
		}
	};

	/**
	 * @author Gregor
	 * @function GetNoClip
	 * @info Get whether or not the entity is in noclip mode
	 * @arguments nil
	 * @return [[Boolean]]:noclipping
	 */

	private static JavaFunction GetNoClip = new JavaFunction() {
		public int invoke(LuaState l) {
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			l.pushBoolean(self.noClip);
			return 1;
		}
	};

	/**
	 * @author Gregor
	 * @function IsMounted
	 * @info Check if the entity is mounted by another entity
	 * @arguments nil
	 * @return [[Boolean]]:mounted
	 */

	private static JavaFunction IsMounted = new JavaFunction() {
		public int invoke(LuaState l) {
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			l.pushBoolean(self.isRiding());
			return 1;
		}
	};

	/**
	 * @author Gregor
	 * @function SetMount
	 * @info Mount the entity to another entity
	 * @arguments [[Entity]]:ent
	 * @return nil
	 */

	private static JavaFunction SetMount = new JavaFunction() {
		public int invoke(LuaState l) {
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			Entity other = (Entity) l.checkUserdata(2, Entity.class, "Entity");
			self.startRiding(other);
			return 0;
		}
	};

	/**
	 * @author Gregor
	 * @function GetMount
	 * @info Get the entity that is being ridden
	 * @arguments nil
	 * @return [[Entity]]:riding
	 */

	private static JavaFunction GetMount = new JavaFunction() {
		public int invoke(LuaState l) {
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			l.pushUserdataWithMeta(self.getRidingEntity(), "Entity");
			return 1;
		}
	};

	/**
	 * @author Gregor
	 * @function IsSneaking
	 * @info Check if the entity is sneaking
	 * @arguments nil
	 * @return [[Boolean]]:sneak
	 */

	private static JavaFunction IsSneaking = new JavaFunction() {
		public int invoke(LuaState l) {
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			l.pushBoolean(self.isSneaking());
			return 1;
		}
	};

	/**
	 * @author Gregor
	 * @function IsInWater
	 * @info Check if the entity is in water
	 * @arguments nil
	 * @return [[Boolean]]:inwater
	 */

	private static JavaFunction IsInWater = new JavaFunction() {
		public int invoke(LuaState l) {
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			l.pushBoolean(self.isInWater());
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @function IsWet
	 * @info Check if the entity is wet
	 * @arguments nil
	 * @return [[Boolean]]:wet
	 */

	private static JavaFunction IsWet = new JavaFunction() {
		public int invoke(LuaState l) {
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			l.pushBoolean(self.isWet());
			return 1;
		}
	};

	/**
	 * @author Gregor
	 * @function GetAir
	 * @info Return the entitys air volume
	 * @arguments nil
	 * @return [[Number]]:air
	 */

	private static JavaFunction GetAir = new JavaFunction() {
		public int invoke(LuaState l) {
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			l.pushInteger(self.getAir());
			return 1;
		}
	};

	/**
	 * @author Gregor
	 * @function SetAir
	 * @info Set the entitys air volume
	 * @arguments [[Number]]:air
	 * @return nil
	 */

	private static JavaFunction SetAir = new JavaFunction() {
		public int invoke(LuaState l) {
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			self.setAir(l.checkInteger(2));
			return 0;
		}
	};

	/**
	 * @author Jake
	 * @function GetFireResistance
	 * @info The amount of ticks you have to stand inside in fire before be set on fire
	 * @arguments nil
	 * @return [[Number]]:res
	 */

	private static JavaFunction GetFireResistance = new JavaFunction() {
		public int invoke(LuaState l) {
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			l.pushInteger(1); //self.getFireImmuneTicks();
			((LuaCraftState) l).depricated();
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @function SetFireResistance
	 * @info Sets the entitys resistance to fire
	 * @arguments [[Number]]:resistance
	 * @return nil
	 */

	private static JavaFunction SetFireResistance = new JavaFunction() {
		public int invoke(LuaState l) {
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			//self.fireResistance = l.checkInteger(2);
			((LuaCraftState) l).depricated();
			return 0;
		}
	};

	/**
	 * @author Gregor
	 * @function GetEyePos
	 * @info Returns the entitys eye position
	 * @arguments nil
	 * @return [[Vector]]:pos
	 */

	private static JavaFunction GetEyePos = new JavaFunction() {
		public int invoke(LuaState l) {
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			Vector pos = new Vector(self.posX, self.posZ, self.posY + self.getEyeHeight());
			pos.push(l);
			return 1;
		}
	};

	/**
	 * @author Gregor
	 * @function GetEyeHeight
	 * @info Returns the entitys eye height
	 * @arguments nil
	 * @return [[Number]]:height
	 */

	private static JavaFunction GetEyeHeight = new JavaFunction() {
		public int invoke(LuaState l) {
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			l.pushNumber(self.getEyeHeight());
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @function GetVelocity
	 * @info Returns the entitys current velocity
	 * @arguments nil
	 * @return [[Vector]]:vel
	 */

	private static JavaFunction GetVelocity = new JavaFunction() {
		public int invoke(LuaState l) {
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			Vector pos = new Vector(self.motionX, self.motionZ, self.motionY);
			pos.push(l);
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @function SetVelocity
	 * @info Sets the entitys current velocity
	 * @arguments [[Vector]]:vel
	 * @return nil
	 */

	private static JavaFunction SetVelocity = new JavaFunction() {
		public int invoke(LuaState l) {
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			Vector vel = (Vector) l.checkUserdata(2, Vector.class, "Vector");
			self.motionX = vel.x;
			self.motionY = vel.z;
			self.motionZ = vel.y;
			return 0;
		}
	};

	/**
	 * @author Gregor
	 * @function AddVelocity
	 * @info Adds to the entitys current velocity
	 * @arguments [[Vector]]:vel
	 * @return nil
	 */

	private static JavaFunction AddVelocity = new JavaFunction() {
		public int invoke(LuaState l) {
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			Vector vel = (Vector) l.checkUserdata(2, Vector.class, "Vector");
			self.addVelocity(vel.x, vel.z, vel.y);
			return 0;
		}
	};

	/**
	 * @author Jake
	 * @function IsInPortal
	 * @info Returns if the entity is in a portal
	 * @arguments nil
	 * @return [[Boolean]]:inportal
	 */

	private static JavaFunction IsInPortal = new JavaFunction() {
		public int invoke(LuaState l) {
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			l.pushBoolean(self.inPortal);
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @function IsInAir
	 * @info Returns if the entity is airborne
	 * @arguments nil
	 * @return [[Boolean]]:inair
	 */

	private static JavaFunction IsInAir = new JavaFunction() {
		public int invoke(LuaState l) {
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			l.pushBoolean(self.isAirBorne);
			return 1;
		}
	};

	/**
	 * @author Gregor
	 * @function IsSprinting
	 * @info Check if the entity is sprinting
	 * @arguments nil
	 * @return [[Boolean]]:sprinting
	 */

	private static JavaFunction IsSprinting = new JavaFunction() {
		public int invoke(LuaState l) {
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			l.pushBoolean(self.isSprinting());
			return 1;
		}
	};

	/**
	 * @author Gregor
	 * @function SetSprinting
	 * @info Set the entity to sprint
	 * @arguments [[Boolean]]:sprinting
	 * @return nil This onls works if the forward movement is not zero
	 */

	private static JavaFunction SetSprinting = new JavaFunction() {
		public int invoke(LuaState l) {
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			self.setSprinting(l.checkBoolean(2));
			return 0;
		}
	};

	/**
	 * @author Jake
	 * @function GetClass
	 * @info Returns the class the entity is a part of
	 * @arguments nil
	 * @return [[Boolean]]:classname
	 */

	private static JavaFunction GetClass = new JavaFunction() {
		public int invoke(LuaState l) {
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			String name = EntityList.getEntityString(self);
			l.pushString(name != null ? name : self.getClass().getSimpleName());
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @function EntIndex
	 * @info Returns the entity index
	 * @arguments nil
	 * @return [[Number]]:index
	 */

	private static JavaFunction EntIndex = new JavaFunction() {
		public int invoke(LuaState l) {
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			l.pushInteger(self.getEntityId());
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @function IsInWeb
	 * @info Checks if the entity is in a web
	 * @arguments nil
	 * @return [[Boolean]]:web
	 */

	private static JavaFunction IsInWeb = new JavaFunction() {
		public int invoke(LuaState l) {
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			l.pushBoolean(self.isInWeb);
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @function GetStepSize
	 * @info Returns the entity step size
	 * @arguments nil
	 * @return [[Number]]:stepsize
	 */

	private static JavaFunction GetStepSize = new JavaFunction() {
		public int invoke(LuaState l) {
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			l.pushNumber(self.stepHeight);
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @function SetStepSize
	 * @info Sets the entity step size
	 * @arguments [[Number]]:stepsize
	 * @return nil
	 */

	private static JavaFunction SetStepSize = new JavaFunction() {
		public int invoke(LuaState l) {
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			self.stepHeight = l.checkInteger(2);
			return 0;
		}
	};

	/**
	 * @author Jake
	 * @function GetDimensionID
	 * @info Return the dimension number the entity is currently in
	 * @arguments nil
	 * @return [[Number]]:dimension
	 */

	private static JavaFunction GetDimensionID = new JavaFunction() {
		public int invoke(LuaState l) {
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			l.pushInteger(self.dimension);
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @function SetWorld
	 * @info Set the world object in which the entity resides in
	 * @arguments [[World]]:world
	 * @return nil
	 */

	private static JavaFunction SetWorld = new JavaFunction() {
		public int invoke(LuaState l) {
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			World world = (World) l.checkUserdata(2, World.class, "World");
			self.world = world;
			return 0;
		}
	};

	/**
	 * @author Jake
	 * @function GetWorld
	 * @info Get the world object in which the entity resides in
	 * @arguments nil
	 * @return [[World]]:world
	 */

	private static JavaFunction GetWorld = new JavaFunction() {
		public int invoke(LuaState l) {
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			LuaUserdata.PushUserdata(l, self.world);
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @function TravelToWorld
	 * @info Moves the entity to another world
	 * @arguments [[World]]:world
	 * @return nil
	 */

	private static JavaFunction TravelToWorld = new JavaFunction() {
		public int invoke(LuaState l) {
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			World world = (World) l.checkUserdata(2, World.class, "World");
			self.changeDimension(world.provider.getDimension());
			return 0;
		}
	};

	/**
	 * @author Jake
	 * @function GetWidth
	 * @info Get the width of the entity
	 * @arguments nil
	 * @return [[Number]]:width
	 */

	private static JavaFunction GetWidth = new JavaFunction() {
		public int invoke(LuaState l) {
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			l.pushNumber(self.width);
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @function GetHeight
	 * @info Get the height of the entity
	 * @arguments nil
	 * @return [[Number]]:height
	 */

	private static JavaFunction GetHeight = new JavaFunction() {
		public int invoke(LuaState l) {
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			l.pushNumber(self.height);
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @function GetSize
	 * @info Get the width and the height of the entity
	 * @arguments nil
	 * @return [[Number]]:width, [[Number]]:height
	 */

	private static JavaFunction GetSize = new JavaFunction() {
		public int invoke(LuaState l) {
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			l.pushNumber(self.width);
			l.pushNumber(self.height);
			return 2;
		}
	};

	/**
	 * @author Jake
	 * @function SetSize
	 * @info Set the width and the height of the entity
	 * @arguments [[Number]]:width, [[Number]]:height
	 * @return nil
	 */

	private static JavaFunction SetSize = new JavaFunction() {
		public int invoke(LuaState l) {
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			self.setSize((float) l.checkNumber(2), (float) l.checkNumber(3));
			return 0;
		}
	};

	/**
	 * @author Jake
	 * @function Explode
	 * @info Explodes the entity
	 * @arguments [[Number]]:size
	 * @return nil
	 */

	private static JavaFunction Explode = new JavaFunction() {
		public int invoke(LuaState l) {
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			self.world.createExplosion(self, self.posX, self.posY, self.posZ, (float) l.checkNumber(2, 5),
					l.checkBoolean(3, false));
			return 0;
		}
	};

	/**
	 * @author Jake
	 * @function OBBMins
	 * @info Gets the entitys minimum bound
	 * @arguments nil
	 * @return [[Vector]]:mins
	 */

	private static JavaFunction OBBMins = new JavaFunction() {
		public int invoke(LuaState l) {
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			AxisAlignedBB bb = self.getEntityBoundingBox();
			Vector mins = new Vector(bb.minX, bb.minZ, bb.minY);
			mins.push(l);
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @function OBBMaxs
	 * @info Gets the entitys maximum bound
	 * @arguments nil
	 * @return [[Vector]]:maxs
	 */

	private static JavaFunction OBBMaxs = new JavaFunction() {
		public int invoke(LuaState l) {
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			AxisAlignedBB bb = self.getEntityBoundingBox();
			Vector maxs = new Vector(bb.maxX, bb.maxZ, bb.maxY);
			maxs.push(l);
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @function WorldToLocal
	 * @info Get the local vector of a world vector in relation to the entity
	 * @arguments [[Vector]]:world
	 * @return [[Vector]]:local
	 */

	private static JavaFunction WorldToLocal = new JavaFunction() {
		public int invoke(LuaState l) {
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			Vector toVec = (Vector) l.checkUserdata(2, Vector.class, "Vector");
			Vector local = new Vector(self.posX - toVec.x, self.posZ - toVec.y, self.posY - toVec.z);
			local.push(l);
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @function LocalToWorld
	 * @info Get the world vector of a local vector in relation to the entity
	 * @arguments [[Vector]]:local
	 * @return [[Vector]]:world
	 */

	private static JavaFunction LocalToWorld = new JavaFunction() {
		public int invoke(LuaState l) {
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			Vector toVec = (Vector) l.checkUserdata(2, Vector.class, "Vector");
			Vector local = new Vector(self.posX + toVec.x, self.posZ + toVec.y, self.posY + toVec.z);
			local.push(l);
			return 1;
		}
	};

	/**
	 * @author Gregor
	 * @function Move
	 * @info Try to move the entity to the given position
	 * @arguments [[Vector]]:pos
	 * @return nil
	 */

	private static JavaFunction Move = new JavaFunction() {
		public int invoke(LuaState l) {
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			Vector pos = (Vector) l.checkUserdata(2, Vector.class, "Vector");
			self.move(MoverType.SELF, pos.x, pos.z, pos.y);
			return 0;
		}
	};

	/**
	 * @author Jake
	 * @function PlaySound
	 * @info Play a sound emitting from the entity
	 * @arguments [[String]]:Sound name, [ [[Number]]:Volume, [[Number]]:Pitch ]
	 * @return nil
	 */

	private static JavaFunction PlaySound = new JavaFunction() {
		public int invoke(LuaState l) {
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			//self.playSound(l.checkString(2), (float) l.checkNumber(3, 1), (float) l.checkNumber(4, 1));
			// TODO: Check to make sure this works
			// Either I do this or use soundEventRegistry
			// Might need to create a new lua metatable for this
			// self.playSound(new SoundEvent(new ResourceLocation(l.checkString(2))), (float) l.checkNumber(3, 1), (float) l.checkNumber(4, 1)); // TODO: Check this
			return 0;
		}
	};

	/**
	 * @author Gregor
	 * @function GetItem
	 * @info Gets the entities [[ItemStack]]
	 * @arguments nil
	 * @return [[ItemStack]]:item
	 */

	private static JavaFunction GetItem = new JavaFunction() {
		public int invoke(LuaState l) {
			EntityItem self = (EntityItem) l.checkUserdata(1, EntityItem.class, "EntityItem");
			l.pushUserdataWithMeta(self.getItem(), "ItemStack");
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @function Spawn
	 * @info Spawn an entity in the world
	 * @arguments [ [[Boolean]]:force ]
	 * @return [[Boolean]]:success
	 */

	private static JavaFunction Spawn = new JavaFunction() {
		public int invoke(LuaState l) {
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			self.forceSpawn = l.checkBoolean(2, false);
			l.pushBoolean(self.world.spawnEntity(self));
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @function Remove
	 * @info Remove the entity
	 * @arguments nil
	 * @return nil
	 */

	private static JavaFunction Remove = new JavaFunction() {
		public int invoke(LuaState l) {
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			self.world.removeEntity(self);
			return 0;
		}
	};

	/**
	 * @author Jake
	 * @function SetInvisible
	 * @info Sets if the entity is invisible or not
	 * @arguments [[Boolean]]:invisible
	 * @return nil
	 */

	private static JavaFunction SetInvisible = new JavaFunction() {
		public int invoke(LuaState l) {
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			self.setInvisible(l.checkBoolean(2));
			return 0;
		}
	};

	/**
	 * @author Jake
	 * @function IsInvisible
	 * @info Returns if the entity is invisible
	 * @arguments nil
	 * @return [[Boolean]]:invisible
	 */

	private static JavaFunction IsInvisible = new JavaFunction() {
		public int invoke(LuaState l) {
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			l.pushBoolean(self.isInvisible());
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @function GetNBTTag
	 * @info Returns the entities NBTTag compound
	 * @arguments nil
	 * @return [[NBTTag]]:nbttag
	 */

	private static JavaFunction GetNBTTag = new JavaFunction() {
		public int invoke(LuaState l) {
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			l.pushUserdataWithMeta(self.getEntityData(), "NBTTag");
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @function GetDataWatcher
	 * @info Returns a DataWatcher which can be used to control the entities networked data
	 * @arguments nil
	 * @return [[DataWatcher]]:networkdata
	 */

	private static JavaFunction GetDataWatcher = new JavaFunction() {
		public int invoke(LuaState l) {
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			l.pushUserdataWithMeta(self.getDataManager(), "DataWatcher");
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @function IsSilent
	 * @info Returns if the player is silent or not
	 * @arguments nil
	 * @return [[Boolean]]:silent
	 */

	private static JavaFunction IsSilent = new JavaFunction() {
		public int invoke(LuaState l) {
			EntityPlayer self = (EntityPlayer) l.checkUserdata(1, EntityPlayer.class, "Player");
			l.pushBoolean(self.isSilent());
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @function SetSilent
	 * @info Sets if the player is silent or not
	 * @arguments [[Boolean]]:silent
	 * @return nil
	 */

	private static JavaFunction SetSilent = new JavaFunction() {
		public int invoke(LuaState l) {
			EntityPlayer self = (EntityPlayer) l.checkUserdata(1, EntityPlayer.class, "Player");
			self.setSilent(l.checkBoolean(2));
			return 0;
		}
	};

	public static void Init(final LuaCraftState l) {
		l.newMetatable("Entity");
		{
			l.pushJavaFunction(__tostring);
			l.setField(-2, "__tostring");

			LuaUserdata.SetupBasicMeta(l);
			LuaUserdata.SetupMeta(l, true);

			l.newMetatable("Object");
			l.setField(-2, "__basemeta");

			l.pushJavaFunction(AttackFrom);
			l.setField(-2, "AttackFrom");
			l.pushJavaFunction(GetUniqueID);
			l.setField(-2, "GetUniqueID");
			l.pushJavaFunction(IsAlive);
			l.setField(-2, "IsAlive");
			l.pushJavaFunction(IsOnGround);
			l.setField(-2, "IsOnGround");
			l.pushJavaFunction(GetAngles);
			l.setField(-2, "GetAngles");
			l.pushJavaFunction(SetAngles);
			l.setField(-2, "SetAngles");
			l.pushJavaFunction(GetPos);
			l.setField(-2, "GetPos");
			l.pushJavaFunction(SetPos);
			l.setField(-2, "SetPos");
			l.pushJavaFunction(IsBurning);
			l.setField(-2, "IsBurning");
			l.pushJavaFunction(Ignite);
			l.setField(-2, "Ignite");
			l.pushJavaFunction(Extinguish);
			l.setField(-2, "Extinguish");
			l.pushJavaFunction(SetNoClip);
			l.setField(-2, "SetNoClip");
			l.pushJavaFunction(GetNoClip);
			l.setField(-2, "GetNoClip");
			l.pushJavaFunction(IsMounted);
			l.setField(-2, "IsMounted");
			l.pushJavaFunction(SetMount);
			l.setField(-2, "SetMount");
			l.pushJavaFunction(GetMount);
			l.setField(-2, "GetMount");
			l.pushJavaFunction(IsSneaking);
			l.setField(-2, "IsSneaking");
			l.pushJavaFunction(IsInWater);
			l.setField(-2, "IsInWater");
			l.pushJavaFunction(IsWet);
			l.setField(-2, "IsWet");
			l.pushJavaFunction(GetAir);
			l.setField(-2, "GetAir");
			l.pushJavaFunction(SetAir);
			l.setField(-2, "SetAir");
			l.pushJavaFunction(GetFireResistance);
			l.setField(-2, "GetFireResistance");
			l.pushJavaFunction(SetFireResistance);
			l.setField(-2, "SetFireResistance");
			l.pushJavaFunction(GetEyePos);
			l.setField(-2, "GetEyePos");
			l.pushJavaFunction(GetEyeHeight);
			l.setField(-2, "GetEyeHeight");
			l.pushJavaFunction(SetVelocity);
			l.setField(-2, "SetVelocity");
			l.pushJavaFunction(AddVelocity);
			l.setField(-2, "AddVelocity");
			l.pushJavaFunction(IsInPortal);
			l.setField(-2, "IsInPortal");
			l.pushJavaFunction(IsInAir);
			l.setField(-2, "IsInAir");
			l.pushJavaFunction(IsSprinting);
			l.setField(-2, "IsSprinting");
			l.pushJavaFunction(SetSprinting);
			l.setField(-2, "SetSprinting");
			l.pushJavaFunction(GetClass);
			l.setField(-2, "GetClass");
			l.pushJavaFunction(EntIndex);
			l.setField(-2, "EntIndex");
			l.pushJavaFunction(IsInWeb);
			l.setField(-2, "IsInWeb");
			l.pushJavaFunction(GetStepSize);
			l.setField(-2, "GetStepSize");
			l.pushJavaFunction(SetStepSize);
			l.setField(-2, "SetStepSize");
			l.pushJavaFunction(GetDimensionID);
			l.setField(-2, "GetDimensionID");
			l.pushJavaFunction(SetWorld);
			l.setField(-2, "SetWorld");
			l.pushJavaFunction(GetWorld);
			l.setField(-2, "GetWorld");
			l.pushJavaFunction(TravelToWorld);
			l.setField(-2, "TravelToWorld");
			l.pushJavaFunction(GetWidth);
			l.setField(-2, "GetWidth");
			l.pushJavaFunction(GetHeight);
			l.setField(-2, "GetHeight");
			l.pushJavaFunction(GetSize);
			l.setField(-2, "GetSize");
			l.pushJavaFunction(SetSize);
			l.setField(-2, "SetSize");
			l.pushJavaFunction(Explode);
			l.setField(-2, "Explode");
			l.pushJavaFunction(OBBMins);
			l.setField(-2, "OBBMins");
			l.pushJavaFunction(OBBMaxs);
			l.setField(-2, "OBBMaxs");
			l.pushJavaFunction(WorldToLocal);
			l.setField(-2, "WorldToLocal");
			l.pushJavaFunction(LocalToWorld);
			l.setField(-2, "LocalToWorld");
			l.pushJavaFunction(Move);
			l.setField(-2, "Move");
			l.pushJavaFunction(PlaySound);
			l.setField(-2, "PlaySound");
			l.pushJavaFunction(GetItem);
			l.setField(-2, "GetItem");
			l.pushJavaFunction(Spawn);
			l.setField(-2, "Spawn");
			l.pushJavaFunction(Remove);
			l.setField(-2, "Remove");
			l.pushJavaFunction(SetInvisible);
			l.setField(-2, "SetInvisible");
			l.pushJavaFunction(IsInvisible);
			l.setField(-2, "IsInvisible");
			l.pushJavaFunction(GetNBTTag);
			l.setField(-2, "GetNBTTag");
			l.pushJavaFunction(GetDataWatcher);
			l.setField(-2, "GetDataWatcher");
			l.pushJavaFunction(IsSilent);
			l.setField(-2, "IsSilent");
			l.pushJavaFunction(SetSilent);
			l.setField(-2, "SetSilent");
		}
		l.pop(1);

	}
}
