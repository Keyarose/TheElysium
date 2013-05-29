package mods.elysium.dimension.portal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import mods.elysium.Elysium;
import mods.elysium.dimension.TutorialPortalPosition;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Direction;
import net.minecraft.util.LongHashMap;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.PortalPosition;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class ElysiumTeleporter extends Teleporter
{
	private final WorldServer worldServer;
    private final Random random;
    public static ArrayList<ElysiumPortalPosition> portals = new ArrayList<ElysiumPortalPosition>();
	
	public ElysiumTeleporter(WorldServer worldServer)
	{
		super(worldServer);
		this.worldServer = worldServer;
		this.random = new Random(worldServer.getSeed());
	}
	
	@Override
	public void placeInPortal(Entity entity, double par2, double par4, double par6, float par8)
	{
		if(!this.placeInExistingPortal(entity, par2, par4, par6, par8))
		{
			this.makePortal(entity);
			this.placeInExistingPortal(entity, par2, par4, par6, par8);
		}
	}
	
	@Override
	public boolean placeInExistingPortal(Entity entity, double par2, double par4, double par6, float par8)
	{
		int x = MathHelper.floor_double(entity.posX);
		int z = MathHelper.floor_double(entity.posZ);
		int i;
		for(i=0; i<portals.size(); i++)
		{
			if((portals.get(i).dim == entity.dimension) && (Math.abs(portals.get(i).posX-x) < 32) && (Math.abs(portals.get(i).posZ-z) < 32))
			{
				entity.motionX = entity.motionY = entity.motionZ = 0.0D;
				entity.setPosition(portals.get(i).posX+0.5D, portals.get(i).posY+1, portals.get(i).posZ+0.5D);
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean makePortal(Entity entity)
	{
		int x = MathHelper.floor_double(entity.posX) + random.nextInt(33) - 16;
		int z = MathHelper.floor_double(entity.posZ) + random.nextInt(33) - 16;
		int y;
		for(y = worldServer.getActualHeight()-1; (y >= 0) && (worldServer.isAirBlock(x, y, z)); y--);
		
		worldServer.setBlock(x, y+9, z, Elysium.portalCore.blockID);
		worldServer.setBlockMetadataWithNotify(x, y+9, z, 1, 0);
		for(int i=-1; i <= 1; i++)
		{
			for(int j=-1; j <= 1; j++)
			{
				worldServer.setBlock(x+i, y+8, z+j, Block.blockNetherQuartz.blockID);
				worldServer.setBlockMetadataWithNotify(x+i, y+8, z+j, 1, 0);
				
				worldServer.setBlock(x+i, y+6, z+j, Block.blockNetherQuartz.blockID);
				worldServer.setBlockMetadataWithNotify(x+i, y+6, z+j, 2, 0);
				worldServer.setBlock(x+i, y+5, z+j, Block.blockNetherQuartz.blockID);
				worldServer.setBlockMetadataWithNotify(x+i, y+5, z+j, 2, 0);
				
				worldServer.setBlock(x+i, y+4, z+j, Block.blockGold.blockID);
				
				worldServer.setBlock(x+i, y+3, z+j, Block.blockNetherQuartz.blockID);
				worldServer.setBlockMetadataWithNotify(x+i, y+3, z+j, 2, 0);
				worldServer.setBlock(x+i, y+2, z+j, Block.blockNetherQuartz.blockID);
				worldServer.setBlockMetadataWithNotify(x+i, y+2, z+j, 2, 0);
			}
		}
		for(int i=-2; i <= 2; i++)
		{
			for(int j=-2; j <= 2; j++)
			{
				worldServer.setBlock(x+i, y+7, z+j, Block.blockNetherQuartz.blockID);
				worldServer.setBlock(x+i, y+1, z+j, Block.blockNetherQuartz.blockID);
			}
		}
		
		entity.motionX = entity.motionY = entity.motionZ = 0.0D;
		entity.setPosition(x+0.5D, y+1, z+0.5D);
		
		return true;
	}
}
