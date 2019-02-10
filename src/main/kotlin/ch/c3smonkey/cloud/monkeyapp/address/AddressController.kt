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
@RequestMapping(value = ["/api/address"])
class AddressController(var addressService: AddressService, val addressResourceAssembler: AddressResourceAssembler) {

    @GetMapping(value = ["/list"])
    fun getAll(): ResponseEntity<AddressResources> {
        val entities = addressService.findAll()
        if (entities.isEmpty()) return ResponseEntity(HttpStatus.NO_CONTENT)
        return ResponseEntity<AddressResources>(AddressResources(addressResourceAssembler.toResources(entities)), HttpStatus.OK)
    }

    @GetMapping(value = ["/{id}"])
    fun getById(@PathVariable id: String): ResponseEntity<AddressResource> {
        val entity = addressService.findById(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Address Not Found")
        return ResponseEntity<AddressResource>(addressResourceAssembler.toResource(entity), HttpStatus.OK)
    }

    @PostMapping
    fun save(@RequestBody address: Address): ResponseEntity<AddressResource> {
        if (addressService.exists(address)) {
            return ResponseEntity<AddressResource>(HttpStatus.CONFLICT)
        } else {
            val entity = addressService.save(address)
            return ResponseEntity<AddressResource>(addressResourceAssembler.toResource(entity), HttpStatus.CREATED)
        }
    }

    @PutMapping(value = ["/{id}"])
    fun update(@PathVariable id: String, @RequestBody address: Address): HttpEntity<*> {
        val entity = addressService.findById(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Address Not Found")
        entity.street = address.street
        entity.city = address.city
        entity.postalCode = address.postalCode
        entity.countryCode = address.countryCode
        entity.country = address.country

        val updatedEntity = addressService.update(entity)
        return ResponseEntity<AddressResource>(addressResourceAssembler.toResource(updatedEntity), HttpStatus.OK)
    }

    @DeleteMapping(value = ["/{id}"])
    fun delete(@PathVariable id: String): ResponseEntity<Any> = ResponseEntity<Any>(addressService.deleteById(id), HttpStatus.NO_CONTENT)

    @DeleteMapping(value = [])
    fun deleteAll(): ResponseEntity<Any> = ResponseEntity<Any>(addressService.deleteAll(), HttpStatus.NO_CONTENT)

}