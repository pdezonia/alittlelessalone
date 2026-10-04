package net.pazooni.LessLonely.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.pazooni.LessLonely.block.ModBlockEntities;

import java.util.List;

public class TownBlockEntity extends BlockEntity {
    public TownBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.TOWN_BLOCK_ENTITY.get(), pos, blockState);
    }

    public int countAnimals(BlockPos pos, Class<? extends Animal> entityClass, int searchRad) {
        /*
            pos          the world coord that is the center of the search cube
            entityClass  the java class for the entity to count
            searchRad    half the side length of the search cube
                this method kind of just exists to prove that the rest of what town block is supposed to do is possible
         */
        assert this.level != null;
        List<? extends Animal> list = this.level.getEntitiesOfClass(entityClass, new AABB(pos).inflate((double) searchRad));

        return list.size();
    }
}
