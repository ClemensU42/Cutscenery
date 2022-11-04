package com.clemensu42.cutscenery.cutscenery.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class CutsceneEntity extends Entity
{
	private static TrackedData<Identifier> TARGET_MOB;

	CutsceneEntity(EntityType<? extends Entity> entityType, World world)
	{
		super(entityType, world);
		this.dataTracker.set(TARGET_MOB, Identifier.tryParse("minecraft:creeper"));
	}

	@Override
	protected void initDataTracker()
	{
		this.dataTracker.startTracking(TARGET_MOB, new Identifier("minecraft", "creeper"));
	}

	@Override
	protected void readCustomDataFromNbt(NbtCompound nbt)
	{
		this.dataTracker.set(TARGET_MOB, Identifier.tryParse(nbt.getString("TargetMob")));
	}

	@Override
	protected void writeCustomDataToNbt(NbtCompound nbt)
	{
		nbt.putString("TargetMob", this.dataTracker.get(TARGET_MOB).toString());
	}

	@Override
	public Packet<?> createSpawnPacket()
	{
		return new EntitySpawnS2CPacket(this);
	}
}
