package ch.c3smonkey.cloud.monkeyapp.customer

import org.springframework.data.mongodb.repository.MongoRepository

/*****
 * Repository
 */
interface CustomerRepository : MongoRepository<Customer, String>