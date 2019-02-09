package ch.c3smonkey.cloud.monkeyapp.customer

import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException


/*****
 * Controller
 */
@RestController
@RequestMapping(value = ["/api/customer"])
class CustomerController(var customerService: CustomerService, val customerResourceAssembler: CustomerResourceAssembler) {

    // http :8080/api/customer/list
    @GetMapping(value = ["/list"])
    fun getAll(): ResponseEntity<CustomerResources> {
        val entities = customerService.findAll()
        if (entities.isEmpty()) return ResponseEntity(HttpStatus.NO_CONTENT)
        return ResponseEntity<CustomerResources>(CustomerResources(customerResourceAssembler.toResources(entities)), HttpStatus.OK)
    }

    // http :8080/api/customer/5c5eeaf9a2d6c3062b10d5f2
    @GetMapping(value = ["/{id}"])
    fun getById(@PathVariable id: String): ResponseEntity<CustomerResource> {
        val entity = customerService.findById(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Customer Not Found")
        return ResponseEntity<CustomerResource>(customerResourceAssembler.toResource(entity), HttpStatus.OK)
    }

    // http POST :8080/api/customer/ firstName="John" lastName="Doe" age=23
    @PostMapping
    fun save(@RequestBody customer: Customer): ResponseEntity<CustomerResource> {
        if (customerService.exists(customer)) {
            return ResponseEntity<CustomerResource>(HttpStatus.CONFLICT)
        } else {
            val entity = customerService.save(customer)
            return ResponseEntity<CustomerResource>(customerResourceAssembler.toResource(entity), HttpStatus.CREATED)
        }
    }

    // http PUT :8080/api/customer/5c5eeaf9a2d6c3062b10d5f2 firstName="Jane" lastName="Doe" age=33
    @PutMapping(value = ["/{id}"])
    fun update(@PathVariable id: String, @RequestBody customer: Customer): HttpEntity<*> {
        val entity = customerService.findById(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Customer Not Found")
        entity.age = customer.age
        entity.firstName = customer.firstName
        entity.lastName = customer.lastName

        val updatedEntity = customerService.update(entity)
        return ResponseEntity<CustomerResource>(customerResourceAssembler.toResource(updatedEntity), HttpStatus.OK)
    }

    // http DELETE :8080/api/customer/5c5eeaf9a2d6c3062b10d5f2
    @DeleteMapping(value = ["/{id}"])
    fun delete(@PathVariable id: String): ResponseEntity<Any> = ResponseEntity<Any>(customerService.deleteById(id), HttpStatus.NO_CONTENT)

    // http DELETE :8080/api/customer/
    @DeleteMapping(value = [])
    fun deleteAll(): ResponseEntity<Any> = ResponseEntity<Any>(customerService.deleteAll(), HttpStatus.NO_CONTENT)

}
