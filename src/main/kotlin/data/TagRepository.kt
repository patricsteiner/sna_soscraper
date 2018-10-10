package data

/**
 * Each tag is unique by name and has an index/id
 */
class TagRepository {

    private val data = HashMap<String, Int>()

    fun save(tag: String): Int {
        var index : Int
        if (!data.containsKey(tag)) {
            data[tag] = data.size
        }
        return data[tag]!!
    }

    fun find(tag: String): Int? {
        return data[tag]
    }

    fun findAll(): HashMap<String, Int> {
        return data
    }

}