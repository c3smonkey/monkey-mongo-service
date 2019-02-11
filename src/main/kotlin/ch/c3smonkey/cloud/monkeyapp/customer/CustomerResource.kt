package ch.c3smonkey.cloud.monkeyapp.customer

import ch.c3smonkey.cloud.monkeyapp.address.Address
import org.springframework.hateoas.ResourceSupport
import org.springframework.hateoas.Resources
import org.springframework.hateoas.core.Relation
import org.springframework.hateoas.mvc.IdentifiableResourceAssemblerSupport
import org.springframework.stereotype.Component
import java.util.*

@Relation(collectionRelation = "customers")
class CustomerResource(
        val customerId: String? = UUID.randomUUID().toString(),
        var firstName: String?,
        var lastName: String?,
        var age: Int?,
        var address: Address?
) : ResourceSupport(){}


open class CustomerResources(customer: Iterable<CustomerResource>) : Resources<CustomerResource>(customer) {
    val total = content.size
}


@Component
class CustomerResourceAssembler : IdentifiableResourceAssemblerSupport<Customer, CustomerResource>(CustomerController::class.java, CustomerResource::class.java) {

    override fun toResource(entity: Customer?): CustomerResource  = createResource(entity)

    override fun instantiateResource(entity: Customer): CustomerResource =
            CustomerResource(
                    customerId = entity.customerId,
                    firstName = entity.firstName,
                    lastName = entity.lastName,
                    age = entity.age,
                    address = entity.address
            )
}
