package balekouy.industries.punchwarrior.data.repository.fighters

import balekouy.industries.punchwarrior.data.database.entity.EFighter
import io.reactivex.Single

interface FighterRepository {
    fun getAllFighters(): Single<List<EFighter>>
    fun getFighterId(fighterName: String): Single<Int?>
    fun getFighterById(fighterId: Int): Single<EFighter>
}