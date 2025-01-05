import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.gadgetlist.data.Good
import com.example.gadgetlist.data.GoodDao
import com.example.gadgetlist.data.InventoryDatabase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import kotlin.jvm.Throws

@RunWith(AndroidJUnit4::class)
class GoosTestDao(){
    private lateinit var goodDao:GoodDao
    private lateinit var inventoryDatabase: InventoryDatabase
    @Before
    fun createDb(){
        val context: Context = ApplicationProvider.getApplicationContext()
        inventoryDatabase= Room.inMemoryDatabaseBuilder(
            context,InventoryDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
        goodDao=inventoryDatabase.goodDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb(){
        inventoryDatabase.close()
    }
    private val good1 =Good(
        id = 1,
        name = "iPhone 11",
        category = "Phone",
        price = 1300,
        quantity = 10,
        description = "expensive iphone"
    )

    private val good2 =Good(
        id = 2,
        name = "samsung phone 20",
        category = "Phone",
        price = 1200,
        quantity = 20,
        description = "inexpensive iphone"
    )
    private suspend fun addOneGood(){
        goodDao.insertGood(good1)
    }
    private suspend fun addAllGood(){
        goodDao.insertGood(good1)
        goodDao.insertGood(good2)
    }
    @Test
    @Throws(Exception::class)
    fun DaoInsertTest1() = runBlocking{
        addOneGood()
        val allGoods = goodDao.getAllGoods().first()
        assertEquals(allGoods[0],good1)
    }

    @Test
    @Throws(Exception::class)
    fun DaoInsertTest2() = runBlocking{
        addAllGood()
        val allGoods = goodDao.getAllGoods().first()
        assertEquals(allGoods[0],good1)
        assertEquals(allGoods[1],good2)
    }
}