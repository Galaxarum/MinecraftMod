package tech.matteosecco.teleport_wand;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class TeleportWand extends AbstractWand {

	public TeleportWand(Settings settings) {
		super(settings, 5);
	}

	@Override
	public void onCraft(ItemStack wand, World world, PlayerEntity player) {
		setTeleportTarget(player,wand);
	}

	@Override
	protected void launchSpell(ItemStack stack, World world, LivingEntity user){
		if(onBlock) setTeleportTarget(user,stack);
		else if(onAir) executeTeleport(user, stack);
	}

	private void setTeleportTarget(LivingEntity player, ItemStack wand){
		final Vec3d pos = player.getPos();
		final CompoundTag target = wand.getOrCreateTag();
		target.putDouble("targetX", pos.x);
		target.putDouble("targetY", pos.y);
		target.putDouble("targetZ", pos.z);
		wand.setTag(target);
	}

	private void executeTeleport(LivingEntity player, ItemStack wand){
		final CompoundTag target = wand.getOrCreateTag();
		player.updatePosition(target.getDouble("targetX"), target.getDouble("targetY"), target.getDouble("targetZ"));
	}

}
