package io.github.pulverizer.movecraft_combat.config;

import io.github.pulverizer.movecraft.api.config.craft.BooleanCraftSetting;
import io.github.pulverizer.movecraft.api.config.craft.CraftSetting;

public abstract class CraftSettings {

    public static void register() {
        CraftSetting.registerSetting(CanHaveAADirectors.class, CanHaveAADirectors::new);
        CraftSetting.registerSetting(CanHaveCannonDirectors.class, CanHaveCannonDirectors::new);
        CraftSetting.registerSetting(CanHaveLoaders.class, CanHaveLoaders::new);
        CraftSetting.registerSetting(CanHaveRepairmen.class, CanHaveRepairmen::new);
    }

    //TODO - Rename these

    public static class CanHaveAADirectors extends BooleanCraftSetting.True {

        @Override public String getNodeString() {
            return "allowAADirectorSign";
        }
    }

    public static class CanHaveCannonDirectors extends BooleanCraftSetting.True {

        @Override public String getNodeString() {
            return "allowCannonDirectorSign";
        }
    }

    public static class CanHaveLoaders extends BooleanCraftSetting.True {

        @Override public String getNodeString() {
            return "allowLoaders";
        }
    }

    public static class CanHaveRepairmen extends BooleanCraftSetting.True {

        @Override public String getNodeString() {
            return "allowRepairmen";
        }
    }
}
