package io.github.raeperd.avoidtransactional

import jakarta.persistence.*
import jakarta.persistence.CascadeType.ALL
import jakarta.persistence.FetchType.LAZY
import jakarta.persistence.GenerationType.IDENTITY
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Entity
@Table(name = "users")
class User(
    val name: String,
    @OneToMany(fetch = LAZY, cascade = [ALL], orphanRemoval = true)
    val articles: MutableList<Article> = mutableListOf(),
) {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    val id: Long = 0
}

@Entity
@Table(name = "articles")
class Article(
    @Column val title: String,
    @Column val contents: String
) {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private val id: Long = 0
}

@Repository
interface UserRepository : JpaRepository<User, Long>
