package ch.c3smonkey.cloud.monkeyapp.department

import ch.c3smonkey.cloud.monkeyapp.customer.Customer
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.hateoas.Identifiable
import java.util.*

/**
 * Domain  Model
 */
@Document
@TypeAlias("Realm")
data class Realm(
        @Id val realmId: String? = UUID.randomUUID().toString(),
        var name: String?,
        var description: String?,
        var customers: List<Customer>?
) : Identifiable<String> {
    override fun getId(): String? {
        return realmId
    }
}

