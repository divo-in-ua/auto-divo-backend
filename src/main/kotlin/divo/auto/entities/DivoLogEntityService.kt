package divo.auto.entities

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Service

interface DivoLogEntityRepository: MongoRepository<DivoLogEntity, String>

@Service
class DivoLogEntityService(private val repository: DivoLogEntityRepository) {
    fun save(logEntity: DivoLogEntity): String? {
        val savedEntity = repository.save(logEntity)
        return savedEntity.id
    }

    fun findById(id: String): DivoLogEntity? {
        return repository.findById(id).orElse(null)
    }
}