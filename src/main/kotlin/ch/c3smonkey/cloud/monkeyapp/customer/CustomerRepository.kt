package ch.c3smonkey.cloud.monkeyapp.customer

import org.springframework.data.mongodb.repository.MongoRepository

/*****
 * Repository
 */
interface CustomerRepository : MongoRepository<Customer, String> {

    fun findCustomerByFirstName(name: String): List<Customer>

    fun findCustomerByAddressCountryCode(address: String): List<Customer>

    fun findCustomerByAddressCountry(address: String): List<Customer>

// QuerydslPredicateExecutor<Customer>
//    @Query("{'customer.address.country': ?0}")
//    fun findByAddressCountry(address: String): List<Customer>

}