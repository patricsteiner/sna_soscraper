package ch.fhnw.sna.soscraper.infrastructure.repositories

import ch.fhnw.sna.soscraper.domain.TagData
import ch.fhnw.sna.soscraper.domain.TagRepository
import org.springframework.stereotype.Repository

@Repository
class InMemoryTagRepository : TagRepository {

    private val data = HashMap<String, TagData>()

    override fun save(tag: String, views: Long, isAnswered: Boolean, bounty: Int, weekDay: Int, weekEnd: Int): TagData {
        if (!data.containsKey(tag)) {
            data[tag] = TagData(data.size, 1, views, if (isAnswered) 1 else 0, bounty, weekDay, weekEnd)
        } else {
            data[tag]!!.occurrence++
            data[tag]!!.views += views
            data[tag]!!.answered += if (isAnswered) 1 else 0
            data[tag]!!.bounty += bounty
            data[tag]!!.weekEnd += weekEnd
            data[tag]!!.weekDay += weekDay
        }

        return data[tag]!!
    }

    override fun find(tag: String): TagData? {
        return data[tag]
    }

    override fun findAll(): Map<String, TagData> {
        return data
    }

    override fun clear() {
        data.clear()
    }

}