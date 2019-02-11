package ch.c3smonkey.cloud.monkeyapp.address

import ch.c3smonkey.cloud.monkeyapp.address.AddressRepository
import org.springframework.data.domain.Example
import org.springframework.stereotype.Service

/*****
 * Service Interface
 */
interface AddressService {
    fun save(entity: Address): Address
    fun findById(id: String): Address?
    fun deleteById(id: String)
    fun update(entity: Address): Address
    fun exists(entity: Address): Boolean
    fun findAll(): List<Address>
    fun deleteAll()
}


/*****
 * Service Implementation
 */
@Service
class AddressServiceImpl(val addressRepository: AddressRepository) : AddressService {

    override fun save(entity: Address): Address =
            addressRepository.save(entity)

    override fun findById(id: String): Address? =
            addressRepository.findById(id).orElse(null)

    override fun deleteById(id: String) =
            addressRepository.deleteById(id)

    override fun update(entity: Address): Address =
            addressRepository.save(entity)

    override fun exists(entity: Address): Boolean =
            addressRepository.exists(Example.of(entity));

    override fun findAll(): List<Address> =
            addressRepository.findAll()

    override fun deleteAll() =
            addressRepository.deleteAll()

}
