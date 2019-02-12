package ch.c3smonkey.cloud.monkeyapp.customer

import org.springframework.data.domain.Example
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Service


/*****
 * Service Interface
 */
interface CustomerService {
    fun save(entity: Customer): Customer
    fun findById(id: String): Customer?
    fun deleteById(id: String)
    fun update(entity: Customer): Customer?
    fun exists(entity: Customer): Boolean
    fun findAll(): List<Customer>
    fun deleteAll()
}

/*****
 * Service Implementation
 */
@Service
class CustomerServiceImpl(val customerRepository: CustomerRepository,
                          val mongoTemplate: MongoTemplate) : CustomerService {

    override fun save(entity: Customer): Customer =
            customerRepository.save(entity)

    override fun findById(id: String): Customer? =
            customerRepository.findById(id).orElse(null)

    override fun deleteById(id: String) =
            customerRepository.deleteById(id)

    override fun update(entity: Customer): Customer? {
        // TODO work
//        val query = Query(Criteria.where("address.country").`is`("USA")) // TODO query where ID is....
        val query = Query(Criteria.where("customerId").`is`(entity.customerId)) // TODO query where ID is....

        val update = Update()
        update.set("address.country", "CH")
        val result = mongoTemplate.updateFirst(query, update, Customer::class.java)
        if (result != null) {
            return findById(entity.customerId!!) // TODO
        }
        return null

    }

//        return entity
    //return customerRepository.save(entity)
//    }

//    override fun update(entity: Customer): Customer =
//            customerRepository.save(entity)

    override fun exists(entity: Customer): Boolean =
            customerRepository.exists(Example.of(entity));






    //    override fun findAll(): List<Customer> =
//            customerRepository.findAll()
    override fun findAll(): List<Customer> {




        println(customerRepository.findCustomerByFirstName("John")) // TODO  work

        println(customerRepository.findCustomerByAddressCountryCode("NY"))  // TODO don`t work
        println(customerRepository.findCustomerByAddressCountry("USA")) // TODO don`t work


        return customerRepository.findAll()
    }

    override fun deleteAll() =
            customerRepository.deleteAll()

}

