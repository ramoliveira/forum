package br.com.alura.forum.service

import br.com.alura.forum.dto.NewTopicForm
import br.com.alura.forum.dto.TopicPerCategoryDTO
import br.com.alura.forum.dto.TopicView
import br.com.alura.forum.dto.UpdateTopicForm
import br.com.alura.forum.exception.NotFoundException
import br.com.alura.forum.mapper.TopicFormMapper
import br.com.alura.forum.mapper.TopicViewMapper
import br.com.alura.forum.repository.TopicRepository
import jakarta.persistence.EntityManager
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class TopicService(
    private val repository: TopicRepository,
    private val topicViewMapper: TopicViewMapper,
    private val topicFormMapper: TopicFormMapper,
    private val notFoundMessage: String = "Tópico não encontrado"
) {

    fun list(
        courseName: String?,
        paging: Pageable
    ): Page<TopicView> {
        val topics = if (courseName == null) {
            repository.findAll(paging)
        } else {
            repository.findByCourseName(courseName, paging)
        }
        return topics.map { t ->
                topicViewMapper.map(t)
            }
    }

    fun fetchById(id: Long): TopicView {
        val topic = repository.findById(id)
            .orElseThrow {
                NotFoundException(notFoundMessage)
            }

        return topicViewMapper.map(topic)
    }

    fun register(form: NewTopicForm): TopicView {
        val topic = topicFormMapper.map(form)
        repository.save(topic)
        return topicViewMapper.map(topic)
    }

    fun update(form: UpdateTopicForm): TopicView {
        var topic = repository.findById(form.id)
            .orElseThrow {
                NotFoundException(notFoundMessage)
            }

        topic.title = form.title
        topic.message = form.message

        return topicViewMapper.map(topic)
    }

    fun delete(id: Long) {
        repository.deleteById(id)
    }

    fun report(): List<TopicPerCategoryDTO> {
        return repository.report()
    }
}