package programs;

import com.battle.heroes.army.Unit;
import com.battle.heroes.army.programs.SuitableForAttackUnitsFinder;

import java.util.*;

public class SuitableForAttackUnitsFinderImpl implements SuitableForAttackUnitsFinder {


    @Override
    public List<Unit> getSuitableUnits(List<List<Unit>> unitsByRow, boolean isLeftArmyTarget) {
        List<Unit> suitableUnits = new ArrayList<>();

        int direction = isLeftArmyTarget ? -1 : 1;

        for (int rowIndex = 0; rowIndex < unitsByRow.size(); rowIndex++) {
            List<Unit> row = unitsByRow.get(rowIndex);
            Unit edgeUnit = isLeftArmyTarget ? getRightmostUnit(row) : getLeftmostUnit(row);

            if (edgeUnit != null && edgeUnit.isAlive()) {
                int blockingRow = rowIndex + direction;
                boolean isBlocked = blockingRow >= 0 && blockingRow < unitsByRow.size() && unitsByRow.get(blockingRow).stream().anyMatch(otherUnit -> otherUnit.getyCoordinate() == edgeUnit.getyCoordinate() && otherUnit.isAlive());

                if (!isBlocked) {
                    suitableUnits.add(edgeUnit);
                }
            }
        }


        if (suitableUnits.isEmpty()) {
            System.out.println("No enemy units to attack");
        }

        return suitableUnits;
    }


    private Unit getRightmostUnit(List<Unit> row) {
        for (int i = row.size() - 1; i >= 0; i--) {
            Unit unit = row.get(i);
            if (unit != null && unit.isAlive()) {
                return unit;
            }
        }
        return null;
    }


    private Unit getLeftmostUnit(List<Unit> row) {
        for (Unit unit : row) {
            if (unit != null && unit.isAlive()) {
                return unit;
            }
        }
        return null;
    }
}