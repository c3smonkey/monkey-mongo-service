package ch.c3smonkey.cloud.monkeyapp

import ch.c3smonkey.cloud.monkeyapp.customer.Address
import ch.c3smonkey.cloud.monkeyapp.customer.Customer
import ch.c3smonkey.cloud.monkeyapp.customer.CustomerController
import ch.c3smonkey.cloud.monkeyapp.customer.CustomerRepository
import ch.c3smonkey.cloud.monkeyapp.department.Realm
import ch.c3smonkey.cloud.monkeyapp.department.RealmController
import ch.c3smonkey.cloud.monkeyapp.department.RealmRepository
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.hateoas.ResourceSupport
import org.springframework.hateoas.mvc.ControllerLinkBuilder
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@SpringBootApplication
class MonkeyAppApplication

fun main(args: Array<String>) {
    runApplication<MonkeyAppApplication>(*args)
}


@CrossOrigin(origins = arrayOf("*"), maxAge = 3600)
@RestController
class RootController {

    @GetMapping(value = ["/"])
    fun root(): ResponseEntity<ResourceSupport> =
            ResponseEntity.ok<ResourceSupport>(
                    ResourceSupport().apply {
                        add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(CustomerController::class.java).getAll()).withRel("get-all-customers"))
                        add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(RealmController::class.java).getAll()).withRel("get-all-realms"))
                        add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(RootController::class.java).root()).withSelfRel())
                    }
            )
}




@Component
class DataLoader(val customerRepository: CustomerRepository, val realmRepository: RealmRepository) : ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        customerRepository.deleteAll()
        realmRepository.deleteAll()

        val e = customerRepository.save(Customer(firstName = "John", lastName = "Doe", age = 33,
                address = Address(
                        street = "4 Pennsylvania Plaza",
                        postalCode = "10001",
                        city = "New York",
                        country = "USA",
                        countryCode = "NY"

                )))
        realmRepository.save(Realm(name = "Service Department", description = "Service Rocks!",
                customers = listOf(e)))
    }
}














