package io.github.riesenpilz.nmsUtilities.entity.pathfinder;

public class PathfinderGoalSelector {
    private final net.minecraft.server.v1_16_R3.PathfinderGoalSelector nms;

    public PathfinderGoalSelector(net.minecraft.server.v1_16_R3.PathfinderGoalSelector nms) {
        this.nms = nms;
    }

    public void addPathFinderGoal(PathfinderGoal pathfinderGoal) {
        nms.a(pathfinderGoal.getPathfinderGoal());
    }

    public void addPathFinderGoal(int priority, PathfinderGoal pathfinderGoal) {
        nms.a(priority, pathfinderGoal.getPathfinderGoal());
    }
    public void addPathFinderGoal(net.minecraft.server.v1_16_R3.PathfinderGoal pathfinderGoal) {
        nms.a(pathfinderGoal);
    }

    public void addPathFinderGoal(int priority, net.minecraft.server.v1_16_R3.PathfinderGoal pathfinderGoal) {
        nms.a(priority, pathfinderGoal);
    }
}