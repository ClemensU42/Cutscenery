package com.clemensu42.cutscenery.cutscenery.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.world.World;

public class CutsceneEntity extends Entity
{
	public String targetMob = "minecraft:creeper";

	public CutsceneEntity(EntityType<? extends Entity> entityType, World world)
	{
		super(entityType, world);
	}

	@Override
	protected void initDataTracker()
	{

	}

	@Override
	protected void readCustomDataFromNbt(NbtCompound nbt)
	{
		targetMob = nbt.getString("TargetMob");
	}

	@Override
	protected void writeCustomDataToNbt(NbtCompound nbt)
	{
		nbt.putString("TargetMob", targetMob);
	}

	@Override
	public Packet<?> createSpawnPacket()
	{
		return new EntitySpawnS2CPacket(this);
	}
}
