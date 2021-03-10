package tech.matteosecco;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import tech.matteosecco.teleport_wand.TeleportWand;


public class MatteoSeccoUtils implements ModInitializer {

	private static final String NAMESPACE = "matteosecco";
	private static final FabricItemSettings WAND_SETTINGS = new FabricItemSettings()
			.group(ItemGroup.TOOLS)
			.fireproof()
			.maxCount(1)
			.maxDamage(0);

	public static final Item WAND_BODY = new Item(new FabricItemSettings().group(ItemGroup.MISC).fireproof().maxDamage(0));
	public static final Item MAGIC_WAND = new TeleportWand(WAND_SETTINGS);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "wand_body"), WAND_BODY);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "teleport_wand"), MAGIC_WAND);
	}
}
