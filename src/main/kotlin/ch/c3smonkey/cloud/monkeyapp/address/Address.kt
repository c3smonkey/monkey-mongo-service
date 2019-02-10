package ch.c3smonkey.cloud.monkeyapp.customer

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.hateoas.Identifiable
import java.util.*

/**
 * Domain  Model
 */
@Document
@TypeAlias("Address") // MongoDb _class name without package
data class Address(
        @Id val addressId: String = UUID.randomUUID().toString(),
        var street: String?,
        var city: String?,
        var postalCode: String?,
        var countryCode: String?,
        var country: String?
) : Identifiable<String> {
    override fun getId(): String? {
        return addressId
    }
}
