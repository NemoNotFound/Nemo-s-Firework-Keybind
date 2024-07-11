package com.nemonotfound.nemosfireworkkeybinding;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NemosFireworkKeybinding implements ModInitializer {

	public static final String MOD_ID = "nemos-firework-keybinding";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Thank you for using Nemo's Firework Keybinding!");
	}
}