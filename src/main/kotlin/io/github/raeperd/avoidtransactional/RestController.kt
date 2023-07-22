package io.github.raeperd.avoidtransactional

import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RestController

@RestController
class RestController(
    private val repository: UserRepository
) {
    @PostMapping("/users")
    fun postUsers(@RequestBody request: UserCreateRequest): ResponseEntity<UserDTO> {
        return ResponseEntity.status(201).body(repository.save(User(request.name)).toDTO())
    }

    @GetMapping("/users/{id}")
    fun getUsers(@PathVariable id: Long): ResponseEntity<UserDTO> {
        return ResponseEntity.ofNullable(
            repository.findByIdOrNull(id)?.toDTO()
        )
    }

    @PostMapping("/users/{id}/articles")
    fun postArticles(@PathVariable id: Long, @RequestBody request: ArticleCreateRequest): ResponseEntity<ArticleDTO> {
        val user = repository.findByIdOrNull(id)
            ?: return ResponseEntity.notFound().build()
        val article = Article(request.title, request.contents)
        if (user.articles.add(article)) {
            return ResponseEntity.status(201).body(article.toDTO())
        }
        return ResponseEntity.badRequest().build()
    }

}

data class UserCreateRequest(val name: String)

data class ArticleCreateRequest(val title: String, val contents: String)

fun User.toDTO() = UserDTO(this)

data class UserDTO(val id: Long, val name: String, val articles: List<ArticleDTO>) {
    constructor(user: User) : this(user.id ?: 0, user.name, user.articles.map { ArticleDTO(it) })
}

fun Article.toDTO() = ArticleDTO(this)

data class ArticleDTO(val title: String, val contents: String) {
    constructor(article: Article) : this(article.title, article.contents)
}