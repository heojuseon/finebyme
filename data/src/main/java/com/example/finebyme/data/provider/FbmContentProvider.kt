package com.example.finebyme.data.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.util.Log
import androidx.room.Room
import com.example.finebyme.data.datasource.db.AppDatabase
import com.example.finebyme.data.datasource.db.FavoritePhotoDAO

// Defines the database name
private const val DBNAME = "photo.db"
class FbmContentProvider: ContentProvider() {
    companion object {
        private const val AUTHORITY = "com.example.finebyme.data.provider.FbmContentProvider" // A 앱의 패키지에 맞게 설정
        val CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY/photos")
    }

    // Defines a handle to the Room database
    private lateinit var appDatabase: AppDatabase
    // Defines a Data Access Object to perform the database operations
    private var favoritePhotoDAO: FavoritePhotoDAO? = null

    override fun onCreate(): Boolean {
        // Creates a new database object
        appDatabase = Room.databaseBuilder(
            context!!,
            AppDatabase::class.java,
            DBNAME
        )
            .allowMainThreadQueries() // 액티비티에서 TEST 하기 위함(메인스레드에서 쿼리 허용)
            .build()

        // Gets a Data Access Object to perform the database operations
        favoritePhotoDAO = appDatabase.favoritePhotoDao()

        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        Log.d("FbmContentProvider", "query() 호출됨! URI: $uri")
        return when (sUriMatcher.match(uri)) {
            1 -> {
                val cursor = appDatabase.favoritePhotoDao().getAllCursor()
                Log.d("FbmContentProvider", "Cursor count==============> ${cursor.count}")

                cursor.setNotificationUri(context?.contentResolver, uri)
                cursor
            }
            else -> {
                Log.e("FbmContentProvider", "Unknown URI: $uri")
                throw IllegalArgumentException("Unknown URI $uri")
            }
        }
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        return 0
    }

    private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        /*
         * The calls to addURI() go here for all the content URI patterns that the provider
         * recognizes. For this snippet, only the calls for table 3 are shown.
         */

        /*
         * Sets the integer value for multiple rows in table 3 to 1. Notice that no wildcard is used
         * in the path.
         */
//        addURI("com.example.finebyme.data.provider.FbmContentProvider", "photos", 1)
        addURI(AUTHORITY, "photos", 1)
    }
}