package us.smt.turizm.data.database.remote

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import us.smt.turizm.domen.model.PlaceDetails

class FireBaseHelper(private val db: FirebaseFirestore) {
    private val collectionName = "Place"

    companion object {
        private var instance: FireBaseHelper? = null
        fun inject(db: FirebaseFirestore) {
            instance = FireBaseHelper(db)
        }

        fun getInstance() = instance!!
    }

    suspend fun getAllData(): List<PlaceDetails> {
        return try {
            db.collection(collectionName)
                .get()
                .await()
                .documents
                .mapNotNull { document ->
                    val data = document.data // Fetch the raw data as a Map
                    if (data != null) {
                        PlaceDetails(
                            id = document.id,
                            name = data["name"] as? String ?: "",
                            address = data["address"] as? String ?: "",
                            about = data["about"] as? String ?: "",
                            isCanBron = data["isCanBron"] as? Boolean ?: false,
                            service = data["service"] as? String ?: "",
                            distance = data["distance"] as? Double ?: 0.0,
                            image = data["image"] as? Int ?: 0,
                            imageLink = data["imageLink"] as? String,
                            isFavourite = data["isFavourite"] as? Boolean ?: false,
                            long = data["long"] as? Double,
                            lat = data["lat"] as? Double,
                            rating = data["rating"] as? Double
                        )
                    } else {
                        null // Skip documents with no data
                    }
                }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    suspend fun getPlaceById(id: String): PlaceDetails? {
        return try {
          val document=  db.collection(collectionName)
                .document(id)
                .get()
                .await()
            val data = document.data
            if (data != null) {
                PlaceDetails(
                    id = document.id,
                    name = data["name"] as? String ?: "",
                    address = data["address"] as? String ?: "",
                    about = data["about"] as? String ?: "",
                    isCanBron = data["isCanBron"] as? Boolean ?: false,
                    service = data["service"] as? String ?: "",
                    distance = data["distance"] as? Double ?: 0.0,
                    image = data["image"] as? Int ?: 0,
                    imageLink = data["imageLink"] as? String,
                    isFavourite = data["isFavourite"] as? Boolean ?: false,
                    long = data["long"] as? Double,
                    lat = data["lat"] as? Double,
                    rating = data["rating"] as? Double
                )
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun updatePlace(place: PlaceDetails): Boolean {
        return try {
            place.id.let {
                val mapData:Map<String,Any?> = mutableMapOf(
                    "name" to place.name,
                    "about" to place.about,
                    "address" to place.address,
                    "distance" to place.distance,
                    "image" to place.image,
                    "imageLink" to place.imageLink,
                    "long" to place.long,
                    "lat" to place.lat,
                    "rating" to place.rating,
                    "service" to place.service,
                    "isFavourite" to place.isFavourite,
                    "isCanBron" to place.isCanBron
                )
                db.collection(collectionName)
                    .document(it)
                    .update(mapData)
                    .await()
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun addNewItem(data: PlaceDetails) {
        val mapData = hashMapOf(
            "name" to data.name,
            "about" to data.about,
            "address" to data.address,
            "distance" to data.distance,
            "image" to data.image,
            "imageLink" to data.imageLink,
            "long" to data.long,
            "lat" to data.lat,
            "rating" to data.rating,
            "service" to data.service,
            "isFavourite" to data.isFavourite,
            "isCanBron" to data.isCanBron
        )
        db.collection(collectionName).add(mapData).await()

    }
}