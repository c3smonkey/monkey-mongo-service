package ch.c3smonkey.cloud.monkeyapp.customer

import org.springframework.data.mongodb.repository.MongoRepository

/*****
 * Repository
 */
interface AddressRepository : MongoRepository<Address, String>