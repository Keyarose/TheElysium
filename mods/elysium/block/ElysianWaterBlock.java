package mods.elysium.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import me.dawars.CraftingPillars.CraftingPillars;
import mods.elysium.BlockItemIDs;
import mods.elysium.Elysium;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fluids.BlockFluidClassic;

public class ElysianWaterBlock extends BlockFluidClassic {

	protected static Icon[] theIcon;

	public ElysianWaterBlock(int id) {
		super(id, Elysium.elysianWaterFluid, Material.water);
		Elysium.elysianWaterFluid.setBlockID(id);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
    /**
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
    public void registerIcons(IconRegister iconRegister)
    {
        this.theIcon = new Icon[]
				{
        		iconRegister.registerIcon(Elysium.id + ":elysian_water_flow"),
        		iconRegister.registerIcon(Elysium.id + ":elysian_water_still")
				};
    }

	
    
    @SideOnly(Side.CLIENT)
    @Override
    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public Icon getIcon(int id, int meta)
    {
        return meta != 0 && meta != 1 ? this.theIcon[0] : this.theIcon[1];
    }
	
//	@Override
//	public int colorMultiplier(IBlockAccess iblockaccess, int x, int y, int z) {
//		return 0x8CF4FF;
//	}
}