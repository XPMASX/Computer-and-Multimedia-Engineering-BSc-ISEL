// File: MyGameDao.kt
package dam.a48965.project_48965.domain

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dam.a48965.project_48965.model.Game
import dam.a48965.project_48965.model.MyGame
import dam.a48965.project_48965.model.MyGameCover
import dam.a48965.project_48965.model.RecGameApi

@Dao
interface MyGameDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(MyGame: MyGame)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecGame(recGameApi: RecGameApi)

    @Query("SELECT * FROM myGame WHERE MyGame_id = :id")
    fun getMyGame(id: Int): MyGame?

    @Query("SELECT * FROM rec_game_api WHERE id = :id")
    fun getRecGameById(id: Int): RecGameApi?

    @Query("UPDATE rec_game_api SET screenshotsUrls = :screenshotUrl WHERE id = :id")
    suspend fun updateScreenshotUrl(id: Int, screenshotUrl: List<String>)

    @Query("DELETE FROM MyGame WHERE MyGame_id = :id")
    fun deleteMyGame(id: Int)

    @Query("SELECT MyGame_id FROM MyGame WHERE MyGame_played = 1")
    fun getAllMyPlayedIds(): List<Int>

    @Query("SELECT MyGame_id FROM MyGame WHERE MyGame_backlog = 1")
    fun getAllMyBacklogdIds(): List<Int>

    @Query("SELECT * FROM rec_game_api")
    fun getAllRecGames(): List<RecGameApi>

    @Query("SELECT * FROM Game WHERE game_name LIKE :query LIMIT 10")
    fun searchGames(query: String): List<Game>

    @Query("""
    SELECT game.game_id as id, game.game_imgUrl as imageUrl, game.game_name as name
    FROM GAME
    WHERE Game.game_id = :Id
            """)
    fun getAllMyGamesByUserId(Id: Int): List<MyGameCover>

    @Query("SELECT * FROM MyGame WHERE MyGame_id = :id AND user_id = :userId")
    suspend fun getMyGameByIdAndUserId(id: Int, userId: String): MyGame?

    @Query("SELECT COUNT(*) FROM MyGame WHERE MyGame_played = 1")
    fun countPlayedGames(): Int

    @Query("SELECT COUNT(*) FROM MyGame WHERE MyGame_backlog = 1")
    fun countBacklogGames(): Int

    @Query("SELECT EXISTS(SELECT 1 FROM rec_game_api WHERE id = :gameId)")
    suspend fun isGameExists(gameId: Int): Boolean
}