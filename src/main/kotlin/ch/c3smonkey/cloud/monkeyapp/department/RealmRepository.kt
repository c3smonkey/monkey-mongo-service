package ch.c3smonkey.cloud.monkeyapp.department

import org.springframework.data.mongodb.repository.MongoRepository

/*****
 * Repository
 */
interface RealmRepository : MongoRepository<Realm, String>
