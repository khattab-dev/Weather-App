package unilever.it.org.domain.usecases

import unilever.it.org.domain.repositories.SearchRepository
import javax.inject.Inject

class GetRecentSearchUseCase @Inject constructor(private val searchRepository: SearchRepository) {
    operator fun invoke() = searchRepository.getLatestSearch()
}