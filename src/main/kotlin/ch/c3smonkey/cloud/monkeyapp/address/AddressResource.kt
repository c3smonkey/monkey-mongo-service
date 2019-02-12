//package ch.c3smonkey.cloud.monkeyapp.address
//
//import ch.c3smonkey.cloud.monkeyapp.address.AddressController
//import org.springframework.hateoas.ResourceSupport
//import org.springframework.hateoas.Resources
//import org.springframework.hateoas.core.Relation
//import org.springframework.hateoas.mvc.IdentifiableResourceAssemblerSupport
//import org.springframework.stereotype.Component
//import java.util.*
//
//@Relation(collectionRelation = "addresses")
//class AddressResource(
//        val addressId: String = UUID.randomUUID().toString(),
//        var street: String?,
//        var city: String?,
//        var postalCode: String?,
//        var countryCode: String?,
//        var country: String?
//) : ResourceSupport() {}
//
//
//open class AddressResources(address: Iterable<AddressResource>) : Resources<AddressResource>(address) {
//    val total = content.size
//}
//
//
//@Component
//class AddressResourceAssembler : IdentifiableResourceAssemblerSupport<Address, AddressResource>(AddressController::class.java, AddressResource::class.java) {
//
//    override fun toResource(entity: Address?): AddressResource = createResource(entity)
//
//    override fun instantiateResource(entity: Address): AddressResource =
//            AddressResource(
//                    addressId = entity.addressId,
//                    street = entity.street,
//                    city = entity.city,
//                    postalCode = entity.postalCode,
//                    countryCode = entity.countryCode,
//                    country = entity.country
//
//            )
//}
//
