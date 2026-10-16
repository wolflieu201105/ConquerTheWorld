package com.conquertheworld.game.content.definition;

import com.conquertheworld.game.config.GameConstants;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

final class FightingRoomDefinitionTest {
    @Test
    void parsesAndCopiesAReachableAuthoredRoom() {
        FightingRoomDefinition room = room(
            "#####",
            "#P.E#",
            "#...#",
            "#...#",
            "#####"
        );

        assertEquals(5, room.getRows());
        assertEquals(5, room.getColumns());
        assertEquals(GameConstants.FLOOR_DRAW_SIZE, room.getPlayerSpawn().x());
        assertEquals(1, room.getEnemySpawns().size());

        int[][] firstCopy = room.copyMap();
        firstCopy[1][1] = FightingRoomDefinition.WALL;
        assertNotEquals(firstCopy[1][1], room.copyMap()[1][1]);
        assertEquals(
            room.tileVariant(2, 2, 9, 17),
            room.tileVariant(2, 2, 9, 17)
        );
    }

    @Test
    void rejectsOpenBoundariesDuplicateSpawnsAndUnreachableFloor() {
        assertThrows(IllegalStateException.class, () -> room(
            "##.##",
            "#P.E#",
            "#...#",
            "#...#",
            "#####"
        ));
        assertThrows(IllegalStateException.class, () -> room(
            "#####",
            "#P.P#",
            "#.E.#",
            "#...#",
            "#####"
        ));
        assertThrows(IllegalStateException.class, () -> room(
            "#######",
            "#P#...#",
            "#.#.E.#",
            "#.#...#",
            "#######"
        ));
    }

    @Test
    void loadsAllShippedFightingRoomsThroughTheRegistry() {
        GameDataRegistry registry = GameDataRegistry.get();

        assertEquals(3, registry.getFightingRooms().size());
        assertEquals("Training Yard", registry.getFightingRoom("training_yard").getDisplayName());
    }

    private static FightingRoomDefinition room(String... rows) {
        return new FightingRoomDefinition("test_room", "Test Room", Arrays.asList(rows));
    }
}
