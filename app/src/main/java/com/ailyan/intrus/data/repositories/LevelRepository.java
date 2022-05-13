package com.ailyan.intrus.data.repositories;

import java.util.ArrayList;
import java.util.List;

public class LevelRepository {

    public List<Integer> loadAllLevels() {
        List<Integer> levels = new ArrayList<>();
        levels.add(0);
        levels.add(1);
        levels.add(2);
        levels.add(3);
        return levels;
    }
}
