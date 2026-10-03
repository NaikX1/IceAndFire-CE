package com.iafenvoy.iceandfire;

import com.iafenvoy.iceandfire.config.IafClientConfig;
import com.iafenvoy.iceandfire.config.IafCommonConfig;
import com.iafenvoy.iceandfire.registry.IafRenderers;
import com.iafenvoy.jupiter.ConfigManager;
import com.iafenvoy.jupiter.render.screen.ConfigSelectScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.minecraft.network.chat.Component;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Environment(EnvType.CLIENT)
public class IceAndFireClient implements ClientModInitializer {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void onInitializeClient() {
        LOGGER.info("Initializing Ice And Fire Client for Fabric 26.3");
        
        ConfigManager.getInstance().registerConfigHandler(IafClientConfig.INSTANCE);
        ConfigManager.getInstance().registerConfigHandler(IafCommonConfig.INSTANCE);

        // Register client-side renderers
        IafRenderers.registerArmorRenderers();
        IafRenderers.registerItemRenderers();
        IafRenderers.registerModelPredicates();

        // Register client lifecycle event for additional setup
        ClientLifecycleEvents.CLIENT_STARTED.register(client -> {
            IafRenderers.registerArmorRenderers();
            IafRenderers.registerItemRenderers();
            
            // Register mod menu/config screen using Fabric's mod menu integration
            // This will be handled through mod menu if available, or through Fabric's native screen system
        });
    }
}
