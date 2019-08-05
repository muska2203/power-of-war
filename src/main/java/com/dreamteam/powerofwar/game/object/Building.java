package com.dreamteam.powerofwar.game.object;

/**
 * Строение. Имеет собственные характеристики на производство чего-либо (юнитов, ресурсов и тд..)
 */
public class Building extends StaticGameObject {

    private BuildingType buildingType;

    public Building(double x, double y, BuildingType buildingType) {
        super(x, y, StaticGameObjectType.BUILDING);
        this.buildingType = buildingType;
    }

    public BuildingType getBuildingType() {
        return buildingType;
    }
}
