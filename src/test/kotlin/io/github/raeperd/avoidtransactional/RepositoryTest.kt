package io.github.raeperd.avoidtransactional

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class RepositoryTest(
    @Autowired private val repository: UserRepository
) {
    @Test
    fun `when save user expect to be saved`() {
        val username = "test-jpa-user"
        val userSaved = repository.save(User(username))

        assertThat(userSaved.id).isNotEqualTo(0)
        assertThat(userSaved.name).isEqualTo(username)
    }
}