package com.lamti.myapplication.data.database

import androidx.room.*
import com.lamti.myapplication.data.repository.Pokemon
import kotlinx.coroutines.flow.Flow

@Database(
    entities = [PokemonEntity::class],
    version = 1,
    exportSchema = true,
)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}

@Dao
interface PokemonDao {

    @Query(value = "SELECT * FROM pokemon")
    fun getPokemonListStream(): Flow<List<PokemonEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrIgnorePokemons(pokemonEntities: List<PokemonEntity>)
}

@Entity(tableName = "pokemon")
data class PokemonEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    @ColumnInfo(name = "image_url")
    val imageUrl: String,
    @ColumnInfo(defaultValue = "")
    val type1: String,
    val type2: String?
)

fun PokemonEntity.asExternalModel() = Pokemon(
    name = name,
    code = id,
    image = imageUrl,
    type1 = type1,
    type2 = type2
)