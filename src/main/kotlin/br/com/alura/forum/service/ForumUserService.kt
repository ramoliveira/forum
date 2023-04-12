package br.com.alura.forum.service

import br.com.alura.forum.model.ForumUser
import br.com.alura.forum.repository.ForumUserRepository
import org.springframework.stereotype.Service

@Service
class ForumUserService(private val repository: ForumUserRepository) {
    fun fetchById(id: Long): ForumUser {
        return repository.getReferenceById(id)
    }
}
