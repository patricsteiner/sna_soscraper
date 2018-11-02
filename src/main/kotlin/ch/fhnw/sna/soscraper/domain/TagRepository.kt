package ch.fhnw.sna.soscraper.domain

interface TagRepository {

    fun clear()
    fun save(tag: String, views: Long, isAnswered: Boolean, bounty: Int) : TagData
    fun find(tag: String): TagData?
    fun findAll() : Map<String, TagData>

}

data class TagData(val id: Int, var occurrence: Long, var views: Long, var answered: Long, var bounty: Int)
