package dev.sebastianb.justenoughguns;

import dev.sebastianb.justenoughguns.entity.ModEntityTypes;
import dev.sebastianb.justenoughguns.registry.ModItems;
import dev.sebastianb.sebautil.ModRegistry;
import net.fabricmc.api.ModInitializer;

import java.util.logging.Logger;

public class JustEnoughGuns implements ModInitializer {

    public static String MOD_ID = "justenoughguns";
    public static final Logger LOGGER = Logger.getLogger(MOD_ID);

    public static final ModRegistry REGISTRY = new ModRegistry(MOD_ID);

    /**
     * Runs the mod initializer.
     */
    @Override
    public void onInitialize() {
        ModItems.register();

        ModEntityTypes.register();

    }
}
