package hu.hundevelopers.elysium.block;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ElysiumEnergyCrystalBlock extends ElysiumBlock
{
	public static final String[] names = new String[] {"pure", "corrupted", "depleted"};
	private IIcon[] icons;


	public ElysiumEnergyCrystalBlock(Material mat) 
	{
		super(mat);
		this.setLightOpacity(1);
	}
	
	@Override
	public int getRenderBlockPass()
	{
		return 1;
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	  /**
     * Gets the block's texture. Args: side, meta
     */
    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta)
    {
        if (meta < 0 || meta >= this.icons.length)
        {
            meta = 0;
        }

        return this.icons[meta];
    }

    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    @Override
    public int damageDropped(int meta)
    {
        return meta;
    }

    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List list)
    {
        for(int i = 0; i < names.length; i++)
        	list.add(new ItemStack(item, 1, i));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        this.icons = new IIcon[names.length];

        for (int i = 0; i < this.icons.length; ++i)
        {
            this.icons[i] = iconRegister.registerIcon(this.getTextureName() + "_" + names[i]);
        }
    }
}
