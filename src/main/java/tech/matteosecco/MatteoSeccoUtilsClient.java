package tech.matteosecco;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;

public class MatteoSeccoUtilsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		FabricModelPredicateProviderRegistry.register(MatteoSeccoUtils.MAGIC_WAND, new Identifier("charge"), (wand, clientWorld, player)->{
			if(player == null || player.getActiveItem() != wand) return 0f;
			return (wand.getMaxUseTime() - player.getItemUseTimeLeft())/20f;
		});
		FabricModelPredicateProviderRegistry.register(MatteoSeccoUtils.MAGIC_WAND, new Identifier("is_charging"),
				(wand, clientWorld, player)-> player==null || !player.isUsingItem() || player.getActiveItem() != wand? 0f: 1f);
	}
}
