package balekouy.industries.punchwarrior.data.database

class RoomContract {
    companion object {
        const val DATABASE_NAME = "ultrapunchwarrior.db"
        const val TABLE_FIGHTER = "fighter"
        const val TABLE_PLACE = "place"
        const val TABLE_SCORES = "score"

        private const val SELECT_FROM = "SELECT * FROM "
        private const val WHERE_NAME_EQUALS = " WHERE name="
        private const val WHERE_ID_EQUALS = " WHERE id="
        const val LIMIT_ONE = " LIMIT 1"

        const val SELECT_ALL_FIGHTERS = SELECT_FROM + TABLE_FIGHTER
        const val SELECT_ALL_PLACES = SELECT_FROM + TABLE_PLACE
        const val SELECT_FIGHTER_BY_NAME = SELECT_FROM + TABLE_FIGHTER + WHERE_NAME_EQUALS
        const val SELECT_FIGHTER_BY_ID = SELECT_FROM + TABLE_FIGHTER + WHERE_ID_EQUALS
        const val SELECT_PLACE_BY_NAME = SELECT_FROM + TABLE_PLACE + WHERE_NAME_EQUALS
        const val SELECT_PLACE_BY_ID = SELECT_FROM + TABLE_PLACE + WHERE_ID_EQUALS
        const val SELECT_SCORES = SELECT_FROM + TABLE_SCORES
    }
}

