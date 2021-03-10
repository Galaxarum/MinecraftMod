package tech.matteosecco.teleport_wand;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public abstract class AbstractWand extends BowItem {
	protected boolean onBlock = false;
	protected boolean onAir = false;
	private boolean charged = false;
	private final int TICKS_TO_LOAD;

	public AbstractWand(Settings settings, int seconds_to_load) {
		super(settings);
		TICKS_TO_LOAD = seconds_to_load * 20;
	}

	@Override
	public final void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks){
		if(charged){
			playLaunchSound(user);
			launchSpell(stack, world, user);
		}else playLaunchFailSound(user);
		onBlock = onAir = charged = false;
	}


	@Override
	public final ActionResult useOnBlock(ItemUsageContext context) {
		if ( !onAir ) onBlock = true;
		return super.useOnBlock(context);
	}

	@Override
	public final TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		if ( !onBlock ) onAir = true;
		return super.use(world, user, hand);
	}

	@Override
	public final void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
		if ( !charged && getMaxUseTime(stack) - remainingUseTicks >= TICKS_TO_LOAD ) {
			playChargedSound(user);
			charged = true;
		}
		super.usageTick(world, user, stack, remainingUseTicks);
	}

	protected abstract void launchSpell(ItemStack stack, World world, LivingEntity user);

	protected void playChargedSound(LivingEntity user){
		user.playSound(SoundEvents.BLOCK_PORTAL_TRIGGER, .7f, 2f);
	}

	protected void playLaunchFailSound(LivingEntity user){
		user.playSound(SoundEvents.BLOCK_LAVA_EXTINGUISH,1f,.98f);
	}

	protected void playLaunchSound(LivingEntity user){
		user.playSound(SoundEvents.BLOCK_PORTAL_AMBIENT, .7f, 5f);
	}

}
