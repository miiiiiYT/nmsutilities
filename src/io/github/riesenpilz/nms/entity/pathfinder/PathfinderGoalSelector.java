package io.github.riesenpilz.nms.entity.pathfinder;

public class PathfinderGoalSelector {
    private final net.minecraft.server.v1_16_R3.PathfinderGoalSelector pathfinderGoalSelector;

    public PathfinderGoalSelector(net.minecraft.server.v1_16_R3.PathfinderGoalSelector pathfinderGoalSelector) {
        this.pathfinderGoalSelector = pathfinderGoalSelector;
    }

    public void addPathFinderGoal(PathfinderGoal pathfinderGoal) {
        pathfinderGoalSelector.a(pathfinderGoal.getPathfinderGoal());
    }

    public void addPathFinderGoal(int priority, PathfinderGoal pathfinderGoal) {
        pathfinderGoalSelector.a(priority, pathfinderGoal.getPathfinderGoal());
    }
    public void addPathFinderGoal(net.minecraft.server.v1_16_R3.PathfinderGoal pathfinderGoal) {
        pathfinderGoalSelector.a(pathfinderGoal);
    }

    public void addPathFinderGoal(int priority, net.minecraft.server.v1_16_R3.PathfinderGoal pathfinderGoal) {
        pathfinderGoalSelector.a(priority, pathfinderGoal);
    }
}