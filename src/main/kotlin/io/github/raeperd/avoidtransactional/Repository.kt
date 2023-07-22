package io.github.raeperd.avoidtransactional

import jakarta.annotation.Generated
import jakarta.persistence.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Entity
@Table(name = "users")
class User(
    val name: String,
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    val articles: MutableList<Article> = mutableListOf(),
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}

@Entity
@Table(name = "articles")
class Article(
    @Column val title: String,
    @Column val contents: String
) {
    @Generated
    @Id
    private var id: Long = 0
}

@Repository
interface UserRepository : JpaRepository<User, Long>
