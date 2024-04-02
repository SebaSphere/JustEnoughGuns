package dev.sebastianb.justenoughguns.registry;

import dev.sebastianb.justenoughguns.JustEnoughGuns;
import dev.sebastianb.justenoughguns.item.gun.PotatoGun;
import dev.sebastianb.sebautil.ItemRegistry;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.Locale;
import java.util.logging.Level;

public class ModItems {

    public static final ItemGroup ITEM_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(Guns.TATERGUN.asItem())) // TODO: change this to an item in the mod
            .displayName(Text.translatable("item_group.justenoughguns.items")) // TODO: properly datagen this
            .entries((context, entries) -> {
                Arrays.stream(Guns.values()).forEach(v -> entries.add(v.asItem()));
            })
            .build();

    public enum Guns implements ItemRegistry {
        TATERGUN();

        private final String name;
        private final Item item;

        Guns() {
            name = this.toString().toLowerCase(Locale.ROOT); // should take the name of the enum
            item = JustEnoughGuns.REGISTRY.item(new PotatoGun(new Item.Settings().maxCount(1)), name);
        }

        @Override
        public Item asItem() {
            return item;
        }

        public PotatoGun asGunItem() {
            return (PotatoGun) item;
        }
    }

    public static void register() {
        // good enough for now
        Arrays.stream(Guns.values()).forEach(v -> JustEnoughGuns.LOGGER.log(Level.INFO, v.name)); // init all guns

        Registry.register(Registries.ITEM_GROUP, new Identifier(JustEnoughGuns.MOD_ID, "item_group.justenoughguns.items"), ITEM_GROUP);

    }

}
