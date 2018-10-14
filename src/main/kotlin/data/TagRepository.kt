package data

/**
 * Each tag is unique by name and has an index/id
 */
class TagRepository {

    private val data = HashMap<String, TagData>()

    fun save(tag: String, views: Long, isAnswered: Boolean, bounty: Int): TagData {
        if (!data.containsKey(tag)) {
            data[tag] = TagData(data.size, 1, views, if (isAnswered) 1 else 0, bounty)
        } else {
            data[tag]!!.occurence++
            data[tag]!!.views += views
            data[tag]!!.answered += if (isAnswered) 1 else 0
            data[tag]!!.bounty += bounty
        }

        return data[tag]!!
    }

    fun find(tag: String): TagData? {
        return data[tag]
    }

    fun findAll(): HashMap<String, TagData> {
        return data
    }

    data class TagData(val id: Int, var occurence: Long, var views: Long, var answered: Long, var bounty: Int)

}