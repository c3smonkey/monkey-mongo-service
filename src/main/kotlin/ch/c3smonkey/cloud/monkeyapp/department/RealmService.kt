package ch.c3smonkey.cloud.monkeyapp.department

import org.springframework.data.domain.Example
import org.springframework.stereotype.Service

/*****
 * Service Interface
 */
interface RealmService {
    fun save(entity: Realm): Realm
    fun findById(id: String): Realm?
    fun deleteById(id: String)
    fun update(entity: Realm): Realm
    fun exists(entity: Realm): Boolean
    fun findAll(): List<Realm>
    fun deleteAll()
}


/*****
 * Service Implementation
 */
@Service
class DepartmentServiceImpl(val realmRepository: RealmRepository) : RealmService {

    override fun save(entity: Realm): Realm =
            realmRepository.save(entity)

    override fun findById(id: String): Realm? =
            realmRepository.findById(id).orElse(null)

    override fun deleteById(id: String) =
            realmRepository.deleteById(id)

    override fun update(entity: Realm): Realm =
            realmRepository.save(entity)

    override fun exists(entity: Realm): Boolean = realmRepository.exists(Example.of(entity));

    override fun findAll(): List<Realm> =
            realmRepository.findAll()

    override fun deleteAll() =
            realmRepository.deleteAll()

}
