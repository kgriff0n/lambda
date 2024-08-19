package io.github.kgriff0n;

import io.github.kgriff0n.commands.*;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lambda implements ModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("lambda");

	public static final String VERSION = "v1.1.0";

	@Override
	public void onInitialize() {

		if (!Config.exist()) {
			Config.createConfigFile();
			Config.writeDefaultConfig();
		}

		Config.loadFile();

		AnvilCommand.register();
		BroadcastCommand.register();
		CartographyTableCommand.register();
		CoinflipCommand.register();
		CraftCommand.register();
		EcseeCommand.register();
		EnchantingTableCommand.register();
		EnderChestCommand.register();
		FeedCommand.register();
		FlyCommand.register();
		GrindstoneCommand.register();
		HatCommand.register();
		HeadCommand.register();
		HealCommand.register();
		InvseeCommand.register();
		LambdaCommand.register();
		LightningCommand.register();
		LoomCommand.register();
		ReplyCommand.register();
		SmithingTableCommand.register();
		StonecutterCommand.register();
		TopCommand.register();
		TrashCommand.register();

		LOGGER.info("Hello Fabric world!");
	}
}