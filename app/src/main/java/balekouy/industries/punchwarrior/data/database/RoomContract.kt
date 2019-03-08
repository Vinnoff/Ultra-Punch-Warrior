package balekouy.industries.punchwarrior.data.database

class RoomContract {
    companion object {
        const val DATABASE_NAME = "ultrapunchwarrior.db"
        const val TABLE_FIGHTER = "fighterId"
        const val TABLE_LEVEL = "level"
        const val TABLE_SCORE = "score"

        private const val SELECT_FROM = "SELECT * FROM "
        private const val UPDATE = "UPDATE "
        private const val WHERE_NAME_EQUALS = " WHERE name="
        private const val WHERE_ID_EQUALS = " WHERE id="
        private const val UNLOCK_LEVEL = " SET unlocked = 1"
        const val LIMIT_ONE = " LIMIT 1"

        const val SELECT_ALL_FIGHTERS = SELECT_FROM + TABLE_FIGHTER
        const val SELECT_ALL_LEVELS = SELECT_FROM + TABLE_LEVEL
        const val SELECT_ALL_SCORES = SELECT_FROM + TABLE_SCORE
        const val SELECT_FIGHTER_BY_NAME = SELECT_FROM + TABLE_FIGHTER + WHERE_NAME_EQUALS
        const val SELECT_FIGHTER_BY_ID = SELECT_FROM + TABLE_FIGHTER + WHERE_ID_EQUALS
        const val SELECT_LEVEL_BY_ID = SELECT_FROM + TABLE_LEVEL + WHERE_ID_EQUALS
        const val UNLOCK_LEVEL_WITH_ID = UPDATE + TABLE_LEVEL + UNLOCK_LEVEL + WHERE_ID_EQUALS

    }
}

