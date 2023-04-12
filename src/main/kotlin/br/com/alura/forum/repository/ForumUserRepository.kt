package br.com.alura.forum.repository

import br.com.alura.forum.model.ForumUser
import org.springframework.data.jpa.repository.JpaRepository

interface ForumUserRepository: JpaRepository<ForumUser, Long> {
}