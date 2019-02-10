package ch.c3smonkey.cloud.monkeyapp.customer

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.hateoas.Identifiable
import java.util.*
import javax.validation.constraints.NotBlank

/**
 * Domain  Model
 */
@Document
@TypeAlias("Customer") // MongoDb _class name without package
data class Customer(
        @Id val customerId: String? = UUID.randomUUID().toString(),
        @get: NotBlank(message = "{firstName.required}")
        var firstName: String?,
        @get: NotBlank(message = "{lastName.required}")
        var lastName: String?,
        var age: Int?,
        var address: Address?
) : Identifiable<String> {
    override fun getId(): String? {
        return customerId
    }
}

