package ch.c3smonkey.cloud.monkeyapp.department

import ch.c3smonkey.cloud.monkeyapp.customer.Customer
import org.springframework.hateoas.ResourceSupport
import org.springframework.hateoas.Resources
import org.springframework.hateoas.core.Relation
import org.springframework.hateoas.mvc.IdentifiableResourceAssemblerSupport
import org.springframework.stereotype.Component
import java.util.*

@Relation(collectionRelation = "realms")
class RealmResource(
        val realmId: String? = UUID.randomUUID().toString(),
        var name: String?,
        var description: String?,
        var customers: List<Customer>?

) : ResourceSupport() {}


// TODO this class must be open (Cannot subclass final class)
open class RealmResources(realm: Iterable<RealmResource>) : Resources<RealmResource>(realm) {
    val total = content.size
}


@Component
class RealmResourceAssembler : IdentifiableResourceAssemblerSupport<Realm, RealmResource>(RealmController::class.java, RealmResource::class.java) {

    override fun toResource(entity: Realm?): RealmResource = createResource(entity)

    override fun instantiateResource(entity: Realm): RealmResource =
            RealmResource(
                    realmId = entity.realmId,
                    description = entity.description,
                    name = entity.name,
                    customers = entity.customers
            )

}

