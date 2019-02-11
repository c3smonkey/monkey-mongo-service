package ch.c3smonkey.cloud.monkeyapp.customer

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

/*****
 * Repository
 */
interface CustomerRepository : MongoRepository<Customer, String>{

    fun findByFirstName(name: String): List<Customer>

    @Query("{'customer.address.countryCode': ?0}")
    fun findByAddressCountryCode(address: String): List<Customer>

    @Query("{'customer.address.country': ?0}")
    fun findByAddressCountry(address: String): List<Customer>

}