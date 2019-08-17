package com.dreamteam.powerofwar.game.event;

import com.dreamteam.powerofwar.game.GameProgram;

/**
 * Событие которое инициируется пользователем для влияния на игровой процесс.
 */
public interface Event {

    /**
     * Исполнение события.
     *
     * @param gameProgram игровой процесс, на которыйц долно повлиять событие.
     */
    void execute(GameProgram gameProgram);
}
