package tech.matteosecco.teleport_wand;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class TeleportWand extends Item {
	public TeleportWand(Settings settings) {
		super(settings);
	}

	private boolean usedOnBlock = false;


	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		final PlayerEntity player = context.getPlayer();
		if(player!=null) {
			setTeleportTarget(player, context.getStack());
			usedOnBlock = true;
			return ActionResult.SUCCESS;
		}
		return ActionResult.FAIL;
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		final ItemStack wand = player.getStackInHand(hand);
		if(usedOnBlock){
			usedOnBlock = false;
			return TypedActionResult.pass(wand);
		}
		executeTeleport(player, wand);
		return TypedActionResult.success(wand);
	}

	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
		return super.finishUsing(stack, world, user);
	}

	@Override
	public void onCraft(ItemStack wand, World world, PlayerEntity player) {
		setTeleportTarget(player,wand);
	}

	@Override
	public boolean isEnchantable(ItemStack stack) {
		return false;
	}

	@Override
	public boolean isFireproof() {
		return true;
	}

	@Override
	public int getMaxUseTime(ItemStack stack) {
		return 10;
	}

	private void setTeleportTarget(PlayerEntity player, ItemStack wand){
		final Vec3d pos = player.getPos();
		final CompoundTag target = wand.getOrCreateTag();
		target.putDouble("targetX", pos.x);
		target.putDouble("targetY", pos.y);
		target.putDouble("targetZ", pos.z);
		wand.setTag(target);
	}

	private void executeTeleport(PlayerEntity player, ItemStack wand){
		final CompoundTag target = wand.getOrCreateTag();
		player.updatePosition(target.getDouble("targetX"), target.getDouble("targetY"), target.getDouble("targetZ"));
	}
}
