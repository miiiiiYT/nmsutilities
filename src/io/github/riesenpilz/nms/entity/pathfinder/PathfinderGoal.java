package io.github.riesenpilz.nms.entity.pathfinder;

import java.util.EnumSet;

public abstract class PathfinderGoal {
    private final EnumSet<PathfinderGoal.Type> pathfinderGoalTypes = EnumSet.noneOf(PathfinderGoal.Type.class);

    public PathfinderGoal() {
    }

    /**
     * Runs every tick.
     *
     * @return true if it should run {@link PathfinderGoal#pathfind()}.
     */
    public abstract boolean onTick();

    /**
     * Runs {@link PathfinderGoal#goToLocation()} as long as it returns true. Runs
     * {@link PathfinderGoal#stopPathFinding()} if it returns false.
     *
     * @return true if it should run {@link PathfinderGoal#goToLocation()}.
     */
    public boolean pathfind() {
        return this.onTick();
    }

    public boolean C_() {
        return true;
    }

    /**
     * Runs if {@link PathfinderGoal#onTick()} is true as long as
     * {@link PathfinderGoal#pathfind()} is true.
     */
    public void goToLocation() {
    }

    /**
     * Runs if {@link PathfinderGoal#pathfind()} returns false.
     */
    public void stopPathFinding() {
    }

    public void e() {
    }

    public void setPathfinderGoalTypes(EnumSet<PathfinderGoal.Type> enumset) {
        this.pathfinderGoalTypes.clear();
        this.pathfinderGoalTypes.addAll(enumset);
    }

    public EnumSet<PathfinderGoal.Type> getPathfinderGoalTypes() {
        return pathfinderGoalTypes;
    }

    public enum Type {

        MOVE, LOOK, JUMP, TARGET

    }

    public net.minecraft.server.v1_16_R3.PathfinderGoal getPathfinderGoal() {
        net.minecraft.server.v1_16_R3.PathfinderGoal pathfinderGoal = new net.minecraft.server.v1_16_R3.PathfinderGoal() {

            @Override
            public boolean a() {
                return onTick();
            }

            @Override
            public boolean b() {
                return pathfind();
            }

            @Override
            public void c() {
                goToLocation();
            }

            @Override
            public void d() {
                stopPathFinding();
            }
        };
        EnumSet<net.minecraft.server.v1_16_R3.PathfinderGoal.Type> a = EnumSet
                .noneOf(net.minecraft.server.v1_16_R3.PathfinderGoal.Type.class);
        for (Type type : getPathfinderGoalTypes())
            a.add(net.minecraft.server.v1_16_R3.PathfinderGoal.Type.valueOf(type.name()));
        pathfinderGoal.a(a);
        return pathfinderGoal;
    }
}