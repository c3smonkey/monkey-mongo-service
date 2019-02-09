package ch.c3smonkey.cloud.monkeyapp.customer

import org.springframework.data.domain.Example
import org.springframework.stereotype.Service

/*****
 * Service Interface
 */
interface CustomerService {
    fun save(entity: Customer): Customer
    fun findById(id: String): Customer?
    fun deleteById(id: String)
    fun update(entity: Customer): Customer
    fun exists(entity: Customer): Boolean
    fun findAll(): List<Customer>
    fun deleteAll()
}


/*****
 * Service Implementation
 */
@Service
class CustomerServiceImpl(val customerRepository: CustomerRepository) : CustomerService {

    override fun save(entity: Customer): Customer =
            customerRepository.save(entity)

    override fun findById(id: String): Customer? =
            customerRepository.findById(id).orElse(null)

    override fun deleteById(id: String) =
            customerRepository.deleteById(id)

    override fun update(entity: Customer): Customer =
            customerRepository.save(entity)

    override fun exists(entity: Customer): Boolean =
            customerRepository.exists(Example.of(entity));

    override fun findAll(): List<Customer> =
            customerRepository.findAll()

    override fun deleteAll() =
            customerRepository.deleteAll()

}
