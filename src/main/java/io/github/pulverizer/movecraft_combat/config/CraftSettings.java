package io.github.pulverizer.movecraft_combat.config;

import io.github.pulverizer.movecraft.api.config.craft.BooleanCraftSetting;

public abstract class CraftSettings {

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
