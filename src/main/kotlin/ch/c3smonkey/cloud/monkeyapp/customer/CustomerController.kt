package ch.c3smonkey.cloud.monkeyapp.customer

import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import javax.validation.Valid


/*****
 * Controller
 */
@RestController
@RequestMapping(value = ["/api/customer"])
class CustomerController(var customerService: CustomerService, val customerResourceAssembler: CustomerResourceAssembler) {

    @GetMapping(value = ["/list"])
    fun getAll(): ResponseEntity<CustomerResources> {
        val entities = customerService.findAll()
        if (entities.isEmpty()) return ResponseEntity(HttpStatus.NO_CONTENT)
        return ResponseEntity<CustomerResources>(CustomerResources(customerResourceAssembler.toResources(entities)), HttpStatus.OK)
    }

    @GetMapping(value = ["/{id}"])
    fun getById(@PathVariable id: String): ResponseEntity<CustomerResource> {
        val entity = customerService.findById(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Customer Not Found")
        return ResponseEntity<CustomerResource>(customerResourceAssembler.toResource(entity), HttpStatus.OK)
    }

    @PostMapping
    fun save(@RequestBody @Valid customer: Customer): ResponseEntity<CustomerResource> {
        if (customerService.exists(customer)) {
            return ResponseEntity<CustomerResource>(HttpStatus.CONFLICT)
        } else {
            val entity = customerService.save(customer)
            return ResponseEntity<CustomerResource>(customerResourceAssembler.toResource(entity), HttpStatus.CREATED)
        }
    }

    @PutMapping(value = ["/{id}"])
    fun update(@PathVariable id: String, @RequestBody customer: Customer): HttpEntity<*> {
        val entity = customerService.findById(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Customer Not Found")
        entity.age = customer.age
        entity.firstName = customer.firstName
        entity.lastName = customer.lastName
//        entity.address = customer.address // TODO check solution

        val updatedEntity = customerService.update(entity)
        return ResponseEntity<CustomerResource>(customerResourceAssembler.toResource(updatedEntity), HttpStatus.OK)
    }

    @DeleteMapping(value = ["/{id}"])
    fun delete(@PathVariable id: String): ResponseEntity<Any> = ResponseEntity<Any>(customerService.deleteById(id), HttpStatus.NO_CONTENT)

    @DeleteMapping(value = [])
    fun deleteAll(): ResponseEntity<Any> = ResponseEntity<Any>(customerService.deleteAll(), HttpStatus.NO_CONTENT)

}
