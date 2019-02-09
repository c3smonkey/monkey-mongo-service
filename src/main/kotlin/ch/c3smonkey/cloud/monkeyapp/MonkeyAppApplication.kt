package ch.c3smonkey.cloud.monkeyapp

import ch.c3smonkey.cloud.monkeyapp.department.Realm
import ch.c3smonkey.cloud.monkeyapp.department.RealmRepository
import ch.c3smonkey.cloud.monkeyapp.customer.Customer
import ch.c3smonkey.cloud.monkeyapp.customer.CustomerRepository
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Component


@SpringBootApplication
class MonkeyAppApplication

fun main(args: Array<String>) {
    runApplication<MonkeyAppApplication>(*args)
}

@Component
class DataLoader(val customerRepository: CustomerRepository, val realmRepository: RealmRepository) : ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        customerRepository.deleteAll()
        realmRepository.deleteAll()

        val e = customerRepository.save(Customer(firstName = "John", lastName = "Doe", age = 33))
        realmRepository.save(Realm(name = "Service Department", description = "Service Rocks!",
                customers = listOf(e)))
    }
}






















