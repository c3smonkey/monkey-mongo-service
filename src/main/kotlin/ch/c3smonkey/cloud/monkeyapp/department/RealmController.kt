package ch.c3smonkey.cloud.monkeyapp.department

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException


/*****
 * Controller
 */
@RestController
@RequestMapping(value = ["/api/realm"])
class RealmController(var realmService: RealmService, var realmResourceAssembler: RealmResourceAssembler) {

    @GetMapping(value = ["/list"])
    fun getAll(): ResponseEntity<RealmResources> {
        val entities = realmService.findAll()
        if (entities.isEmpty()) return ResponseEntity(HttpStatus.NO_CONTENT)
        return ResponseEntity<RealmResources>(RealmResources(realmResourceAssembler.toResources(entities)), HttpStatus.OK)
    }

    @GetMapping(value = ["/{id}"])
    fun getById(@PathVariable id: String): ResponseEntity<RealmResource> {
        val entity = realmService.findById(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Realm Not Found")
        return ResponseEntity<RealmResource>(realmResourceAssembler.toResource(entity), HttpStatus.OK)

    }

    @PostMapping
    fun save(@RequestBody dep: Realm): ResponseEntity<RealmResource> {
        if (realmService.exists(dep)) {
            return ResponseEntity<RealmResource>(HttpStatus.CONFLICT)
        } else {
            val entity = realmService.save(dep)
            return ResponseEntity<RealmResource>(realmResourceAssembler.toResource(entity), HttpStatus.CREATED)
        }
    }

    @PutMapping(value = ["/{id}"])
    fun update(@PathVariable id: String, @RequestBody realm: Realm): ResponseEntity<RealmResource> {
        val entity = realmService.findById(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Realm Not Found")
        entity.name = realm.name
        entity.description = realm.description

        val updatedEntity = realmService.update(entity)
        return ResponseEntity<RealmResource>(realmResourceAssembler.toResource(updatedEntity), HttpStatus.OK)
    }

    @DeleteMapping(value = ["/{id}"])
    fun delete(@PathVariable id: String): ResponseEntity<Any> = ResponseEntity<Any>(realmService.deleteById(id), HttpStatus.NO_CONTENT)

    @DeleteMapping(value = [])
    fun deleteAll(): ResponseEntity<Any> = ResponseEntity<Any>(realmService.deleteAll(), HttpStatus.NO_CONTENT)

}
