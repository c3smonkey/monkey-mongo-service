package ch.c3smonkey.cloud.monkeyapp.customer

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.hateoas.Identifiable
import java.util.*

/**
 * Domain  Model
 */
@Document
data class Customer(
        @Id val customerId: String = UUID.randomUUID().toString(),
        var firstName: String?,
        var lastName: String?,
        var age: Int?
) : Identifiable<String> {
    override fun getId(): String? {
        return customerId
    }
}

