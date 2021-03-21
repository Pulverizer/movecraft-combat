package io.github.pulverizer.movecraft_combat.config;

import io.github.pulverizer.movecraft.api.config.crew.CrewRole;
import io.github.pulverizer.movecraft.api.config.crew.ManyPlayerCrewRole;
import io.github.pulverizer.movecraft.config.craft.CraftType;

public abstract class CrewRoles {

    public static void register() {
        CrewRole.registerSubType(AADirector.class, AADirector::new);
        CrewRole.registerSubType(CannonDirector.class, CannonDirector::new);
        CrewRole.registerSubType(Loader.class, Loader::new);
        CrewRole.registerSubType(Repairman.class, Repairman::new);
    }

    public static final class AADirector extends ManyPlayerCrewRole {

        @Override public final String getPermissionNode() {
            return "crew.director.aa";
        }

        @Override public boolean enabledOnCraftType(CraftType craftType) {
            return craftType.getValue(CraftSettings.CanHaveAADirectors.class).get();
        }

        @Override public String getName() {
            return "AA Director";
        }

        @Override public AADirector newInstance() {
            return new AADirector();
        }
    }

    public static final class CannonDirector extends ManyPlayerCrewRole {

        @Override public final String getPermissionNode() {
            return "crew.director.cannon";
        }

        @Override public boolean enabledOnCraftType(CraftType craftType) {
            return craftType.getValue(CraftSettings.CanHaveCannonDirectors.class).get();
        }

        @Override public String getName() {
            return "Cannon Director";
        }

        @Override public CannonDirector newInstance() {
            return new CannonDirector();
        }
    }

    public static final class Loader extends ManyPlayerCrewRole {

        @Override public final String getPermissionNode() {
            return "crew.loader";
        }

        @Override public boolean enabledOnCraftType(CraftType craftType) {
            return craftType.getValue(CraftSettings.CanHaveLoaders.class).get();
        }

        @Override public Loader newInstance() {
            return new Loader();
        }
    }

    public static final class Repairman extends ManyPlayerCrewRole {

        @Override public final String getPermissionNode() {
            return "crew.repairman";
        }

        @Override public boolean enabledOnCraftType(CraftType craftType) {
            return craftType.getValue(CraftSettings.CanHaveRepairmen.class).get();
        }

        @Override public Repairman newInstance() {
            return new Repairman();
        }
    }
}
